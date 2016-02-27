package com.hipstercrm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orders")
public class Orders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "details")
    private String details;
    
    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "fruitpack_id")
    private Fruitpack fruitpack;

    @ManyToOne
    @JoinColumn(name = "week_id")
    private Week week;

    @ManyToOne
    @JoinColumn(name = "deliveryday_id")
    private Deliveryday deliveryday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Fruitpack getFruitpack() {
        return fruitpack;
    }

    public void setFruitpack(Fruitpack fruitpack) {
        this.fruitpack = fruitpack;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Deliveryday getDeliveryday() {
        return deliveryday;
    }

    public void setDeliveryday(Deliveryday deliveryday) {
        this.deliveryday = deliveryday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Orders orders = (Orders) o;
        if(orders.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + id +
            ", details='" + details + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
