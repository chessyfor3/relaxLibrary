/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.controller;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Thread.sleep;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import library.model.*;
import library.util.*;
import library.view.*;
/**
 *  Books Controller
 *  This class is the controller for the view(jFrame), AddBook class, and uses the model, Book class.
 *  All the operations for adding, editing, deleting and viewing books.
 * @author Jpoy
 */

public class AddBookController extends Thread{
    private final Thread previous;
    private AddBook form;
    private boolean edit = false;
    private Book book;
    private final String admin;
    private final db_book bookdb = new db_book();
    /**
     * Initializes some fields of the instance, previous thread and admin
     * @param previous The previous thread prior to this thread
     * @param admin The username of the admin
     */
    public AddBookController(Thread previous, String admin){
        this.previous = previous;
        this.admin = admin;
    }
    /**
     * Overrides the method run()
     */
    @Override
    public void run(){
        try{
            initAddBook();
            
        }catch(Exception e){
            form.setResult(e.getMessage(),Color.red);
        }
        
    }
    /**
     * Inner class that implements ActionListener to perform adding operations for the add button
     */
    class AddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            form.resetResult();
            try{
                if(edit == true){
                    saveEdit();
                    resetFields();
                    setTable();
                    edit = false;
                }else{
                    checkFields();
                    saveBook();
                    setTable();
                }
                form.setResult("Success",Color.black);
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to perform edit operations for edit button
     */
    class EditAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            form.resetResult();
            try{
                
                if(!edit){
                    if(!emptyFields()){
                        int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                        if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                            throw new Exception("");
                        }
                    }
                    resetFields();
                    editBook();
                    edit = true;
                }else{
                    resetFields();
                    edit = false;
                }
                
                
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to perform delete operations for delete button
     */
    class DeleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            form.resetResult();
            try{
                if(form.tableSelectedRow() == -1){
                    throw new Exception("Please select a book");
                }
                
                deleteBook();
                form.setResult("Book successfully deleted",Color.black);
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
    }
    /**
     * Inner class that implements MouseListener to perform operations for the table
     */
    class TableAction implements MouseListener{
        /**
         * Overrides mouseClicked
         * @param me Mouse Event
         */
        @Override
        public void mouseClicked(MouseEvent me) {
            form.resetResult();
            try{
                if(edit == true){
                    String ISBN = form.tableFirstCol(form.tableSelectedRow());
                    setBook(ISBN);
                    form.setMinQty(book.getQuantity());
                }
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
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
     * Inner class that implements ActionListener to cancel operations and go back to the previous thread
     */
    class BackAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            form.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                previous.start();
                form.dispose();
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to open the admin settings
     */
    class SettingsAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new AdminController(admin,false).start();
                form.dispose();
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to logout the current admin user
     */
    class LogoutAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                int a = JOptionPane.showConfirmDialog(form,"Are you sure to logout?","Warning",JOptionPane.YES_NO_OPTION);
                if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                    throw new Exception("");
                }
                new LoginController().start();
                form.dispose();
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to direct to the charge out form
     */
    class ChargeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new ChargeOutController(new AddBookController(previous,admin),admin).start();
                form.dispose();
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to direct to the view transaction details
     */
    class ReturnAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new TransDetailsController(new AddBookController(previous,admin), 0,admin).start();
                form.dispose();
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to direct to view the lists
     */
    class ViewAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new ViewListController(new AddBookController(previous,admin),admin).start();
                form.dispose();
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to direct to the borrowers module
     */
    class BorrowersAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(form,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new BorrowerController(new AddBookController(previous,admin),admin).start();
                form.dispose();
            }catch(Exception e){
                form.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Method to check enabled JTextFields if empty
     * @return Boolean, true if the fields are empty, otherwise, if one is not empty, false
     */
    private boolean emptyFields(){
        boolean empty = true;
        try{
            if(!form.getISBN().equals("   - -  -      - ")){
                throw new Exception();
            }
            if(!form.getBookTitle().equals("")){
                throw new Exception();
            }
            if(!form.getAuthor().equals("")){
                throw new Exception();
            }
            if(!form.getPublisher().equals("")){
                throw new Exception();
            }
            
        }catch(Exception e){
            empty = false;
        }
        return empty;
    }
    /**
     * Performs saving of the changes of the book data
     * @throws Exception Error that occurred within the operation
     */
    private void saveEdit() throws Exception{
        String oldISBN = book.getISBN();
        book.setISBN(form.getISBN());
        book.setTitle(form.getBookTitle());
        book.setAuthor(form.getAuthor());
        book.setPublisher(form.getPublisher());
        book.setAvailableCount(book.getAvailableCount()+(form.getQuantity()-book.getQuantity()));
        book.setQuantity(form.getQuantity());
        
        bookdb.updateAllFields(book.getISBN(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getQuantity(), book.getAvailableCount(), oldISBN);
        
        bookdb.updateQuantity(book.getQuantity(), book.getISBN());
        bookdb.updateAvailable(book.getAvailableCount(), book.getISBN());
        form.setAvailable(Integer.toString(book.getAvailableCount()));
    }
    /**
     * Performs deleting of the book 
     * @throws SQLException Error that occurred while performing SQL operations
     * @throws Exception  Error that occurred while performing the operations
     */
    private void deleteBook() throws SQLException, Exception{
        setBook(form.tableFirstCol(form.tableSelectedRow()));
        resetFields();
        if(book.getQuantity() != book.getAvailableCount()){
            throw new Exception("Unable to delete. Book being borrowed");
        }
        int a = JOptionPane.showConfirmDialog(form, "Are you sure to delete this book?","Warning", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
            throw new Exception("");
        }
        bookdb.deleteOne(book.getISBN());
        setTable();
        book = null;
    }
    /**
     * Sets the text of the text fields to blank
     */
    private void resetFields(){
        form.setMaxQty(1000);
        form.setAddBtnText("Add", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/plus.png")));
        form.setEditBtnText("Edit", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/edit.png")));
        
        form.resetAllTextFields("", 0);
        form.hideAvailable();
    }
    /**
     * Performs operations to prepare frame for editing
     * @throws Exception Error that occurred while performing the operation
     */
    private void editBook() throws Exception{
        
        if(form.tableSelectedRow() == -1){
            throw new Exception("Please select from the list");
        }
        String ISBN = form.tableFirstCol(form.tableSelectedRow());
        setBook(ISBN);
        form.setMinQty(book.getQuantity());
        
        form.showAvailable();
        form.setEditBtnText("Cancel", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/ex16.png")));
        form.setAddBtnText("Save", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/save16.png")));

    }
    /**
     * Saving the book information to the SQL Database
     * @throws SQLException Error that occurred while saving the book to the SQL Database
     */
    private void saveBook() throws SQLException{
        book = new Book(form.getISBN(),form.getBookTitle(),form.getAuthor(),form.getPublisher(),form.getQuantity());
        bookdb.saveToDB(book);
        resetFields();
    }
    /**
     * Creating an instance of the book to be used in the operation and setting book information text fields
     * @param ISBN The ISBN of the book
     * @throws SQLException  Error that occurred while communicating to the SQL Database
     */
    private void setBook(String ISBN) throws SQLException{
        ResultSet rs = bookdb.selectOne(ISBN);
        rs.first();
        book = new Book(ISBN,rs.getString("bookTitle"),rs.getString("bookAuthor"),rs.getString("bookPublisher"),rs.getInt("bookQty"));
        book.setAvailableCount(rs.getInt("bookAvailable"));
        form.setISBN(ISBN);
        form.setBookTitle(book.getTitle());
        form.setAuthor(book.getAuthor());
        form.setPublisher(book.getPublisher());
        form.setQuantity(book.getQuantity());
        form.setMinQty(book.getQuantity());
        form.setAvailable(Integer.toString(book.getAvailableCount()));
    }
    /**
     * Setting the other information such as total quantity of books, number of available books, quantity of books borrowed 
     * @throws SQLException Error that occurred while getting information from SQL Database
     */
    @SuppressWarnings("empty-statement")
    private void setOtherInfo() throws SQLException{
        ResultSet rs = bookdb.selectAll();
        int total, borrow, types, onhand;
        for(total = borrow = types = onhand = 0; rs.next();types++){
            for(int i = 0; i < rs.getInt("bookQty");i++, total++);
            onhand += rs.getInt("bookAvailable");
            borrow += (rs.getInt("bookQty") - rs.getInt("bookAvailable"));
        }
        form.setTotalNo(Integer.toString(total));
        form.setNoOfTypes(Integer.toString(types));
        form.setQtyBorrowed(Integer.toString(borrow));
        form.setQtyOnHand(Integer.toString(onhand));
    }
    /**
     * Initializing the table model from SQL Database
     * @throws SQLException Error that occurred while initializing the table
     */
    private void setTable() throws SQLException{
        String[] header = new String[]{"ISBN", "Title", "Qty","Avl"}; 
        ResultSet rs = bookdb.selectAll();
        rs.last();
        int count = rs.getRow();
        Object[][] data = new Object[count][4];
        rs.beforeFirst();
        for(int i = 0; i < count && rs.next(); i++){
            data[i][0] = rs.getString("bookISBN");
            data[i][1] = "  "+rs.getString("bookTitle")+"  ";
            data[i][2] = "  "+rs.getInt("bookQty")+"  ";
            data[i][3] = "  "+rs.getInt("bookAvailable")+"  ";
        }
        form.initTable(data, header);
        setOtherInfo();
    }
    /**
     * Check all text fields for empty fields and invalid input
     * @throws Exception Error that occurred while performing the operation and if requirement not met
     */
    private void checkFields() throws Exception{
        if(form.getISBN().equals("   - -  -      - ")){
            throw new Exception("Please input ISBN");
        }
        if(form.getBookTitle().equals("")){
            throw new Exception("Please input Title");
        }
        if(form.getAuthor().equals("")){
            throw new Exception("Please input Author");
        }
        if(form.getPublisher().equals("")){
            throw new Exception("Please input Publisher");
        }
        if(bookdb.selectOne(form.getISBN()).first()){
            throw new Exception("ISBN already exists");
        }
    }
    /**
     * Initializes the frame for this controller which is the AddBook JFrame
     * @throws SQLException Error that occurred while communicating to the SQL Database
     */
    private void initAddBook() throws SQLException{
        this.form = new AddBook();
        setTable();
        
        form.addBtnListener(new AddAction());
        form.backBtnListener(new BackAction());
        form.editBtnListener(new EditAction());
        form.deleteBtnListener(new DeleteAction());
        form.tableAddListener(new TableAction());
        form.userLogoutListener(new LogoutAction());
        form.userSettingsListener(new SettingsAction());
        form.chargeMenuListener(new ChargeAction());
        form.viewMenuListener(new ViewAction());
        form.returnMenuListener(new ReturnAction());
        form.borrowersListener(new BorrowersAction());
        form.hideAvailable();
        form.setVisible(true);
        form.setMaxQty(1000);
        form.setUserMenu(admin);
        currentDateTime();
    }
    /**
     * Running thread, display the current date and time while the app is running
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
                        form.setDate(dformat.format(date));
                        DateTimeFormatter tformat = DateTimeFormatter.ofPattern("hh:mm:ss a");
                        form.setTime(LocalTime.now().format(tformat));
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
