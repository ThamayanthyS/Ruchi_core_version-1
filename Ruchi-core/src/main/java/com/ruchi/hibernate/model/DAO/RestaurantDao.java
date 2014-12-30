package com.ruchi.hibernate.model.DAO;

import javax.persistence.*;

/**
 * Created by Thamayanthy on 12/22/2014.
 */

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id"})})
public class RestaurantDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "rest_id", nullable = false, unique = true, length = 20)
    private String rest_id;

    @Column(name = "city_id", nullable = false, unique = false, length = 20)
    private String city_id;

    @Column(name = "full_address", nullable = true, unique = false, length = 200)
    private String full_address;

    @Column(name = "rest_name", nullable = true, unique = false, length = 200)
    private String rest_name;

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
    }

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }
}