package hum.main;

import hum.main.emp.EmpDashboard;
import java.awt.EventQueue;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException e) {
            System.out.println(e);
        }

        EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
//            new Dashboard().setVisible(true);
//            new EmpDashboard().setVisible(true);
        });
    }
}
