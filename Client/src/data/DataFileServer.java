/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import io.socket.client.Ack;
import io.socket.client.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import org.json.JSONException;
import org.json.JSONObject;
import swing.PanelStatus_Item;

/**
 *
 * @author RAVEN
 */
public class DataFileServer {

    public PanelStatus_Item getItem() {
        return item;
    }

    public void setItem(PanelStatus_Item item) {
        this.item = item;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public File getOutPutPath() {
        return outPutPath;
    }

    public void setOutPutPath(File outPutPath) {
        this.outPutPath = outPutPath;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DataFileServer(int fileID, String fileName, String fileSize, File outPutPath, boolean status) {
        this.fileID = fileID;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.outPutPath = outPutPath;
        this.status = status;
    }

    // JSON으로부터 초기화하는 생성자
    public DataFileServer(JSONObject json, JTable table, Socket socket) throws JSONException {
        fileID = json.getInt("fileID");
        fileName = json.getString("fileName");
        fileSize = json.getString("fileSize");
        fileSizeLength = json.getLong("fileSizeLength");
        item = new PanelStatus_Item();
        this.table = table;
        this.socket = socket;
        
        // 클라이언트 이벤트 처리
        item.addEventSave(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 // 파일을 저장할 디렉토리 선택
                JFileChooser ch = new JFileChooser();
                ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int opt = ch.showSaveDialog(null);
                if (opt == JFileChooser.APPROVE_OPTION) {
                    outPutPath = new File(ch.getSelectedFile().getAbsolutePath() + "/" + fileName);
                    item.startFile();
                    try {
                        saveFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
        item.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                 // 일시 중지 및 계속 진행
                if (!item.isPause() && pause) {
                    pause = false;
                    try {
                        saveFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private int fileID;
    private String fileName;
    private String fileSize;
    private long fileSizeLength;
    private File outPutPath;
    private boolean status;
    private PanelStatus_Item item;
    private JTable table;
    private DataWriter writer;
    private Socket socket;
    private boolean pause;

    // 파일 저장 메서드
    private void saveFile() throws IOException, JSONException {
         // 파일 쓰기 시작
        if (writer == null) {
            writer = new DataWriter(outPutPath, fileSizeLength);
        }
      // 서버에게 파일 길이 요청
        JSONObject data = new JSONObject();
        data.put("fileID", fileID);
        data.put("length", writer.getFileLength());
        socket.emit("request_file", data, new Ack() {
            @Override
            public void call(Object... os) {
                try {
                    if (os.length > 0) {
                        byte[] b = (byte[]) os[0];
                        writer.writeFile(b);
                        item.showStatus((int) writer.getPercentage());
                        table.repaint();
                        if (!item.isPause()) {
                            saveFile();
                        } else {
                            pause = true;
                        }
                    } else {
                        item.showStatus((int) writer.getPercentage());
                        item.done();
                        table.repaint();
                        writer.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    // 테이블 행으로 변환하는 메서드
    public Object[] toTableRow(int row) {
        return new Object[]{this, row, fileName, fileSize, "Next Update"};
    }
}
