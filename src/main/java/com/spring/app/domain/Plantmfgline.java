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
 * A Plantmfgline.
 */
@Entity
@Table(name = "PLANTMFGLINE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plantmfgline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "capacity", nullable = false)
    private String capacity;

    @ManyToOne
    private Plantsec plantsec;

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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Plantsec getPlantsec() {
        return plantsec;
    }

    public void setPlantsec(Plantsec plantsec) {
        this.plantsec = plantsec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Plantmfgline plantmfgline = (Plantmfgline) o;

        if ( ! Objects.equals(id, plantmfgline.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Plantmfgline{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", capacity='" + capacity + "'" +
                '}';
    }
}
