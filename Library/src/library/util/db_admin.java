/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.util;
import library.model.*;
import java.sql.*;

/**
 * Database Connection for the table admin
 * @author Jpoy
 */
public class db_admin {
    private final db_connection db = new db_connection();
    
    public int save(Admin admin) throws SQLException{
        String query = "insert into admin (username,password) values (?,?)";
        PreparedStatement p = db.prepare(query);
        p.setString(1, admin.getUsername());
        p.setString(2, admin.getPassword());
        return p.executeUpdate();
    }
    public int updatePassword(String pass, String user) throws SQLException{
        String query = "update admin set password=? where username =?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, pass);
        p.setString(2, user);
        return p.executeUpdate();
    }
    public int updateBorrowerID(int id, String user) throws SQLException{
        String query = "update admin set borrowerID=? where username =?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, id);
        p.setString(2, user);
        return p.executeUpdate();
    }
    public int deleteOne(String user) throws SQLException{
        String query = "delete from admin where username=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, user);
        return p.executeUpdate();
    }
    public ResultSet selectByBorrower(int id) throws SQLException{
        String query = "select * from admin where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, id);
        return p.executeQuery();
    }
    public ResultSet selectOne(String user) throws SQLException{
        String query = "select * from admin where username=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, user);
        return p.executeQuery();
    }
    public ResultSet selectAll(){
        return db.execute("select * from admin");
    }
}
