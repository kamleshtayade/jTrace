package com.spring.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Itemcat.
 */
@Entity
@Table(name = "T_ITEMCAT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itemcat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(mappedBy = "itemcat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Itemsubcat> itemsubcats = new HashSet<>();

    @OneToMany(mappedBy = "itemcat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Itemmaster> itemmasters = new HashSet<>();

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

    public Set<Itemsubcat> getItemsubcats() {
        return itemsubcats;
    }

    public void setItemsubcats(Set<Itemsubcat> itemsubcats) {
        this.itemsubcats = itemsubcats;
    }

    public Set<Itemmaster> getItemmasters() {
        return itemmasters;
    }

    public void setItemmasters(Set<Itemmaster> itemmasters) {
        this.itemmasters = itemmasters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Itemcat itemcat = (Itemcat) o;

        if (id != null ? !id.equals(itemcat.id) : itemcat.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Itemcat{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", enabled='" + enabled + "'" +
                '}';
    }
}
