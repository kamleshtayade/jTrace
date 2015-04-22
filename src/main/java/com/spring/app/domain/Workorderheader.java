package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Workorderheader.
 */
@Entity
@Table(name = "T_WORKORDERHEADER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workorderheader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wo_number")
    private String woNumber;

    @Column(name = "kit_number")
    private String kitNumber;

    @Column(name = "customer")
    private String customer;

    @Column(name = "ship_to_loc")
    private String shipToLoc;

    @Column(name = "plant")
    private String plant;

    @Column(name = "plant_mfg_line")
    private String plantMfgLine;

    @Column(name = "status")
    private String status;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "so_number")
    private String soNumber;

    @Column(name = "item")
    private String item;

    @Column(name = "asy_code")
    private String asyCode;

    @Column(name = "bom")
    private String bom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWoNumber() {
        return woNumber;
    }

    public void setWoNumber(String woNumber) {
        this.woNumber = woNumber;
    }

    public String getKitNumber() {
        return kitNumber;
    }

    public void setKitNumber(String kitNumber) {
        this.kitNumber = kitNumber;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getShipToLoc() {
        return shipToLoc;
    }

    public void setShipToLoc(String shipToLoc) {
        this.shipToLoc = shipToLoc;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getPlantMfgLine() {
        return plantMfgLine;
    }

    public void setPlantMfgLine(String plantMfgLine) {
        this.plantMfgLine = plantMfgLine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getSoNumber() {
        return soNumber;
    }

    public void setSoNumber(String soNumber) {
        this.soNumber = soNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAsyCode() {
        return asyCode;
    }

    public void setAsyCode(String asyCode) {
        this.asyCode = asyCode;
    }

    public String getBom() {
        return bom;
    }

    public void setBom(String bom) {
        this.bom = bom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Workorderheader workorderheader = (Workorderheader) o;

        if (id != null ? !id.equals(workorderheader.id) : workorderheader.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Workorderheader{" +
                "id=" + id +
                ", woNumber='" + woNumber + "'" +
                ", kitNumber='" + kitNumber + "'" +
                ", customer='" + customer + "'" +
                ", shipToLoc='" + shipToLoc + "'" +
                ", plant='" + plant + "'" +
                ", plantMfgLine='" + plantMfgLine + "'" +
                ", status='" + status + "'" +
                ", qty='" + qty + "'" +
                ", soNumber='" + soNumber + "'" +
                ", item='" + item + "'" +
                ", asyCode='" + asyCode + "'" +
                ", bom='" + bom + "'" +
                '}';
    }
}
