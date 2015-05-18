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
    private Supplier sup; //supplier details

    public Supplier getSup() {
		return sup;
	}

	public void setSup(Supplier sup) {
		this.sup = sup;
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
                ", remark='" + remark + "'" +
                '}';
    }
}
