package client;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.*;
import java.io.*;
/**
 *
 * @author Hun
 */
public class dbconn {  // db연결부  ( mysql db의 주소,계정)
    String strDriver = "com.mysql.cj.jdbc.Driver";
   //String strURL = "jdbc:odbc:Automobile_xx64";
    //String strURL = "jdbc:sqlserver://localhost:1433;DatabaseName=Automobile;";
    String strURL = "jdbc:mysql://mmj.ctjtwbfhskrs.ap-northeast-2.rds.amazonaws.com:3306/MMJ?useUnicode=true&characterEncoding=UTF-8";
    String strUser = "meo";  
    String strPWD = "Ska3028!";
    
    Connection DB_con;
    Statement DB_stmt;
    ResultSet DB_rs;
   
    /**
     *
     */
   public void dbOpen() throws IOException{
    
    try{
    Class.forName(strDriver);
    
    DB_con = DriverManager.getConnection(strURL,strUser,strPWD);
    DB_stmt = DB_con.createStatement();
    
    }catch(Exception e){
        System.out.println("SQLException" + e.getMessage());
    
    
    }
    
    
    }
    public void dbclose() {  //db와의 연결을 종료 
    try{
    
    DB_stmt.close();
    DB_con.close();
    }catch(SQLException e){
    
        System.out.println("SQLException"+e.getMessage());
    }
    
    
    }
public PreparedStatement prepareStatement(String query) throws SQLException {
    return DB_con.prepareStatement(query);
}
    
    
}
