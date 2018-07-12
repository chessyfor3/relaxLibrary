/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import library.model.*;
import library.util.*;
import library.view.*;
/**
 * Home Controller
 * Operations for the home page
 * @author Jpoy
 */
public class HomeController extends Thread{
    private Home frame;
    private final String admin;
    /**
     * Constructor initializing the user
     * @param name  The username
     */
    public HomeController(String name){
        admin = name;
    }
    
    @Override
    public void run(){
        initHome();
        
    }
    /**
     * Customized MouseListener for the borrower panel mouse click
     */
    class BorrowersListen implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            try{
                new BorrowerController(new HomeController(admin),admin).start();
            frame.dispose();
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
            frame.setColor(frame.getBorrowsPanel());
        }

        @Override
        public void mouseExited(MouseEvent me) {
            frame.resetColor(frame.getBorrowsPanel());
        }
        
    }
    /**
     * Customized MouseListener for the books panel mouse click
     */
    class BooksListen implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            try{
                new AddBookController(new HomeController(admin),admin).start();
                frame.dispose();
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
            frame.setColor(frame.getBooksPanel());
        }

        @Override
        public void mouseExited(MouseEvent me) {
            frame.resetColor(frame.getBooksPanel());
        }
        
    }
    /**
     * Customized MouseListener for the circulation panel mouse click
     */
    class CirculationListen implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            try{
                new ChargeOutController(new HomeController(admin),admin).start();
                frame.dispose();
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
            frame.setColor(frame.getCirculationPanel());
        }

        @Override
        public void mouseExited(MouseEvent me) {
            frame.resetColor(frame.getCirculationPanel());
        }
        
    }
    /**
     * Customized MouseListener for the view list panel mouse click
     */
    class ViewListen implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent me) {
            try{
                new ViewListController(new HomeController(admin),admin).start();
                frame.dispose();
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
            frame.setColor(frame.getViewPanel());
        }

        @Override
        public void mouseExited(MouseEvent me) {
            frame.resetColor(frame.getViewPanel());
        }
        
    }
    /**
     * Customized ActionListener to logout the user
     */
    
    class UserLogoutListen implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try{
                int a = JOptionPane.showConfirmDialog(frame,"Are you sure to logout?","Warning",JOptionPane.YES_NO_OPTION);
                if( a == JOptionPane.NO_OPTION || a == JOptionPane.CLOSED_OPTION ){
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
     * Customized ActionListener which redirects to the admin settings
     */
    class SettingsListen implements ActionListener{

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
     * Initializes the controller
     */
    
    private void initHome(){
        frame = new Home();
        frame.setVisible(true);
        frame.borrowersMouseListener(new BorrowersListen());
        frame.booksMouseListener(new BooksListen());
        frame.circulationMouseListener(new CirculationListen());
        frame.viewMouseListener(new ViewListen());
        frame.userLogout(new UserLogoutListen());
        frame.userSettings(new SettingsListen());
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
