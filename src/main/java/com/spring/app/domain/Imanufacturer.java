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
 * A Imanufacturer.
 */
@Entity
@Table(name = "IMANUFACTURER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Imanufacturer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isenabled")
    private Boolean isenabled;

    @NotNull
    @Size(min = 3)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "mfrcat")
    private String mfrcat;

    @Column(name = "address")
    private String address;

    @ManyToMany(mappedBy = "imanufacturers")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Isupplier> isuppliers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(Boolean isenabled) {
        this.isenabled = isenabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMfrcat() {
        return mfrcat;
    }

    public void setMfrcat(String mfrcat) {
        this.mfrcat = mfrcat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Isupplier> getIsuppliers() {
        return isuppliers;
    }

    public void setIsuppliers(Set<Isupplier> isuppliers) {
        this.isuppliers = isuppliers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Imanufacturer imanufacturer = (Imanufacturer) o;

        if ( ! Objects.equals(id, imanufacturer.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Imanufacturer{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", isenabled='" + isenabled + "'" +
                ", code='" + code + "'" +
                ", mfrcat='" + mfrcat + "'" +
                ", address='" + address + "'" +
                '}';
    }
}
