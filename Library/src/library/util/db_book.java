/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.util;

import java.sql.*;
import library.model.*;

/**
 * Database Connection for the table book
 * @author Jpoy
 */
public class db_book {
    private final db_connection db = new db_connection();
    
    public int saveToDB(Book book) throws SQLException{
        String query = "insert into book (bookISBN,bookTitle,bookAuthor,bookPublisher,bookQty,bookAvailable) values (?,?,?,?,?,?)";
        PreparedStatement p = db.prepare(query);
        p.setString(1,book.getISBN());
        p.setString(2, book.getTitle());
        p.setString(3, book.getAuthor());
        p.setString(4, book.getPublisher());
        p.setInt(5, book.getQuantity());
        p.setInt(6, book.getAvailableCount());
        return p.executeUpdate();
    }
    
    public int updateAllFields(String ISBN, String title, String author, String publisher, int qty, int available, String oldISBN) throws SQLException{
        String query = "update book set bookISBN=?,bookTitle=?,bookAuthor=?,bookPublisher=?,bookQty=?,bookAvailable=? where bookISBN =?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, ISBN);
        p.setString(2,title);
        p.setString(3,author);
        p.setString(4,publisher);
        p.setInt(5,qty);
        p.setInt(6,available);
        p.setString(7, oldISBN);
        return p.executeUpdate();
    }
    
    public int updateQuantity(int data, String ISBN) throws SQLException{
        String query = "update book set bookQty=? where bookISBN =?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, data);
        p.setString(2,ISBN);
        return p.executeUpdate();
    }
    
    public int updateAvailable(int data, String ISBN) throws SQLException{
        String query = "update book set bookAvailable=? where bookISBN =?";
        PreparedStatement p = db.prepare(query);
        p.setInt(1, data);
        p.setString(2,ISBN);
        return p.executeUpdate();
    }
    
    public int deleteOne(String ISBN) throws SQLException{
        String query = "delete from book where bookISBN=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1,ISBN);
        return p.executeUpdate();
    }
    
    public int deleteAll() throws SQLException{
        String query = "delete * from book";
        return db.executeUpdate(query);
    }
    
    public ResultSet selectOne(String ISBN) throws SQLException{
        String query = "select * from book where bookISBN=?";
        PreparedStatement p = db.prepare(query);
        p.setString(1, ISBN);
        return p.executeQuery();
    }
    
    public ResultSet selectAll(){
        String query = "select * from book";
        return db.execute(query);
    }
    
    
}
