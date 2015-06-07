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
 * A Isupplier.
 */
@Entity
@Table(name = "ISUPPLIER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Isupplier implements Serializable {

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
    @Size(min = 5)
    @Column(name = "remark", nullable = false)
    private String remark;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ISUPPLIER_IMANUFACTURER",
               joinColumns = @JoinColumn(name="isuppliers_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="imanufacturers_id", referencedColumnName="ID"))
    private Set<Imanufacturer> imanufacturers = new HashSet<>();

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Imanufacturer> getImanufacturers() {
        return imanufacturers;
    }

    public void setImanufacturers(Set<Imanufacturer> imanufacturers) {
        this.imanufacturers = imanufacturers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Isupplier isupplier = (Isupplier) o;

        if ( ! Objects.equals(id, isupplier.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Isupplier{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", isenabled='" + isenabled + "'" +
                ", remark='" + remark + "'" +
                ", address='" + address + "'" +
                '}';
    }
}
