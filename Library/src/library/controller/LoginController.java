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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import library.view.*;
import library.model.*;
import library.util.*;
/**
 * Login Controller
 * Operations for login module
 * @author Jpoy
 */
public class LoginController extends Thread{
    private LoginPage loginFrame;
    private Admin admin;
    private final db_admin addb = new db_admin();
    private final String code = "EXAI7980";
    @Override
    public void run(){
        initLogin();
        
    }
    /**
     * Customized ActionListener for the login button
     */
    class LoginRequest implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                loginCheckFields();
                if(admin.getBorrower_id() == 0){
                    JOptionPane.showMessageDialog(loginFrame, "Set up your account first");
                    new AdminController(admin.getUsername(), true).start();
                }else{
                    new HomeController(admin.getUsername()).start();;
                }
                loginFrame.dispose();
                
            }catch(Exception e){
                loginFrame.showDialogMessage(e.getMessage(),Color.RED);
            }
        }
        
    }
    /**
     * Customized ActionListener for the signup button
     */
    class SignupAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                signCheckFields();
                signup();
                JOptionPane.showMessageDialog(loginFrame, "Set up your account first");
                new AdminController(admin.getUsername(), true).start();
                loginFrame.dispose();
            }catch(Exception e){
                loginFrame.setResult(e.getMessage(), Color.red);
            }
        }
        
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
                        loginFrame.setDate(dformat.format(date));
                        DateTimeFormatter tformat = DateTimeFormatter.ofPattern("hh:mm:ss a");
                        loginFrame.setTime(LocalTime.now().format(tformat));
                        sleep(1000);
                    }
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }
        };
        dateTime.start();
    }
    /**
     * Saves the credentials into the SQL Database
     * @throws SQLException Error communicating the SQL Database
     */
    private void signup() throws SQLException{
        admin = new Admin(loginFrame.getSignUsername(),loginFrame.getNewpass());
        addb.save(admin);
    }
    /**
     * Checks the sign up text fields for any invalid input
     * @throws Exception Error for invalid input and others
     */
    private void signCheckFields() throws Exception{
        if(loginFrame.getSignUsername().equals("")){
            throw new Exception("Input username");
        }
        if(loginFrame.getNewpass().equals("")){
            throw new Exception("Input password");
        }
        if(loginFrame.getNewconfpass().equals("")){
            throw new Exception("Input confirm password");
        }
        if(loginFrame.getCode().equals("")){
            throw new Exception("Input the code");
        }
        if(!loginFrame.getNewconfpass().equals(loginFrame.getNewpass())){
            throw new Exception("Input confirm password");
        }
        if(!loginFrame.getCode().equals(code)){
            throw new Exception("Incorrect code");
        }
    }
    /**
     * Checks the login text fields for any invalid input
     * @throws Exception Error for any invalid input and others
     */
    private void loginCheckFields() throws Exception{
        boolean found = false;
        ResultSet rs = addb.selectAll();
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();
        if(username.equals("")){
            throw new Exception("Please input username");
        }
        if(password.equals("")){
            throw new Exception("Please input password");
        }
        while(rs.next()){
            if(username.equals(rs.getString("username")) && password.equals(rs.getString("password"))){
                admin = new Admin(username,password);
                admin.setBorrower_id(rs.getInt("borrowerID"));
                rs.last();
                found = true;
            }
        }
        if(!found){
            throw new Exception("Incorrect username or password");
        }
        
    }
    /**
     * Initializes the controller
     */
    private void initLogin(){
        this.loginFrame = new LoginPage();
        loginFrame.setVisible(true);
        loginFrame.loginListener(new LoginRequest());
        loginFrame.signupListener(new SignupAction());
        currentDateTime();
    }
}
