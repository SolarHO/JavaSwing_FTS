package swing;

import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class PanelStatus_Item extends javax.swing.JLayeredPane {

    public PanelStatus_Item() {
        initComponents();
    }

    public void showStatus(int values) {
        pro.setValue(values);  //파일 업로드 진행상태표시 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pro = new javax.swing.JProgressBar();
        cmd = new javax.swing.JButton();

        setOpaque(true);

        pro.setStringPainted(true);

        cmd.setForeground(new java.awt.Color(255, 0, 0));
        cmd.setText("🔻");
        cmd.setContentAreaFilled(false);
        cmd.setMargin(new java.awt.Insets(2, 1, 2, 1));
        cmd.setName("S"); // NOI18N
        cmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdActionPerformed(evt);
            }
        });

        setLayer(pro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        setLayer(cmd, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(cmd, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmd, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdActionPerformed
            if (cmd.getName().equals("R")) {
            
            cmd.setName("P");
            eventPause.actionPerformed(evt); // 파일이 다 올라가기 전까진 R과 P 상태이다가 파일이 업로드가 되었을 때 S상태로 변경 이 때 다운로드 가능 
        } else if (cmd.getName().equals("P")) {
           ;
            cmd.setName("R");
            eventPause.actionPerformed(evt);
        } else if (cmd.getName().equals("S")) {
            eventSave.actionPerformed(evt);
        }
    }//GEN-LAST:event_cmdActionPerformed

    private ActionListener eventSave;
    private ActionListener eventPause;

    public void addEventSave(ActionListener eventSave) {
        this.eventSave = eventSave;  //액션리스너의 파일저장
    }

    public void addEvent(ActionListener event) {
        this.eventPause = event;
    }

    public void done() {
      
        cmd.setName("S"); // 내려받기가 끝나면 다시 파일저장이 가능한 상태로 바꿈 
    }

    public boolean isPause() {
        return cmd.getName().equals("P");
    }

    public void startFile() {
        cmd.setText("🔻"); 
        cmd.setName("R");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmd;
    private javax.swing.JProgressBar pro;
    // End of variables declaration//GEN-END:variables
}
