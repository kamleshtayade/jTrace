package com.spring.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Itemmfrpart.
 */
@Entity
@Table(name = "T_ITEMMFRPART")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itemmfrpart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mfrpart")
    private String mfrpart;

    @Column(name = "status")
    private String status;

    @Column(name = "suppart")
    private String suppart;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    private Manufacturer manufacturer;
    
    @ManyToOne
    private Itemmtr itemmtr;
    
    public Itemmtr getItemmtr() {
		return itemmtr;
	}

	public void setItemmtr(Itemmtr itemmtr) {
		this.itemmtr = itemmtr;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMfrpart() {
        return mfrpart;
    }

    public void setMfrpart(String mfrpart) {
        this.mfrpart = mfrpart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuppart() {
        return suppart;
    }

    public void setSuppart(String suppart) {
        this.suppart = suppart;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Itemmfrpart itemmfrpart = (Itemmfrpart) o;

        if (id != null ? !id.equals(itemmfrpart.id) : itemmfrpart.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Itemmfrpart{" +
                "id=" + id +
                ", mfrpart='" + mfrpart + "'" +
                ", status='" + status + "'" +
                ", suppart='" + suppart + "'" +
                ", remarks='" + remarks + "'" +
                '}';
    }
}
