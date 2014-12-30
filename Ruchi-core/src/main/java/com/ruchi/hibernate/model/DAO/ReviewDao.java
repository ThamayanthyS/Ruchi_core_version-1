package com.ruchi.hibernate.model.DAO;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Thamayanthy on 12/22/2014.
 */


@Entity
@Table(name = "reviews", uniqueConstraints = {@UniqueConstraint(columnNames = {"review_id"})})
public class ReviewDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "review_id", nullable = false, unique = true, length = 20)
    private String review_id;

    @Column(name = "rest_id", nullable = false, unique = false, length = 20)
    private String rest_id;

    @Column(name = "review", nullable = true, unique = false, length = 200)
    private String review;

    @Column(name = "timestamp", nullable = true, unique = false, length = 50)
    private Date timestamp;

    @Column(name = "sentiment_value", nullable = true, unique = false, length = 50)
    private float sentiment_value;

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getRest_id() {
        return rest_id;
    }

    public void setRest_id(String rest_id) {
        this.rest_id = rest_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getSent_value() {
        return sentiment_value;
    }

    public void setSent_value(float sentiment_value) {
        this.sentiment_value = sentiment_value;
    }
}
