
import java.time.Duration;

public class MetaDataMP3 {
    private String title;
    private String artist;
    private String Album;
    private String genere;
    private int launchYear;
    private int bpm;
    private byte[] albumArt;
    private Duration sizeDuration;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String Album) {
        this.Album = Album;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getLaunchYear() {
        return launchYear;
    }

    public void setLaunchYear(int launchYear) {
        this.launchYear = launchYear;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public byte[] getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(byte[] albumArt) {
        this.albumArt = albumArt;
    }

    public Duration getSizeDuration() {
        return sizeDuration;
    }

    public void setSizeDuration(Duration sizeDuration) {
        this.sizeDuration = sizeDuration;
    }

    


}