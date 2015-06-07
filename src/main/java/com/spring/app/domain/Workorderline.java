package com.spring.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Workorderline.
 */
@Entity
@Table(name = "WORKORDERLINE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workorderline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "attrition")
    private String attrition;

    @Column(name = "bom_qty")
    private Integer bomQty;

    @Column(name = "requ_qty")
    private Integer requQty;

    @Column(name = "issued_qty")
    private Integer issuedQty;

    @Column(name = "is_cust_supplied")
    private Boolean isCustSupplied;

    @Size(min = 5)
    @Column(name = "remark")
    private String remark;

    @ManyToOne
    private Itemctn itemctn;
    
    @ManyToOne
    private Workorderheader workorderheader;
    
    @ManyToOne
    private Itemmtr itemmtr;

    public Workorderheader getWorkorderheader() {
		return workorderheader;
	}

	public void setWorkorderheader(Workorderheader workorderheader) {
		this.workorderheader = workorderheader;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttrition() {
        return attrition;
    }

    public void setAttrition(String attrition) {
        this.attrition = attrition;
    }

    public Integer getRequQty() {
        return requQty;
    }

    public void setRequQty(Integer requQty) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Itemctn getItemctn() {
        return itemctn;
    }

    public void setItemctn(Itemctn itemctn) {
        this.itemctn = itemctn;
    }

    public Integer getBomQty() {
		return bomQty;
	}

	public void setBomQty(Integer bomQty) {
		this.bomQty = bomQty;
	}

	public Itemmtr getItemmtr() {
		return itemmtr;
	}

	public void setItemmtr(Itemmtr itemmtr) {
		this.itemmtr = itemmtr;
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

        if ( ! Objects.equals(id, workorderline.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Workorderline{" +
                "id=" + id +
                ", attrition='" + attrition + "'" +
                ", bomQty='"+bomQty+"'"+
                ", requQty='" + requQty + "'" +
                ", issuedQty='" + issuedQty + "'" +
                ", isCustSupplied='" + isCustSupplied + "'" +
                ", remark='" + remark + "'" +
                '}';
    }
}
