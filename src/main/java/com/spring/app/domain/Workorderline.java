package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Workorderline.
 */
@Entity
@Table(name = "T_WORKORDERLINE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workorderline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "workorderheader")
    private String workorderheader;

    @Column(name = "bom_child_item")
    private String bomChildItem;

    @Column(name = "attrition")
    private String attrition;

    @Column(name = "requ_qty")
    private String requQty;

    @Column(name = "issued_qty")
    private Integer issuedQty;

    @Column(name = "is_cust_supplied")
    private Boolean isCustSupplied;

    @Column(name = "item_ctn")
    private String itemCtn;

    @Column(name = "remarks")
    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkorderheader() {
        return workorderheader;
    }

    public void setWorkorderheader(String workorderheader) {
        this.workorderheader = workorderheader;
    }

    public String getBomChildItem() {
        return bomChildItem;
    }

    public void setBomChildItem(String bomChildItem) {
        this.bomChildItem = bomChildItem;
    }

    public String getAttrition() {
        return attrition;
    }

    public void setAttrition(String attrition) {
        this.attrition = attrition;
    }

    public String getRequQty() {
        return requQty;
    }

    public void setRequQty(String requQty) {
        this.requQty = requQty;
    }

    public Integer getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(Integer issuedQty) {
        this.issuedQty = issuedQty;
    }

    public Boolean getIsCustSupplied() {
        return isCustSupplied;
    }

    public void setIsCustSupplied(Boolean isCustSupplied) {
        this.isCustSupplied = isCustSupplied;
    }

    public String getItemCtn() {
        return itemCtn;
    }

    public void setItemCtn(String itemCtn) {
        this.itemCtn = itemCtn;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Workorderline workorderline = (Workorderline) o;

        if (id != null ? !id.equals(workorderline.id) : workorderline.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Workorderline{" +
                "id=" + id +
                ", workorderheader='" + workorderheader + "'" +
                ", bomChildItem='" + bomChildItem + "'" +
                ", attrition='" + attrition + "'" +
                ", requQty='" + requQty + "'" +
                ", issuedQty='" + issuedQty + "'" +
                ", isCustSupplied='" + isCustSupplied + "'" +
                ", itemCtn='" + itemCtn + "'" +
                ", remarks='" + remarks + "'" +
                '}';
    }
}
