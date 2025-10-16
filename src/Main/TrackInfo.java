package Main;

public class TrackInfo {
    private String name;
    private int popularity;
    private String artist;
    private String spotifyUrl;
    private String imageUrl;

    public TrackInfo(String name, int popularity, String artist, String spotifyUrl, String imageUrl) {
        this.name = name;
        this.popularity = popularity;
        this.artist = artist;
        this.spotifyUrl = spotifyUrl;
        this.imageUrl = imageUrl;
    }

    // Getters pentru fiecare atribut
    public String getName() { return name; }
    public int getPopularity() { return popularity; }
    public String getArtist() { return artist; }
    public String getSpotifyUrl() { return spotifyUrl; }
    public String getImageUrl() { return imageUrl; }
}
