package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Itemmtr.
 */
@Entity
@Table(name = "ITEMMTR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itemmtr implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Size(min = 5)
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "specification", nullable = false)
    private String specification;

    @NotNull
    @Column(name = "catid", nullable = false)
    private String catid;

    @NotNull
    @Column(name = "subcatid", nullable = false)
    private String subcatid;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getSubcatid() {
        return subcatid;
    }

    public void setSubcatid(String subcatid) {
        this.subcatid = subcatid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Itemmtr itemmtr = (Itemmtr) o;

        if ( ! Objects.equals(id, itemmtr.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Itemmtr{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", description='" + description + "'" +
                ", specification='" + specification + "'" +
                ", catid='" + catid + "'" +
                ", subcatid='" + subcatid + "'" +
                '}';
    }
}
