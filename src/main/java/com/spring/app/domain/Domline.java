package com.spring.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Domline.
 */
@Entity
@Table(name = "DOMLINE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Domline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "serialno")
    private String serialno;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "scantime")
    private DateTime scantime;
    
    /*its unique key refer to JMXQueue*/
    @Column(name = "jmxid")
    private String jmxid;

    @ManyToOne
    private Domheader domheader;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public DateTime getScantime() {
        return scantime;
    }

    public void setScantime(DateTime scantime) {
        this.scantime = scantime;
    }

    public Domheader getDomheader() {
        return domheader;
    }

    public void setDomheader(Domheader domheader) {
        this.domheader = domheader;
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

        Domline domline = (Domline) o;

        if ( ! Objects.equals(id, domline.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Domline{" +
                "id=" + id +
                ", serialno='" + serialno + "'" +
                ", scantime='" + scantime + "'" +
                " jmxid='"+jmxid+"'"+
                '}';
    }
}
