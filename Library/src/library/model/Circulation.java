/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.model;

import java.sql.*;

/**
 *
 * @author navajizz
 */
public class Circulation {
    
    private final String dateOut;
    private String dueBack;
    private int borrowQty;
    private final String book_ISBN;
    private final int borrower_id;
    private int returnQty;

    public Circulation(String dateOut, String dueBack, int borrowQty, String book_ISBN, int borrower_id, int returnQty) {
       
        this.dateOut = dateOut;
        this.dueBack = dueBack;
        this.borrowQty = borrowQty;
        this.book_ISBN = book_ISBN;
        this.borrower_id = borrower_id;
        this.returnQty = returnQty;
    }

    public String getDateOut() {
        return dateOut;
    }

    public String getDueBack() {
        return dueBack;
    }

    public int getBorrowQty() {
        return borrowQty;
    }

    public String getBook_ISBN() {
        return book_ISBN;
    }

    public int getBorrower_id() {
        return borrower_id;
    }

    public int getReturnQty() {
        return returnQty;
    }

    public void setDueBack(String dueBack) {
        this.dueBack = dueBack;
    }

    public void setBorrowQty(int borrowQty) {
        this.borrowQty = borrowQty;
    }

    public void setReturnQty(int returnQty) {
        this.returnQty = returnQty;
    }
    
    
    
    
    
}
