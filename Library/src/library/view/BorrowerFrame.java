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
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Jpoy
 */
public class BorrowerFrame extends javax.swing.JFrame {

    /**
     * Creates new form Borrower
     */
    public BorrowerFrame() {
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
    /**
     * 
     * @param row
     * @return 
     */
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
    public void hideQty(){
        qty.setVisible(false);
        qtyLabel.setVisible(false);
    }
    
    // Show
    public void showQty(){
        qty.setVisible(true);
        qtyLabel.setVisible(true);
    }
    
    // ActionListener
    public void addBtnListener(ActionListener action){
        addBtn.addActionListener(action);
    }
    public void editBtnListener(ActionListener action){
        editBtn.addActionListener(action);
    }
    public void deleteBtnListener(ActionListener action){
        deleteBtn.addActionListener(action);
    }
    public void backBtnListener(ActionListener action){
        backBtn.addActionListener(action);
    }
    public void userSettingsListener(ActionListener action){
        userSettings.addActionListener(action);
    }
    public void homeListener(ActionListener action){
        home.addActionListener(action);
    }
    public void chargeListener(ActionListener action){
        chargeMenu.addActionListener(action);
    }
    public void returnListener(ActionListener action){
        returnMenu.addActionListener(action);
    }
    public void booksListener(ActionListener action){
        booksMenu.addActionListener(action);
    }
    public void viewListener(ActionListener action){
        viewMenu.addActionListener(action);
    }
    public void userLogoutListener(ActionListener action){
        userLogout.addActionListener(action);
    }
    // Setters
    public void setUser(String name){
        userMenu.setText(name);
    }
    public void setDate(String date){
        dateMenu.setText(date);
    }
    public void setTime(String time){
        timeMenu.setText(time);
    }
    public void addBtnText(String txt, Icon icon) {
        addBtn.setText(txt);
        addBtn.setIcon(icon);
    }
    public void editBtnText(String txt, Icon icon) {
        editBtn.setText(txt);
        editBtn.setIcon(icon);
    }
    public void setEmail(String email) {
        this.email.setText(email);
    }
    public void setFname(String name) {
        this.fname.setText(name);
    }
    public void setLname(String name) {
        this.lname.setText(name);
    }
    public void setPhone(String phone) {
        this.phone.setText(phone);
    }
    public void setQty(String qty) {
        this.qty.setText(qty);
    }
    public void resetResult(){
        result.setText("");
    }
    public void setResult(String result, Color color) {
        this.result.setText(result);
        this.result.setForeground(color);
    }
    public void setTotal(String total) {
        this.total.setText(total);
    }

    // Getters
    public String getEmail() {
        return email.getText();
    }
    public String getFname() {
        return fname.getText();
    }
    public String getLname() {
        return lname.getText();
    }
    public String getPhone() {
        return phone.getText();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        fname = new javax.swing.JTextField();
        lname = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        addBtn = new javax.swing.JButton();
        phone = new javax.swing.JFormattedTextField();
        tablePane = new javax.swing.JScrollPane();
        backBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        result = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        qtyLabel = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        totalLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        userMenu = new javax.swing.JMenu();
        home = new javax.swing.JMenuItem();
        userSettings = new javax.swing.JMenuItem();
        userLogout = new javax.swing.JMenuItem();
        dateMenu = new javax.swing.JMenu();
        timeMenu = new javax.swing.JMenu();
        circulation = new javax.swing.JMenu();
        chargeMenu = new javax.swing.JMenuItem();
        returnMenu = new javax.swing.JMenuItem();
        booksMenu = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Relax - Borrower Information");
        setLocation(new java.awt.Point(200, 100));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Borrower Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jLabel1.setText("First name :");

        jLabel2.setText("Last name :");

        jLabel3.setText("Email :");

        jLabel4.setText("Phone :");

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/plus.png"))); // NOI18N
        addBtn.setText("Add");

        try {
            phone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(+63) ### ### ####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(email)
                            .addComponent(phone, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(addBtn)
                .addGap(22, 22, 22))
        );

        backBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/back.png"))); // NOI18N
        backBtn.setText("Back");

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/garbage16.png"))); // NOI18N
        deleteBtn.setText("Delete");

        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/edit.png"))); // NOI18N
        editBtn.setText("Edit");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        result.setFont(new java.awt.Font("Trajan Pro", 1, 12)); // NOI18N
        result.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        result.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        qty.setEditable(false);

        qtyLabel.setText("Quantity borrowed by borrower :");

        total.setEditable(false);

        totalLabel.setText("Total borrowers :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(qtyLabel)
                            .addComponent(totalLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(qty))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(result, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(result, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qtyLabel)
                    .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalLabel))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tablePane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(backBtn)
                            .addComponent(deleteBtn)
                            .addComponent(editBtn))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
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

        circulation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/redbook24.png"))); // NOI18N
        circulation.setText("Circulation");

        chargeMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/out24.png"))); // NOI18N
        chargeMenu.setText("Charge Out");
        circulation.add(chargeMenu);

        returnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/return24.png"))); // NOI18N
        returnMenu.setText("Return Books");
        circulation.add(returnMenu);

        booksMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/books24.png"))); // NOI18N
        booksMenu.setText("Books");
        circulation.add(booksMenu);

        viewMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/library/view/images/view24.png"))); // NOI18N
        viewMenu.setText("View");
        circulation.add(viewMenu);

        jMenuBar1.add(circulation);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(BorrowerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BorrowerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BorrowerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BorrowerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BorrowerFrame().setVisible(true);
            }
        });
    }
    
    
    private JTable table;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JMenuItem booksMenu;
    private javax.swing.JMenuItem chargeMenu;
    private javax.swing.JMenu circulation;
    private javax.swing.JMenu dateMenu;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fname;
    private javax.swing.JMenuItem home;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField lname;
    private javax.swing.JFormattedTextField phone;
    private javax.swing.JTextField qty;
    private javax.swing.JLabel qtyLabel;
    private javax.swing.JLabel result;
    private javax.swing.JMenuItem returnMenu;
    private javax.swing.JScrollPane tablePane;
    private javax.swing.JMenu timeMenu;
    private javax.swing.JTextField total;
    private javax.swing.JLabel totalLabel;
    private javax.swing.JMenuItem userLogout;
    private javax.swing.JMenu userMenu;
    private javax.swing.JMenuItem userSettings;
    private javax.swing.JMenuItem viewMenu;
    // End of variables declaration//GEN-END:variables
}
