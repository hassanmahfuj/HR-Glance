package hum.util;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

/**
 *
 * @author HASSAN MAHFUJ
 */
public class PanelSetter {

    private JPanel root;
    
    public PanelSetter(JPanel root) {
        this.root = root;
    }
    
    public void set(JPanel child) {
        root.removeAll();
        GroupLayout layout = new GroupLayout(root);
        root.setLayout(layout);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(child, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(child, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
        );
        System.gc();
    }
}
