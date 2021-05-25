package main;

import java.util.Date;

public class Album {
    private Integer id;
    private String title;
    private Date publicationDate;
    private String performer;
    private Integer review;

    public Album(Integer id, String title, Date publicationDate, String performer, Integer review) {
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
