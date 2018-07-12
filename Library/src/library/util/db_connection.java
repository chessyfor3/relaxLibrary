/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.util;

import java.sql.*;
/**
 * Database Connection
 * @author Jpoy
 */
public class db_connection {
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private PreparedStatement p = null;
    
    
    public db_connection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            st = con.createStatement();
            
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("SQL Vendor Error: " + e.getErrorCode());
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    public PreparedStatement prepare(String query){
        try{
            p = con.prepareStatement(query);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return p;
    }
    public int executeUpdate(String query) throws SQLException{
        
        return st.executeUpdate(query);
        
    }
    
    
    public ResultSet execute(String query){
        try{
            rs = st.executeQuery(query);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet executePrepare(PreparedStatement p){
        try{
            rs = p.executeQuery();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    
}
