/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.controller;

import java.awt.event.*;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.event.*;
import library.view.*;
import library.model.*;
import library.util.*;
/**
 * View List Controller
 * Operations for viewing lists of books, borrowers, transaction, returned and unreturned books
 * @author Jpoy
 */
public class ViewListController extends Thread{
    private ViewList frame;
    private final ViewListController control = this;
    private final Thread previous;
    private String option;
    private Book book;
    private Borrower borrower;
    private Circulation circulation;
    private final String admin;
    private final db_book bookdb = new db_book();
    private final db_circulation cirdb = new db_circulation();
    private final db_borrower bordb = new db_borrower();
    private final db_return ret = new db_return();
    private final db_admin addb = new db_admin();
    public ViewListController(Thread previous, String admin){
        this.previous = previous;
        this.admin = admin;
    }
    
    
    @Override
    public void run(){
        try{
            initController();
        }catch(Exception e){
            if(!e.getMessage().equals("")){
                JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
            }
        }
        
    }
    /**
     * Customized ActionListener for the list option picker (combo box)
     */
    class ListOptionAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                option = frame.getSelectedOption();
                initButtons();
                frame.disableButtons();
                switch(option){
                    case "Books": frame.initTable(objBookList(), headBooks());
                                frame.setAddText("Options", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/options16.png")));
                                frame.showAdd();
                                frame.showDelete();break;
                    case "Transactions": frame.initTable(objTransactions(), headCirculations());
                                frame.showButtons();
                                break;
                    case "Borrowers": frame.initTable(objBorrowers(), headBorrowers());
                                frame.showAdd();
                                frame.showDelete();
                                frame.setAddText("Options", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/options16.png")));
                                break;
                    case "Returned Books": frame.initTable(objReturned(), headReturned());
                                frame.showAdd();frame.disableAddBtn();
                                frame.setAddText("Delete", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/garbage16.png")));
                                break;
                    case "Unreturned Books": frame.initTable(objUnreturned(), headUnreturned());
                    frame.setAddText("Return", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/bookreturn.png")));
                    frame.showAdd();frame.disableAddBtn();
                                
                                break;
                }
                if(option.equals("-- Select --")){
                    frame.tableReset();
                }else{
                    frame.tableListener(new TableAction());
                }
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the details button
     */
    class DetailsAction implements ActionListener{
        private int idx;
        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                idx = frame.tableSelectedRow();
                if(idx == -1){
                    throw new Exception("Please select a row in the table");
                }
                setCirculation(getCirculationID());
                new TransDetailsController(new ViewListController(previous,admin),getCirculationID(),admin).start();
                frame.dispose();
                
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
            
        }
        
    }
    /**
     * Customized ActionListener for the cancel button
     */
    class CancelAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                previous.start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the add button
     */
    class AddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                switch(option){
                    case "Books": new AddBookController(new ViewListController(previous,admin),admin).start(); 
                            frame.dispose(); ;break;
                    case "Transactions": new ChargeOutController(new ViewListController(previous,admin),admin).start(); 
                            frame.dispose();break;
                    case "Borrowers": new BorrowerController(new ViewListController(previous,admin),admin).start();
                            frame.dispose();break;
                    case "Returned Books": deleteReturn();break;
                    case "Unreturned Books": returnBook(); break;
                }
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e.getMessage());
                }
                
            }
        }
        
    }
    /**
     * Customized ActionListener for the delete button
     */
    class DeleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                switch(option){
                    case "Transactions": deleteCirculation(); break;
                    case "Books": deleteBook(); break;
                    case "Borrowers": deleteBorrower(); break;
                }
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e.getMessage());
                }
                
            }
        }
        
    }
    /**
     * Customized MouseListener for table mouse click selected row
     */
    class TableAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            try{
                frame.enableButtons();
                frame.enableAddBtn();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
        
    }
    /**
     * Customized ActionListener for the home menu
     */
    class HomeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                new HomeController(admin).start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the settings menu
     */
    class SettingsAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                new AdminController(admin,false).start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the logout menu
     */
    class LogoutAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                int a = JOptionPane.showConfirmDialog(frame, "Are you sure to logout?", "Warning", JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                    throw new Exception("");
                }
                new LoginController().start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the charge out menu
     */
    class ChargeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                new ChargeOutController(new ViewListController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the return book menu
     */
    class ReturnAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                new TransDetailsController(new ViewListController(previous,admin),0,admin).start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the books menu
     */
    class BooksAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                new AddBookController(new ViewListController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Customized ActionListener for the borrowers menu
     */
    class BorrowersAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                    throw new Exception("");
                }
                new BorrowerController(new ViewListController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                if(!e.getMessage().equals("")){
                    JOptionPane.showMessageDialog(frame, e + " " + e.getMessage());
                }
            }
        }
        
    }
    /**
     * Deletes the record for the returned book
     * @throws Exception Error during the process
     */
    
    private void deleteReturn() throws Exception{
        if(frame.tableSelectedRow() == -1){
            throw new Exception("Please select from the table");
        }
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure to delete this record?","Warning", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
            throw new Exception("");
        }
        ResultSet rs = ret.selectOne(getReturnID());
        rs.first();
        ret.deleteOne(getReturnID());
        setCirculation(rs.getInt("circulation_id"));
        circulation.setReturnQty(circulation.getReturnQty()-1);
        cirdb.updateQty(circulation.getBorrowQty(),circulation.getReturnQty(),rs.getInt("circulation_id"));
        frame.initTable(objReturned(), headReturned());
    }
    /**
     * Deletes the circulation record
     * @throws Exception Error during the process
     */
    private void deleteCirculation() throws Exception{
        if(frame.tableSelectedRow() == -1){
            throw new Exception("Please select from the table");
        }
        setCirculation(getCirculationID());
        if(circulation.getBorrowQty() != 0){
            throw new Exception("Unable to delete. Book not returned");
        }
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure to delete this transaction?","Warning", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
            throw new Exception("");
        }
        
        cirdb.deleteOne(getCirculationID());
        ret.deleteMultiple(getCirculationID());
        frame.initTable(objTransactions(), headCirculations());
        circulation = null;
    }
    /**
     * Deletes the borrower
     * @throws Exception Error during the process
     */
    private void deleteBorrower() throws Exception{
        if(frame.tableSelectedRow() == -1){
            throw new Exception("Please select from the table");
        }
        ResultSet rs = addb.selectByBorrower(getBorrowerID());
        if(rs.first()){
            throw new Exception("Cannot delete admin");
        }
        setBorrower(getBorrowerID());
        if(borrower.getBorrowQty() != 0){
            throw new Exception("Unable to delete. Borrower is borrowing");
        }
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure to delete this borrower?","Warning", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
            throw new Exception("");
        }
        bordb.deleteOne(getBorrowerID());
        frame.initTable(objBorrowers(), headBorrowers());
        borrower = null;
    }
    /**
     * Deletes book
     * @throws Exception Error during the process
     */
    private void deleteBook() throws Exception{
        if(frame.tableSelectedRow() == -1){
            throw new Exception("Please select from the table");
        }
        setBook(getISBN());
        if(book.getQuantity() != book.getAvailableCount()){
            throw new Exception("Unable to delete. Book being borrowed");
        }
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure to delete this book?","Warning", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
            throw new Exception("");
        }
        bookdb.deleteOne(getISBN());
        frame.initTable(objBookList(), headBooks());
        book = null;
    }
    /**
     * Returns the book and creates new record for the returned book
     * @throws SQLException Error communicating the SQL Database
     */
    private void returnBook() throws SQLException{
        setCirculation(getCirculationID());
        setBook(circulation.getBook_ISBN());
        setBorrower(circulation.getBorrower_id());
        ret.save(getCirculationID());
        circulation.setReturnQty(circulation.getReturnQty()+1);
        circulation.setBorrowQty(circulation.getBorrowQty()-1);
        book.setAvailableCount(book.getAvailableCount()+1);
        borrower.setBorrowQty(borrower.getBorrowQty()-1);
        cirdb.updateQty(circulation.getBorrowQty(), circulation.getReturnQty(), getCirculationID());
        bookdb.updateAvailable(book.getAvailableCount(), book.getISBN());
        bordb.updateQty(borrower.getBorrowQty(), circulation.getBorrower_id());
        frame.initTable(objUnreturned(), headUnreturned());
    }
    /**
     * Gets the returned book details ID of the record
     * @return Integer, ID of the details
     * @throws SQLException Error communicating the SQL Database
     */
    private int getReturnID() throws SQLException{
        ResultSet rs = ret.selectAll();
        rs.absolute(frame.tableSelectedRow()+1);
        return rs.getInt("returnID");
    }
    /**
     * Gets circulation ID
     * @return Integer, circulation ID
     * @throws SQLException Error communicating the SQL Database
     */
    private int getCirculationID() throws SQLException{
        ResultSet rs = cirdb.selectAll();
        rs.absolute(frame.tableSelectedRow()+1);
        return rs.getInt("circulationID");
    }
    /**
     * Gets book ISBN
     * @return String, book ISBN
     * @throws SQLException Error communicating the SQL Database
     */
    private String getISBN() throws SQLException{
        ResultSet rs = bookdb.selectAll();
        rs.absolute(frame.tableSelectedRow()+1);
        return rs.getString("bookISBN");
    }
    /**
     * Gets the borrower ID
     * @return Integer, borrower ID
     * @throws SQLException Error communicating the SQL Database
     */
    private int getBorrowerID() throws SQLException{
        ResultSet rs = bordb.selectAll();
        rs.absolute(frame.tableSelectedRow()+1);
        return rs.getInt("borrowerID");
    }
    /**
     * Sets the circulation model
     * @param id Integer, circulation ID
     * @throws SQLException Error communicating the SQL Database
     */
    private void setCirculation(int id) throws SQLException{
        ResultSet rs = cirdb.selectOne(id);
        rs.first();
        circulation = new Circulation(rs.getString("dateOut"),rs.getString("dueBack"),rs.getInt("borrowQty"),rs.getString("book_ISBN"),rs.getInt("borrower_id"),rs.getInt("returnedQty"));
    
    }
    /**
     * Sets the borrower model
     * @param id Integer, borrower ID
     * @throws SQLException Error communicating the SQL Database
     */
    private void setBorrower(int id) throws SQLException{
        ResultSet rs = bordb.selectOne(id);
        rs.first();
        borrower = new Borrower(rs.getString("borrowerFirstname"),rs.getString("borrowerLastname"),rs.getString("borrowerEmail"),rs.getString("borrowerPhone"),rs.getInt("borrowQty"));
        
    }
    /**
     * Sets the book model
     * @param ISBN String, book ISBN
     * @throws SQLException Error communicating the SQL Database
     */
    private void setBook(String ISBN) throws SQLException{
        ResultSet rs = bookdb.selectOne(ISBN);
        rs.first();
        book = new Book(rs.getString("bookISBN"),rs.getString("bookTitle"),rs.getString("bookAuthor"),rs.getString("bookPublisher"),rs.getInt("bookQty"));
        book.setAvailableCount(rs.getInt("bookAvailable"));
    }
    /**
     * Unreturned books header for the table
     * @return String array
     */
    private String[] headUnreturned(){
        return new String[]{"Ref. #","Book ISBN", "Book Title", "Borrower"}; 
    }
    /**
     * Creates unreturned books list as object
     * @return Object list
     * @throws SQLException Error communicating the SQL Database
     */
    private Object[][] objUnreturned() throws SQLException{
        ResultSet rs = cirdb.selectAll();
        int count=0;
        while(rs.next()){
            if(rs.getInt("borrowQty") > 0){
                count += rs.getInt("borrowQty");
            }
        }
        Object[][] obj = new Object[count][4];
        rs.beforeFirst();
        for(int i = 0; i<count && rs.next();){
            ResultSet bor = bordb.selectOne(rs.getInt("borrower_id")); bor.first();
            ResultSet b = bookdb.selectOne(rs.getString("book_ISBN")); b.first();
            for(int c = 0;c < rs.getInt("borrowQty");c++){
                obj[i][0] = "  RLCS-" + rs.getString("circulationID")+"  ";
                obj[i][1] = "  "+b.getString("bookISBN")+"  ";
                obj[i][2] = "  "+b.getString("bookTitle")+"  ";
                obj[i][3] = "  "+bor.getString("borrowerFirstname") +" " +bor.getString("borrowerLastname")+"  ";
                i++;
            }
            
        }
        
        return obj;
    }
    /**
     * Returned books header for the table
     * @return String array
     */
    private String[] headReturned(){
        return new String[]{"Ref. #","Date Returned","Borrower", "Book ISBN", "Book Title"}; 
    }
    /**
     * Create returned books list as object
     * @return Object list
     * @throws SQLException Error communicating the SQL Database 
     */
    private Object[][] objReturned() throws SQLException{
        ResultSet rs = ret.selectAll();
        rs.last();
        int count= rs.getRow();
        rs.beforeFirst();
        Object[][] obj = new Object[count][5];
        for(int i = 0; i<count && rs.next();i++){
            int cir_id = rs.getInt("circulation_id");
            ResultSet cir = cirdb.selectOne(cir_id);cir.first();
            ResultSet bor = bordb.selectOne(cir.getInt("borrower_id")); bor.first();
            ResultSet b = bookdb.selectOne(cir.getString("book_ISBN")); b.first();
            obj[i][0] = "  RLCS-"+ Integer.toString(cir_id) + "-" +rs.getString("returnID")+"  ";
            obj[i][1] = "  "+rs.getString("dateReturned")+"  ";
            obj[i][2] = "  "+bor.getString("borrowerFirstname") + bor.getString("borrowerLastname")+"  ";
            obj[i][3] = "  "+b.getString("bookISBN")+"  ";
            obj[i][4] = "  "+b.getString("bookTitle")+"  ";
        }
        
        return obj;
    }
    /**
     * Borrowers header for the table
     * @return String array
     */
    private String[] headBorrowers(){
        return new String[]{"Borrower ID","Name", "Email", "Contact #","Qty"}; 
    }
    /**
     * Creates borrowers list as object
     * @return Object list
     * @throws SQLException Error communicating the SQL Database 
     */
    private Object[][] objBorrowers() throws SQLException{
        ResultSet rs = bordb.selectAll();
        rs.last();
        int count = rs.getRow();
        rs.beforeFirst();
        Object[][] obj = new Object[count][5];
        for(int i=0; i<count && rs.next();i++){
            obj[i][0] = "  10010" + rs.getString("borrowerID")+"  ";
            obj[i][1] = "  "+rs.getString("borrowerFirstname") + " " + rs.getString("borrowerLastname")+"  ";
            obj[i][2] = "  "+rs.getString("borrowerEmail")+"  ";
            obj[i][3] = "  "+rs.getString("borrowerPhone")+"  ";
            obj[i][4] = "  "+rs.getString("borrowQty")+"  ";
        }
        return obj;
    }
    /**
     * Transaction records header for the table
     * @return String array
     */
    private String[] headCirculations(){
        return new String[]{"Ref. #","Date","Bor","Ret","Borrower","ISBN","Title of Book"};
    }
    /**
     * Creates transaction list as object
     * @return Object list
     * @throws SQLException Error communicating the SQL Database 
     */
    private Object[][] objTransactions() throws SQLException{
        ResultSet rs = cirdb.selectAll();
        rs.last();
        int count = rs.getRow();
        rs.beforeFirst();
        Object[][] obj = new Object[count][7];
        for(int i=0; i< count && rs.next();i++){
            int id = rs.getInt("borrower_id");
            String ISBN = rs.getString("book_ISBN");
            ResultSet bor = bordb.selectOne(id);
            ResultSet b = bookdb.selectOne(ISBN);
            bor.first();
            b.first();
            obj[i][0] = "  "+"RLCS-"+rs.getString("circulationID")+"  ";
            obj[i][1] = "  "+ rs.getString("dateOut")+"  ";
            obj[i][2] = "  " + rs.getString("borrowQty") + "  ";
            obj[i][3] = "  " + rs.getString("returnedQty") + "  "; 
            obj[i][4] = "  " + bor.getString("borrowerFirstname") + " " +bor.getString("borrowerLastname") + "  ";
            obj[i][5] = "  "+ ISBN + "  ";
            obj[i][6] = "  "+b.getString("bookTitle")+"  ";
        }
        return obj;
    }
    /**
     * Books header for the table
     * @return String array
     */
    private String[] headBooks(){
        return new String[]{"ISBN","Title","Author","Publisher","Qty","Avl"};
    }
    /**
     * Creates book list as object
     * @return Object list
     * @throws SQLException Error communicating the SQL Database 
     */
    private Object[][] objBookList() throws SQLException{
        
        ResultSet rs = bookdb.selectAll();
        rs.last();
        int count = rs.getRow();
        rs.beforeFirst();
        Object[][] obj = new Object[count][6];
        for(int i = 0; i < count && rs.next(); i++ ){
            obj[i][0] = "  "+rs.getString("bookISBN")+"  ";
            obj[i][1] = "  "+rs.getString("bookTitle")+"  ";
            obj[i][2] = "  "+rs.getString("bookAuthor")+"  ";
            obj[i][3] = "  "+rs.getString("bookPublisher")+"  ";
            obj[i][4] = "  "+rs.getInt("bookQty")+"  ";
            obj[i][5] = "  "+rs.getInt("bookAvailable")+"  ";
        }
        
       return obj;
    }
    /**
     * Initializes button text and visibility
     */
    private void initButtons(){
        frame.hideButtons();
        frame.setDeleteText("Delete", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/garbage.png")));
        frame.setAddText("Add", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/plus.png")));
    }
    /**
     * Initializes the controller
     */
    private void initController(){
        frame = new ViewList();
        frame.setVisible(true);
        frame.hideButtons();
        frame.addListOptionListener(new ListOptionAction());
        frame.cancelListener(new CancelAction());
        frame.addListener(new AddAction());
        frame.deleteListener(new DeleteAction());
        frame.detailsListener(new DetailsAction());
        frame.homeListener(new HomeAction());
        frame.settingsListener(new SettingsAction());
        frame.logoutListener(new LogoutAction());
        frame.chargeListener(new ChargeAction());
        frame.returnListener(new ReturnAction());
        frame.booksListener(new BooksAction());
        frame.borrowersListener(new BorrowersAction());
        frame.setUser(admin);
        currentDateTime();
    }
    /**
     * Running thread, displays the current date and time
     */
    public void currentDateTime (){
        Thread dateTime;
        dateTime = new Thread(){
            @Override
            public void run(){
                try{
                    for(;;){
                        SimpleDateFormat dformat = new SimpleDateFormat("MMMM dd, yyyy EEEE");
                        java.util.Date date = new java.util.Date();
                        frame.setDate(dformat.format(date));
                        DateTimeFormatter tformat = DateTimeFormatter.ofPattern("hh:mm:ss a");
                        frame.setTime(LocalTime.now().format(tformat));
                        sleep(1000);
                    }
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        };
        dateTime.start();
    }
}
