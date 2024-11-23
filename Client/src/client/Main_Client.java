/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import data.DataFileServer;
import data.DataReader;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;
import swing.CellEditor;
import swing.CellEditorFile;

/**
 *
 * @author RAVEN
 */
public class Main_Client extends javax.swing.JFrame {

    String IP = "localhost";  //ip와 로그인세션 값 저장 
    String loginSession = null; 
    public void setTxtNameText(String loginName){
        txtName.setText(loginName);
    }
    public void setSession(String loginName){
        this.loginSession = loginName;
    }
    public Main_Client() { 
        initComponents();
        
        model = (DefaultTableModel) table.getModel(); // 클라이언트와 파일 온 서버탭 테이블들 초기화 
        modelFile = (DefaultTableModel) tableFile.getModel();
        
        //셀 랜더러와 편집 설정 (CellEditor,CellEditorFile)
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() { 
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
                Object data = jtable.getValueAt(i, 0);
                if (data instanceof DataReader) {
                    DataReader reader = (DataReader) data;
                    Component c = reader.getStatus();
                    c.setBackground(com.getBackground());
                    return c;
                } else {
                    return com;
                }
            }
        });
        table.getColumnModel().getColumn(4).setCellEditor(new CellEditor()); 
        tableFile.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
                DataFileServer data = (DataFileServer) jtable.getValueAt(i, 0);
                Component c = data.getItem();
                c.setBackground(com.getBackground());
                return c;
            }
        });
        tableFile.getColumnModel().getColumn(4).setCellEditor(new CellEditorFile());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmdConnect = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnGoLogin = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtIPAddress = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableFile = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No", "File Name", "Size", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setPreferredWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        txtName.setEditable(false);
        txtName.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("Name");

        cmdConnect.setText("접속");
        cmdConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdConnectActionPerformed(evt);
            }
        });

        jButton1.setText("File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnGoLogin.setText("로그인");
        btnGoLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoLoginActionPerformed(evt);
            }
        });

        jLabel2.setText("IP :");

        txtIPAddress.setText("localhost");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGoLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdConnect))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(6, 661, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdConnect)
                    .addComponent(btnGoLogin)
                    .addComponent(jLabel2)
                    .addComponent(txtIPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Client", jPanel1);

        tableFile.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No", "FIle Name", "Size", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableFile);
        if (tableFile.getColumnModel().getColumnCount() > 0) {
            tableFile.getColumnModel().getColumn(0).setMinWidth(0);
            tableFile.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableFile.getColumnModel().getColumn(0).setMaxWidth(0);
            tableFile.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("File On Server", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private final int DEFAULT_PORT = 9999;  //  써먹을 포트번호
    private final DefaultTableModel model;
    private final DefaultTableModel modelFile;
    private Socket client; //소켓객체 
    private void cmdConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdConnectActionPerformed
        if (loginSession == null) {
            JOptionPane.showMessageDialog(this, "로그인을 해주세요");
        } else {
            //로그인을 정상적으로 했을 때 
            IP = txtIPAddress.getText().trim(); // txtIPAddress에 있는 텍스트값을 ip로 읽음 
        if (client == null) {
            try {
                // 소켓 생성 및 서버연결 
                client = IO.socket("http://" + IP + ":" + DEFAULT_PORT);
                // "exit_app" 이벤트 처리: 서버에서 종료 명령이 오면 클라이언트 종료
                client.on("exit_app", new Emitter.Listener() {
                    @Override
                    public void call(Object... os) {
                        System.exit(0);
                    }
                });
                
        // "new_file" 이벤트 처리: 서버에서 새 파일이 전송되면 테이블에 추가
                client.on("new_file", new Emitter.Listener() {
                    @Override
                    public void call(Object... os) {
                        // 파일추가
                        try {
                            addFile(new DataFileServer((JSONObject) os[0], table, client));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                client.open();
                // 사용자 이름  서버에 전송
                String userName = txtName.getText().trim(); 
                client.emit("set_user", userName);
                
                // 서버에 파일 목록 요청 및 응답 처리
                client.emit("request", "list_file", new Ack() {
                    @Override
                    public void call(Object... os) {
                        try {
                            for (Object o : os) {
                                addFile(new DataFileServer((JSONObject) o, table, client));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (URISyntaxException e) {
                System.err.println(e);
            }
        } else {
            // 이미 클라이언트가 존재하는 경우, 사용자 이름을 서버에 다시 전송
            client.emit("set_user", txtName.getText().trim());
        }
        }
    }//GEN-LAST:event_cmdConnectActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       // 파일선택 
        JFileChooser ch = new JFileChooser();
        ch.setMultiSelectionEnabled(true);
        // 파일선택후 확인버튼 눌렀을 경우 
        int opt = ch.showOpenDialog(this);
        if (opt == JFileChooser.APPROVE_OPTION) {
             // 선택한 파일들을 배열로 가져옴
            File[] files = ch.getSelectedFiles();
                  // 선택한 각 파일에 대해 처리
            for (File file : files) {
                try {
                      // 선택한 파일을 읽어들이는 DataReader 객체 생성
                    DataReader reader = new DataReader(file, table);
                   // 테이블에 새로운 행 추가
                    model.addRow(reader.toRowTable(table.getRowCount() + 1));
                   // 클라이언트에 파일 전송 시작 요청
                    reader.startSend(client);
                } catch (Exception e) {
                    //예외처리
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnGoLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoLoginActionPerformed
        try {
            // 로그인버튼을 눌렀을 때 로그인창을 엽니다.(login.java)
            login loginWindow = new login();
            Dimension frameSize = loginWindow.getSize();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            loginWindow.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
            this.dispose();
            loginWindow.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGoLoginActionPerformed

    //DataFileServer 객체를 받아서 modelFile에 해당 데이터를 추가
    private void addFile(DataFileServer data) {
       
        modelFile.addRow(data.toTableRow(tableFile.getRowCount() + 1));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        try {
             // Nimbus 룩 앤 필 설정
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGoLogin;
    private javax.swing.JButton cmdConnect;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable table;
    private javax.swing.JTable tableFile;
    private javax.swing.JTextField txtIPAddress;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}