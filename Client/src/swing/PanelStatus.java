package swing;

import java.awt.event.ActionListener;

public class PanelStatus extends javax.swing.JPanel {

    public PanelStatus() {
        initComponents();
    }

    public void showStatus(int values) {
        pro.setValue(values);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pro = new javax.swing.JProgressBar();
        cmd = new javax.swing.JButton();

        pro.setStringPainted(true);

        cmd.setContentAreaFilled(false);
        cmd.setName("R"); // NOI18N
        cmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmd)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cmd, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdActionPerformed
        if (cmd.getName().equals("R")) {
            cmd.setContentAreaFilled(false); 
            cmd.setName("P");
        } else if (cmd.getName().equals("P")) {
            cmd.setContentAreaFilled(false); 
            cmd.setName("R");
        }
        event.actionPerformed(evt);
    }//GEN-LAST:event_cmdActionPerformed

    public void done() {
        cmd.setContentAreaFilled(false); 
        cmd.setName("D");
    }

    public boolean isPause() {
        return cmd.getName().equals("P");
    }

    private ActionListener event;

    public void addEvent(ActionListener event) {
        this.event = event;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmd;
    private javax.swing.JProgressBar pro;
    // End of variables declaration//GEN-END:variables
}
