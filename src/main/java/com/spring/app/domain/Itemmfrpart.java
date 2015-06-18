package com.spring.app.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.app.domain.util.CustomLocalDateSerializer;
import com.spring.app.domain.util.ISO8601LocalDateDeserializer;

/**
 * A Itemmfrpart.
 */
@Entity
@Table(name = "ITEMMFRPART")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itemmfrpart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "mfrpart", nullable = false)
    private String mfrpart;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "supplier")
    private String supplier;//supplier part number

    @NotNull
    @Size(min = 10)
    @Column(name = "remark", nullable = false)
    private String remark;

    @ManyToOne
    private Itemmtr itemmtr;
    
    @ManyToOne
    private Imanufacturer manufacturer; 

    @ManyToOne
    private Isupplier isupplier; 
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "activefrom")
    private LocalDate activefrom;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "activetill")
    private LocalDate activetill;
    
	public Isupplier getIsupplier() {
		return isupplier;
	}

	public void setIsupplier(Isupplier isupplier) {
		this.isupplier = isupplier;
	}

	public Imanufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Imanufacturer manufacturer) {
		this.manufacturer = manufacturer;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Itemmtr getItemmtr() {
        return itemmtr;
    }

    public void setItemmtr(Itemmtr itemmtr) {
        this.itemmtr = itemmtr;
    }
    
    public LocalDate getActivefrom() {
		return activefrom;
	}

	public void setActivefrom(LocalDate activefrom) {
		this.activefrom = activefrom;
	}

	public LocalDate getActivetill() {
		return activetill;
	}

	public void setActivetill(LocalDate activetill) {
		this.activetill = activetill;
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

        if ( ! Objects.equals(id, itemmfrpart.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Itemmfrpart{" +
                "id=" + id +
                ", mfrpart='" + mfrpart + "'" +
                ", status='" + status + "'" +
                ", supplier='" + supplier + "'" +
                ", activefrom='" + activefrom + "'" +
                ", activetill='" + activetill + "'" +
                ", remark='" + remark + "'" +
                '}';
    }
}
