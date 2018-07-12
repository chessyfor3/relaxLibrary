/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.model;

/**
 *
 * @author navajizz
 */
public class Borrower {

    
    private String firstname;
    private String lastname;
    private String email;
    private String contactNo;
    private int borrowQty;

    public Borrower(String firstname, String lastname, String email, String contactNo, int borrowQty) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contactNo = contactNo;
        this.borrowQty = borrowQty;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public int getBorrowQty() {
        return borrowQty;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setBorrowQty(int borrowQty) {
        this.borrowQty = borrowQty;
    }
    
}
