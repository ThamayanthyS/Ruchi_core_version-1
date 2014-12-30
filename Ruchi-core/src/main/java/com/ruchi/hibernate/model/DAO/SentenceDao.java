package com.ruchi.hibernate.model.DAO;

import javax.persistence.*;

/**
 * Created by Thamayanthy on 12/22/2014.
 */


@Entity
@Table(name = "sentence", uniqueConstraints = {@UniqueConstraint(columnNames = {"review_id", "sentence_location","food_id"})})
public class SentenceDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "review_id", nullable = false, unique = false, length = 50)
    private String review_id;

    @Column(name = "sentence_location", nullable = false, unique = false, length = 50)
    private int sentence_location;

    @Column(name = "food_id", nullable = false, unique = false, length = 50)
    private String food_id;

    @Column(name = "sentiment_value", nullable = false, unique = false, length = 50)
    private float sentiment_value;

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public int getSentence_location() {
        return sentence_location;
    }

    public void setSentence_location(int sentence_location) {
        this.sentence_location = sentence_location;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public float getSentiment_value() {
        return sentiment_value;
    }

    public void setSentiment_value(float sentiment_value) {
        this.sentiment_value = sentiment_value;
    }
}