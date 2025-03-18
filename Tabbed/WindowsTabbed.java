package Tabbed;

import Main.Main;
import Particle.ParticlePanel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import raven.drawer.Drawer;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.util.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author blesDev
 */

public class WindowsTabbed {

    private ResultSet rs;
    private Connection con;
    private PreparedStatement ps;
    

    private static final int POLLING_DELAY_MS = 500;
    private static final int MAX_TABS = 100;

    private static WindowsTabbed instance;
    private JMenuBar menuBar;
    private PanelTabbed panelTabbed;
    private JPanel body;
    private TabbedForm temp;

    public WindowsTabbed() {}
    
    public static WindowsTabbed getInstance() {
        if (instance == null) {
            instance = new WindowsTabbed();
        }
        return instance;
    }

    public void install(JFrame frame, JPanel body) {
        this.body = body;
        createMenuBar();
        frame.setJMenuBar(menuBar);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.putClientProperty(FlatClientProperties.STYLE, "borderColor:$TitlePane.background;border:0,0,0,0");
        panelTabbed = new PanelTabbed();
        panelTabbed.putClientProperty(FlatClientProperties.STYLE, "background:$TitlePane.background");

        menuBar.add(createDrawerButton());
        menuBar.add(createScroll(panelTabbed));
    }

    public void removeAllTabbed() {
        panelTabbed.removeAll();
        panelTabbed.repaint();
        panelTabbed.revalidate();
        body.removeAll();
        body.repaint();
        body.revalidate();
        initializeDesign();
    }

    public void showTabbed(boolean show) {
        menuBar.setVisible(show);
    }

    private JButton createDrawerButton() {
        JButton drawerButton = new JButton(new FlatSVGIcon("SvgIcon/menu.svg", 0.9f));
        drawerButton.addActionListener(e -> Drawer.getInstance().showDrawer());
        drawerButton.putClientProperty(FlatClientProperties.STYLE, "borderWidth:0;focusWidth:0;background:null;arc:5");
        drawerButton.setToolTipText("Menu");
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(1008);
        return drawerButton;
    }

    private JScrollPane createScroll(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, "width:0");
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.putClientProperty(FlatClientProperties.STYLE, "border:0,0,0,0");
        return scrollPane;
    }

    public boolean addTab(String title, TabbedForm component) {
        if (panelTabbed.getComponentCount() >= MAX_TABS) {
            return false;
        }

        TabbedItem item = new TabbedItem(title, component);
        ActionListener actionListener = e -> {
            showForm(item.getComponent());
            Drawer.getInstance().closeDrawer();
        };
        item.addActionListener(actionListener);

        panelTabbed.addTab(item);
        showForm(component);
        item.setSelected(true);
        return true;
    }

    public void showForm(TabbedForm component) {
        body.removeAll();
        body.add(component);
        body.repaint();
        body.revalidate();
        panelTabbed.repaint();
        panelTabbed.revalidate();
        temp = component;
    }

    public void initializeDesign() {
        JLayeredPane layeredPane = new JLayeredPane();
        body.setLayout(new BorderLayout());
        body.add(layeredPane, BorderLayout.CENTER);

        bgImage(layeredPane);
        totalStudent(layeredPane);
        strandSexChart(layeredPane);
        caseChart(layeredPane);
        scholarChart(layeredPane);

        body.revalidate();
        body.repaint();
    }

    private void bgImage(JLayeredPane layeredPane) {
        JLabel bgLabel = new JLabel();
        bgLabel.setIcon(new ImageIcon(getClass().getResource("/Image/stums.png")));
        bgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bgLabel.setBounds(0, 0, body.getWidth(), body.getHeight());
        layeredPane.add(bgLabel, JLayeredPane.DEFAULT_LAYER);
    }

    private void totalStudent(JLayeredPane layeredPane) {
        JPanel totalStudent = new JPanel(new BorderLayout());
        totalStudent.setBounds(31, 38, 255, 105);
        totalStudent.setOpaque(false);
        totalStudent.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel studentLabel = new JLabel("Total of Students", SwingConstants.CENTER);
        studentLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        totalStudent.add(studentLabel, BorderLayout.NORTH);

        JLabel totalNumber = new JLabel("0", SwingConstants.CENTER);
        totalNumber.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 35));
        totalStudent.add(totalNumber, BorderLayout.CENTER);

        layeredPane.add(totalStudent, JLayeredPane.MODAL_LAYER);
        startCountingThread(totalNumber);
    }

    private void startCountingThread(JLabel totalNumberLabel) {
        new Thread(() -> {
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
                 Statement stmt = con.createStatement()) {

                while (!Thread.currentThread().isInterrupted()) {
                    try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS studentCount FROM studentt")) {
                        if (rs.next()) {
                            int count = rs.getInt("studentCount");
                            SwingUtilities.invokeLater(() -> totalNumberLabel.setText(String.valueOf(count)));
                        }
                    }
                    Thread.sleep(POLLING_DELAY_MS);
                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //StrandSexChart
    private Font labelFont = new Font("Segoe UI Semibold", Font.BOLD, 14);
    private Map<String, Color[]> strandGradientColors = Map.of(
            "ABM", new Color[]{new Color(255, 215, 0), new Color(255, 140, 0)},
            "STEM", new Color[]{new Color(0, 100, 0), new Color(57, 255, 20)},
            "ICT", new Color[]{new Color(0, 0, 255), new Color(135, 206, 250)},
            "HE", new Color[]{new Color(128, 0, 128), new Color(255, 0, 255)},
            "ADAS", new Color[]{new Color(255, 0, 0), new Color(255, 69, 0)},
            "GAS", new Color[]{new Color(210, 105, 30), new Color(218, 165, 32)}
    );

    private Map<String, Color[]> sexGradientColors = Map.of(
             "Male", new Color[]{new Color(135, 206, 250), new Color(173, 216, 230)},
            "Female", new Color[]{new Color(255, 182, 193), new Color(255, 105, 180)}

    );

    private String selectedSchoolYear = "All";
    private String selectedSemester = "All";

    private void strandSexChart(JLayeredPane layeredPane) {
        JPanel strandSexChart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!selectedSchoolYear.isEmpty() && !selectedSemester.isEmpty()) {
                    drawStrandAndSexBars(g, selectedSchoolYear, selectedSemester);
                }
            }
        };

        strandSexChart.setLayout(null);
        strandSexChart.setBounds(31, 153, 790, 450);
        strandSexChart.setOpaque(false);
        strandSexChart.setBorder(new javax.swing.border.LineBorder(java.awt.Color.BLACK, 2));

        JLabel schoolYearLabel = new JLabel("School Year:");
        schoolYearLabel.setBounds(10, 10, 100, 30);
        strandSexChart.add(schoolYearLabel);

        JComboBox<String> schoolYearComboBox = new JComboBox<>(addAllOption(fetchDistinctValuesSorted("SchoolYr")));
        schoolYearComboBox.setBounds(90, 10, 150, 30);
        strandSexChart.add(schoolYearComboBox);

        JLabel semesterLabel = new JLabel("Semester:");
        semesterLabel.setBounds(265, 10, 100, 30);
        strandSexChart.add(semesterLabel);

        JComboBox<String> semesterComboBox = new JComboBox<>(addAllOption(fetchDistinctValuesSorted("Semester")));
        semesterComboBox.setBounds(335, 10, 150, 30);
        strandSexChart.add(semesterComboBox);

        layeredPane.add(strandSexChart, JLayeredPane.MODAL_LAYER);

        schoolYearComboBox.addActionListener(e -> {
            selectedSchoolYear = (String) schoolYearComboBox.getSelectedItem();
            strandSexChart.repaint();
        });

        semesterComboBox.addActionListener(e -> {
            selectedSemester = (String) semesterComboBox.getSelectedItem();
            strandSexChart.repaint();
        });

        strandSexChart.revalidate();
        strandSexChart.repaint();
    }

    private void drawStrandAndSexBars(Graphics g, String schoolYear, String semester) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(labelFont);

        Map<String, Integer> combinedData = fetchStrandDataAndSex(schoolYear, semester);

        Map<String, Integer> strandData = new java.util.TreeMap<>();
        Map<String, Integer> sexData = new java.util.TreeMap<>();

        for (Map.Entry<String, Integer> entry : combinedData.entrySet()) {
            if (strandGradientColors.containsKey(entry.getKey())) {
                strandData.put(entry.getKey(), entry.getValue());
            } else if (sexGradientColors.containsKey(entry.getKey())) {
                sexData.put(entry.getKey(), entry.getValue());
            }
        }

        if (strandData.isEmpty() && sexData.isEmpty()) return;

        Map<String, Integer> orderedStrandData = new java.util.LinkedHashMap<>();
        java.util.List<String> strandOrder = java.util.Arrays.asList("STEM", "GAS", "ABM", "ICT", "HE", "ADAS");
        for (String key : strandOrder) {
            if (strandData.containsKey(key)) {
                orderedStrandData.put(key, strandData.get(key));
            }
        }

        Map<String, Integer> orderedSexData = new java.util.LinkedHashMap<>();
        java.util.List<String> sexOrder = java.util.Arrays.asList("Male", "Female");
        for (String key : sexOrder) {
            if (sexData.containsKey(key)) {
                orderedSexData.put(key, sexData.get(key));
            }
        }

        int chartWidth = 800;
        int chartHeight = 360;
        int xOffset = 30;
        int yOffset = 60;

        int totalCategories = orderedStrandData.size() + orderedSexData.size();
        int barWidth = Math.max((chartWidth - xOffset) / (totalCategories + 2) - 15, 35);
        int maxCount = Math.max(
                orderedStrandData.values().stream().max(Integer::compare).orElse(1),
                orderedSexData.values().stream().max(Integer::compare).orElse(1)
        );
        int scale = (chartHeight - 50) / Math.max(maxCount, 1);

        drawBars(g2, orderedStrandData, barWidth, chartHeight, scale, xOffset, yOffset, strandGradientColors);
        int sexXOffset = xOffset + (orderedStrandData.size() * (barWidth + 35));
        drawBars(g2, orderedSexData, barWidth, chartHeight, scale, sexXOffset, yOffset, sexGradientColors);
    }

    private void drawBars(Graphics2D g2, Map<String, Integer> data, int barWidth, int chartHeight, int scale, int xOffset, int yOffset, Map<String, Color[]> gradientColors) {
        int x = xOffset;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            int count = entry.getValue();
            int barHeight = count * scale;

            Color[] colors = gradientColors.getOrDefault(key, new Color[]{Color.GRAY, Color.LIGHT_GRAY});
            GradientPaint gradient = new GradientPaint(
                    x, yOffset + chartHeight - barHeight, colors[0],
                    x, yOffset + chartHeight, colors[1]
            );
            g2.setPaint(gradient);

            g2.fillRoundRect(x, yOffset + chartHeight - barHeight, barWidth, barHeight, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x, yOffset + chartHeight - barHeight, barWidth, barHeight, 15, 15);
            g2.drawString(key, x + (barWidth / 4), yOffset + chartHeight + 20);
            g2.drawString(String.valueOf(count), x + (barWidth / 4), yOffset + chartHeight - barHeight - 5);

            x += barWidth + 25;
        }
    }

    private Map<String, Integer> fetchStrandDataAndSex(String schoolYear, String semester) {
        Map<String, Integer> data = new java.util.LinkedHashMap<>();
        String strandQuery = "SELECT strand, COUNT(*) AS count FROM studentt WHERE (? = 'All' OR SchoolYr = ?) AND (? = 'All' OR Semester = ?) GROUP BY strand";
        String sexQuery = "SELECT sex, COUNT(*) AS count FROM studentt WHERE (? = 'All' OR SchoolYr = ?) AND (? = 'All' OR Semester = ?) GROUP BY sex";

        try (java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
             java.sql.PreparedStatement strandStmt = con.prepareStatement(strandQuery);
             java.sql.PreparedStatement sexStmt = con.prepareStatement(sexQuery)) {

            strandStmt.setString(1, schoolYear);
            strandStmt.setString(2, schoolYear);
            strandStmt.setString(3, semester);
            strandStmt.setString(4, semester);

            try (java.sql.ResultSet rs = strandStmt.executeQuery()) {
                while (rs.next()) {
                    data.put(rs.getString("strand"), rs.getInt("count"));
                }
            }

            sexStmt.setString(1, schoolYear);
            sexStmt.setString(2, schoolYear);
            sexStmt.setString(3, semester);
            sexStmt.setString(4, semester);

            try (java.sql.ResultSet rs = sexStmt.executeQuery()) {
                while (rs.next()) {
                    data.put(rs.getString("sex"), rs.getInt("count"));
                }
            }

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private String[] fetchDistinctValuesSorted(String column) {
        String query = "SELECT DISTINCT " + column + " FROM studentt ORDER BY " + column;
        java.util.List<String> values = new java.util.ArrayList<>();

        try (java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
             java.sql.Statement stmt = con.createStatement();
             java.sql.ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                values.add(rs.getString(column));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return values.toArray(new String[0]);
    }

    private String[] addAllOption(String[] values) {
        String[] updatedValues = new String[values.length + 1];
        updatedValues[0] = "All";
        System.arraycopy(values, 0, updatedValues, 1, values.length);
        return updatedValues;
    }

    
    //CaseChart
    private void caseChart(JLayeredPane layeredPane) {

        JPanel caseChart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                caseChart(g);
            }
        };

        caseChart.setBounds(832, 153, 407, 215);
        caseChart.setOpaque(false);
        caseChart.setBorder(new LineBorder(Color.BLACK, 2));

        layeredPane.add(caseChart, JLayeredPane.MODAL_LAYER);

        caseChart.revalidate();
        caseChart.repaint();
    }

    private void caseChart(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = 430;
        int panelHeight = 230;

        int studentsWithoutCases = 0;
        int studentsWithCases = 0;

        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
            Statement stmt = con.createStatement();
        ) {
            ResultSet rsNoCases = stmt.executeQuery("SELECT COUNT(*) FROM studentt WHERE `Case` IS NULL OR `Case` = '' OR `Case` = 'None'");
            if (rsNoCases.next()) {
                studentsWithoutCases = rsNoCases.getInt(1);
            }
            ResultSet rsWithCases = stmt.executeQuery("SELECT COUNT(*) FROM studentt WHERE `Case` IS NOT NULL AND `Case` != '' AND `Case` != 'None'");
            if (rsWithCases.next()) {
                studentsWithCases = rsWithCases.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] values = {studentsWithoutCases, studentsWithCases};
        int total = values[0] + values[1];
        if (total == 0) {
            g2.setColor(Color.BLACK);
            return;
        }

        int chartWidth = 150;
        int chartHeight = 150;
        int x = (panelWidth - chartWidth) / 2;
        int y = (panelHeight - chartHeight) / 2;

        int startAngle = 0;
        String[] labels = {"Student Without Case", "Student With Case"};

        int textXStart = x - 125;
        int textYStart = y - 30;
        int verticalGap = 20;

        g2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));

        for (int i = 0; i < values.length; i++) {
            GradientPaint boxGradient = (i == 0)
                ? new GradientPaint(x, y, new Color(135, 206, 250), x + chartWidth, y + chartHeight, new Color(70, 130, 180))
                : new GradientPaint(x, y, new Color(216, 191, 216), x + chartWidth, y + chartHeight, new Color(123, 104, 238));

            g2.setPaint(boxGradient);
            g2.fillRect(textXStart, textYStart + (i * verticalGap), 10, 10);

            g2.setColor(Color.BLACK);
            g2.drawString(labels[i] + ": " + values[i], textXStart + 15, textYStart + 10 + (i * verticalGap));
        }
        for (int i = 0; i < values.length; i++) {
            int arcAngle = (int) Math.round((values[i] / (double) total) * 360);

            GradientPaint gradient = (i == 0)
                ? new GradientPaint(x, y, new Color(135, 206, 250), x + chartWidth, y + chartHeight, new Color(70, 130, 180))
                : new GradientPaint(x, y, new Color(216, 191, 216), x + chartWidth, y + chartHeight, new Color(123, 104, 238));

            g2.setPaint(gradient);
            g2.fillArc(x, y, chartWidth, chartHeight, startAngle, arcAngle);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));

            Shape sliceBorder = new Arc2D.Double(
                x, y, chartWidth, chartHeight, startAngle, arcAngle, Arc2D.PIE
            );
            g2.draw(sliceBorder);

            startAngle += arcAngle;
        }


        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, chartWidth, chartHeight);
    }

    //ScholarChart
    private void scholarChart(JLayeredPane layeredPane) {

        JPanel scholarChart = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                scholarChart(g);
            }
        };
        scholarChart.setBounds(832, 378, 406, 225);
        scholarChart.setOpaque(false);
        scholarChart.setBorder(new LineBorder(Color.BLACK, 2));
        layeredPane.add(scholarChart, JLayeredPane.MODAL_LAYER);

        scholarChart.revalidate();
        scholarChart.repaint();
    }

    private void scholarChart(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = 430;
        int panelHeight = 230;

        int studentsWithMSD = 0;
        int studentsWith4PS = 0;
        int studentsWithBarangay = 0;

        try (
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
            Statement stmt = con.createStatement();
        ) {
            ResultSet rsMSD = stmt.executeQuery("SELECT COUNT(*) FROM studentt WHERE `Scholar` = 'MSD'");
            if (rsMSD.next()) {
                studentsWithMSD = rsMSD.getInt(1);
            }
            ResultSet rs4PS = stmt.executeQuery("SELECT COUNT(*) FROM studentt WHERE `Scholar` = '4Ps'");
            if (rs4PS.next()) {
                studentsWith4PS = rs4PS.getInt(1);
            }
            ResultSet rsBarangay = stmt.executeQuery("SELECT COUNT(*) FROM studentt WHERE `Scholar` = 'Barangay'");
            if (rsBarangay.next()) {
                studentsWithBarangay = rsBarangay.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] values = {studentsWithMSD, studentsWith4PS, studentsWithBarangay};
        int total = values[0] + values[1] + values[2];
        if (total == 0) {
            g2.setColor(Color.BLACK);
            return;
        }

        int chartWidth = 150;
        int chartHeight = 150;
        int x = (panelWidth - chartWidth) / 2;
        int y = (panelHeight - chartHeight) / 2;

        int startAngle = 0;
        String[] labels = {"MSD", "4PS", "Barangay"};
        
        int textXStart = x - 125;
        int textYStart = y - 30;
        int verticalGap = 20;

        g2.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));

        for (int i = 0; i < values.length; i++) {
            GradientPaint boxGradient = switch (i) {
                case 0 -> new GradientPaint(x, y, new Color(85, 107, 47), x + 20, y + 20, new Color(107, 142, 35));
                case 1 -> new GradientPaint(x, y, new Color(255, 140, 0), x + 20, y + 20, new Color(205, 92, 92));
                case 2 -> new GradientPaint(x, y, new Color(72, 61, 139), x + 20, y + 20, new Color(123, 104, 238));
                default -> new GradientPaint(x, y, Color.GRAY, x + 20, y + 20, Color.DARK_GRAY);
            };

            g2.setPaint(boxGradient);
            g2.fillRect(textXStart, textYStart + (i * verticalGap), 10, 10);

            g2.setColor(Color.BLACK);
            g2.drawString(labels[i] + ": " + values[i], textXStart + 15, textYStart + 10 + (i * verticalGap));
        }

        for (int i = 0; i < values.length; i++) {
            int arcAngle = (int) Math.round((values[i] / (double) total) * 360);

            GradientPaint boxGradient = switch (i) {
                case 0 -> new GradientPaint(x, y, new Color(85, 107, 47), x + 20, y + 20, new Color(107, 142, 35));
                case 1 -> new GradientPaint(x, y, new Color(255, 140, 0), x + 20, y + 20, new Color(205, 92, 92));
                case 2 -> new GradientPaint(x, y, new Color(72, 61, 139), x + 20, y + 20, new Color(123, 104, 238));
                default -> new GradientPaint(x, y, Color.GRAY, x + 20, y + 20, Color.DARK_GRAY);

            };

            g2.setPaint(boxGradient);
            g2.fillArc(x, y, chartWidth, chartHeight, startAngle, arcAngle);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            Shape sliceBorder = new Arc2D.Double(
                x, y, chartWidth, chartHeight, startAngle, arcAngle, Arc2D.PIE
            );
            g2.draw(sliceBorder);

            startAngle += arcAngle;
        }
        
        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, chartWidth, chartHeight);
    }
}