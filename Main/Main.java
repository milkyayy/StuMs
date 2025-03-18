package Main;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import raven.drawer.Drawer;
import Drawer.DrawerBuilder;
import Login.Login;
import Particle.ParticlePanel;
import raven.popup.GlassPanePopup;
import Tabbed.WindowsTabbed;
import raven.toast.Notifications;
import javax.swing.UIManager;

/**
 *
 * @author blesDev
 */

public class Main extends javax.swing.JFrame {

    public static Main main;
    private Login loginForm;
    private ParticlePanel particlePanel;
    
    public Main() {
        initComponents();
        init();
    }

    
    private void init() {
        particlePanel = new ParticlePanel();
        this.setResizable(false);

//        getLayeredPane().add(particlePanel, javax.swing.JLayeredPane.DRAG_LAYER);
//        particlePanel.setBounds(0, 0, getWidth(), getHeight());

        GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        DrawerBuilder myDrawerBuilder = new DrawerBuilder();
        Drawer.getInstance().setDrawerBuilder(myDrawerBuilder);
        WindowsTabbed.getInstance().install(this, body);

        login();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                //particlePanel.setSize(getWidth(), getHeight());
            }
        });
    }

    
    public void login() {
        if (loginForm == null) {
            loginForm = new Login();
        }
        WindowsTabbed.getInstance().showTabbed(false);
        loginForm.applyComponentOrientation(getComponentOrientation());
        setContentPane(loginForm);
        revalidate();
        repaint();
    }

    public void showMainForm() {
        WindowsTabbed.getInstance().showTabbed(true);
        WindowsTabbed.getInstance().removeAllTabbed();
        setContentPane(body);
        revalidate();
        repaint();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, 1270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );

        body.getAccessibleContext().setAccessibleName("");
        body.getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("Themes");
        UIManager.put("defaultFont", new Font("Segoe UI Semibold", java.awt.Font.BOLD, 13));
        FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            main = new Main();
            main.setVisible(true);
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    // End of variables declaration//GEN-END:variables
}
