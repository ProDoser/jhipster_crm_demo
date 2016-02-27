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
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "contactperson", nullable = false)
    private String contactperson;
    
    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "email")
    private String email;
    
    @NotNull
    @Column(name = "country", nullable = false)
    private String country;
    
    @NotNull
    @Column(name = "city", nullable = false)
    private String city;
    
    @NotNull
    @Column(name = "postcode", nullable = false)
    private String postcode;
    
    @NotNull
    @Column(name = "street", nullable = false)
    private String street;
    
    @Column(name = "comment")
    private String comment;
    
    @NotNull
    @Column(name = "isprivate", nullable = false)
    private Boolean isprivate;
    
    @OneToMany(mappedBy = "customer")
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

    public String getContactperson() {
        return contactperson;
    }
    
    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }

    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsprivate() {
        return isprivate;
    }
    
    public void setIsprivate(Boolean isprivate) {
        this.isprivate = isprivate;
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
        Customer customer = (Customer) o;
        if(customer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", contactperson='" + contactperson + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", country='" + country + "'" +
            ", city='" + city + "'" +
            ", postcode='" + postcode + "'" +
            ", street='" + street + "'" +
            ", comment='" + comment + "'" +
            ", isprivate='" + isprivate + "'" +
            '}';
    }
}
