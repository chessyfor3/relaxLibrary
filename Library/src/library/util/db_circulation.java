/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.util;
import java.sql.*;
import library.model.*;
/**
 * Database Connection for the table circulation
 * @author Jpoy
 */
public class db_circulation {
    private final db_connection db= new db_connection();
    
    public int saveCirculation(Circulation data) throws SQLException{
        String query = "insert into circulation (dueBack,borrowQty,book_ISBN,borrower_id) values (?,?,?,?)";
        PreparedStatement p = db.prepare(query);
        p.setString(1, data.getDueBack());
        p.setInt(2, data.getBorrowQty());
        p.setString(3, data.getBook_ISBN());
        p.setInt(4, data.getBorrower_id());
        return p.executeUpdate();
    }
    
    public int updateQty(int borrowQty, int returnQty ,int cir_id) throws SQLException{
        String query = "update circulation set borrowQty=?,returnedQty=? where circulationID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, borrowQty);
        p.setInt(2, returnQty);
        p.setInt(3, cir_id);
        return p.executeUpdate();
    }
    
    public int updateDueDate(String date, int cir_id) throws SQLException{
        String query = "update circulation set dueBack=? where circulationID=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, date);
        p.setInt(2, cir_id);
        return p.executeUpdate();
    }
    
    
    public ResultSet selectAll(){
        String query = "select * from circulation";
        return db.execute(query);
    }
    
    public ResultSet selectOne(int cir_id) throws SQLException{
        String query = "select * from circulation where circulationID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, cir_id);
        return p.executeQuery();
    }
    
    public int deleteOne(int cir_id) throws SQLException{
        String query = "delete from circulation where circulationID=?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, cir_id);
        return p.executeUpdate();
    }
    public int deleteAll() throws SQLException{
        String query = "delete * from cirulation";
        return db.executeUpdate(query);
    }
    
}
