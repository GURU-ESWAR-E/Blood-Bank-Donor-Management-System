import ui.LoginFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main.java
 *
 * The main entry point for the Blood Bank & Donor Management System.
 *
 * For beginners:
 * - Every Java desktop application begins execution in the 'main' method.
 * - Swing GUI applications should always be launched on the Event Dispatch Thread (EDT)
 *   using SwingUtilities.invokeLater() to ensure thread safety and smooth UI rendering.
 */
public class Main {

    public static void main(String[] args) {
        // Set native system look and feel for modern UI appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Using default Swing Look and Feel: " + e.getMessage());
        }

        // Launch the initial Login window on the Swing Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
