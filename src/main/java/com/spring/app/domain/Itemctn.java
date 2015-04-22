package com.spring.app.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.app.domain.util.CustomLocalDateSerializer;
import com.spring.app.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Itemctn.
 */
@Entity
@Table(name = "T_ITEMCTN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itemctn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ctn")
    private String ctn;

    @Column(name = "sr_no_from")
    private String srNoFrom;

    @Column(name = "reqd_qty")
    private Integer reqdQty;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "recd_dt", nullable = false)
    private LocalDate recdDt;

    @Column(name = "item")
    private String item;

    @Column(name = "sr_no_to")
    private String srNoTo;

    @Column(name = "self_life")
    private String selfLife;

    @Column(name = "po_qty")
    private Integer poQty;

    @Column(name = "invoice")
    private String invoice;

    @Column(name = "manufaturer")
    private String manufaturer;

    @Column(name = "mfg_part_no")
    private String mfgPartNo;

    @Column(name = "supplier")
    private String supplier;

    @Column(name = "date_code")
    private String dateCode;

    @Column(name = "lot_code")
    private String lotCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getSrNoFrom() {
        return srNoFrom;
    }

    public void setSrNoFrom(String srNoFrom) {
        this.srNoFrom = srNoFrom;
    }

    public Integer getReqdQty() {
        return reqdQty;
    }

    public void setReqdQty(Integer reqdQty) {
        this.reqdQty = reqdQty;
    }

    public LocalDate getRecdDt() {
        return recdDt;
    }

    public void setRecdDt(LocalDate recdDt) {
        this.recdDt = recdDt;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSrNoTo() {
        return srNoTo;
    }

    public void setSrNoTo(String srNoTo) {
        this.srNoTo = srNoTo;
    }

    public String getSelfLife() {
        return selfLife;
    }

    public void setSelfLife(String selfLife) {
        this.selfLife = selfLife;
    }

    public Integer getPoQty() {
        return poQty;
    }

    public void setPoQty(Integer poQty) {
        this.poQty = poQty;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getManufaturer() {
        return manufaturer;
    }

    public void setManufaturer(String manufaturer) {
        this.manufaturer = manufaturer;
    }

    public String getMfgPartNo() {
        return mfgPartNo;
    }

    public void setMfgPartNo(String mfgPartNo) {
        this.mfgPartNo = mfgPartNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getLotCode() {
        return lotCode;
    }

    public void setLotCode(String lotCode) {
        this.lotCode = lotCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Itemctn itemctn = (Itemctn) o;

        if (id != null ? !id.equals(itemctn.id) : itemctn.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Itemctn{" +
                "id=" + id +
                ", ctn='" + ctn + "'" +
                ", srNoFrom='" + srNoFrom + "'" +
                ", reqdQty='" + reqdQty + "'" +
                ", recdDt='" + recdDt + "'" +
                ", item='" + item + "'" +
                ", srNoTo='" + srNoTo + "'" +
                ", selfLife='" + selfLife + "'" +
                ", poQty='" + poQty + "'" +
                ", invoice='" + invoice + "'" +
                ", manufaturer='" + manufaturer + "'" +
                ", mfgPartNo='" + mfgPartNo + "'" +
                ", supplier='" + supplier + "'" +
                ", dateCode='" + dateCode + "'" +
                ", lotCode='" + lotCode + "'" +
                '}';
    }
}
