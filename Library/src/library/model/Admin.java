/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.model;

/**
 * Admin Model
 * @author Jpoy
 */
public class Admin{
    private final String username;
    private String password;
    private int borrower_id;
    
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    // Setters
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setBorrower_id(int borrower_id) {
        this.borrower_id = borrower_id;
    }
    
    // Getters
    public int getBorrower_id() {
        return borrower_id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    
}
