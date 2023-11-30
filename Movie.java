import java.util.ArrayList;

public class Movie {
    private int id;
    private String name;
    private ArrayList<String> genres;

    public Movie(int id, String name, ArrayList<String> genres) {
        this.id = id;
        this.name = name;
        this.genres = genres;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getGenres() {
        return this.genres;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // Join genres with commas and space, and enclose them in square brackets
        String genresString = "[" + String.join(", ", genres) + "]";
        return String.format("Movie[%d, %s, %s]", id, name, genresString);
    }

}