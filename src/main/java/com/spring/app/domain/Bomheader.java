package com.spring.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Bomheader.
 */
@Entity
@Table(name = "BOMHEADER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bomheader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;
    
    @ManyToOne
    private Itemmtr itemmtr;
    
    @OneToMany(mappedBy = "bomheader")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Bomline> bomlines = new HashSet<>();  
    
    public Set<Bomline> getBomlines() {
		return bomlines;
	}

	public void setBomlines(Set<Bomline> bomlines) {
		this.bomlines = bomlines;
	}

	public Itemmtr getItemmtr() {
		return itemmtr;
	}

	public void setItemmtr(Itemmtr itemmtr) {
		this.itemmtr = itemmtr;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bomheader bomheader = (Bomheader) o;

        if ( ! Objects.equals(id, bomheader.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bomheader{" +
                "id=" + id +
                ", code='" + code + "'" +
                '}';
    }
}
