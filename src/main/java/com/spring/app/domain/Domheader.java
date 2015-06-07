package com.spring.app.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.app.domain.util.CustomDateTimeDeserializer;
import com.spring.app.domain.util.CustomDateTimeSerializer;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Domheader.
 */
@Entity
@Table(name = "DOMHEADER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Domheader implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_auto")
    private Boolean isAuto;

    @Column(name = "cycle_time")
    private Integer cycleTime;

    @Column(name = "is_multi")
    private Boolean isMulti;

    @Column(name = "panel_qty")
    private Integer panelQty;

    @Column(name = "opr")
    private String opr;

    @Column(name = "shiftsup")
    private String shiftsup;

    @Column(name = "shift")
    private String shift;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "shiftstart")
    private DateTime shiftstart;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "shiftend")
    private DateTime shiftend;

    @Column(name = "solder")
    private String solder;
    
    @ManyToOne
    private Plantmc machine;

    /*its unique key refer to JMXQueue*/
    @Column(name = "jmxid")
    private String jmxid;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Boolean isAuto) {
        this.isAuto = isAuto;
    }

    public Integer getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
    }

    public Boolean getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(Boolean isMulti) {
        this.isMulti = isMulti;
    }

    public Integer getPanelQty() {
        return panelQty;
    }

    public void setPanelQty(Integer panelQty) {
        this.panelQty = panelQty;
    }

    public String getOpr() {
        return opr;
    }

    public void setOpr(String opr) {
        this.opr = opr;
    }

    public String getShiftsup() {
        return shiftsup;
    }

    public void setShiftsup(String shiftsup) {
        this.shiftsup = shiftsup;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public DateTime getShiftstart() {
        return shiftstart;
    }

    public void setShiftstart(DateTime shiftstart) {
        this.shiftstart = shiftstart;
    }

    public DateTime getShiftend() {
        return shiftend;
    }

    public void setShiftend(DateTime shiftend) {
        this.shiftend = shiftend;
    }

    public String getSolder() {
        return solder;
    }

    public void setSolder(String solder) {
        this.solder = solder;
    }

    
    public Plantmc getMachine() {
		return machine;
	}

	public void setMachine(Plantmc machine) {
		this.machine = machine;
	}

	public String getJmxid() {
		return jmxid;
	}

	public void setJmxid(String jmxid) {
		this.jmxid = jmxid;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Domheader domheader = (Domheader) o;

        if ( ! Objects.equals(id, domheader.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Domheader{" +
                "id=" + id +
                ", isAuto='" + isAuto + "'" +
                ", cycleTime='" + cycleTime + "'" +
                ", isMulti='" + isMulti + "'" +
                ", panelQty='" + panelQty + "'" +
                ", opr='" + opr + "'" +
                ", shiftsup='" + shiftsup + "'" +
                ", shift='" + shift + "'" +
                ", shiftstart='" + shiftstart + "'" +
                ", shiftend='" + shiftend + "'" +
                ", solder='" + solder + "'" +
                ", jmxid='"+jmxid+"'"+
                '}';
    }
}
