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
@Table(name = "CUSTOMERS")
@NamedQueries({
    @NamedQuery(name = "TheCustomers.findAll", query = "SELECT c FROM TheCustomers c"),
    @NamedQuery(name = "TheCustomers.findByName", query = "SELECT c FROM TheCustomers c WHERE c.name = :name"),
    @NamedQuery(name = "TheCustomers.findByLocation", query = "SELECT c FROM TheCustomers c WHERE c.location = :location"),
    @NamedQuery(name = "TheCustomers.findByMobilephone", query = "SELECT c FROM TheCustomers c WHERE c.mobilephone = :mobilephone")})
public class TheCustomers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LOCATION")
    private String location;
    @Id
    @Basic(optional = false)
    @Column(name = "MOBILEPHONE")
    private Integer mobilephone;

    public TheCustomers() {
    }

    public TheCustomers(Integer mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(Integer mobilephone) {
        this.mobilephone = mobilephone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mobilephone != null ? mobilephone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TheCustomers)) {
            return false;
        }
        TheCustomers other = (TheCustomers) object;
        if ((this.mobilephone == null && other.mobilephone != null) || (this.mobilephone != null && !this.mobilephone.equals(other.mobilephone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.secondsemesterproject.TheCustomers[ mobilephone=" + mobilephone + " ]";
    }
    
}
