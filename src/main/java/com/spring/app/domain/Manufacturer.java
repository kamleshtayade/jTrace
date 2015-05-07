package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Manufacturer.
 */
@Entity
@Table(name = "MANUFACTURER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Manufacturer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Size(min = 3)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "isenabled")
    private Boolean isenabled;

    @Column(name = "mfrcat")
    private String mfrcat;

    @Column(name = "address")
    private String address;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Manufacturer manufacturer = (Manufacturer) o;

        if ( ! Objects.equals(id, manufacturer.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", name='" + name + "'" +
                ", isenabled='" + isenabled + "'" +
                ", mfrcat='" + mfrcat + "'" +
                ", address='" + address + "'" +
                '}';
    }
}
