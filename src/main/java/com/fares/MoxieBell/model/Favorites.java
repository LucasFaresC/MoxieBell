package com.fares.MoxieBell.model;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class Favorites extends MediaList {
    
    // Links a Track's UUID to the exact time it was favorited
    private Map<UUID, LocalDateTime> favoritedTimestamps; 

    public Map<UUID, LocalDateTime> getFavoritedTimestamps() {
        return favoritedTimestamps;
    }

    public void setFavoritedTimestamps(Map<UUID, LocalDateTime> favoritedTimestamps) {
        this.favoritedTimestamps = favoritedTimestamps;
    }
    
    
}