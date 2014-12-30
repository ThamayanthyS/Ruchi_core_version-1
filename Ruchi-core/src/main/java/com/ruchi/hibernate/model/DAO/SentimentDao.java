package com.ruchi.hibernate.model.DAO;

import javax.persistence.*;

/**
 * Created by Thamayanthy on 12/22/2014.
 */


@Entity
@Table(name = "sentiments", uniqueConstraints = {@UniqueConstraint(columnNames = {"rest_id", "food_id"})})
public class SentimentDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "rest_id", nullable = false, unique = false, length = 50)
    private String rest_id;

    @Column(name = "food_id", nullable = false, unique = false, length = 50)
    private String food_id;

    @Column(name = "sentiment_value", nullable = true, unique = false, length = 50)
    private String sentiment_value;

    @Column(name = "rank", nullable = true, unique = false, length = 50)
    private String rank;

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getSentiment_value() {
        return sentiment_value;
    }

    public void setSentiment_value(String sentiment_value) {
        this.sentiment_value = sentiment_value;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
