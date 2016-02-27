package com.hipstercrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fruitpack.
 */
@Entity
@Table(name = "fruitpack")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fruitpack")
public class Fruitpack implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "price", precision=10, scale=2, nullable = false)
    private BigDecimal price;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "fruitpack")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orders> orderss = new HashSet<>();

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

    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
        Fruitpack fruitpack = (Fruitpack) o;
        if(fruitpack.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fruitpack.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fruitpack{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
