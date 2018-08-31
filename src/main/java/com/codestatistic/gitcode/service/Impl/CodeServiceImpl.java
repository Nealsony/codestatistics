package com.codestatistic.gitcode.service.Impl;

import com.codestatistic.gitcode.Domain.Code;
import com.codestatistic.gitcode.service.CodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Nielson
 * @Date: 2018/8/31 11:21
 * @Description:
 */
@Service
public class CodeServiceImpl implements CodeService {
    public List<Code> queryAll(){
        //创建数据库连接
        Connection con = null;
        //创建预编译语句对象
        Statement pstmt = null;
        List<Code> codes = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:D:/gitcode/gitcode/domain.db");
            pstmt = con.createStatement();
            String sql = " SELECT t.* FROM CODE t;";
            ResultSet rs = pstmt.executeQuery( sql );
            while ( rs.next() ){
                Code code = new Code();
                int id = rs.getInt("id");
                String username = rs.getString("username");
                int add = rs.getInt("add");
                int remove = rs.getInt("remove");
                int increment = rs.getInt("increment");
                String datetime = rs.getString("datetime");
                code.setId(id);
                code.setUsername(username);
                code.setAdd(add);
                code.setRemove(remove);
                code.setIncrement(increment);
                code.setDatetime(datetime);
                codes.add(code);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codes;
    }
}
