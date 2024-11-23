package data;

import io.socket.client.Ack;
import io.socket.client.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import javax.swing.JTable;
import org.json.JSONException;
import org.json.JSONObject;
import swing.PanelStatus;

public class DataReader {

    public PanelStatus getStatus() {
        return status;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public DataReader(File file, JTable table) throws IOException {
        // 파일 읽어오기 
        accFile = new RandomAccessFile(file, "r");
        this.file = file;
        this.fileSize = accFile.length();
        this.fileName = file.getName();
        this.status = new PanelStatus();
       // PanelStatus 객체에 이벤트 핸들러 등록
        this.status.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // PanelStatus가 일시 중지 상태가 아니면서 pause 변수가 true이면
                if (!status.isPause() && pause) {
                    pause = false;
               // 서버에게 파일 ID를 전달하여 계속 진행하도록 요청
                    client.emit("r_f_l", fileID, new Ack() {
                        @Override
                        public void call(Object... os) {
                            if (os.length > 0) {
                                long length = Long.valueOf(os[0].toString());
                                try {
                                    accFile.seek(length);
                                    sendingFile(client);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    // 파일 전송

                }
            }
        });
        this.table = table;
    }

    private int fileID; // 파일식별자
    private File file; // 작업중인 파일
    private long fileSize; //파일크기
    private String fileName; //파일이름
    private RandomAccessFile accFile; //파일 읽기작업수행
    private PanelStatus status; // 파일 전송상태 
    
    private JTable table;
    private Socket client;

    public synchronized byte[] readFile() throws IOException { //파일 읽기 
        long filePointer = accFile.getFilePointer();
        if (filePointer != fileSize) {
            int max = 2000;
         
             // 읽을 데이터의 길이 계산  
            long length = filePointer + max >= fileSize ? fileSize - filePointer : max;
              // 읽을 데이터를 저장할 배열 생성
            byte[] data = new byte[(int) length];
             // 파일에서 데이터 읽기
            accFile.read(data);
            return data;
        } else {
            // 파일의 끝까지 읽었을 경우
            return null;
        }
    }

    public void close() throws IOException {
        accFile.close(); 
    }

    public String getFileSizeConverted() { //파일의 크기단위를 변환 
        double bytes = fileSize;
        String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        String sizeToReturn;
        DecimalFormat df = new DecimalFormat("0.#");
        int index;
        for (index = 0; index < fileSizeUnits.length; index++) {
            if (bytes < 1024) {
                break;
            }
            bytes = bytes / 1024;
        }
        sizeToReturn = df.format(bytes) + " " + fileSizeUnits[index];
        return sizeToReturn;
    }

    public double getPercentage() throws IOException { //파일 전송상태 퍼센테이지로 표시
        double percentage;
        long filePointer = accFile.getFilePointer();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }

    public Object[] toRowTable(int no) {
        return new Object[]{this, no, fileName, getFileSizeConverted(), "Next update"};
    }

    public void startSend(Socket socket) throws JSONException { //파일 전송시작기능
        this.client = socket;
        // 파일 정보를 담은 JSON 객체 생성
        JSONObject data = new JSONObject();
        data.put("fileName", fileName);
        data.put("fileSize", fileSize);
            // "send_file" 이벤트를 서버로 전송하고 Ack 콜백을 정의
        socket.emit("send_file", data, new Ack() {
            @Override
            public void call(Object... os) {
              // 콜백 함수
            // Index 0: Boolean, Index 1: FileID
                if (os.length > 0) {
                    boolean action = (boolean) os[0];
                    if (action) {
                        //  서버에서 생성한 fileID를 받아옴
                        fileID = (int) os[1];
                        //  // 파일 전송 시작
                        try {
                            sendingFile(socket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private boolean pause = false;

    private void sendingFile(Socket socket) throws IOException, JSONException {
        JSONObject data = new JSONObject();   //파일을 소켓을 통해 서버에 전송
        data.put("fileID", fileID);
        byte[] bytes = readFile();
        if (bytes != null) {
             // 파일 데이터가 존재하면 데이터와 전송 완료 여부를 설정
            data.put("data", bytes);
            data.put("finish", false);
        } else {
             // 파일 데이터가 없으면 전송 완료를 설정하고 파일을 닫음
            data.put("finish", true);
            close();    //  to close file
            status.done(); // 전송 완료 상태
        }
        socket.emit("sending", data, new Ack() {
            @Override
            public void call(Object... os) {
                // 콜백 함수: 더 많은 파일을 전송하기 위한 응답 처리
            // 서버가 파일을 받았다는 신호를 받으면 더 많은 파일을 전송
             
                if (os.length > 0) {
                    boolean act = (boolean) os[0];
                    if (act) {
                        try {
                            // 상태가 일시 중지 상태가 아니면서 계속 진행 가능한 경우
                            if (!status.isPause()) {
                                showStatus((int) getPercentage());
                                sendingFile(socket);
                            } else {
                                // 일시 중지되었을 경우 pause 변수 설정
                                pause = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void showStatus(int values) {
        status.showStatus(values);
        table.repaint();
    }
}
