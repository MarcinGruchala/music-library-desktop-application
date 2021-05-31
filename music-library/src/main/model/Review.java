package main.model;

public class Review {
    private final Integer id;
    private final String review;
    private final String userID;
    private final Integer rating;
    private final String albumID;

    public Review(Integer id, String review, String userID, Integer rating, String albumID) {
        this.id = id;
        this.review = review;
        this.userID = userID;
        this.rating = rating;
        this.albumID = albumID;
    }

    public Integer getId() {
        return id;
    }

    public String getReview() {
        return review;
    }

    public String getUserID(){ return userID; }

    public Integer getRating() { return rating; }

    public String getAlbumID() { return albumID; }


    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", review='" + review + '\'' +
                ", userID='" + userID + '\'' +
                ", rating=" + rating +
                ", albumID='" + albumID + '\'' +
                '}';
    }
}
