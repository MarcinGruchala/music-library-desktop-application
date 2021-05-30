package main.model;

import java.util.Date;

public class Album {
    private final Integer id;
    private final String title;
    private final java.sql.Date publicationDate;
    private final String performer;
    private final Integer review;

    public Album(Integer id, String title, java.sql.Date publicationDate, String performer, Integer review) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.performer = performer;
        this.review = review;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getPerformer() {
        return performer;
    }

    public Integer getReview() {
        return review;
    }
}
