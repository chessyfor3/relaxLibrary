/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.util;
import java.sql.*;
import library.model.*;
/**
 * Database Connection for the table borrower
 * @author Jpoy
 */
public class db_borrower {
    private final db_connection db = new db_connection();
    
    public int saveBorrower(Borrower borrower) throws SQLException{
        String query = "insert into borrower (borrowerFirstname,borrowerLastname,borrowerEmail,borrowerPhone,borrowQty) values (?,?,?,?,?)";
        PreparedStatement p = db.prepare(query);
        p.setString(1, borrower.getFirstname());
        p.setString(2, borrower.getLastname());
        p.setString(3, borrower.getEmail());
        p.setString(4, borrower.getContactNo());
        p.setInt(5, borrower.getBorrowQty());
        return p.executeUpdate();
    }
    
    
    public int updateQty(int qty,int borrower_id) throws SQLException{
        String query = "update borrower set borrowQty=? where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, qty);
        p.setInt(2, borrower_id);
        return p.executeUpdate();
    }
    public int updateFirstname(String name,int borrower_id) throws SQLException{
        String query = "update borrower set borrowerFirstname=? where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, name);
        p.setInt(2, borrower_id);
        return p.executeUpdate();
    }
    public int updateLastname(String name,int borrower_id) throws SQLException{
        String query = "update borrower set borrowerLastname=? where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, name);
        p.setInt(2, borrower_id);
        return p.executeUpdate();
    }
    public int updateEmail(String email,int borrower_id) throws SQLException{
        String query = "update borrower set borrowerEmail=? where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, email);
        p.setInt(2, borrower_id);
        return p.executeUpdate();
    }
    public int updatePhone(String phone,int borrower_id) throws SQLException{
        String query = "update borrower set borrowerPhone=? where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, phone);
        p.setInt(2, borrower_id);
        return p.executeUpdate();
    }
    public ResultSet selectOne(int borrower_id) throws SQLException{
        String query = "select * from borrower where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, borrower_id);
        return p.executeQuery();
    }
    public ResultSet selectAll(){
        String query = "select * from borrower";
        return db.execute(query);
    }
    
    public int count() throws SQLException{
        ResultSet rs = selectAll();
        rs.last();
        return rs.getRow();
    }
    public int deleteOne(int borrower_id) throws SQLException{
        String query = "delete from borrower where borrowerID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1,borrower_id);
        return p.executeUpdate();
    }
    
    public int deleteAll() throws SQLException{
        String query = "delete * from borrower";
        return db.executeUpdate(query);
    }
    
}
