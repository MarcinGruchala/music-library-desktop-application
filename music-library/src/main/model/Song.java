package main.model;

public class Song {
    private final Integer id;
    private final String title;
    private final String albumTitle;
    private final Integer numberInAlbum;
    private final String genre;
    private final Integer duration;

    public Song(Integer id, String title, String albumTitle, Integer numberInAlbum, String genre, Integer duration) {
        this.id = id;
        this.title = title;
        this.albumTitle = albumTitle;
        this.numberInAlbum = numberInAlbum;
        this.genre = genre;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public Integer getNumberInAlbum() {
        return numberInAlbum;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", albumTitle='" + albumTitle + '\'' +
                ", numberInAlbum=" + numberInAlbum +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                '}';
    }
}
