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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import library.model.*;
import library.util.*;
import library.view.*;
/**
 * Borrower Controller
 * This class is the controller for the view BorrowerFrame and uses the model Borrower
 * All operations for adding, editing, view and deleting a borrower
 * @author Jpoy
 */
public class BorrowerController extends Thread{
    private BorrowerFrame frame;
    private Borrower borrower;
    private final String admin;
    private int borrower_id;
    private boolean edit = false;
    private final Thread previous;
    private final db_borrower bordb = new db_borrower();
    private final db_admin addb = new db_admin();
    /**
     * Constructor that initializes the previous controller thread and sends the user 
     * @param previous The previous controller thread
     * @param user The username of the user
     */
    public BorrowerController(Thread previous, String user){
        this.previous = previous;
        admin = user;
    }
    /**
     * Overrides the method run of the thread
     */
    @Override
    public void run() {
        try{
            init();
        }catch(Exception e){
            frame.setResult(e.getMessage(),Color.red);
        }
    }
    /**
     * Customized ActionListener for the add button. Adding and saving the information of the borrower
     */
    class AddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(edit == true){
                    saveEdit();
                    resetFields();
                    setTable();
                    edit = false;
                }else{
                    checkFields();
                    saveBorrower();
                    setTable();
                }
                frame.setResult("Successfully saved",Color.black);
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the delete button. Deleting the borrower
     */
    class DeleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                deleteBorrower();
                frame.setResult("Borrower successfully deleted",Color.black);
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the edit button. This ActionListener can be of two
     * use, first one is for preparing the borrower to be edited, and the second one, canceling
     * the edit
     */
    class EditAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!edit){
                    if(!emptyFields()){
                        int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                        if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
                            throw new Exception("");
                        }
                    }
                    resetFields();
                    editBorrower();
                    edit = true;
                }else{
                    resetFields();
                    edit = false;
                }
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the back button which redirects to the previous controller
     */
    class BackAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                previous.start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized MouseListener for the table, when a row is being selected
     */
    class TableAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            frame.resetResult();
            try{
                if(edit == true){
                    borrower_id = getID();
                    setBorrower(borrower_id);
                }
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
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
     * Customized ActionListener for the settings menu of the user to redirect to the controller
     */
    class SettingsAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
           try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new AdminController(admin,false).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the home menu to redirect to the home controller
     */
    class HomeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new HomeController(admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the charge out menu to redirect to the charge out controller
     */
    class ChargeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new ChargeOutController(new BorrowerController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the return menu which redirects to the
     * controller for returning of books
     */
    class ReturnAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new TransDetailsController(new BorrowerController(previous,admin),0,admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the book menu which redirects to the books controller
     */
    class BooksAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new AddBookController(new BorrowerController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the view menu which redirects
     * to view the lists of books, borrowers, transactions, returned and unreturned books
     */
    class ViewAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                new ViewListController(new BorrowerController(previous,admin),admin).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
        
    }
    /**
     * Customized ActionListener for the logout menu to logout the user
     */
    class LogoutAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(!emptyFields()){
                    int a = JOptionPane.showConfirmDialog(frame,"Changes have not been saved. Continue?","Warning",JOptionPane.YES_NO_OPTION);
                    if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                        throw new Exception("");
                    }
                }
                int a = JOptionPane.showConfirmDialog(frame,"Are you sure to logout?","Warning",JOptionPane.YES_NO_OPTION);
                if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                    throw new Exception("");
                }
                new LoginController().start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(),Color.red);
            }
        }
    }
    /**
     * Checks if all fields are empty. Returns false if one field is not empty
     * @return The boolean return, false - not all are empty, true - all are empty
     */
    
    private boolean emptyFields(){
        boolean empty = true;
        try{
            if(!frame.getFname().equals("")){
                throw new Exception();
            }
            if(!frame.getLname().equals("")){
                throw new Exception();
            }
            if(!frame.getEmail().equals("")){
                throw new Exception();
            }
            if(!frame.getPhone().equals("(+63)             ")){
                throw new Exception();
            }
            
        }catch(Exception e){
            empty = false;
        }
        return empty;
    }
    /**
     * Saves the edited information of the borrower
     * @throws SQLException  Error occurred when accessing the SQL Database
     */
    private void saveEdit() throws SQLException{
        borrower.setFirstname(capitalizeFirstLetters(frame.getFname()));
        borrower.setLastname(capitalizeFirstLetters(frame.getLname()));
        borrower.setEmail(frame.getEmail());
        borrower.setContactNo(frame.getPhone());
        
        bordb.updateFirstname(borrower.getFirstname(), borrower_id);
        bordb.updateLastname(borrower.getLastname(), borrower_id);
        bordb.updateEmail(borrower.getEmail(), borrower_id);
        bordb.updatePhone(borrower.getContactNo(), borrower_id);
        resetFields();
    }
    /**
     * Process of deleting the borrower
     * @throws Exception Error occurred when deleting
     */
    private void deleteBorrower() throws Exception{
        if(frame.tableSelectedRow() == -1){
            throw new Exception("Please select from the list");
        }
        borrower_id = getID();
        ResultSet rs = addb.selectByBorrower(borrower_id);
        if(rs.first()){
            throw new Exception("Cannot delete admin");
        }
        setBorrower(borrower_id);
        resetFields();
        if(borrower.getBorrowQty()!=0){
            throw new Exception("Unable to delete. Borrower's borrowing");
        }
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure to delete this borrower?","Warning", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION){
            throw new Exception("");
        }
        bordb.deleteOne(borrower_id);
        setTable();
        borrower = null;
    }
    /**
     * Prepares the borrower to be edited
     * @throws Exception Error occurred while preparing for editing
     */
    private void editBorrower() throws Exception{
        if(frame.tableSelectedRow() == -1){
            throw new Exception("Please select from the list");
        }
        borrower_id = getID();
        setBorrower(borrower_id);
        frame.editBtnText("Cancel", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/ex16.png")));
        frame.addBtnText("Save", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/save16.png")));

    }
    /**
     * Gets the borrower ID
     * @return The borrower ID
     * @throws SQLException Error occurred when communicating with the SQL Database
     */
    private int getID() throws SQLException{
        ResultSet rs = bordb.selectAll();
        rs.absolute(frame.tableSelectedRow()+1);
        return rs.getInt("borrowerID");
    }
    /**
     * Sets the borrower model and the corresponding text fields
     * @param id The borrower ID
     * @throws SQLException Error occurred when communicating with the SQL Database 
     */
    private void setBorrower(int id) throws SQLException{
        ResultSet rs = bordb.selectOne(id);
        rs.first();
        borrower = new Borrower(rs.getString("borrowerFirstname"),rs.getString("borrowerLastname"),rs.getString("borrowerEmail"),rs.getString("borrowerPhone"),rs.getInt("borrowQty"));
        frame.setFname(borrower.getFirstname());
        frame.setLname(borrower.getLastname());
        frame.setEmail(borrower.getEmail());
        frame.setPhone(borrower.getContactNo());
        frame.setQty(Integer.toString(borrower.getBorrowQty()));
    }
    /**
     * Capitalizes the first letters of the string 
     * @param str The string to be changed
     * @return The new string with the capitalized letters
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
     * Saves the borrower into the SQL Database
     * @throws SQLException Error occurred when communicating the SQL Database
     */
    private void saveBorrower() throws SQLException{
        String fname = capitalizeFirstLetters(frame.getFname());
        String lname = capitalizeFirstLetters(frame.getLname());
        borrower = new Borrower(fname,lname,frame.getEmail(),frame.getPhone(),0);
        bordb.saveBorrower(borrower);
        resetFields();
        setTable();
    }
    /**
     * Sets the text fields into empty and sets the default text for the buttons
     */
    private void resetFields(){
        frame.setFname("");
        frame.setLname("");
        frame.setEmail("");
        frame.setPhone("");
        frame.addBtnText("Add", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/plus.png")));
        frame.editBtnText("Edit", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/edit.png")));
        
    }
    /**
     * Checks the input string from the text fields and validates if there are any errors
     * @throws Exception Error occurred, some inputs are not valid
     */
    private void checkFields() throws Exception{
        String regex =  "^[a-z,A-Z,0-9_+&*-]+(?:\\."+"[a-z,A-Z,0-9_+&*-]+)*@" +"(?:[a-z,A-Z,0-9-]+\\.)+[a-z" +"A-Z]{2,7}$";
        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(frame.getEmail());
        if(frame.getFname().equals("")){
            throw new Exception("Please input first name");
        }
        if(frame.getLname().equals("")){
            throw new Exception("Please input last name");
        }
        if(frame.getEmail().equals("")){
            throw new Exception("Please input email");
        }
        if(!matcher.matches()){
            throw new Exception("Invalid email");
        }
        if(frame.getPhone().equals("(+63)             ")){
            throw new Exception("Please input phone");
        }
        ResultSet rs = bordb.selectAll();
        while(rs.next()){
            if(frame.getFname().equals(rs.getString("borrowerFirstname")) && frame.getLname().equals(rs.getString("borrowerLastname"))){
                throw new Exception("Borrower already exists");
            }
            if(frame.getEmail().equals(rs.getString("borrowerEmail"))){
                throw new Exception("Email has been used already");
            }
        }
    }
    /**
     * Initializing the data of the table with borrowers
     * @throws SQLException Error communicating the SQL Database
     */
    private void setTable() throws SQLException{
        String[] header = new String[]{"ID","Name","Email","Contact No.","Qty"};
        ResultSet rs = bordb.selectAll();
        rs.last();
        int count = rs.getRow();
        Object[][] data = new Object[count][5];
        rs.beforeFirst();
        for(int i = 0; i < count && rs.next(); i++){
            data[i][0] = "  10010" + rs.getString("borrowerID")+" ";
            data[i][1] = " "+rs.getString("borrowerFirstname")+rs.getString("borrowerLastname")+" ";
            data[i][2] = " "+rs.getString("borrowerEmail")+" ";
            data[i][3] = " "+rs.getString("borrowerPhone")+" ";
            data[i][4] = "     "+rs.getInt("borrowQty")+"      ";
        }
        frame.initTable(data, header);
        frame.setTotal(Integer.toString(count));
    }
    /**
     * Initializes the controller setup
     * @throws SQLException Error communicating with the SQL Database
     */
    private void init() throws SQLException{
        frame = new BorrowerFrame();
        frame.setVisible(true);
        setTable();
        frame.addBtnListener(new AddAction());
        frame.deleteBtnListener(new DeleteAction());
        frame.editBtnListener(new EditAction());
        frame.backBtnListener(new BackAction());
        frame.tableAddListener(new TableAction());
        frame.homeListener(new HomeAction());
        frame.userSettingsListener(new SettingsAction());
        frame.chargeListener(new ChargeAction());
        frame.returnListener(new ReturnAction());
        frame.booksListener(new BooksAction());
        frame.viewListener(new ViewAction());
        frame.userLogoutListener(new LogoutAction());
        frame.setUser(admin);
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
