package Login;

import javax.swing.*;
import Main.Main;
import Tabbed.WindowsTabbed;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.miginfocom.swing.MigLayout;
import java.util.prefs.Preferences;

/**
 *
 * @author blesDev
 */
public class Login extends JPanel {

    ResultSet rs;
    Connection con;
    PreparedStatement ps;
    
    
    Preferences prefs = Preferences.userRoot().node("LoginPrefs");
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;

    public Login() {
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill,insets -1", "[center]", "[center]"));

        JLabel background = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/Image/StuMsBG.png"));
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        background.setLayout(new MigLayout("fill,insets 0", "[center]", "[center]"));
        background.setPreferredSize(new Dimension(1800, 700));
        add(background);

        txtUsername = new JTextField();
        txtUsername.setColumns(20);
        txtUsername.setPreferredSize(new Dimension(500, 30));

        txtPassword = new JPasswordField();
        txtPassword.setColumns(20);
        txtPassword.setPreferredSize(new Dimension(500, 30));

        chRememberMe = new JCheckBox("Remember me");

        cmdLogin = new JButton("Login");

        JPanel panel = new JPanel(new MigLayout("wrap,fillx,insets 35 40 25 40", "fill,250:280")) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
            }
        };

        panel.setBorder(new AbstractBorder() {
            @Override
            public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(5));
                g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, 20, 20));
            }
        });

        panel.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:20;"
                + "[light]background:darken(@background,6%);"
                + "[dark]background:lighten(@background,22%)");

        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "[light]background:darken(@background,4%);"
                + "[dark]background:lighten(@background,15%);"
                + "margin:4,6,4,6;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        cmdLogin.addActionListener((e) -> {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/stums", "root", "");
                ps = con.prepareStatement("select * from login where username=? and password=?");
                ps.setString(1, txtUsername.getText());
                ps.setString(2, new String(txtPassword.getPassword()));
                rs = ps.executeQuery();

                if (rs.next()) {
                    if (chRememberMe.isSelected()) {
                        prefs.put("username", txtUsername.getText());
                        prefs.put("password", new String(txtPassword.getPassword()));
                        prefs.putBoolean("rememberMe", true);
                    } else {
                        prefs.remove("username");
                        prefs.remove("password");
                        prefs.putBoolean("rememberMe", false);
                    }
                    if (Main.main != null) {
                        Main.main.showMainForm();
                    } else {
                        
                    }
                } 
//                else {
//                    JOptionPane.showMessageDialog(this, "Incorrect Username or Password.", "Invalid Log-In",
//                            JOptionPane.WARNING_MESSAGE);
//                }
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        Font defaultFont = new Font("Segoe UI Semibold", Font.PLAIN, 13);
        UIManager.put("defaultFont", defaultFont);

        JLabel lbTitle = new JLabel("STUMS", JLabel.CENTER);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font: bold 30");

        JLabel lblUsername = new JLabel("Username");
        setLabelFont(lblUsername, new Font("Segoe UI Semibold", Font.PLAIN, 14));
 
        JLabel lblPassword = new JLabel("Password");
        setLabelFont(lblPassword, new Font("Segoe UI Semibold", Font.PLAIN, 14));

        panel.add(lbTitle);
        panel.add(lblUsername, "gapy 15");
        panel.add(txtUsername, "growx, h 30!");
        panel.add(lblPassword, "gapy 5");
        panel.add(txtPassword, "growx, h 30!");
        panel.add(chRememberMe, "grow 0");
        panel.add(cmdLogin, "gapy 15");

        background.add(panel);

        checkRememberMe();
    }

    private void checkRememberMe() {
        if (prefs.getBoolean("rememberMe", false)) {
            txtUsername.setText(prefs.get("username", ""));
            txtPassword.setText(prefs.get("password", ""));
            chRememberMe.setSelected(true);
            cmdLogin.doClick(); // Automatically log in
        }
    }

    private void setLabelFont(JLabel label, Font font) {
        label.setFont(font);
        label.setForeground(Color.BLACK);
    }
}