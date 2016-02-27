package com.hipstercrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Deliveryday.
 */
@Entity
@Table(name = "deliveryday")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "deliveryday")
public class Deliveryday implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 7)
    @Column(name = "weekday", nullable = false)
    private Integer weekday;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "deliveryday")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> orderss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeekday() {
        return weekday;
    }
    
    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Set<Orders> getOrderss() {
        return orderss;
    }

    public void setOrderss(Set<Orders> orderss) {
        this.orderss = orderss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deliveryday deliveryday = (Deliveryday) o;
        if(deliveryday.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, deliveryday.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Deliveryday{" +
            "id=" + id +
            ", weekday='" + weekday + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
