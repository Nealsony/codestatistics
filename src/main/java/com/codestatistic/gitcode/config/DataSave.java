package com.codestatistic.gitcode.config;

import org.springframework.stereotype.Component;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Nielson
 * @Date: 2018/8/30 11:37
 * @Description:
 */
@Component
public class DataSave {
    //创建数据库连接
    static Connection con = null;
    //创建预编译语句对象
    static Statement pstmt = null;
    public static void main(String[] args){
        DataSave dataSave = new DataSave();
        String fileName = "D:\\Source\\codestatistic.log";
        try {
            long begin = System.currentTimeMillis();
            List<String> insertList = new ArrayList<>();
            System.out.println("数据插入准备！开始时间为：" + begin);
            dataSave.initJDBC();
//            insertList = dataSave.readFile(fileName);
//            dataSave.insertData(insertList);
            long end = System.currentTimeMillis();
            System.out.println("数据插入完毕！结束时间为：" + end);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化数据库连接驱动
     */
    private void initJDBC(){

        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:domain.db");
//            String creatsql = "CREATE TABLE CODE " +
//                    "(" +
//                    "ID INT PRIMARY KEY ," +
//                    "USERNAME   VARCHAR(50)    NOT NULL, " +
//                    "`ADD`    INT, " +
//                    "REMOVE    INT, " +
//                    "INCREMENT  INT," +
//                    "DATETIME   TEXT" +
//                    ")";
//            pstmt = con.createStatement();
//            pstmt.executeUpdate(creatsql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 文件读取操作
     * @param fileName 文件名(全路径名称)
     * @throws IOException
     */
    private List<String> readFile(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName)); //阅读器
        String line; //行数据
        int i = 1;
        long id = 1;
        String tbname = "CODE"; //标签名--数据库表名
        String cols = (" (USERNAME,`ADD`,REMOVE,INCREMENT,DATETIME)"); //标签列名--数据库列名
        //插入语句集合
        List<String> insertList = new ArrayList<>();

        PrintWriter out = new PrintWriter(new FileWriter("D:\\gitcode\\insert.txt"));// 插入脚本文件
        String username = null;
        String datetime = null;
        try{
            while(null!=(line = in.readLine())){
                if(line.isEmpty())
                    continue;
                String[] vals = new String[6]; //标签列值--数据库列值

                if(id % 2 == 1){
                    String[] strs = line.split(";");
                    username = strs[0];
                    datetime = strs[1].substring(0,19);
                }
                else{
                    Map<String,String> map = new HashMap<>();
                    String[] codes = line.split(",");
                    for(String code : codes){
                        String[] s = code.split(" ");
                        map.put(s[2],s[1]);
                    }

//                    int add = Integer.valueOf(codes[4]);
//                    int remove = Integer.valueOf(codes[6]);
                    vals[1] = username;
                    vals[2] = map.get("insertions(+)") == null ? "0" : map.get("insertions(+)");
                    vals[3] = map.get("deletions(-)") == null ? "0" : map.get("deletions(-)");
                    vals[4] = String.valueOf(Integer.valueOf(vals[2]) - Integer.valueOf(vals[3]));
                    vals[5] = datetime;
                    String ists = null;         //插入数据SQL
                    ists = "INSERT INTO " + tbname + cols + " VALUES "
                            + "(" + "'" + vals[1] + "'" + ","
                            + vals[2] + ","
                            + vals[3] + ","
                            + vals[4] + ","
                            + "'" + vals[5] + "'" + ")";

                    insertList.add(ists);
                }
                id++;
            }
        System.out.println(insertList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return insertList;
    }

    /**
     * 插入数据库脚本
     * @param insertList 插入数据脚本list
     * @throws SQLException
     */
    private void insertData(List<String> insertList) throws SQLException{
        pstmt = con.createStatement();
        System.out.println("数据条数：" + insertList.size());
        //避免ID主键冲突
        for (String string : insertList) {
            pstmt.addBatch(string);
        }
        pstmt.executeBatch();
    }
}
