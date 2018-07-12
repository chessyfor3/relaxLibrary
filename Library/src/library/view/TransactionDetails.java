/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Jpoy
 */
public class TransactionDetails extends javax.swing.JFrame {

    /**
     * Creates new form TransactionDetails
     */
    public TransactionDetails() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/leaf24.png")).getImage());
    }
    
    //Enable fields
    public void disableRefNoList(){
        refNoList.setEnabled(false);
    }
    public void disableSaveBtn(){
        saveBtn.setEnabled(false);
    }
    public void enableSaveBtn(){
        saveBtn.setEnabled(true);
    }
    public void disableDeleteBtn(){
        deleteBtn.setEnabled(false);
    }
    public void enableDeleteBtn(){
        deleteBtn.setEnabled(true);
    }
    public void disableRetQty(){
        returnQty.setEnabled(false);
    }
    
    public void enableRetQty(){
        returnQty.setEnabled(true);
    }
    
    public void disableDueDate(){
        dueDate.setEnabled(false);
    }
    
    public void enableDueDate(){
        dueDate.setEnabled(true);
    }
    
    public void enableReturnBtn(){
        returnBtn.setEnabled(true);
    }
    
    public void disableReturnBtn(){
        returnBtn.setEnabled(false);
    }
    
    public void enableExtendBtn(){
        extendBtn.setEnabled(true);
    }
    
    public void disableExtendBtn(){
        extendBtn.setEnabled(false);
    }
    
    // Button ActionListeners
    public void homeListener(ActionListener action){
        home.addActionListener(action);
    }
    public void settingsListener(ActionListener action){
        userSettings.addActionListener(action);
    }
    public void logoutListener(ActionListener action){
        userLogout.addActionListener(action);
    }
    public void chargeListener(ActionListener action){
        chargeMenu.addActionListener(action);
    }
    public void booksListener(ActionListener action){
        booksMenu.addActionListener(action);
    }
    public void borrowersListener(ActionListener action){
        borrowersMenu.addActionListener(action);
    }
    public void viewListener(ActionListener action){
        viewMenu.addActionListener(action);
    }
    
    public void refNoListListener(ActionListener action){
        refNoList.addActionListener(action);
    }
    
    public void returnBtnListener(ActionListener action){
        returnBtn.addActionListener(action);
    }
    
    public void extendBtnListener(ActionListener action){
        extendBtn.addActionListener(action);
    }
    
    public void deleteBtnListener(ActionListener action){
        deleteBtn.addActionListener(action);
    }
    
    public void saveBtnListener(ActionListener action){
        saveBtn.addActionListener(action);
    }
    
    public void backBtnListener(ActionListener action){
        backBtn.addActionListener(action);
    }
    
    //Getters
    public String getRefNoSelected(int idx){
        return refNoList.getItemAt(idx);
    }
    public int getReferenceIdx(){
        return refNoList.getSelectedIndex();
    }
    
    public String getDueDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(dueDate.getDate());
    }

    public String getRefNo() {
        return refNo.getText();
    }

    public int getReturnQty() {
        return (Integer) returnQty.getValue();
    }
    
    

    //Setter for fields
    public void addRefNo(String num){
        refNoList.addItem(num);
    }
    
    public void setUser(String user){
        userMenu.setText(user);
    }
    
    public void setDateMenu(String date) {
        dateMenu.setText(date);
    }
    
    public void setTimeMenu(String time) {    
        timeMenu.setText(time);
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor.setText(bookAuthor);
    }

    public void setBookAvailable(String bookAvailable) {
        this.bookAvailable.setText(bookAvailable);
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN.setText(bookISBN);
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher.setText(bookPublisher);
    }

    public void setBookQty(String bookQty) {
        this.bookQty.setText(bookQty);
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle.setText(bookTitle);
    }

    public void setBorr_email(String borr_email) {
        this.borr_email.setText(borr_email);
    }

    public void setBorr_id(String borr_id) {
        this.borr_id.setText(borr_id);
    }

    public void setBorr_name(String borr_name) {
        this.borr_name.setText(borr_name);
    }

    public void setBorr_phone(String borr_phone) {
        this.borr_phone.setText(borr_phone);
    }

    public void setBorrowQty(String borrowQty) {
        this.borrowQty.setText(borrowQty);
    }

    public void setDueDate(Date dueDate) {
        this.dueDate.setDate(dueDate);
        this.dueDate.setMinSelectableDate(dueDate);
    }

    public void setRefNo(String refNo) {
        this.refNo.setText(refNo);
    }

    public void resetResult(){
        resultMessage.setText("");
    }
    public void setResult(String message, Color color) {
        resultMessage.setText(message);
        resultMessage.setForeground(color);
    }

    public void setReturnQty(int max) {
        returnQty.setModel(new SpinnerNumberModel(0,0,max,1));
    }
    
    public void setReturned(String qty){
        returnedQty.setText(qty);
    }
    public  void resetAllFields(String txt){
        refNo.setText(txt);
        borrowQty.setText(txt);
        returnedQty.setText(txt);
        dueDate.setDate(null);
        dueDate.setMinSelectableDate(null);
        returnQty.setValue(0);
        
        borr_id.setText(txt);
        borr_name.setText(txt);
        borr_email.setText(txt);
        borr_phone.setText(txt);
        
        bookISBN.setText(txt);
        bookTitle.setText(txt);
        bookAuthor.setText(txt);
        bookPublisher.setText(txt);
        bookQty.setText(txt);
        bookAvailable.setText(txt);
    }
    
    public void setCirculationFields(String ref, String qty, Date date, String ret){
        refNo.setText(ref);
        borrowQty.setText(qty);
        returnedQty.setText(ret);
        dueDate.setDate(date);
        dueDate.setMinSelectableDate(date);
        returnQty.setModel(new SpinnerNumberModel(0,0,Integer.parseInt(qty),1));
    }
    
    public void setBorrowerFields(String id, String name, String email, String phone){
        borr_id.setText(id);
        borr_name.setText(name);
        borr_email.setText(email);
        borr_phone.setText(phone);
    }
    public void setBookFields(String ISBN, String title, String author, String publisher, String qty, String available){
        bookISBN.setText(ISBN);
        bookTitle.setText(title);
        bookAuthor.setText(author);
        bookPublisher.setText(publisher);
        bookQty.setText(qty);
        bookAvailable.setText(available);
    }
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        borr_id = new javax.swing.JTextField();
        borr_name = new javax.swing.JTextField();
        borr_email = new javax.swing.JTextField();
        borr_phone = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        bookISBN = new javax.swing.JTextField();
        bookTitle = new javax.swing.JTextField();
        bookAuthor = new javax.swing.JTextField();
        bookPublisher = new javax.swing.JTextField();
        bookQty = new javax.swing.JTextField();
        bookAvailable = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        refNo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        borrowQty = new javax.swing.JTextField();
        dueDate = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        returnQty = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        returnedQty = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        extendBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        resultMessage = new javax.swing.JLabel();
        refNoList = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        userMenu = new javax.swing.JMenu();
        home = new javax.swing.JMenuItem();
        userSettings = new javax.swing.JMenuItem();
        userLogout = new javax.swing.JMenuItem();
        dateMenu = new javax.swing.JMenu();
        timeMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        chargeMenu = new javax.swing.JMenuItem();
        booksMenu = new javax.swing.JMenuItem();
        borrowersMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Relax - Transaction Details");
        setLocation(new java.awt.Point(200, 100));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Borrower Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        borr_id.setEditable(false);

        borr_name.setEditable(false);

        borr_email.setEditable(false);

        borr_phone.setEditable(false);

        jLabel1.setText("ID :");

        jLabel2.setText("Name :");

        jLabel3.setText("Email :");

        jLabel4.setText("Contact No. :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borr_id)
                    .addComponent(borr_name)
                    .addComponent(borr_email)
                    .addComponent(borr_phone))
                .addGap(36, 36, 36))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borr_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borr_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borr_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borr_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Book Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        bookISBN.setEditable(false);

        bookTitle.setEditable(false);

        bookAuthor.setEditable(false);

        bookPublisher.setEditable(false);

        bookQty.setEditable(false);

        bookAvailable.setEditable(false);

        jLabel5.setText("ISBN :");

        jLabel6.setText("Title :");

        jLabel7.setText("Author :");

        jLabel8.setText("Publisher :");

        jLabel9.setText("Quantity :");

        jLabel10.setText("Available :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bookQty, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(bookAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bookPublisher)
                    .addComponent(bookAuthor)
                    .addComponent(bookTitle)
                    .addComponent(bookISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        refNo.setEditable(false);

        jLabel11.setText("Reference Number :");

        jLabel12.setText("Quantity Borrowed :");

        borrowQty.setEditable(false);
        borrowQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        dueDate.setEnabled(false);

        jLabel13.setText("Due Date :");

        returnQty.setEnabled(false);

        jLabel14.setText("Return Quantity :");

        returnedQty.setEditable(false);
        returnedQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel15.setText("Quantity Returned :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(refNo, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(borrowQty))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(returnQty, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dueDate, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(19, 19, 19)
                        .addComponent(returnedQty)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(refNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(borrowQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnedQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(returnQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        extendBtn.setText("Extend Due Date");

        returnBtn.setText("Return Book");

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/save16.png"))); // NOI18N
        saveBtn.setText("Save");

        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/back.png"))); // NOI18N
        backBtn.setText("Back");

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/garbage16.png"))); // NOI18N
        deleteBtn.setText("Delete");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(extendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                    .addComponent(returnBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(extendBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(returnBtn)
                .addGap(28, 28, 28)
                .addComponent(deleteBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(backBtn)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        resultMessage.setFont(new java.awt.Font("Trajan Pro", 1, 12)); // NOI18N
        resultMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        refNoList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"-- Select Reference Number --"}));

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(refNoList, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addContainerGap(37, Short.MAX_VALUE)
                        .addComponent(refNoList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resultMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        userMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/user.png"))); // NOI18N
        userMenu.setText("User");

        home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/home24.png"))); // NOI18N
        home.setText("Home");
        userMenu.add(home);

        userSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/settings.png"))); // NOI18N
        userSettings.setText("Settings");
        userMenu.add(userSettings);

        userLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/logout.png"))); // NOI18N
        userLogout.setText("Logout");
        userMenu.add(userLogout);

        jMenuBar1.add(userMenu);

        dateMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/calendar.png"))); // NOI18N
        dateMenu.setText("Date");
        jMenuBar1.add(dateMenu);

        timeMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/clock.png"))); // NOI18N
        timeMenu.setText("Time");
        jMenuBar1.add(timeMenu);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/redbook24.png"))); // NOI18N
        jMenu1.setText("Circulation");

        chargeMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/out24.png"))); // NOI18N
        chargeMenu.setText("Charge Out");
        jMenu1.add(chargeMenu);

        booksMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/books24.png"))); // NOI18N
        booksMenu.setText("Books");
        jMenu1.add(booksMenu);

        borrowersMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/users24.png"))); // NOI18N
        borrowersMenu.setText("Borrowers");
        jMenu1.add(borrowersMenu);

        viewMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/view24.png"))); // NOI18N
        viewMenu.setText("View");
        jMenu1.add(viewMenu);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransactionDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JPanel bg;
    private javax.swing.JTextField bookAuthor;
    private javax.swing.JTextField bookAvailable;
    private javax.swing.JTextField bookISBN;
    private javax.swing.JTextField bookPublisher;
    private javax.swing.JTextField bookQty;
    private javax.swing.JTextField bookTitle;
    private javax.swing.JMenuItem booksMenu;
    private javax.swing.JTextField borr_email;
    private javax.swing.JTextField borr_id;
    private javax.swing.JTextField borr_name;
    private javax.swing.JTextField borr_phone;
    private javax.swing.JTextField borrowQty;
    private javax.swing.JMenuItem borrowersMenu;
    private javax.swing.JMenuItem chargeMenu;
    private javax.swing.JMenu dateMenu;
    private javax.swing.JButton deleteBtn;
    private com.toedter.calendar.JDateChooser dueDate;
    private javax.swing.JButton extendBtn;
    private javax.swing.JMenuItem home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField refNo;
    private javax.swing.JComboBox<String> refNoList;
    private javax.swing.JLabel resultMessage;
    private javax.swing.JButton returnBtn;
    private javax.swing.JSpinner returnQty;
    private javax.swing.JTextField returnedQty;
    private javax.swing.JButton saveBtn;
    private javax.swing.JMenu timeMenu;
    private javax.swing.JMenuItem userLogout;
    private javax.swing.JMenu userMenu;
    private javax.swing.JMenuItem userSettings;
    private javax.swing.JMenuItem viewMenu;
    // End of variables declaration//GEN-END:variables
}
