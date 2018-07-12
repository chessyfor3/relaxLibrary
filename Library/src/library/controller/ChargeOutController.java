/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.controller;
import java.awt.Color;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import javax.swing.*;
import library.view.*;
import library.model.*;
import library.util.*;
/**
 * Charge Out Controller
 * Operations for borrowing of books
 * @author Jpoy
 */
public class ChargeOutController extends Thread{
    private ChargeOutForm frame;
    private Book book;
    private Borrower borrower;
    private Circulation circulation;
    private final String admin;
    private final Thread previous;
    private final ChargeOutController main = this;
    private final db_circulation cirdb = new db_circulation();
    private final db_book bookdb = new db_book();
    private final db_borrower bordb = new db_borrower();
    
    /**
     * Constructor initializing the previous controller and the admin
     * @param previous
     * @param admin 
     */
    public ChargeOutController(Thread previous, String admin){
        this.previous = previous;
        this.admin = admin;
    }
    
    @Override
    public void run(){
        try{
            initController();
        }catch(SQLException ex){
            frame.setResultMessage(ex.getMessage(), Color.red);
        }catch(Exception e){
            frame.setResultMessage(e.getMessage(), Color.red);
        }
    }
    /**
     * Customized ActionListener for the charge out button
     */
    class ChargeOutRequest implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResultMessage();
            try{
                
                checkBorrowerFields();
                checkOtherFields();
                if(frame.getBorrowerIdx() == 0){
                    ifExists();
                    saveBorrower();
                }else{
                    if(updateBorrower() == 0){
                        throw new Exception("Error updating. .");
                    }
                }
                saveCirculation();
                frame.emptyBookFields();
                frame.emptyBorrowerFields();
                frame.setResultMessage("Saved Successfully", Color.black);
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the ISBN picker(combo box)
     */
    class ISBNRequest implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            String ISBN = frame.getBookISBN();
            frame.resetResultMessage();
            try {
                frame.resetResultMessage();
                
                if(ISBN.equals("-- Select ISBN --")){
                    frame.emptyBookFields();
                    throw new Exception("Select an ISBN");
                }
                setBook(ISBN);
            } catch (SQLException ex) {
                frame.setResultMessage(ex.getMessage(), Color.red);
            } catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
            
                    
        }
        
    }
    /**
     * Customized ActionListener for the borrower picker (combo box)
     */
    class BorrowerRequest implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResultMessage();
            try{
                int idx = frame.getBorrowerIdx();
                if(idx == 0){
                    frame.emptyBorrowerFields();
                    frame.enableBorrowerFields();
                    setBorrowFieldsBlack();
                    throw new Exception("Enter Borrower Information");
                }
                setBorrower(getBorrowerID(idx));
                frame.disableBorrowerFields();
                setBorrowFieldsGreen();
            }catch (SQLException ex) {
                frame.setResultMessage(ex.getMessage(), Color.red);
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the cancel button
     */
    class Cancel implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResultMessage();
            try{
                previous.start();
                frame.dispose();
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
            
            
        }
        
    }
    /**
     * Customized FocusListener when text fields are out focused
     */
    class Focus implements FocusListener{

        @Override
        public void focusGained(FocusEvent fe) {
            fe.getComponent().setBackground(new java.awt.Color(255,255,204));
        }

        @Override
        public void focusLost(FocusEvent e) {
            try{
            String var_name = e.getComponent().getAccessibleContext().getAccessibleName();
            String regex =  "^[a-z,A-Z,0-9_+&*-]+(?:\\."+"[a-z,A-Z,0-9_+&*-]+)*@" +"(?:[a-z,A-Z,0-9-]+\\.)+[a-z" +"A-Z]{2,7}$";
            Pattern pat = Pattern.compile(regex);
            Matcher matcher = pat.matcher(frame.getBorrowerEmail());
            e.getComponent().setBackground(Color.WHITE);
            switch(var_name){
                case "borrowerFirstname": 
                    if(frame.getBorrowerFirstname().equals("")){
                        frame.borrowerFirstname(BorderFactory.createLineBorder(Color.red,1));
                    }else{
                        frame.borrowerFirstname(BorderFactory.createLineBorder(Color.green,1));
                    }
                    break;
                case "borrowerLastname": 
                    if(frame.getBorrowerLastname().equals("")){
                        frame.borrowerLastname(BorderFactory.createLineBorder(Color.red,1));
                    }else{
                        frame.borrowerLastname(BorderFactory.createLineBorder(Color.green,1));
                    }
                    
                    break;
                case "borrowerEmail": 
                    if(frame.getBorrowerEmail().equals("") || !matcher.matches()){
                        frame.borrowerEmail(BorderFactory.createLineBorder(Color.red,1));
                    }else{
                        frame.borrowerEmail(BorderFactory.createLineBorder(Color.green,1));
                    }
                    break;
                case "borrowerPhone": 
                    if(frame.getBorrowerPhone().equals("(+63)             ") || !phonecheck(frame.getBorrowerPhone())){
                        frame.borrowerPhone(BorderFactory.createLineBorder(Color.red,1));
                        frame.setPhoneVal(null);
                        frame.setBorrowerPhone("(+63)             ");
                    }else{
                        frame.borrowerPhone(BorderFactory.createLineBorder(Color.green,1));
                    }
                    break;
            }
            }catch(Exception ex){
                frame.setResultMessage(ex.getMessage(), Color.red);
            }
            
        }
        
    }
    /**
     * Customized ActionListener for the view transaction button 
     * which redirects to the view details controller
     */
    class ViewTransAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                        throw new Exception("");
                    }
                }
                new TransDetailsController(new ChargeOutController(previous,admin),0,admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the home menu which redirects to the home controller
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
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     *  Customized ActionListener for the settings menu which redirects to the admin settings controller
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
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the logout menu 
     * which redirects to the login page controller
     */
    class LogoutAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                        throw new Exception("");
                    }
                }
                new LoginController().start();
                frame.dispose();
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the return book menu 
     * which redirects to the transaction details controller
     */
    class ReturnAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame, "Save changes first before leaving. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                        throw new Exception("");
                    }
                }
                new TransDetailsController(new ChargeOutController(previous,admin),0,admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the books menu which redirects to the books controller
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
                new AddBookController(new ChargeOutController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the borrowers menu 
     * which redirects to the borrowers controller
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
                new BorrowerController(new ChargeOutController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     *  Customized ActionListener for the view menu which redirects to the view controller
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
                new ViewListController(new ChargeOutController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResultMessage(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Checks the text fields for any invalid input
     * @return True if all text fields are empty, otherwise false
     */
    private boolean emptyFields(){
        boolean ok = true;
        try{
            if(!frame.getBorrowerFirstname().equals("")){
                throw new Exception("");
            }
            if(!frame.getBorrowerLastname().equals("")){
                throw new Exception("");
            }
            if(!frame.getBorrowerEmail().equals("")){
                throw new Exception("");
            }
            if(!frame.getBorrowerPhone().equals("(+63)             ")){
                throw new Exception("");
            }
            if(!frame.getBookTitle().equals("")){
                throw new Exception("");
            }
        }catch(Exception e){
            ok = false;
        }
        return ok;
    }
    /**
     * Sets the borrower model from the SQL Database
     * @param id The id of the borrower
     * @throws SQLException Error communicating the SQL Database
     */
    private void setBorrower(int id) throws SQLException{
        ResultSet rs = bordb.selectOne(id);
        rs.first();
        borrower = new Borrower(rs.getString("borrowerFirstname"),rs.getString("borrowerLastname"),rs.getString("borrowerEmail"), rs.getString("borrowerPhone"),rs.getInt("borrowQty"));
        borrower.setBorrowQty(rs.getInt("borrowQty"));
        frame.setBorrowerFields(borrower.getFirstname(),borrower.getLastname(),borrower.getEmail(),borrower.getContactNo());
    }
    /**
     * Sets the book model information from the SQL Database
     * @param ISBN The ISBN of the book
     * @throws SQLException Error communicating the SQL Database
     */
    private void setBook(String ISBN) throws SQLException{
        ResultSet rs = bookdb.selectOne(ISBN);
        rs.first();
        book = new Book(ISBN, rs.getString("bookTitle"), rs.getString("bookAuthor"), rs.getString("bookPublisher"), rs.getInt("bookQty") );
        book.setAvailableCount( rs.getInt("bookAvailable"));
        frame.setBookFields(book.getTitle(), book.getAuthor(),book.getPublisher(), rs.getString("bookQty"), rs.getString("bookAvailable"));
        frame.setBorrowQty(book.getAvailableCount());
    }
    /**
     * Updates the borrower borrow quantity
     * @return Integer for updating the borrower in the SQL Database
     * @throws SQLException Error communicating the SQL Database
     */
    private int updateBorrower() throws SQLException{
        int borrower_id = getBorrowerID(frame.getBorrowerIdx());;
        borrower.setBorrowQty(borrower.getBorrowQty()+frame.getBorrowQty());
        return bordb.updateQty(borrower.getBorrowQty(), borrower_id);
    }
    /**
     * Saves the new borrower into the SQL Database
     * @throws SQLException Error communicating the SQL Database
     * @throws Exception Other errors during the process
     */
    private void saveBorrower() throws SQLException, Exception{
        String fname = frame.getBorrowerFirstname();
        String lname = frame.getBorrowerLastname();
        String email = frame.getBorrowerEmail();
        String phone = frame.getBorrowerPhone();
        fname = capitalizeFirstLetters(fname);
        lname = capitalizeFirstLetters(lname);
        borrower = new Borrower(fname,lname,email,phone,frame.getBorrowQty());
        if(bordb.saveBorrower(borrower)== 0){
            throw new Exception("Failed Adding Borrower");
        }
        frame.addBorrowerID(fname + " " + lname);
    }
    /**
     * Sets the circulation model and saves it to the SQL Database
     * @throws SQLException Error communicating the SQL Database
     * @throws Exception Other errors during the process
     */
    private void saveCirculation() throws SQLException, Exception{
        int borrower_id;
        if(frame.getBorrowerIdx() == 0){
            borrower_id = getBorrowerID(bordb.count());
        }else{
            borrower_id = getBorrowerID(frame.getBorrowerIdx());
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        circulation = new Circulation(dateFormat.format(new java.util.Date()),frame.getDueDate(),frame.getBorrowQty(),frame.getBookISBN(),borrower_id,0);
        if(cirdb.saveCirculation(circulation) == 0){
            throw new Exception("Failed Saving Circulation details");
        }
        book.setAvailableCount(book.getAvailableCount()-circulation.getBorrowQty());
        int i = bookdb.updateAvailable(book.getAvailableCount(), book.getISBN());
        if(i == 0){
            throw new Exception("Failed Saving Circulation details");
        }
        borrower.setBorrowQty(borrower.getBorrowQty()+frame.getBorrowQty());
        
    }
    /**
     * Capitalizes the first letters of a string
     * @param str The string to be edited
     * @return New string with the capitalized first letters
     */
    private String capitalizeFirstLetters(String str){
        String newWord = "";
        char[] temp = str.toCharArray();
        int s;
        for(int i = s = 0; i < str.length(); i++){
            if(temp[i] == ' ' || i == (str.length()-1)){ 
                newWord += str.substring(s, s+1).toUpperCase()+str.substring(s+1,i+1)+" " ;
                s = i+1;
            } 
        }
        return newWord;
    }
    /**
     * Gets the borrower ID 
     * @param idx Index from the combo box (select borrower)
     * @return The borrower ID
     * @throws SQLException Error communicating the SQL Database
     */
    private int getBorrowerID(int idx) throws SQLException{
        ResultSet rs = bordb.selectAll();
        rs.absolute(idx);
        return rs.getInt("borrowerID");
    }
    /**
     * Set the color of the borders of the text fields in the borrower information into black
     */
    private void setBorrowFieldsBlack(){
        frame.borrowerFirstname(BorderFactory.createLineBorder(Color.black,1));
        frame.borrowerLastname(BorderFactory.createLineBorder(Color.black,1));
        frame.borrowerEmail(BorderFactory.createLineBorder(Color.black,1));
        frame.borrowerPhone(BorderFactory.createLineBorder(Color.black,1));
    }
    /**
     * Set the color of the borders of the text fields in the borrower information into green
     */
    private void setBorrowFieldsGreen(){
        frame.borrowerFirstname(BorderFactory.createLineBorder(Color.green,1));
        frame.borrowerLastname(BorderFactory.createLineBorder(Color.green,1));
        frame.borrowerEmail(BorderFactory.createLineBorder(Color.green,1));
        frame.borrowerPhone(BorderFactory.createLineBorder(Color.green,1));
    }
    /**
     * Checks the phone number if valid
     * @param str The phone number
     * @return True if valid, otherwise false
     */
    private boolean phonecheck(String str){
        char[] chars = str.toCharArray();
        boolean ok = true;
        for ( int i = 0; i < chars.length; i++ ) {

            try {
                if(i != 0 && i!= 1 && i != 4 && i != 5 && i != 9 && i != 13){
                    Integer.parseInt( String.valueOf( chars[i] ) );
                }
                
            } catch ( NumberFormatException exc ) {
                ok = false;
                break;
            }
        }
        return ok;
    }
    /**
     * Adds focus listeners to the text fields
     */
    private void addFocusListener(){
        frame.addFirstnameListener(new Focus());
        frame.addEmailListener(new Focus());
        frame.addLastnameListener(new Focus());
        frame.addPhoneListener(new Focus());
        frame.addDueDateListener(new Focus());
    }
    /**
     * Checks the other fields of the form for any invalid input
     * @throws Exception Error for any invalid input and others
     */
    private void checkOtherFields() throws Exception{
        frame.resetResultMessage();
        if(frame.getBookISBN().equals("-- Select ISBN --")){
            throw new Exception("Please select a book");
        }
        if(0 == (Integer)frame.getBorrowQty()){
            throw new Exception("Invalid Quantity");
        }
        
    }
    /**
     * Checks borrower's existence in the SQL Database
     * @throws SQLException Error communicating the SQL Database
     * @throws Exception Other errors during the process
     */
    private void ifExists() throws SQLException, Exception{
        String fname = frame.getBorrowerFirstname();
        String lname = frame.getBorrowerLastname();
        String email = frame.getBorrowerEmail();
        ResultSet rs = bordb.selectAll();
        while(rs.next()){
            if(fname.equals(rs.getString("borrowerFirstname")) && lname.equals(rs.getString("borrowerLastname"))){
                throw new Exception("Borrower already exists. Select the borrower for the dropdown");
            }
            if(email.equals(rs.getString("borrowerEmail"))){
                throw new Exception("Email is being used already. Choose another email");
            }
        }
    }
    /**
     * Checks the borrower text fields for any invalid input
     * @throws Exception Error for any invalid input and others
     */
    private void checkBorrowerFields() throws Exception{
        frame.resetResultMessage();
        if(frame.getBorrowerFirstname().equals("")){
            throw new Exception("Empty Firstname");
        }
        if(frame.getBorrowerLastname().equals("")){
            throw new Exception("Empty Lastname");
        }
        String regex =  "^[a-z,A-Z,0-9_+&*-]+(?:\\."+"[a-z,A-Z,0-9_+&*-]+)*@" +"(?:[a-z,A-Z,0-9-]+\\.)+[a-z" +"A-Z]{2,7}$";
        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(frame.getBorrowerEmail());
        if(frame.getBorrowerEmail().equals("")){
            throw new Exception("Please input email");
        }
        if(!matcher.matches()){
            throw new Exception("Invalid email");
        }
        if(frame.getBorrowerPhone().equals("(+63)             ")){
            throw new Exception("Invalid Phone number");
        }
    }
    /**
     * Initializing the controller to charge out mode
     */
    private void chargeOutMode(){
        frame.addISBNListener(new ISBNRequest());
        frame.addBorrowerListener(new BorrowerRequest());
        addFocusListener();
        frame.setDueDate(new java.util.Date());
        
    }
    /**
     * Initializes the controller
     * @throws SQLException 
     */
    private void initController() throws SQLException{
        frame = new ChargeOutForm();
        frame.setVisible(true);
        frame.hideReturnQty();
        ResultSet b = bookdb.selectAll();
        while(b.next()){
            frame.addBookISBN(b.getString("bookISBN"));
        }
        ResultSet br = bordb.selectAll();
        while(br.next()){
            frame.addBorrowerID(br.getString("borrowerFirstname") + " " + br.getString("borrowerLastName"));
        }
        chargeOutMode();
        frame.addChargeOutListener(new ChargeOutRequest());
        frame.addCancelLisener(new Cancel());
        frame.viewTransListener(new ViewTransAction());
        frame.homeListener(new HomeAction());
        frame.settingsListener(new SettingsAction());
        frame.logoutListener(new LogoutAction());
        frame.returnListener(new ReturnAction());
        frame.booksListener(new BooksAction());
        frame.borrowersListener(new BorrowersAction());
        frame.viewListener(new ViewAction());
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
