/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Jpoy
 */
public class AddBook extends javax.swing.JFrame {

    /**
     * Creates new form AddBook
     */
    public AddBook() {
        initComponents();
        this.setIconImage(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/leaf24.png")).getImage());
    }
    
    
    // Table
    public void initTable(Object[][] data, String[] header){
        table = new JTable(data,header){
            private boolean trackViewportWidth = false;
            private boolean inited = false;
            private boolean ignoreUpdates = false;

            @Override
            protected void initializeLocalVars() {
                super.initializeLocalVars();
                inited = true;
                updateColumnWidth();
            }

            @Override
            public void addNotify() {
                super.addNotify();
                updateColumnWidth();
                getParent().addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        invalidate();
                    }
                });
            }

            @Override
            public void doLayout() {
                super.doLayout();
                if (!ignoreUpdates) {
                    updateColumnWidth();
                }
                ignoreUpdates = false;
            }

            protected void updateColumnWidth() {
                if (getParent() != null) {
                    int width = 0;
                    for (int col = 0; col < getColumnCount(); col++) {
                        int colWidth = 0;
                        for (int row = 0; row < getRowCount(); row++) {
                            int prefWidth = getCellRenderer(row, col).
                                    getTableCellRendererComponent(this, getValueAt(row, col), false, false, row, col).
                                    getPreferredSize().width;
                            colWidth = Math.max(colWidth, prefWidth + getIntercellSpacing().width);
                        }

                        TableColumn tc = getColumnModel().getColumn(convertColumnIndexToModel(col));
                        tc.setPreferredWidth(colWidth);
                        width += colWidth;
                    }

                    Container parent = getParent();
                    if (parent instanceof JViewport) {
                        parent = parent.getParent();
                    }

                    trackViewportWidth = width < parent.getWidth();
                }
            }

            @Override
            public void tableChanged(TableModelEvent e) {
                super.tableChanged(e);
                if (inited) {
                    updateColumnWidth();
                }
            }

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return trackViewportWidth;
            }

            @Override
            protected TableColumnModel createDefaultColumnModel() {
                TableColumnModel model = super.createDefaultColumnModel();
                model.addColumnModelListener(new TableColumnModelListener() {
                    @Override
                    public void columnAdded(TableColumnModelEvent e) {
                    }

                    @Override
                    public void columnRemoved(TableColumnModelEvent e) {
                    }

                    @Override
                    public void columnMoved(TableColumnModelEvent e) {
                        if (!ignoreUpdates) {
                            ignoreUpdates = true;
                            updateColumnWidth();
                        }
                    }

                    @Override
                    public void columnMarginChanged(ChangeEvent e) {
                        if (!ignoreUpdates) {
                            ignoreUpdates = true;
                            updateColumnWidth();
                        }
                    }

                    @Override
                    public void columnSelectionChanged(ListSelectionEvent lse) {
                    
                    }
                    
                });
                return model;
            }
        };
        tablePane.setViewportView(table);
    }
    public String tableFirstCol(int row){
        return (String) table.getValueAt(row,0);
    }
    public int tableSelectedRow(){
        return table.getSelectedRow();
    }
    public void tableAddListener(MouseListener action){
        table.addMouseListener(action);
    }
    
    // Hide
    public void hideAvailable(){
        availLabel.setVisible(false);
        available.setVisible(false);
    }
    
    // Show
    public void showAvailable(){
        availLabel.setVisible(true);
        available.setVisible(true);
    }
    
    // Enable
    public void enableAllFields(){
        ISBN.setEditable(true);
        title.setEditable(true);
        author.setEditable(true);
        publisher.setEditable(true);
        quantity.setEnabled(true);
    }
    public void enableAddBtn(){
        addBtn.setEnabled(true);
    }
    public void enableDeleteBtn(){
        deleteBtn.setEnabled(true);
    }
    public void enableEditBtn(){
        editBtn.setEnabled(true);
    }
    public void enableISBN(){
        ISBN.setEditable(true);
    }
    public void enableTitle(){
        title.setEditable(true);
    }
    public void enableAuthor(){
        author.setEditable(true);
    }
    public void enablePublisher(){
        publisher.setEditable(true);
    }
    public void enableQty(){
        quantity.setEnabled(true);
    }
    
    // Disable
    public void disableAllFields(){
        ISBN.setEditable(false);
        title.setEditable(false);
        author.setEditable(false);
        publisher.setEditable(false);
        quantity.setEnabled(false);
    }
    public void disableAddBtn(){
        addBtn.setEnabled(false);
    }
    public void disableDeleteBtn(){
        deleteBtn.setEnabled(false);
    }
    public void disableEditBtn(){
        editBtn.setEnabled(false);
    }
    public void disableISBN(){
        ISBN.setEditable(false);
    }
    public void disableTitle(){
        title.setEditable(false);
    }
    public void disableAuthor(){
        author.setEditable(false);
    }
    public void disablePublisher(){
        publisher.setEditable(false);
    }
    public void disableQty(){
        quantity.setEnabled(false);
    }
    
    // ActionListeners
    public void userSettingsListener(ActionListener action){
        userSettings.addActionListener(action);
    }
    public void userLogoutListener(ActionListener action){
        userLogout.addActionListener(action);
    }
    public void addBtnListener(ActionListener action){
        addBtn.addActionListener(action);
    }
    public void deleteBtnListener(ActionListener action){
        deleteBtn.addActionListener(action);
    }
    public void backBtnListener(ActionListener action){
        backBtn.addActionListener(action);
    }
    public void editBtnListener(ActionListener action){
        editBtn.addActionListener(action);
    }
    public void returnMenuListener(ActionListener action){
        returnMenu.addActionListener(action);
    }
    public void chargeMenuListener(ActionListener action){
        chargeMenu.addActionListener(action);
    }
    public void borrowersListener(ActionListener action){
        borrowerMenu.addActionListener(action);
    }
    public void viewMenuListener(ActionListener action){
        viewMenu.addActionListener(action);
    }
    // Setters
    public void resetResult(){
        result.setText("");
    }
    public void setResult(String message, Color color){
        result.setText(message);
        result.setForeground(color);
    }
    public void resetAllTextFields(String txt, int qty){
        ISBN.setText(txt);
        title.setText(txt);
        author.setText(txt);
        publisher.setText(txt);
        quantity.setValue(qty);
        
    }
    public void setDeleteBtnText(String text, Icon icon){
        deleteBtn.setText(text);
        deleteBtn.setIcon(icon);
    }
    public void setEditBtnText(String text, Icon icon){
        editBtn.setText(text);
        editBtn.setIcon(icon);
    }
    public void setAddBtnText(String text, Icon icon){
        addBtn.setText(text);
        addBtn.setIcon(icon);
    }
    public void setISBN(String ISBN) {
        this.ISBN.setText(ISBN);
    }
    public void setAuthor(String author) {
        this.author.setText(author);
    }
    public void setDate(String date) {
        this.dateMenu.setText(date);
    }
    public void setNoOfTypes(String noOfTypes) {
        this.noOfTypes.setText(noOfTypes);
    }
    public void setPublisher(String publisher) {
        this.publisher.setText(publisher);
    }
    public void setQtyBorrowed(String qtyBorrowed) {
        this.qtyBorrowed.setText(qtyBorrowed);
    }
    public void setQtyOnHand(String qtyOnHand) {
        this.qtyOnHand.setText(qtyOnHand);
    }
    public void setMaxQty(int max){
        quantity.setModel(new SpinnerNumberModel(0,0,max,1));
    }
    public void setMinQty(int min){
        quantity.setModel(new SpinnerNumberModel(min,min,1000,1));
    }
    public void setQuantity(int quantity) {
        this.quantity.setValue(quantity);
    }
    public void setTime(String time) {
        this.timeMenu.setText(time);
    }
    public void setBookTitle(String title) {
        this.title.setText(title);
    }
    public void setTotalNo(String totalNo) {
        this.totalNo.setText(totalNo);
    }
    public void setUserMenu(String user) {
        this.userMenu.setText(user);
    }
    public void setAvailable(String num){
        available.setText(num);
    }
    
    // Getters
    public String getISBN() {
        return ISBN.getText();
    }
    public String getBookTitle() {
        return title.getText();
    }
    public String getAuthor() {
        return author.getText();
    }
    public String getPublisher() {
        return publisher.getText();
    }
    public int getQuantity() {
        return (Integer) quantity.getValue();
    }

    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        bg = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        title = new javax.swing.JTextField();
        author = new javax.swing.JTextField();
        publisher = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ISBN = new javax.swing.JFormattedTextField();
        quantity = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        available = new javax.swing.JTextField();
        availLabel = new javax.swing.JLabel();
        tablePane = new javax.swing.JScrollPane();
        backBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        totalNo = new javax.swing.JTextField();
        qtyOnHand = new javax.swing.JTextField();
        qtyBorrowed = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        noOfTypes = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        result = new javax.swing.JLabel();
        editBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        userMenu = new javax.swing.JMenu();
        userSettings = new javax.swing.JMenuItem();
        userLogout = new javax.swing.JMenuItem();
        dateMenu = new javax.swing.JMenu();
        timeMenu = new javax.swing.JMenu();
        circMenu = new javax.swing.JMenu();
        chargeMenu = new javax.swing.JMenuItem();
        returnMenu = new javax.swing.JMenuItem();
        borrowerMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Relax - Book Information");
        setLocation(new java.awt.Point(200, 100));
        setMinimumSize(new java.awt.Dimension(800, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Book Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel1.setText("ISBN :");

        jLabel2.setText("Title :");

        jLabel3.setText("Author :");

        jLabel4.setText("Publisher :");

        try {
            ISBN.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-#-##-######-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("Quantity :");

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/plus.png"))); // NOI18N
        addBtn.setText("Add");

        available.setEditable(false);

        availLabel.setText("Available :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(ISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(author, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(availLabel))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(publisher, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(available, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(54, 54, 54))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(publisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBtn)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(available, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(availLabel)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/back.png"))); // NOI18N
        backBtn.setText("Back");

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/garbage16.png"))); // NOI18N
        deleteBtn.setText("Delete");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setMinimumSize(new java.awt.Dimension(377, 238));

        totalNo.setEditable(false);
        totalNo.setFocusable(false);

        qtyOnHand.setEditable(false);

        qtyBorrowed.setEditable(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Total number of books :");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Quantity on hand :");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Quantity borrowed :");

        noOfTypes.setEditable(false);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("No. of types of book :");

        result.setFont(new java.awt.Font("Trajan Pro", 1, 12)); // NOI18N
        result.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        result.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(totalNo, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(qtyOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(qtyBorrowed, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(noOfTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(result, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9))
                    .addComponent(noOfTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(totalNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel7))
                    .addComponent(qtyOnHand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8))
                    .addComponent(qtyBorrowed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61))
        );

        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/edit.png"))); // NOI18N
        editBtn.setText("Edit");

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(tablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(backBtn)
                            .addComponent(deleteBtn)
                            .addComponent(editBtn))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(tablePane))
                .addContainerGap())
        );

        userMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/user.png"))); // NOI18N
        userMenu.setText("User");

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

        circMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/redbook24.png"))); // NOI18N
        circMenu.setText("Circulation");

        chargeMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/out24.png"))); // NOI18N
        chargeMenu.setText("Charge Out");
        circMenu.add(chargeMenu);

        returnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/return24.png"))); // NOI18N
        returnMenu.setText("Return Books");
        circMenu.add(returnMenu);

        borrowerMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/users24.png"))); // NOI18N
        borrowerMenu.setText("Borrowers");
        circMenu.add(borrowerMenu);

        viewMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/view24.png"))); // NOI18N
        viewMenu.setText("View List");
        circMenu.add(viewMenu);

        jMenuBar1.add(circMenu);

        setJMenuBar(jMenuBar1);

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
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBook.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddBook().setVisible(true);
            }
        });
    }
    
    
    private JTable table;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField ISBN;
    private javax.swing.JButton addBtn;
    private javax.swing.JTextField author;
    private javax.swing.JLabel availLabel;
    private javax.swing.JTextField available;
    private javax.swing.JButton backBtn;
    private javax.swing.JPanel bg;
    private javax.swing.JMenuItem borrowerMenu;
    private javax.swing.JMenuItem chargeMenu;
    private javax.swing.JMenu circMenu;
    private javax.swing.JMenu dateMenu;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField noOfTypes;
    private javax.swing.JTextField publisher;
    private javax.swing.JTextField qtyBorrowed;
    private javax.swing.JTextField qtyOnHand;
    private javax.swing.JSpinner quantity;
    private javax.swing.JLabel result;
    private javax.swing.JMenuItem returnMenu;
    private javax.swing.JScrollPane tablePane;
    private javax.swing.JMenu timeMenu;
    private javax.swing.JTextField title;
    private javax.swing.JTextField totalNo;
    private javax.swing.JMenuItem userLogout;
    private javax.swing.JMenu userMenu;
    private javax.swing.JMenuItem userSettings;
    private javax.swing.JMenuItem viewMenu;
    // End of variables declaration//GEN-END:variables
}
