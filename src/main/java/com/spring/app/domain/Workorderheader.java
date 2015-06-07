package com.spring.app.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * A Workorderheader.
 */
@Entity
@Table(name = "WORKORDERHEADER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workorderheader implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "wo_number")
    private String woNumber;

    @Column(name = "kit_number")
    private String kitNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "qty")
    private Integer qty;
    
    @Column(name = "itemserial")
    private String itemserial;
    
    @ManyToOne
    private Itemmtr itemmtr;

    @ManyToOne
    private Plantmfgline plantmfgline;
    
    @OneToMany(mappedBy = "workorderheader")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Workorderline> worderlines = new HashSet<>();

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

    public Itemmtr getItemmtr() {
        return itemmtr;
    }

    public void setItemmtr(Itemmtr itemmtr) {
        this.itemmtr = itemmtr;
    }

    public Plantmfgline getPlantmfgline() {
        return plantmfgline;
    }

    public void setPlantmfgline(Plantmfgline plantmfgline) {
        this.plantmfgline = plantmfgline;
    }

    public Set<Workorderline> getWorderlines() {
		return worderlines;
	}

	public void setWorderlines(Set<Workorderline> worderlines) {
		this.worderlines = worderlines;
	}
	
    public String getItemserial() {
		return itemserial;
	}

	public void setItemserial(String itemserial) {
		this.itemserial = itemserial;
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

        if ( ! Objects.equals(id, workorderheader.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Workorderheader{" +
                "id=" + id +
                ", woNumber='" + woNumber + "'" +
                ", kitNumber='" + kitNumber + "'" +
                ", status='" + status + "'" +
                ", qty='" + qty + "'" +
                ", itemserial='"+itemserial+"'"+
                '}';
    }
}
