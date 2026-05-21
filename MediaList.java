
import java.time.LocalDate;
import java.util.Date;


public abstract class MediaList{
    private int id;
    private Date dateWhenCreated;
    private LocalDate lastModif;
    private int numberOfMusics;
    private int totalDuration;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateWhenCreated() {
        return dateWhenCreated;
    }

    public void setDateWhenCreated(Date dateWhenCreated) {
        this.dateWhenCreated = dateWhenCreated;
    }


    public int getNumberOfMusics() {
        return numberOfMusics;
    }

    public void setNumberOfMusics(int numberOfMusics) {
        this.numberOfMusics = numberOfMusics;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public LocalDate getLastModif() {
        return lastModif;
    }

    public void setLastModif(LocalDate lastModif) {
        this.lastModif = lastModif;
    }
    
    public void updateLastModif(){
        this.setLastModif(LocalDate.now());
    } 


}