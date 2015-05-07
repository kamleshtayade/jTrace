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

    @Size(min = 5)
    @Column(name = "description")
    private String description;

    @NotNull
    @Size(min = 5)
    @Column(name = "specification", nullable = false)
    private String specification;

    @ManyToOne
    private Itemsubcat itemsubcat;

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

    public Itemsubcat getItemsubcat() {
        return itemsubcat;
    }

    public void setItemsubcat(Itemsubcat itemsubcat) {
        this.itemsubcat = itemsubcat;
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
                '}';
    }
}
