package data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

public class DataWriter {

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

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public DataWriter(File file, long fileSize) throws IOException {
        //  rw is mode read and write
        accFile = new RandomAccessFile(file, "rw"); // 파일과 파일 크기를 받아서 RandomAccessFile을 초기화 파일 모드는 "rw" (읽기/쓰기)로 설정
        this.file = file;
        this.fileSize = fileSize;

    }

    private File file; //작업중인 파일
    private long fileSize; // 작업중인 파일의 크기 
    private RandomAccessFile accFile; //파일의 읽기/쓰기작업 수행

    public synchronized long writeFile(byte[] data) throws IOException { //파일에 데이터 추가 (파일쓰기)
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }

    public void close() throws IOException {
        accFile.close(); //파일작업 닫기 
    }

    public String getMaxFileSize() {
        return convertFile(fileSize); //최대파일크기 반환 
    }

    public String getCurrentFileSize() throws IOException {
        return convertFile(accFile.length()); //현재파일크기 반환
    }

    public double getPercentage() throws IOException { //파일 크기를 퍼센테이지로 반환 
        double percentage;
        long filePointer = accFile.length();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }

    public long getFileLength() throws IOException {
        return accFile.length();
    }

    private String convertFile(double bytes) { //파일의 크기단위를 변환
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
}
