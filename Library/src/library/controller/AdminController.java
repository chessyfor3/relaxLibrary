/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.controller;
import java.awt.Color;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import library.view.*;
import library.model.*;
import library.util.*;
/**
 * Admin Settings Controller
 * Controller for the admin settings
 * @author Jpoy
 */
public class AdminController extends Thread{
    private AdminSettings frame;
    private Admin admin;
    private Borrower borrower;
    private String tempPass;
    private final boolean initialize;
    private boolean edit = false;
    private boolean changepass = false;
    private final String user;
    private final db_admin addb = new db_admin();
    private final db_borrower bordb = new db_borrower();
    
    /**
     * Creates an instance and initialize the admin user name and initialize state
     * @param username The admin username
     * @param init The initialize state, if the admin hasn't put his/her information
     */
    public AdminController(String username, boolean init){
        user = username;
        initialize = init;
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
     * Inner class that implements ActionListener to save changes
     */
    class SaveAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                frame.resetResult();
                if(edit){
                    checkFields();
                    saveAdmin();
                    if(initialize){
                        saveBorrower();
                    }else{
                        saveEdit();
                    }
                    setFields();
                    resetButtons();
                    edit = false;
                }else if(changepass){
                    saveConfirm();
                    resetButtons();
                    changepass = false;
                }else{
                    editFields();
                    edit = true;
                }
                if(initialize){
                    new HomeController(user).start();
                    frame.dispose();
                }
                frame.setResult("Changes saved", Color.black);
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements MouseListener to direct to the home page
     */
    class HomeAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            try{
                frame.resetResult();
                if(edit){
                    JOptionPane.showMessageDialog(frame, "Save Changes first!");
                    throw new Exception("Save changes first");
                }
                new HomeController(user).start();
                frame.dispose();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
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
     * Inner class that implements MouseListener to logout the admin user
     */
    class LogoutAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            try{
                frame.resetResult();
                if(edit){
                    JOptionPane.showMessageDialog(frame, "Save Changes first!");
                    throw new Exception("Save changes first");
                }
                int a = JOptionPane.showConfirmDialog(frame, "Are you sure to log out?", "Warning",JOptionPane.YES_NO_OPTION);
                if(a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
                    throw new Exception();
                }
                new LoginController().start();
                frame.dispose();
            }catch(Exception e){
               frame.setResult(e.getMessage(), Color.red); 
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
     * Inner class that implements ActionListener to prepare for changing the password
     */
    class ChangeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.resetResult();
            try{
                if(edit){
                    cancelEdit();
                    resetButtons();
                    edit = false;
                }else if(changepass){
                    cancelChange();
                    resetButtons();
                    changepass = false;
                }else{
                    changePass();
                    changepass = true;
                }
                
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
     * Inner class that implements ActionListener to verify new password and confirm
     */
    class VerifyAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            try{
                frame.resetResult();
                savePass();
            }catch(Exception e){
                frame.setResult(e.getMessage(), Color.red);
            }
        }
        
    }
    /**
    * Cancels the edit operation
    */
    private void cancelEdit(){
        frame.setFirstname(borrower.getFirstname());
        frame.setLastname(borrower.getLastname());
        frame.setEmail(borrower.getEmail());
        frame.setPhone(borrower.getContactNo());
        disableFields();
    }
    /**
     * Resets the text of the text fields and button text
     */
    private void editFields(){
        enableFields();
        frame.saveBtnText("Save", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/save16.png")));
        frame.changepassText("Cancel", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/ex16.png")));
    }
    /**
     * Disables the text fields for first name, last name, email and phone
     */
    private void disableFields(){
        frame.disableFirstname();
        frame.disableLastname();
        frame.disableEmail();
        frame.disablePhone();
    }
    /**
     * Enables the text fields for first name, last name, email and phone
     */
    private void enableFields(){
        frame.enableFirstname();
        frame.enableLastname();
        frame.enableEmail();
        frame.enablePhone();
    }
    /**
     * Verifying new password and saves it to the SQL Database
     * @throws Exception Error that occurred after saving
     */
    private void saveConfirm() throws Exception{
        String verify = frame.getPassword();
        if(!verify.equals(tempPass)){
            throw new Exception("Confirm password didn't match");
        }
        addb.updatePassword(tempPass, user);
        frame.disablePass();
    }
    /**
     * Cancels the process of changing the password
     */
    private void cancelChange(){
        frame.disablePass();
        frame.setPassword(admin.getPassword());
        frame.setPassLabel("Password :");
        resetButtons();
    }
    /**
     * Sets the text of each button and the visibility and usability
     */
    private void resetButtons(){
        frame.changepassText("Change password", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/changepass.png")));
        frame.saveBtnText("Edit", new javax.swing.ImageIcon(getClass().getResource("/library/view/images/edit.png")));
        frame.hideVerify();
        frame.enableSave();
    }
    /**
     * Process of confirming the new password
     */
    private void savePass() throws Exception{
        tempPass = frame.getPassword();
        if(tempPass.equals("")){
            throw new Exception("Please input password");
        }
        frame.setPassLabel("Confirm :");
        frame.setPassword("");
        frame.hideVerify();
        frame.enableSave(); 
    }
    /**
     * Prepares the form for changing the password
     */
    private void changePass(){
        frame.saveBtnText("Save",  new javax.swing.ImageIcon(getClass().getResource("/library/view/images/save16.png")));
        frame.changepassText("Cancel",  new javax.swing.ImageIcon(getClass().getResource("/library/view/images/ex16.png")));
        frame.setPassLabel("New password :");
        frame.enablePass();
        frame.setPassword("");
        frame.disableSave();
        frame.disableFirstname();
        frame.disableLastname();
        frame.disableEmail();
        frame.disablePhone();
        frame.showVerify();
    }
    /**
     * Checks the text fields for any invalid input
     * @throws Exception Error for invalid input and other errors
     */
    private void checkFields() throws Exception{
        String regex =  "^[a-z,A-Z,0-9_+&*-]+(?:\\."+"[a-z,A-Z,0-9_+&*-]+)*@" +"(?:[a-z,A-Z,0-9-]+\\.)+[a-z" +"A-Z]{2,7}$";
        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(frame.getEmail());
        if(frame.getFirstname().equals("")){
            throw new Exception("Please input firstname");
        }
        if(frame.getLastname().equals("")){
            throw new Exception("Please input lastname");
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
            if(rs.getInt("borrowerID") != admin.getBorrower_id()){
                if(frame.getFirstname().equals(rs.getString("borrowerFirstname")) && frame.getFirstname().equals(rs.getString("borrowerLastname"))){
                    throw new Exception("Name already exists");
                }
                if(frame.getEmail().equals(rs.getString("borrowerEmail"))){
                    throw new Exception("Email has been used already");
                }
            }
        }
        
    }
    /**
     * Capitalizes the first letters of the string
     * @param str The string the first letters to be capitalized
     * @return New string with capitalized letters
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
     * Saves the changes to the borrower model
     * @throws SQLException Error communicating with the SQL Database
     */
    private void saveEdit() throws SQLException{
        borrower.setFirstname(capitalizeFirstLetters(frame.getFirstname()));
        borrower.setLastname(capitalizeFirstLetters(frame.getLastname()));
        borrower.setEmail(frame.getEmail());
        borrower.setContactNo(frame.getPhone());
        
        bordb.updateFirstname(borrower.getFirstname(), admin.getBorrower_id());
        bordb.updateLastname(borrower.getLastname(), admin.getBorrower_id());
        bordb.updateEmail(borrower.getEmail(),admin.getBorrower_id());
        bordb.updatePhone(borrower.getContactNo(), admin.getBorrower_id());
    }
    /**
     * Sets the text fields with the admin information
     */
    private void setFields(){
        frame.setFirstname(borrower.getFirstname());
        frame.setLastname(borrower.getLastname());
        frame.setEmail(borrower.getEmail());
        frame.setPhone(borrower.getContactNo());
        disableFields();
    }
    /**
     * Saves the changes of the borrower to the SQL Database
     * @throws SQLException Error communicating with the SQL Database
     */
    private void saveBorrower() throws SQLException{
        String fname = capitalizeFirstLetters(frame.getFirstname());
        String lname = capitalizeFirstLetters(frame.getLastname());
        borrower = new Borrower(fname,lname,frame.getEmail(),frame.getPhone(),0);
        bordb.saveBorrower(borrower);
        ResultSet rs = bordb.selectAll();
        rs.last();
        admin.setBorrower_id(rs.getInt("borrowerID"));
        addb.updateBorrowerID(admin.getBorrower_id(), admin.getUsername());
    }
    /**
     * Saves admin credentials to the SQL Database
     * @throws SQLException Error communicating with the SQL Database
     */
    private void saveAdmin() throws SQLException{
        admin.setPassword(frame.getPassword());
        addb.updatePassword(admin.getPassword(), user);
    }
    /**
     * Sets the information of the admin to the borrower model
     * @throws SQLException Error communicating the SQL Database
     * @throws Exception Other errors during the process
     */
    private void setBorrower() throws SQLException, Exception{
        ResultSet rs = bordb.selectOne(admin.getBorrower_id());
        if(!rs.first()){
            throw new Exception("");
        }
        borrower = new Borrower(rs.getString("borrowerFirstname"),rs.getString("borrowerLastname"),rs.getString("borrowerEmail"),rs.getString("borrowerPhone"),rs.getInt("borrowQty"));
        frame.setFirstname(borrower.getFirstname());
        frame.setLastname(borrower.getLastname());
        frame.setEmail(borrower.getEmail());
        frame.setPhone(borrower.getContactNo());
    }
    /**
     * Sets the admin model with data from the SQL Database
     * @throws SQLException Error communicating the SQL Database
     * @throws Exception Other errors during the process
     */
    private void setAdmin() throws SQLException, Exception{
        ResultSet rs = addb.selectOne(user);
        rs.first();
        admin = new Admin(rs.getString("username"),rs.getString("password"));
        admin.setBorrower_id(rs.getInt("borrowerID"));
        frame.setUsername(admin.getUsername());
        frame.setPassword(admin.getPassword());
        setBorrower();
    }
    /**
     * Initializes the controller
     * @throws Exception 
     */
    private void init() throws Exception{
        frame = new AdminSettings();
        frame.setVisible(true);
        frame.saveBtnListener(new SaveAction());
        frame.homeListener(new HomeAction());
        frame.logoutListener(new LogoutAction());
        frame.changepassListener(new ChangeAction());
        frame.verifyListener(new VerifyAction());
        frame.hideVerify();
        if(initialize){
            frame.disableHome();
            frame.disableLogout();
            editFields();
            frame.disableChangepass();
            edit = true;
        }
        setAdmin();
        
    }
    
}
