public class Rating {
    private int viewerId;
    private int movieId;
    private int ratingStar;
    private long timestamp; 

    // Constructor
    public Rating(int viewerId, int movieId, int ratingStar, long timestamp) {
        this.viewerId = viewerId;
        this.movieId = movieId;
        this.ratingStar = ratingStar;
        this.timestamp = timestamp;
    }

    // Getter and Setter methods
      public int getViewerId() {
        return viewerId;
    }

    public void setViewerId(int viewerId) {
        this.viewerId = viewerId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(int ratingStar) {
        this.ratingStar = ratingStar;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    

    // toString() method
    @Override
    public String toString() {
        return String.format("Rating[viewerId=%d, movieId=%d, ratingStar=%d, timestamp=%d]", 
                             viewerId, movieId, ratingStar, timestamp);
    }

    
    // Example of how to use the Rating class
    // public static void main(String[] args) {
    //     // Create a Rating object
    //     Rating rating = new Rating(1, 101, 4L, 123456789);

    //     // Print the rating using toString()
    //     System.out.println(rating.toString());
    // }
}
