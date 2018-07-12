/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.util;
import java.sql.*;
import library.model.*;
/**
 * Database Connection for the table returned (books)
 * @author Jpoy
 */
public class db_return {
    private final db_connection db = new db_connection();
    
    public int save(int cir_id) throws SQLException{
        String query = "insert into returned (circulation_id) values (?)";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, cir_id);
        return p.executeUpdate();
    }
    public int deleteMultiple(int cir_id) throws SQLException{
        String query = "delete from returned where circulation_id=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1,cir_id);
        return p.executeUpdate();
    }
    
    public int deleteOne(int ret_id) throws SQLException{
        String query = "delete from returned where returnID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1,ret_id);
        return p.executeUpdate();
    }
    public int deleteAll() throws SQLException{
        return db.executeUpdate("delete * from returned");
    }
    public ResultSet selectOne(int ret_id) throws SQLException{
        String query = "select * from returned where returnID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, ret_id);
        return p.executeQuery();
    }
    public ResultSet selectAll(){
        return db.execute("select * from returned");
    }
}
