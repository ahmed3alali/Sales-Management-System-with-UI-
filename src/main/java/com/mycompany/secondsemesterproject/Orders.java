/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.secondsemesterproject;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "ORDERS")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrdernumber", query = "SELECT o FROM Orders o WHERE o.ordernumber = :ordernumber"),
    @NamedQuery(name = "Orders.findByNameofcustomer", query = "SELECT o FROM Orders o WHERE o.nameofcustomer = :nameofcustomer"),
    @NamedQuery(name = "Orders.findByItemname", query = "SELECT o FROM Orders o WHERE o.itemname = :itemname"),
    @NamedQuery(name = "Orders.findByCity", query = "SELECT o FROM Orders o WHERE o.city = :city"),
    @NamedQuery(name = "Orders.findByTotalprice", query = "SELECT o FROM Orders o WHERE o.totalprice = :totalprice"),
    @NamedQuery(name = "Orders.findByQuantity", query = "SELECT o FROM Orders o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "Orders.findByCategory", query = "SELECT o FROM Orders o WHERE o.category = :category")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ORDERNUMBER")
    private Integer ordernumber;
    @Column(name = "NAMEOFCUSTOMER")
    private String nameofcustomer;
    @Column(name = "ITEMNAME")
    private String itemname;
    @Column(name = "CITY")
    private String city;
    @Column(name = "TOTALPRICE")
    private Integer totalprice;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "CATEGORY")
    private String category;

    public Orders() {
    }

    public Orders(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public Integer getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(Integer ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getNameofcustomer() {
        return nameofcustomer;
    }

    public void setNameofcustomer(String nameofcustomer) {
        this.nameofcustomer = nameofcustomer;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ordernumber != null ? ordernumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.ordernumber == null && other.ordernumber != null) || (this.ordernumber != null && !this.ordernumber.equals(other.ordernumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.secondsemesterproject.Orders[ ordernumber=" + ordernumber + " ]";
    }
    
}
