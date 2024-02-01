/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.secondsemesterproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class Customers extends javax.swing.JFrame {

  
  
    
    
    
    
     DefaultTableModel myCustTable = new DefaultTableModel();
    
    String cols[] = {"Customer Name", "Mobile", "Location"};
    
    
    String custName;
    long MobPhone;
    String Location;

   
    
    EntityManagerFactory ordersMng = Persistence.createEntityManagerFactory("TheOrdersManagement");
    
    
    
    /**
     * Creates new form Customers
     */
    Connection cn;
    public Customers() {
        initComponents();
        try {
               cn = DriverManager.getConnection("jdbc:derby://localhost:1527/SecondSemesterProject","app","app");
           } catch (SQLException ex) {
               Logger.getLogger(Customers.class.getName()).log(Level.SEVERE, null, ex);
           }
         
         EntityManager ordersManage = ordersMng.createEntityManager();
        custtable.setModel(myCustTable);
        myCustTable.setColumnIdentifiers(cols);
        populateWithDataBase();
        
         this.setLocationRelativeTo(null);
         CustomerNameLabel.setVisible(false);
         CustomerMobileFld.setVisible(false);
         LocationLabel.setVisible(false);
CustomerNameFld.setVisible(false);
MobilePhoneLabel.setVisible(false);
LocationLabel.setVisible(false);
ConfirmBtn.setVisible(false);
CustomerLocationFld.setVisible(false);
EmptyFieldsLabel.setVisible(false);
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
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        custtable = new javax.swing.JTable();
        DeleteCustomerBtn = new javax.swing.JButton();
        NewCustomerBtn = new javax.swing.JButton();
        CustomerNameLabel = new javax.swing.JLabel();
        CustomerNameFld = new javax.swing.JTextField();
        MobilePhoneLabel = new javax.swing.JLabel();
        CustomerMobileFld = new javax.swing.JTextField();
        ConfirmBtn = new javax.swing.JButton();
        CustomerLocationFld = new javax.swing.JTextField();
        LocationLabel = new javax.swing.JLabel();
        EmptyFieldsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(71, 6, 56));
        jPanel2.setForeground(new java.awt.Color(71, 6, 56));

        jLabel11.setFont(new java.awt.Font("Leelawadee UI", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText(" Customers Management");

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Desktop\\CP2\\Samm.png")); // NOI18N
        jLabel3.setText("jLabel1");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(47, 47, 47)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(44, 44, 44))
        );

        custtable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(custtable);

        DeleteCustomerBtn.setText("Delete Customer");
        DeleteCustomerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteCustomerBtnActionPerformed(evt);
            }
        });

        NewCustomerBtn.setText("New Customer");
        NewCustomerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewCustomerBtnActionPerformed(evt);
            }
        });

        CustomerNameLabel.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        CustomerNameLabel.setForeground(new java.awt.Color(0, 0, 0));
        CustomerNameLabel.setText("NAME");

        MobilePhoneLabel.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        MobilePhoneLabel.setForeground(new java.awt.Color(0, 0, 0));
        MobilePhoneLabel.setText("MOBILE");

        CustomerMobileFld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CustomerMobileFldActionPerformed(evt);
            }
        });

        ConfirmBtn.setText("Confirm");
        ConfirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmBtnActionPerformed(evt);
            }
        });

        LocationLabel.setFont(new java.awt.Font("Leelawadee UI", 0, 14)); // NOI18N
        LocationLabel.setForeground(new java.awt.Color(0, 0, 0));
        LocationLabel.setText("Location");

        EmptyFieldsLabel.setForeground(new java.awt.Color(204, 0, 0));
        EmptyFieldsLabel.setText("ALL FIELDS ARE COMPLORSY / ENTER PROPER FORMAT*");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MobilePhoneLabel)
                    .addComponent(CustomerNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CustomerMobileFld, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CustomerNameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(LocationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CustomerLocationFld, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ConfirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EmptyFieldsLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteCustomerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NewCustomerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(DeleteCustomerBtn)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(NewCustomerBtn)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CustomerNameLabel)
                            .addComponent(CustomerNameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EmptyFieldsLabel)
                        .addGap(31, 31, 31)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MobilePhoneLabel)
                    .addComponent(CustomerMobileFld, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CustomerLocationFld, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LocationLabel)
                    .addComponent(ConfirmBtn))
                .addGap(18, 59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void populateWithDataBase () {
    
  myCustTable.setRowCount(0);
  
   try {

             Statement stmt = cn.createStatement();
            String query = "SELECT NAME, LOCATION, MOBILEPHONE FROM Customers";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("Name");
                String location = rs.getString("Location");
                int mobile = rs.getInt("Mobilephone");

                // Add customer data to the table model
                myCustTable.addRow(new Object[]{name, mobile, location});
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    

    
    }
    
    
    public void checker() {
        int selectedRow = custtable.getSelectedRow();
                if (selectedRow != -1) {
                    String customerName = myCustTable.getValueAt(selectedRow, 0).toString();

                    // Check if the customer has an order
                    if (hasOrderForCustomer(customerName)) {
                        int option = JOptionPane.showConfirmDialog(this,
                                "This customer has an order. Do you want to delete the order as well?",
                                "Delete Customer",
                                JOptionPane.YES_NO_OPTION);

                        if (option == JOptionPane.YES_OPTION) {
                            // Delete the customer and the order
                            deleteCustomerAndOrder(customerName, myCustTable, selectedRow);
                        }
                    } else {
                        // Delete the customer only
                        deleteCustomer(customerName, myCustTable, selectedRow);
                    }
                }
    
    
    
    }
    
    
    
    
     private boolean hasOrderForCustomer(String customerName) {
        try {
             PreparedStatement stmt = cn.prepareStatement("SELECT * FROM Orders WHERE nameofcustomer = ?");
            stmt.setString(1, customerName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
     
     public boolean checkFields () {
     boolean checkpassed = true;
         String name = CustomerNameFld.getText();
          String location  =CustomerLocationFld.getText();
          String mobile = CustomerMobileFld.getText();
          int MobPhoneNumber = 0;
              if (name.isEmpty() || mobile.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                checkpassed = false;
                EmptyFieldsLabel.setVisible(name.isEmpty());
                EmptyFieldsLabel.setVisible(mobile.isEmpty());
                EmptyFieldsLabel.setVisible(location.isEmpty());
                
            } else {
                try {
                    MobPhoneNumber = Integer.parseInt(mobile);
                    

                    if (MobPhoneNumber == 0) {
                        JOptionPane.showMessageDialog(this, "Mobile number cannot be 0!");
                        checkpassed = false;
                        EmptyFieldsLabel.setVisible(true);
                    }

                  
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid mobile number in the required fields");
                    checkpassed = false;
                }
     
     }
              return checkpassed;
     }
    
    
     private void deleteCustomerAndOrder(String customerName, DefaultTableModel tableModel, int selectedRow) {
        try {
             PreparedStatement deleteOrderStmt = cn.prepareStatement("DELETE FROM Orders WHERE nameofcustomer = ?");
             PreparedStatement deleteCustomerStmt = cn.prepareStatement("DELETE FROM Customers WHERE Name = ?"); 

            // Delete the order
            deleteOrderStmt.setString(1, customerName);
            deleteOrderStmt.executeUpdate();

            // Delete the customer
            deleteCustomerStmt.setString(1, customerName);
            deleteCustomerStmt.executeUpdate();

            // Remove the customer row from the table
            tableModel.removeRow(selectedRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    private void deleteCustomer(String customerName, DefaultTableModel tableModel, int selectedRow) {
        try {
             PreparedStatement deleteCustomerStmt = cn.prepareStatement("DELETE FROM Customers WHERE Name = ?");

            // Delete the customer
            deleteCustomerStmt.setString(1, customerName);
            deleteCustomerStmt.executeUpdate();

            // Remove the customer row from the table
            tableModel.removeRow(selectedRow);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseEntered

    private void DeleteCustomerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteCustomerBtnActionPerformed

checker();
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteCustomerBtnActionPerformed

    private void NewCustomerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewCustomerBtnActionPerformed

             int i = JOptionPane.showConfirmDialog(this, "Are you sure you don't want to add a new customer to the system ? ", "Customer Management", JOptionPane.YES_NO_OPTION);
        if (i==JOptionPane.YES_OPTION) {
            
        
        
        CustomerNameLabel.setVisible(true);
         CustomerMobileFld.setVisible(true);
         LocationLabel.setVisible(true);
CustomerNameFld.setVisible(true);
MobilePhoneLabel.setVisible(true);
LocationLabel.setVisible(true);
ConfirmBtn.setVisible(true);

CustomerLocationFld.setVisible(true);

        }


        // TODO add your handling code here:
    }//GEN-LAST:event_NewCustomerBtnActionPerformed

    private void CustomerMobileFldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CustomerMobileFldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CustomerMobileFldActionPerformed

    private void ConfirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmBtnActionPerformed
boolean testFields = checkFields();

if (testFields) {
EntityManager CustomersAddition = ordersMng.createEntityManager();
TheCustomers c = new TheCustomers();
 c.setName(CustomerNameFld.getText());
                c.setMobilephone(Integer.parseInt(CustomerMobileFld.getText()));
                c.setLocation(CustomerLocationFld.getText());
                CustomersAddition.getTransaction().begin();
                CustomersAddition.persist(c);
                CustomersAddition.getTransaction().commit();
                    CustomersAddition.close();

         populateWithDataBase ();
}

        // TODO add your handling code here:
    }//GEN-LAST:event_ConfirmBtnActionPerformed

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
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Customers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Customers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConfirmBtn;
    private javax.swing.JTextField CustomerLocationFld;
    private javax.swing.JTextField CustomerMobileFld;
    private javax.swing.JTextField CustomerNameFld;
    private javax.swing.JLabel CustomerNameLabel;
    private javax.swing.JButton DeleteCustomerBtn;
    private javax.swing.JLabel EmptyFieldsLabel;
    private javax.swing.JLabel LocationLabel;
    private javax.swing.JLabel MobilePhoneLabel;
    private javax.swing.JButton NewCustomerBtn;
    private javax.swing.JTable custtable;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
