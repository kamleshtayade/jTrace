package com.spring.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Itemsubcat.
 */
@Entity
@Table(name = "ITEMSUBCAT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itemsubcat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne
    private Itemcat itemcat;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Itemcat getItemcat() {
        return itemcat;
    }

    public void setItemcat(Itemcat itemcat) {
        this.itemcat = itemcat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Itemsubcat itemsubcat = (Itemsubcat) o;

        if ( ! Objects.equals(id, itemsubcat.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Itemsubcat{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", enabled='" + enabled + "'" +
                '}';
    }
}
