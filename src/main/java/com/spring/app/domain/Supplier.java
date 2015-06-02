package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Supplier.
 */
@Entity
@Table(name = "SUPPLIER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "isenabled")
    private Boolean isenabled;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Size(min = 5)
    @Column(name = "remark", nullable = false)
    private String remark;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "SUPPLIER_MANUFACTURER",
               joinColumns = @JoinColumn(name="suppliers_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="manufacturers_id", referencedColumnName="ID"))
    private Set<Manufacturer> manufacturers = new HashSet<>();


	public Set<Manufacturer> getManufacturers() {
		return manufacturers;
	}

	public void setManufacturers(Set<Manufacturer> manufacturers) {
		this.manufacturers = manufacturers;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(Boolean isenabled) {
        this.isenabled = isenabled;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Supplier supplier = (Supplier) o;

        if ( ! Objects.equals(id, supplier.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", isenabled='" + isenabled + "'" +
                ", address='" + address + "'" +
                ", remark='" + remark + "'" +
                '}';
    }
}
