/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.view;

import java.awt.Color;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.util.StringConverter;

import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Jpoy
 */
public class ChargeOutForm extends javax.swing.JFrame {

    /**
     * Creates new form ChargeOutForm
     */
    public ChargeOutForm() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/leaf24.png")).getImage());
    }
    
    
    
    public void homeListener(ActionListener action){
        home.addActionListener(action);
    }
    public void settingsListener(ActionListener action){
        userSettings.addActionListener(action);
    }
    public void logoutListener(ActionListener action){
        userLogout.addActionListener(action);
    }
    public void returnListener(ActionListener action){
        returnMenu.addActionListener(action);
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
    public void viewTransListener(ActionListener action){
        viewTransBtn.addActionListener(action);
    }
    public void setUser(String user){
        userMenu.setText(user);
    }
    
    public void borrowerFirstname(Border border){
        borrowerFirstname.setBorder(border);
    }
    public void borrowerLastname(Border border){
        borrowerLastname.setBorder(border);
    }
    public void borrowerEmail(Border border){
        borrowerEmail.setBorder(border);
    }
    public void borrowerPhone(Border border){
        borrowerPhone.setBorder(border);
    }
    
    public void setPhoneVal(String value){
        borrowerPhone.setValue(value);
    }
    public void addDueDateListener(FocusListener evt){
        dueDate.addFocusListener(evt);
    }
    
    public void addBorrowerListener(ActionListener request){
        borrowerID.addActionListener(request);
    }
    
    public void addISBNListener(ActionListener request){
        bookISBN.addActionListener(request);
    }
    
    public void addChargeOutListener(ActionListener request){
        submitBtn.addActionListener(request);
    }
    
    public void resetResultMessage(){
        resultMessage.setText("");
    }
    
    public void setResultMessage(String message, Color color){
        resultMessage.setText(message);
        resultMessage.setForeground(color);
    }
    
    public void setBorrowerFields(String fname, String lname, String email, String phone){
        borrowerFirstname.setText(fname);
        borrowerLastname.setText(lname);
        borrowerEmail.setText(email);
        borrowerPhone.setText(phone);
    }
    
    public void emptyBorrowerFields(){
        borrowerFirstname.setText("");
        borrowerLastname.setText("");
        borrowerEmail.setText("");
        borrowerPhone.setText("");
        borrowerID.setSelectedIndex(0);
    }
    
    public void setBorrowQty(int max){
        borrowQty.setModel(new SpinnerNumberModel(0,0,max,1));
        borrowQty.setEnabled(true);
    }
    public void emptyBookFields(){
        bookISBN.setSelectedIndex(0);
        bookTitle.setText("");
        bookAuthor.setText("");
        bookQty.setText("");
        bookPublisher.setText("");
        bookAvailable.setText("");
        borrowQty.setValue(0);
        borrowQty.setEnabled(false);
    }
    
    public void setBorrowerDefault(){
        borrowerID.setSelectedIndex(0);
    }
    
    public void setBookISBN(String ISBN){
        bookISBN.setSelectedItem(ISBN);
    }
    
    public void setBookFields(String bookTitle,String bookAuthor,String bookPublisher,String bookQty,String bookAvailable) {
        this.bookTitle.setText(bookTitle);
        this.bookAuthor.setText(bookAuthor);
        this.bookQty.setText(bookQty);
        this.bookPublisher.setText(bookPublisher);
        this.bookAvailable.setText(bookAvailable);
    }

    
    public void addBookISBN(String bookISBN) {
        this.bookISBN.addItem(bookISBN);
    }

    public void setBorrowerEmail(String borrowerEmail) {
        this.borrowerEmail.setText(borrowerEmail);
    }

    public void setBorrowerFirstname(String borrowerFirstname) {
        this.borrowerFirstname.setText(borrowerFirstname);
    }

    public void addBorrowerID(String borrowerID) {
        this.borrowerID.addItem(borrowerID);
    }

    public void setBorrowerLastname(String borrowerLastname) {
        this.borrowerLastname.setText(borrowerLastname);
    }

    public void setBorrowerPhone(String borrowerPhone) {
        this.borrowerPhone.setText(borrowerPhone);
    }
    
    public void setTime(String time){
        timeMenu.setText(time);
    }
    
    public void setDate(String date){
        dateMenu.setText(date);
    }
    public String getBookAuthor() {
        return bookAuthor.getText();
    }

    public String getBookAvailable() {
        return bookAvailable.getText();
    }

    public String getBookISBN() {
        return bookISBN.getItemAt(bookISBN.getSelectedIndex());
    }

    public String getBookPublisher() {
        return bookPublisher.getText();
    }

    public int getBookQty() {
        return Integer.parseInt(bookQty.getText());
    }

    public String getBookTitle() {
        return bookTitle.getText();
    }
    public void disableBorrowerID(){
        borrowerID.setEnabled(false);
    }
    public void disableISBN(){
        bookISBN.setEnabled(false);
    }
    public void disableBorrowerFields(){
        borrowerFirstname.setEditable(false);
        borrowerLastname.setEditable(false);
        borrowerEmail.setEditable(false);
        borrowerPhone.setEditable(false);
    }
    public void enableBorrowerFields(){
        borrowerFirstname.setEditable(true);
        borrowerLastname.setEditable(true);
        borrowerEmail.setEditable(true);
        borrowerPhone.setEditable(true);
    }
    
    public String getDueDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(dueDate.getDate());
    }
    
    public int getBorrowQty() {
        return (Integer) borrowQty.getValue();
    }

    public String getBorrowerEmail() {
        return borrowerEmail.getText();
    }

    public String getBorrowerFirstname() {
        return borrowerFirstname.getText();
    }

    public int getBorrowerIdx() {
        return borrowerID.getSelectedIndex();
    }

    public String getBorrowerLastname() {
        return borrowerLastname.getText();
    }

    public String getBorrowerPhone() {
        return borrowerPhone.getText();
    }
    
    public void addFirstnameListener(FocusListener focus){
        borrowerFirstname.addFocusListener(focus);
    }
    
    public void addLastnameListener(FocusListener focus){
        borrowerLastname.addFocusListener(focus);
    }
    
    public void addEmailListener(FocusListener focus){
        borrowerEmail.addFocusListener(focus);
    }
    
    public void addPhoneListener(FocusListener focus){
        borrowerPhone.addFocusListener(focus);
    }
    
    public void addCancelLisener(ActionListener request){
        cancelBtn.addActionListener(request);
    }
    
    public void dueDateFormat(String format){
        dueDate.setDateFormatString(format);
    }
    
    public void setBorQtyVal(int val){
        borrowQty.setValue(val);
    }
    
    public void setRetQtyVal(int val){
        returnQty.setValue(val);
    }
    
    public void showReturnQty(){
        returnQty.setVisible(true);
        qtyRetLabel.setVisible(true);
    }
    
    public void hideReturnQty(){
        returnQty.setVisible(false);
        qtyRetLabel.setVisible(false);
    }
    
    public void setReturnQty(int max){
        returnQty.setModel(new SpinnerNumberModel(0,0,max,1));
        returnQty.setVisible(true);
    }
    
    public void disableDueDate(){
        dueDate.setEnabled(false);
    }
    
    public void enableDueDate(){
        dueDate.setEnabled(true);
    }
    
    public void setDueDate(Date date){
        dueDate.setDate(date);
        dueDate.setMinSelectableDate(date);
    }
    
   
    
    public void setQtyBorrLabel(String text){
        qtyBorrLabel.setText(text);
    }
    public void ISBNLabel(String text){
        ISBNLabel.setText(text);
    }
    public void setBorrIDLabel(String text){
        borrIDLabel.setText(text);
    }
    
    public void disableBorrQty(){
        borrowQty.setEnabled(false);
    }
    public void enableBorrQty(){
        borrowQty.setEnabled(true);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        submitBtn = new javax.swing.JButton();
        resultMessage = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JButton();
        borrowerInfo = new javax.swing.JPanel();
        borrowerID = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        borrowerFirstname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        borrowerLastname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        borrowerEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        borrowerPhone = new javax.swing.JFormattedTextField();
        borrIDLabel = new javax.swing.JLabel();
        bookInfo = new javax.swing.JPanel();
        ISBNLabel = new javax.swing.JLabel();
        bookISBN = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        bookTitle = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        bookAuthor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        bookPublisher = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        bookQty = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        bookAvailable = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        qtyBorrLabel = new javax.swing.JLabel();
        borrowQty = new javax.swing.JSpinner();
        dueDate = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        qtyRetLabel = new javax.swing.JLabel();
        returnQty = new javax.swing.JSpinner();
        viewTransBtn = new javax.swing.JButton();
        menu = new javax.swing.JMenuBar();
        userMenu = new javax.swing.JMenu();
        home = new javax.swing.JMenuItem();
        userSettings = new javax.swing.JMenuItem();
        userLogout = new javax.swing.JMenuItem();
        dateMenu = new javax.swing.JMenu();
        timeMenu = new javax.swing.JMenu();
        circulation = new javax.swing.JMenu();
        returnMenu = new javax.swing.JMenuItem();
        booksMenu = new javax.swing.JMenuItem();
        borrowersMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Relax - Charge-out Circulation");
        setLocation(new java.awt.Point(200, 100));
        setResizable(false);

        bg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        submitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/chargeout.png"))); // NOI18N
        submitBtn.setText("Submit");

        resultMessage.setFont(new java.awt.Font("Trajan Pro", 1, 12)); // NOI18N
        resultMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cancelBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/back.png"))); // NOI18N
        cancelBtn.setText("Cancel");

        borrowerInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Borrower Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        borrowerID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " -- Select Borrower --"}));

        jLabel2.setText("First name :");

        jLabel3.setText("Last name :");

        jLabel4.setText("Email address :");

        jLabel5.setText("Phone number :");

        try {
            borrowerPhone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(+63) ### ### ####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        borrowerPhone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        borrowerPhone.setPreferredSize(new java.awt.Dimension(6, 20));

        borrIDLabel.setText("Select Borrower :");

        javax.swing.GroupLayout borrowerInfoLayout = new javax.swing.GroupLayout(borrowerInfo);
        borrowerInfo.setLayout(borrowerInfoLayout);
        borrowerInfoLayout.setHorizontalGroup(
            borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowerInfoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(borrIDLabel)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(borrowerEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borrowerLastname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borrowerFirstname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borrowerPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(borrowerID, 0, 300, Short.MAX_VALUE))
                .addGap(0, 36, Short.MAX_VALUE))
        );
        borrowerInfoLayout.setVerticalGroup(
            borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrowerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrIDLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(borrowerFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(borrowerLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(borrowerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(borrowerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(borrowerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        borrowerFirstname.getAccessibleContext().setAccessibleName("borrowerFirstname");
        borrowerLastname.getAccessibleContext().setAccessibleName("borrowerLastname");
        borrowerEmail.getAccessibleContext().setAccessibleName("borrowerEmail");
        borrowerPhone.getAccessibleContext().setAccessibleName("borrowerPhone");

        bookInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Book Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        ISBNLabel.setText("Select ISBN :");

        bookISBN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select ISBN --"}));

        jLabel8.setText("Title :");

        bookTitle.setEditable(false);

        jLabel9.setText("Author :");

        bookAuthor.setEditable(false);

        jLabel10.setText("Publisher :");

        bookPublisher.setEditable(false);

        jLabel11.setText("Quantity :");

        bookQty.setEditable(false);

        jLabel13.setText("Available :");

        bookAvailable.setEditable(false);

        javax.swing.GroupLayout bookInfoLayout = new javax.swing.GroupLayout(bookInfo);
        bookInfo.setLayout(bookInfoLayout);
        bookInfoLayout.setHorizontalGroup(
            bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookInfoLayout.createSequentialGroup()
                .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookInfoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(ISBNLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookInfoLayout.createSequentialGroup()
                        .addComponent(bookQty, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(bookAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addGroup(bookInfoLayout.createSequentialGroup()
                        .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bookTitle)
                            .addComponent(bookISBN, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookAuthor)
                            .addComponent(bookPublisher))
                        .addGap(36, 36, 36))))
        );
        bookInfoLayout.setVerticalGroup(
            bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ISBNLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bookInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bookAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setMinimumSize(new java.awt.Dimension(227, 213));

        qtyBorrLabel.setText("Borrow quantity:");

        borrowQty.setEnabled(false);

        dueDate.setRequestFocusEnabled(false);

        jLabel12.setText("Due date :");

        qtyRetLabel.setText("Return quantity:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dueDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(qtyBorrLabel)
                                    .addGap(18, 18, 18)
                                    .addComponent(borrowQty, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(returnQty, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(qtyRetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(borrowQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qtyBorrLabel))
                .addGap(16, 16, 16)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qtyRetLabel)
                    .addComponent(returnQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        borrowQty.getAccessibleContext().setAccessibleName("borrowQty");
        dueDate.getAccessibleContext().setAccessibleName("dueDate");

        viewTransBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/return24.png"))); // NOI18N
        viewTransBtn.setText("View Transactions");

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(resultMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(borrowerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bookInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cancelBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(submitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                                    .addComponent(viewTransBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(8, 8, 8)))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(borrowerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(bookInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bgLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(viewTransBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelBtn)))
                .addContainerGap(70, Short.MAX_VALUE))
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

        menu.add(userMenu);

        dateMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/calendar.png"))); // NOI18N
        dateMenu.setText("Date");
        menu.add(dateMenu);

        timeMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/clock.png"))); // NOI18N
        timeMenu.setText("Time");
        menu.add(timeMenu);

        circulation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/redbook24.png"))); // NOI18N
        circulation.setText("Circulation");

        returnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/return24.png"))); // NOI18N
        returnMenu.setText("Return Books");
        circulation.add(returnMenu);

        booksMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/books24.png"))); // NOI18N
        booksMenu.setText("Books");
        circulation.add(booksMenu);

        borrowersMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/users24.png"))); // NOI18N
        borrowersMenu.setText("Borrowers");
        circulation.add(borrowersMenu);

        viewMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/view24.png"))); // NOI18N
        viewMenu.setText("View");
        circulation.add(viewMenu);

        menu.add(circulation);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(ChargeOutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChargeOutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChargeOutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChargeOutForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChargeOutForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ISBNLabel;
    private javax.swing.JPanel bg;
    private javax.swing.JTextField bookAuthor;
    private javax.swing.JTextField bookAvailable;
    private javax.swing.JComboBox<String> bookISBN;
    private javax.swing.JPanel bookInfo;
    private javax.swing.JTextField bookPublisher;
    private javax.swing.JTextField bookQty;
    private javax.swing.JTextField bookTitle;
    private javax.swing.JMenuItem booksMenu;
    private javax.swing.JLabel borrIDLabel;
    private javax.swing.JSpinner borrowQty;
    private javax.swing.JTextField borrowerEmail;
    private javax.swing.JTextField borrowerFirstname;
    private javax.swing.JComboBox<String> borrowerID;
    private javax.swing.JPanel borrowerInfo;
    private javax.swing.JTextField borrowerLastname;
    private javax.swing.JFormattedTextField borrowerPhone;
    private javax.swing.JMenuItem borrowersMenu;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JMenu circulation;
    private javax.swing.JMenu dateMenu;
    private com.toedter.calendar.JDateChooser dueDate;
    private javax.swing.JMenuItem home;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JLabel qtyBorrLabel;
    private javax.swing.JLabel qtyRetLabel;
    private javax.swing.JLabel resultMessage;
    private javax.swing.JMenuItem returnMenu;
    private javax.swing.JSpinner returnQty;
    private javax.swing.JButton submitBtn;
    private javax.swing.JMenu timeMenu;
    private javax.swing.JMenuItem userLogout;
    private javax.swing.JMenu userMenu;
    private javax.swing.JMenuItem userSettings;
    private javax.swing.JMenuItem viewMenu;
    private javax.swing.JButton viewTransBtn;
    // End of variables declaration//GEN-END:variables
}
