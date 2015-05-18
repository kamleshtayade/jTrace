package com.spring.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.app.domain.util.CustomLocalDateSerializer;
import com.spring.app.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Itemctn.
 */
@Entity
@Table(name = "ITEMCTN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Itemctn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "ctn", nullable = false)
    private String ctn;

    @NotNull
    @Column(name = "reqd_qty", nullable = false)
    private Integer reqdQty;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "recd_dt", nullable = false)
    private LocalDate recdDt;


    @Column(name = "sr_no_to")
    private String srNoTo;
    
    @Column(name = "sr_no_from")
    private String srNoFrom;

    @Column(name = "self_life")
    private String selfLife;

    @Column(name = "po_qty")
    private String poQty;

    @Column(name = "invoice")
    private String invoice;
    
    @Column(name = "date_code")
    private String dateCode;

    @Column(name = "lot_code")
    private String lotCode;

    
    @ManyToOne
    private Itemmfrpart itemmfrpart;
    
    @ManyToOne
    private Customer customer;
    
    public String getSrNoFrom() {
		return srNoFrom;
	}

	public void setSrNoFrom(String srNoFrom) {
		this.srNoFrom = srNoFrom;
	}

	public String getDateCode() {
		return dateCode;
	}

	public void setDateCode(String dateCode) {
		this.dateCode = dateCode;
	}

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public Itemmfrpart getItemmfrpart() {
		return itemmfrpart;
	}

	public void setItemmfrpart(Itemmfrpart itemmfrpart) {
		this.itemmfrpart = itemmfrpart;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public Integer getReqdQty() {
        return reqdQty;
    }

    public void setReqdQty(Integer reqdQty) {
        this.reqdQty = reqdQty;
    }

    public LocalDate getRecdDt() {
        return recdDt;
    }

    public void setRecdDt(LocalDate recdDt) {
        this.recdDt = recdDt;
    }

    public String getSrNoTo() {
        return srNoTo;
    }

    public void setSrNoTo(String srNoTo) {
        this.srNoTo = srNoTo;
    }

    public String getSelfLife() {
        return selfLife;
    }

    public void setSelfLife(String selfLife) {
        this.selfLife = selfLife;
    }

    public String getPoQty() {
        return poQty;
    }

    public void setPoQty(String poQty) {
        this.poQty = poQty;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Itemctn itemctn = (Itemctn) o;

        if ( ! Objects.equals(id, itemctn.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Itemctn{" +
                "id=" + id +
                ", ctn='" + ctn + "'" +
                ", reqdQty='" + reqdQty + "'" +
                ", recdDt='" + recdDt + "'" +
                ", srNoTo='" + srNoTo + "'" +
                ", selfLife='" + selfLife + "'" +
                ", poQty='" + poQty + "'" +
                ", invoice='" + invoice + "'" +
                '}';
    }
}
