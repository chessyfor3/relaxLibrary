/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.controller;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import library.view.*;
import library.model.*;
import library.util.*;

/**
 * Transaction Details Controller
 * Operations for viewing transactions, returning books, and extending due date
 * @author Jpoy
 */
public class TransDetailsController extends Thread{
    private TransactionDetails frame;
    private Book book;
    private Borrower borrower;
    private Circulation circulation;
    private boolean ret = false;
    private boolean extend = false;
    private final String admin;
    private final Thread previous;
    private int circulation_id;
    private final db_book bookdb = new db_book();
    private final db_circulation cirdb = new db_circulation();
    private final db_borrower bordb = new db_borrower();
    private final db_return retdb = new db_return();
    
    /**
     * Constructor initializing the previous controller, circulation ID and admin
     * @param previous Thread, previous controller
     * @param id Integer, circulation ID
     * @param admin String, admin username
     */
    public TransDetailsController(Thread previous, int id, String admin){
        this.previous = previous;
        this.circulation_id = id;
        this.admin = admin;
    }

    @Override
    public void run() {
        try{
            init();
        }catch(Exception e){
            frame.setResult(e.getMessage(), Color.red);
        }
    }
    /**
     * Customized ActionListener for the return button
     */
    class ReturnAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                frame.disableReturnBtn();
                frame.enableExtendBtn();
                frame.disableDueDate();
                frame.enableRetQty();
                ret = true;
                extend = false;
                frame.enableSaveBtn();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
            
        }
        
    }
    /**
     * Customized ActionListener for the extend button
     */
    class ExtendAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                frame.enableReturnBtn();
                frame.disableExtendBtn();
                frame.disableRetQty();
                frame.enableDueDate();
                extend = true;
                ret = false;
                frame.enableSaveBtn();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
            
        }
        
    }
    /**
     * Customized ActionListener for the delete button
     */
    class DeleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                int a = JOptionPane.showConfirmDialog(frame, "You are about to delete this record. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.YES_OPTION){
                    deleteRecord();
                }
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
            
        }
        
    }
    /**
     * Customized ActionListener for the save button
     */
    class SaveAction implements ActionListener{
        

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(ret){
                    saveReturned();
                    ret = false;
                }
                if(extend){
                    saveExtend();
                    extend = false;
                }
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the back button
     */
    class BackAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                previous.start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
            
        }
        
    }
    /**
     * Customized ActionListener for the reference number picker (combo box)
     */
    class ReferenceAction implements ActionListener{
        

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(frame.getReferenceIdx() == 0){
                    circulation_id = 0;
                    frame.resetAllFields("");
                    throw new Exception();
                }
                circulation_id = getID();
                setFields();
                updateButtons();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        
        }
    
    }
    /**
     * Customized ActionListener for the home menu to redirect to home controller
     */
    class HomeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                        throw new Exception("");
                    }
                }
                new HomeController(admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the settings menu to redirect to admin settings controller
     */
    class SettingsAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                        throw new Exception("");
                    }
                }
                new AdminController(admin,false).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the logout menu to logout
     */
    class LogoutAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
               if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Are you sure to logout?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                    throw new Exception("");
                    }
                }
                    
                new LoginController().start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the charge out menu to redirect to charge out
     */
    class ChargeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                        throw new Exception("");
                    }
                }
                new ChargeOutController(new TransDetailsController(previous,0,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the books menu to redirect to books
     */
    class BooksAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
           try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                        throw new Exception("");
                    }
                }
                new AddBookController(new TransDetailsController(previous,0,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
            
        }
        
    }
    /**
     * Customized ActionListener for the borrowers menu to redirect to borrowers
     */
    class BorrowersAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                    throw new Exception("");
                    }
                }
                new BorrowerController(new TransDetailsController(previous,0,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the view list menu
     * to redirect to view lists
     */
    class ViewAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                    throw new Exception("");
                    }
                }
                new ViewListController(new TransDetailsController(previous,0,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Check if text fields are empty
     * @return Boolean, true if all fields are empty, otherwise, no
     */
    
    private boolean emptyFields(){
        boolean ok = true;
        try{
            if(!frame.getRefNo().equals("")){
                throw new Exception();
            }
        }catch(Exception e){
            ok = false;
        }
        
        return ok;
    }
    /**
     * Gets the circulation ID
     * @return Integer, circulation ID
     * @throws SQLException Error communicating the SQL Database
     */
    private int getID() throws SQLException{
        ResultSet rs = cirdb.selectAll();
        rs.absolute(frame.getReferenceIdx());
        return rs.getInt("circulationID");
    }
    /**
     * Deletes the circulation record
     * @throws SQLException Error communicating the SQL Database
     */
    private void deleteRecord() throws SQLException{
        cirdb.deleteOne(circulation_id);
        retdb.deleteMultiple(circulation_id);
        previous.start();
        frame.dispose();
    }
    /**
     * Saves the extended due date
     * @throws Exception Error during the process
     */
    private void saveExtend() throws Exception{
        if(frame.getDueDate().equals(circulation.getDueBack())){
            throw new Exception("Due Date not changed");
        }
        
        cirdb.updateDueDate(frame.getDueDate(), circulation_id);
        circulation.setDueBack(frame.getDueDate());
        frame.setDueDate(new SimpleDateFormat("yyyy-MM-dd").parse(circulation.getDueBack()));
        frame.disableDueDate();
        updateButtons();
    }
    /**
     * Save returned books details and update the record in the SQL Database
     * @throws SQLException Error communicating the SQL Database
     * @throws Exception Other errors during the process
     */
    private void saveReturned() throws SQLException, Exception{
        if(frame.getReturnQty() == 0){
            throw new Exception("Set quantity of books to return");
        }
        for(int i = 0; i < frame.getReturnQty();i++){
            retdb.save(circulation_id);
            circulation.setBorrowQty(circulation.getBorrowQty()-1);
            circulation.setReturnQty(circulation.getReturnQty()+1);
            cirdb.updateQty(circulation.getBorrowQty(), circulation.getReturnQty(), circulation_id);
            book.setAvailableCount(book.getAvailableCount()+1);
            bookdb.updateAvailable(book.getAvailableCount(), book.getISBN());
            borrower.setBorrowQty(borrower.getBorrowQty()-1);
            bordb.updateQty(borrower.getBorrowQty(),circulation.getBorrower_id());
        }
        frame.setBorrowQty(Integer.toString(circulation.getBorrowQty()));
        frame.setReturnQty(circulation.getBorrowQty());
        frame.setReturned(Integer.toString(circulation.getReturnQty()));
        frame.setBookAvailable(Integer.toString(book.getAvailableCount()));
        frame.disableRetQty();
        updateButtons();
    }
    /**
     * Sets the circulation model and text fields
     * @param id Integer, circulation ID
     * @throws SQLException Error communicating the SQL Database
     * @throws ParseException Error while parsing the date
     */
    private void setCirculation(int id) throws SQLException, ParseException{
        ResultSet rs = cirdb.selectOne(id);
        rs.first();
        circulation = new Circulation(rs.getString("dateOut"), rs.getString("dueBack"),rs.getInt("borrowQty"),rs.getString("book_ISBN"),rs.getInt("borrower_id"),rs.getInt("returnedQty"));
        frame.setCirculationFields("RLCS-"+rs.getString("circulationID"),Integer.toString(circulation.getBorrowQty()), new SimpleDateFormat("yyyy-MM-dd").parse(circulation.getDueBack()),Integer.toString(circulation.getReturnQty()));
    }
    /**
     * Sets the borrower model and text fields
     * @param id Integer, borrower ID
     * @throws SQLException Error communicating the SQL Database
     */
    private void setBorrower(int id) throws SQLException{
        ResultSet rs = bordb.selectOne(id);
        rs.first();
        borrower = new Borrower(rs.getString("borrowerFirstname"),rs.getString("borrowerLastname"),rs.getString("borrowerEmail"), rs.getString("borrowerPhone"),rs.getInt("borrowQty"));
        borrower.setBorrowQty(rs.getInt("borrowQty"));
        frame.setBorrowerFields("10010"+rs.getString("borrowerID"),borrower.getFirstname()+" "+borrower.getLastname(),borrower.getEmail(),borrower.getContactNo());
    }
    /**
     * Sets the book model and text fields
     * @param ISBN String, book ISBN
     * @throws SQLException Error communicating the SQL Database
     */
    private void setBook(String ISBN) throws SQLException{
        ResultSet rs = bookdb.selectOne(ISBN);
        rs.first();
        book = new Book(ISBN, rs.getString("bookTitle"), rs.getString("bookAuthor"), rs.getString("bookPublisher"), rs.getInt("bookQty") );
        book.setAvailableCount( rs.getInt("bookAvailable"));
        frame.setBookFields(ISBN, book.getTitle(), book.getAuthor(), book.getPublisher(), Integer.toString(book.getQuantity()),Integer.toString(book.getAvailableCount()));
    }
    /**
     * Updates the text and usability of the buttons
     */
    private void updateButtons(){
        frame.disableSaveBtn();
        if(circulation.getBorrowQty()!= 0){
            frame.disableDeleteBtn();
            frame.enableExtendBtn();
            frame.enableReturnBtn();
            
        }else{
            frame.enableDeleteBtn();
            frame.disableExtendBtn();
            frame.disableReturnBtn();
        }
        
    }
    /**
     * Sets the circulation, book, borrower and circulation by calling setCirculation(),
     * setBook() and setBorrower()
     * @throws SQLException Error communicating the SQL Database
     * @throws ParseException Error while parsing the date
     */
    private void setFields() throws SQLException, ParseException{
        setCirculation(circulation_id);
        setBook(circulation.getBook_ISBN());
        setBorrower(circulation.getBorrower_id());
        
    }
    /**
     * Initializes the controller
     * @throws SQLException Error communicating the SQL Database
     * @throws ParseException Error parsing the date
     */
    private void init() throws SQLException, ParseException{
        frame = new TransactionDetails();
        currentDateTime();
        if(circulation_id != 0){
            setFields();
        }else{
            frame.disableDeleteBtn();
            frame.disableExtendBtn();
            frame.disableReturnBtn();
        }
        ResultSet rs = cirdb.selectAll();
        while(rs.next()){
            frame.addRefNo("RLCS-"+rs.getString("circulationID"));
        }
        frame.setVisible(true);
        frame.returnBtnListener(new ReturnAction());
        frame.extendBtnListener(new ExtendAction());
        frame.deleteBtnListener(new DeleteAction());
        frame.saveBtnListener(new SaveAction());
        frame.backBtnListener(new BackAction());
        frame.refNoListListener(new ReferenceAction());
        
        frame.homeListener(new HomeAction());
        frame.settingsListener(new SettingsAction());
        frame.logoutListener(new LogoutAction());
        frame.chargeListener(new ChargeAction());
        frame.booksListener(new BooksAction());
        frame.borrowersListener(new BorrowersAction());
        frame.viewListener(new ViewAction());
        frame.setUser(admin);
        updateButtons();
        
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
                        frame.setDateMenu(dformat.format(date));
                        DateTimeFormatter tformat = DateTimeFormatter.ofPattern("hh:mm:ss a");
                        frame.setTimeMenu(LocalTime.now().format(tformat));
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
