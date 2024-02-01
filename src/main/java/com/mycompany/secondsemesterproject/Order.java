/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.secondsemesterproject;

import java.awt.Color;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Order extends javax.swing.JFrame {

    DefaultTableModel myOrdersTable = new DefaultTableModel();

    String cols[] = {"Order Number", "Item Name", "Category", "Quantity", "Total Price", "Destination", "Customer"};

    



    EntityManagerFactory ordersMng = Persistence.createEntityManagerFactory("TheOrdersManagement");

   

    
    
    
    /**
     * Creates new form Order
     *
     *
     */
    Connection conn;

    public Order() {
        initComponents();

        this.setLocationRelativeTo(null);

        EntityManager ordersManage = ordersMng.createEntityManager();

        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/SecondSemesterProject", "app", "app");
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }

        AvaliableOrdersTable.setModel(myOrdersTable);
        myOrdersTable.setColumnIdentifiers(cols);

        PopulateWithStocksAvaliable();
        CustError.setVisible(false);
        MobileERROR.setVisible(false);
        QuantityError.setVisible(false);
        FromError.setVisible(false);
        ToError.setVisible(false);
        OnlyNumbersError.setVisible(false);
        OnlyNumbersError2.setVisible(false);
       myOrdersTable.setRowCount(0);
        PopulateWithDatabase();
        PreviousCustomersCmb.setEnabled(false);

    }
    
      private void deleteProductFromStock(String productName) {
        String mySQL = "DELETE FROM ORDERS WHERE ITEMNAME = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(mySQL); 
            
            statement.setString(1, productName);
            statement.executeUpdate();
            
            
        }catch (SQLException ex) {
            
        } 

     }
    

    String PrName;
    String cat;
    int quantity;
    String StartingLocation;
    String Destination;
    int total;
    

 

    public int determinePrice(String selectedProduct) {

        int thePrice = 0;
        String query = "SELECT SELLINGPRICE FROM ITEMSINSTOCK WHERE itemname = ?";
        try {

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, selectedProduct);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                thePrice = resultSet.getInt("SELLINGPRICE");

            }

        } catch (SQLException ex) {
            System.out.println("Error updating labels: " + ex.getMessage());
        }

        return thePrice;
    }

    public boolean checker() {
        boolean checkPassed = true;
        String custname = CustName.getText();
        String mobText = MobPhone.getText();
        int mob = 0;
        String from = FromFld.getText();
        String to = ToFldf.getText();
        String quantityText = Quantityfld.getText();

        if (ExisitingCustomer.isSelected() == false) {

            if (custname.isEmpty() || mobText.isEmpty() || from.isEmpty() || to.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                checkPassed = false;
                CustError.setVisible(custname.isEmpty());
                MobileERROR.setVisible(mobText.isEmpty());
                FromError.setVisible(from.isEmpty());
                ToError.setVisible(to.isEmpty());
                QuantityError.setVisible(quantityText.isEmpty());
            } else {
                try {
                    mob = Integer.parseInt(mobText);
                    int quantity = Integer.parseInt(quantityText);

                    if (mob == 0) {
                        JOptionPane.showMessageDialog(this, "Mobile number cannot be 0!");
                        checkPassed = false;
                        MobileERROR.setVisible(true);
                    }

                    if (quantity == 0) {
                        JOptionPane.showMessageDialog(this, "Quantity cannot be 0!");
                        checkPassed = false;
                        QuantityError.setVisible(true);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers in the required fields!");
                    checkPassed = false;
                }
            }
        } else {

            if (from.isEmpty() || to.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                checkPassed = false;

                FromError.setVisible(from.isEmpty());
                ToError.setVisible(to.isEmpty());
                QuantityError.setVisible(quantityText.isEmpty());
            } else {
                try {

                    int quantity = Integer.parseInt(quantityText);

                    if (quantity == 0) {
                        JOptionPane.showMessageDialog(this, "Quantity cannot be 0!");
                        checkPassed = false;
                        QuantityError.setVisible(true);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers in the required fields!");
                    checkPassed = false;
                }

            }

        }
        return checkPassed;
    }

    boolean checkIfAFldNumber(String fieldContain) {
        boolean status = true;
        if  (ExisitingCustomer.isSelected() == false) {

            try {
                Integer.parseInt(fieldContain);

            } catch (NumberFormatException e) {
                OnlyNumbersError.setVisible(true);
                status = false;
            }

        }
        return status;
    }

    boolean checkIfAQntNumber(String fieldContain) {

        try {

            Integer.parseInt(fieldContain);

            return true;
        } catch (NumberFormatException e) {
            OnlyNumbersError2.setVisible(true);
            return false;

        }

    }

    private int getNextOrderNumber() throws SQLException {
        int nextOrderNumber = 1;
        String query = "SELECT MAX(ORDERNUMBER) AS maxordernumber FROM app.ORDERS";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int maxOrderNumber = rs.getInt("maxordernumber");
            if (!rs.wasNull()) {
                nextOrderNumber = maxOrderNumber + 1;
            }
        }
        return nextOrderNumber;
    }

    public int generateOrderNumber() throws SQLException {
        int nextOrderNumber = getNextOrderNumber();
        return nextOrderNumber;
    }

    public void PopulateWithStocksAvaliable() {
        String sql = "SELECT * FROM ITEMSINSTOCK";
        try ( PreparedStatement p = conn.prepareStatement(sql)) {

            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()) {
                String productName = resultSet.getString("ITEMNAME");
                ProductCombo.addItem(productName);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void PopulateWithDatabase() {
        String sql = "SELECT * FROM ORDERS";
        try ( PreparedStatement p = conn.prepareStatement(sql)) {

            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()) {
                int OrderNumber = resultSet.getInt("ORDERNUMBER");
                String NameOfCustomer = resultSet.getString("NAMEOFCUSTOMER");
                String itemName = resultSet.getString("ITEMNAME");
                String City = resultSet.getString("CITY");
                String Category = resultSet.getString("CATEGORY");
                int Quantity = resultSet.getInt("QUANTITY");
                int totalPrice = resultSet.getInt("TOTALPRICE");
                myOrdersTable.addRow(new Object[]{OrderNumber, itemName, Category, Quantity, totalPrice, City, NameOfCustomer});
            }

        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlForCustomers = "SELECT NAMEOFCUSTOMER FROM ORDERS";
        try ( PreparedStatement pCust = conn.prepareStatement(sqlForCustomers)) {

            ResultSet resultSet = pCust.executeQuery();
            while (resultSet.next()) {
                String theCustomer = resultSet.getString("NAMEOFCUSTOMER");
                PreviousCustomersCmb.addItem(theCustomer);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean QuantityCheckerAdjustS(String ItemName, int amountWanted) {
        boolean avaliable = false;
        int quantity = 0;
        try {
            String theSQL = "SELECT QUANTITYAVALIABLE FROM app.ITEMSINSTOCK WHERE ITEMNAME=?";
            PreparedStatement ps = conn.prepareStatement(theSQL);
            ps.setString(1, ItemName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("QUANTITYAVALIABLE");
            } else {

                JOptionPane.showMessageDialog(this, "Object no found");
            }

            if (amountWanted > 0) {

                if (quantity >= amountWanted) {
                    avaliable = true;
                    int theLatestQNTty = quantity - amountWanted;

                    String theUpdateSql = "UPDATE ITEMSINSTOCK SET QUANTITYAVALIABLE = ? WHERE ITEMNAME=?";
                    PreparedStatement psy = conn.prepareStatement(theUpdateSql);
                    psy.setInt(1, theLatestQNTty);
                    psy.setString(2, ItemName);
                    psy.executeUpdate();
                    System.out.println("Done !! TEST PASSED !");
                } else {

                    avaliable = false;
                    JOptionPane.showMessageDialog(this, "No Stock Avalaible, Please request Products !");
                }

            } else {

                JOptionPane.showMessageDialog(this, "Please write proper quantity !");

            }

            // ... rest of your code
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
        return avaliable;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        OrdersTabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CustName = new javax.swing.JTextField();
        MobPhone = new javax.swing.JTextField();
        ProductCombo = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        Quantityfld = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        TotalLabel = new javax.swing.JLabel();
        FromFld = new javax.swing.JTextField();
        ToFldf = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        CustError = new javax.swing.JLabel();
        FromError = new javax.swing.JLabel();
        QuantityError = new javax.swing.JLabel();
        ToError = new javax.swing.JLabel();
        MobileERROR = new javax.swing.JLabel();
        OnlyNumbersError = new javax.swing.JLabel();
        OnlyNumbersError2 = new javax.swing.JLabel();
        CategoryFromCombo = new javax.swing.JLabel();
        ExisitingCustomer = new javax.swing.JCheckBox();
        PreviousCustomersCmb = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AvaliableOrdersTable = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        jLabel12.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        jLabel12.setText("Products In Stock");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Gill Sans MT", 0, 24)); // NOI18N
        jLabel14.setText("Request");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(71, 6, 56));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Franklin Gothic Medium", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("New Order");

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Orders Menu");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel18MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel18MouseExited(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\CP2\\Samm.png")); // NOI18N
        jLabel4.setText("jLabel1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(259, 259, 259)
                .addComponent(jLabel2)
                .addContainerGap(393, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addContainerGap())
        );

        CustName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustNameActionPerformed(evt);
            }
        });

        ProductCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProductComboItemStateChanged(evt);
            }
        });

        jButton5.setText("Confirm");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Total : ");

        TotalLabel.setFont(new java.awt.Font("Leelawadee UI", 0, 18)); // NOI18N
        TotalLabel.setForeground(new java.awt.Color(0, 0, 0));
        TotalLabel.setText("0 ");

        jLabel1.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Mobile ");

        jLabel5.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Category");

        jLabel7.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Customer Name");

        jLabel8.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Product");

        jLabel9.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Quantity");

        jLabel10.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("From ");

        jLabel11.setFont(new java.awt.Font("Gill Sans MT", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("To");

        CustError.setBackground(new java.awt.Color(204, 0, 0));
        CustError.setForeground(new java.awt.Color(204, 0, 0));
        CustError.setText("THIS FIELD IS CUMPLOSERY *");

        FromError.setBackground(new java.awt.Color(204, 0, 0));
        FromError.setForeground(new java.awt.Color(204, 0, 0));
        FromError.setText("THIS FIELD IS COMPULSORY *");

        QuantityError.setBackground(new java.awt.Color(204, 0, 0));
        QuantityError.setForeground(new java.awt.Color(204, 0, 0));
        QuantityError.setText("THIS FIELD IS COMPULSORY *");

        ToError.setBackground(new java.awt.Color(204, 0, 0));
        ToError.setForeground(new java.awt.Color(204, 0, 0));
        ToError.setText("THIS FIELD IS COMPULSORY *");

        MobileERROR.setBackground(new java.awt.Color(204, 0, 0));
        MobileERROR.setForeground(new java.awt.Color(204, 0, 0));
        MobileERROR.setText("THIS FIELD IS CUMPLOSERY *");

        OnlyNumbersError.setForeground(new java.awt.Color(204, 0, 0));
        OnlyNumbersError.setText("Please use numbers");

        OnlyNumbersError2.setForeground(new java.awt.Color(204, 0, 0));
        OnlyNumbersError2.setText("Please Use Numbers");

        CategoryFromCombo.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 14)); // NOI18N
        CategoryFromCombo.setForeground(new java.awt.Color(0, 0, 0));
        CategoryFromCombo.setText("SELECT AN ITEM TO DISPLAY CATEGORY");

        ExisitingCustomer.setText("Existing Customer");
        ExisitingCustomer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ExisitingCustomerItemStateChanged(evt);
            }
        });

        PreviousCustomersCmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviousCustomersCmbActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Leelawadee UI Semilight", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("TL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)))
                            .addComponent(ExisitingCustomer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(OnlyNumbersError2, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(OnlyNumbersError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(TotalLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ToFldf, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MobPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(CustName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(ProductCombo, javax.swing.GroupLayout.Alignment.LEADING, 0, 245, Short.MAX_VALUE)
                            .addComponent(Quantityfld, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(FromFld, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 32, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(CustError, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(MobileERROR, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FromError, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ToError, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(QuantityError, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 427, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CategoryFromCombo)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PreviousCustomersCmb, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ExisitingCustomer)
                    .addComponent(PreviousCustomersCmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CustName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CustError)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MobPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OnlyNumbersError)
                    .addComponent(MobileERROR)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CategoryFromCombo)
                    .addComponent(jLabel5))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProductCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Quantityfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OnlyNumbersError2)
                            .addComponent(jLabel9)
                            .addComponent(QuantityError))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(FromFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FromError))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ToFldf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ToError))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(TotalLabel)
                        .addComponent(jLabel3)))
                .addGap(47, 47, 47))
        );

        OrdersTabs.addTab("tab1", jPanel1);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(71, 6, 56));

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Back");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jLabel25.setIcon(new javax.swing.ImageIcon("C:/Users/User/Desktop/CP2/icons8-open-box-40.png")); // NOI18N
        jLabel25.setText("jLabel20");

        jLabel22.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Orders Avaliable");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(399, 399, 399)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(484, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addGap(1, 1, 1)
                .addComponent(jLabel6)
                .addGap(18, 18, 18))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(67, Short.MAX_VALUE)
                    .addComponent(jLabel22)
                    .addGap(34, 34, 34)))
        );

        AvaliableOrdersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(AvaliableOrdersTable);

        jButton2.setText("Delete Order");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton2)
                .addContainerGap(127, Short.MAX_VALUE))
        );

        OrdersTabs.addTab("tab2", jPanel6);

        getContentPane().add(OrdersTabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -26, 860, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CustNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CustNameActionPerformed


    private void ProductComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProductComboItemStateChanged

        String selectedProduct = (String) ProductCombo.getSelectedItem();
        String query = "SELECT  itemname, category FROM ITEMSINSTOCK WHERE itemname = ?";
        try {

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, selectedProduct);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String category = resultSet.getString("category");
                CategoryFromCombo.setText(category);

            }

        } catch (SQLException ex) {
            System.out.println("Error updating labels: " + ex.getMessage());
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_ProductComboItemStateChanged

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked

        dispose();
        Store b = new Store();

        b.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        jLabel2.setForeground(Color.WHITE);

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited

        jLabel2.setForeground(Color.WHITE);

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseExited

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked

        dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered

        jLabel9.setForeground(Color.BLACK);

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseExited


    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        boolean check = checker();
        boolean numbercheck = checkIfAFldNumber(MobPhone.getText());
        boolean numbercheckTwo = checkIfAQntNumber(Quantityfld.getText());
        boolean ExistingCustomerChecker = ExisitingCustomer.isSelected();
        String customerNameFromField = null;
        int mob = 0;
        if (check && numbercheck && numbercheckTwo) {
            try {
                String prNamey = (String) ProductCombo.getSelectedItem();
                if(ExisitingCustomer.isSelected() == false) {
                    customerNameFromField = CustName.getText();
                    mob = Integer.parseInt(MobPhone.getText());
                }
                String cat = CategoryFromCombo.getText();
                String category = CategoryFromCombo.getText();
                int quantity = Integer.parseInt(Quantityfld.getText());
                int total = determinePrice(prNamey) * quantity;
                TotalLabel.setText(Integer.toString(total));
                String StartLocation = FromFld.getText();
                String Destination = ToFldf.getText();
                int newStoreQuantity = 0;
                int orderno = generateOrderNumber();

                if (QuantityCheckerAdjustS(prNamey, quantity)) {
                    EntityManager OrderConfim = ordersMng.createEntityManager();
                    TheCustomers c = new TheCustomers();
                  
                    //  Query q = OrderConfim.createQuery("SELECT c FROM TheCustomers c WHERE c.mobilephone = :mobilephone");
                    // q.setParameter("mobilephone", mob);
                    //  List<TheCustomers> Thexisting = q.getResultList();

                    if (ExistingCustomerChecker) {
                        Orders o = new Orders();
                        o.setOrdernumber(orderno);
                        o.setItemname(prNamey);
                        o.setQuantity(quantity);
                        o.setCategory(category);
                        o.setCity(Destination);
                        o.setTotalprice(total);
                        o.setNameofcustomer(PreviousCustomersCmb.getSelectedItem().toString());

                        OrderConfim.getTransaction().begin();
                        OrderConfim.persist(o);
                        OrderConfim.getTransaction().commit();
                        OrderConfim.close();

                    } else {

                        Orders o = new Orders();
                        o.setOrdernumber(orderno);
                        o.setItemname(prNamey);
                        o.setQuantity(quantity);
                        o.setCategory(category);
                        o.setCity(Destination);
                        o.setTotalprice(total);
                        o.setNameofcustomer(customerNameFromField);
                        c.setName(customerNameFromField);
                        c.setMobilephone(mob);
                        c.setLocation(Destination);
                        OrderConfim.getTransaction().begin();
                        OrderConfim.persist(o);
                        OrderConfim.persist(c);
                        OrderConfim.getTransaction().commit();
                        OrderConfim.close();

                    }

                    JOptionPane.showMessageDialog(this, "Order Confirmed With Order Number : " + orderno);
                    
                    WelV1 w = new WelV1();
                    w.PopulateDashboard();
                     myOrdersTable.setRowCount(0);
 PopulateWithDatabase();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers in the required fields!");
            } catch (SQLException ex) {
                Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("failed");
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed


    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked

        OrdersTabs.setSelectedIndex(1);

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel18MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseEntered

        jLabel18.setForeground(new Color(121,158,81));  
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel18MouseEntered

    private void jLabel18MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseExited

        jLabel18.setForeground(Color.WHITE);

// TODO add your handling code here:
    }//GEN-LAST:event_jLabel18MouseExited

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked

        OrdersTabs.setSelectedIndex(0);

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered

        jLabel6.setForeground(new Color(121,158,81));  
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited

        jLabel6.setForeground(Color.WHITE);
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseExited

    private void PreviousCustomersCmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousCustomersCmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PreviousCustomersCmbActionPerformed

    private void ExisitingCustomerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ExisitingCustomerItemStateChanged
        if (ExisitingCustomer.isSelected()) {
            PreviousCustomersCmb.setEnabled(true);
            CustName.setEnabled(false);
            MobPhone.setEnabled(false);
        } else {

            PreviousCustomersCmb.setEnabled(false);
            CustName.setEnabled(true);
            MobPhone.setEnabled(true);
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_ExisitingCustomerItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed


int selectedRow =AvaliableOrdersTable.getSelectedRow();
                
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a product to delete.");
                    return;
                }
                
                String productName = (String) AvaliableOrdersTable.getValueAt(selectedRow, 1);
               
                
                
                  
                
                    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the product?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    
                    if (confirmation == JOptionPane.YES_OPTION) {
                        deleteProductFromStock(productName);
                        myOrdersTable.removeRow(selectedRow);
                        
                        }
                            
                



        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Order.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                Order b = new Order();
                b.setVisible(true);
                

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AvaliableOrdersTable;
    private javax.swing.JLabel CategoryFromCombo;
    private javax.swing.JLabel CustError;
    private javax.swing.JTextField CustName;
    private javax.swing.JCheckBox ExisitingCustomer;
    private javax.swing.JLabel FromError;
    private javax.swing.JTextField FromFld;
    private javax.swing.JTextField MobPhone;
    private javax.swing.JLabel MobileERROR;
    private javax.swing.JLabel OnlyNumbersError;
    private javax.swing.JLabel OnlyNumbersError2;
    private javax.swing.JTabbedPane OrdersTabs;
    private javax.swing.JComboBox<String> PreviousCustomersCmb;
    private javax.swing.JComboBox<String> ProductCombo;
    private javax.swing.JLabel QuantityError;
    private javax.swing.JTextField Quantityfld;
    private javax.swing.JLabel ToError;
    private javax.swing.JTextField ToFldf;
    private javax.swing.JLabel TotalLabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
