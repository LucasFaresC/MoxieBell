import java.io.File;
import java.util.Date;


public class Musica{
    
    private int id;
    private Date addedDate;
    private boolean favorite;
    private MetaDataMP3 metaData;
    private File fileLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public MetaDataMP3 getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaDataMP3 metaData) {
        this.metaData = metaData;
    }

    public File getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(File fileLocation) {
        this.fileLocation = fileLocation;
    }

    
    
}