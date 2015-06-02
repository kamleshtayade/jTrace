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
 * A Bomline.
 */
@Entity
@Table(name = "BOMLINE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bomline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "lineid", nullable = false)
    private Integer lineid;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "refdesignator")
    private String refdesignator;

    @Column(name = "physical")
    private Integer physical;

    @ManyToOne
    private Itemmtr itemmtr;

    @ManyToOne
    private Itemctn itemctn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineid() {
        return lineid;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getRefdesignator() {
        return refdesignator;
    }

    public void setRefdesignator(String refdesignator) {
        this.refdesignator = refdesignator;
    }

    public Integer getPhysical() {
        return physical;
    }

    public void setPhysical(Integer physical) {
        this.physical = physical;
    }

    public Itemmtr getItemmtr() {
        return itemmtr;
    }

    public void setItemmtr(Itemmtr itemmtr) {
        this.itemmtr = itemmtr;
    }

    public Itemctn getItemctn() {
        return itemctn;
    }

    public void setItemctn(Itemctn itemctn) {
        this.itemctn = itemctn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bomline bomline = (Bomline) o;

        if ( ! Objects.equals(id, bomline.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bomline{" +
                "id=" + id +
                ", lineid='" + lineid + "'" +
                ", quantity='" + quantity + "'" +
                ", refdesignator='" + refdesignator + "'" +
                ", physical='" + physical + "'" +
                '}';
    }
}
