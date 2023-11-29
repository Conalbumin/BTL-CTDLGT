import java.util.*;

public class RatingManagement {
    private ArrayList<Rating> ratings;
    private ArrayList<Movie> movies;
    private ArrayList<User> users;

    // @Requirement 1
    public RatingManagement(String moviePath, String ratingPath, String userPath) {
        this.movies = loadMovies(moviePath);
        this.users = loadUsers(userPath);
        this.ratings = loadEdgeList(ratingPath);
    }

    private ArrayList<Rating> loadEdgeList(String ratingPath) {
        ArrayList<Rating> edgeList = new ArrayList<>();
        try (Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream(ratingPath))) {
            // Skip the header if it exists
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                int userId = Integer.parseInt(data[0]);
                int movieId = Integer.parseInt(data[1]);
                int rating = Integer.parseInt(data[2]);
                int timestamp = Integer.parseInt(data[3]);
                edgeList.add(new Rating(userId, movieId, rating, timestamp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return edgeList;
    }

    private ArrayList<Movie> loadMovies(String moviePath) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try (Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream(moviePath))) {
            // Skip the header if it exists
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                int movieId = Integer.parseInt(data[0]);
                String movieName = data[1];
                ArrayList<String> genres = new ArrayList<>(Arrays.asList(data[2].split(",")));
                // Add other movie attributes as needed
                movieList.add(new Movie(movieId, movieName, genres));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList;
    }

    private ArrayList<User> loadUsers(String userPath) {
        ArrayList<User> userList = new ArrayList<>();
        try (Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream(userPath))) {
            // Skip the header if it exists
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                int userId = Integer.parseInt(data[0]);
                String gender = data[1];
                int age = Integer.parseInt(data[2]);
                String occupation = data[3];
                String zipCode = data[4];
                // Add other user attributes as needed
                userList.add(new User(userId, gender, age, occupation, zipCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Rating> getRating() {
        return ratings;
    }

    // @Requirement 2
    public ArrayList<Movie> findMoviesByNameAndMatchRating(int userId, int rating) {
        ArrayList<Movie> result = new ArrayList<>();

        for (Rating r : ratings) {
            if (r.getViewerId() == userId && r.getRatingStar() >= rating) {
                int movieId = r.getMovieId();
                // Find the movie with the corresponding movieId
                for (Movie movie : movies) {
                    if (movie.getId() == movieId) {
                        result.add(movie);
                        break;
                    }
                }
            }
        }
    
        // Sort the result alphabetically by movie name
        result.sort(Comparator.comparing(Movie::getName));
    
        return result;
    }

    // Requirement 3
    public ArrayList<User> findUsersHavingSameRatingWithUser(int userId, int movieId) {
        /* code here */
        return null; /* change here */
    }

    // Requirement 4
    public ArrayList<String> findMoviesNameHavingSameReputation() {
        /* code here */
        return null; /* change here */
    }

    // @Requirement 5
    public ArrayList<String> findMoviesMatchOccupationAndGender(String occupation, String gender, int k,
            int rating) {
        /* code here */
        return null; /* change here */
    }

    // @Requirement 6
    public ArrayList<String> findMoviesByOccupationAndLessThanRating(String occupation, int k, int rating) {
        /* code here */
        return null; /* change here */
    }

    // @Requirement 7
    public ArrayList<String> findMoviesMatchLatestMovieOf(int userId, int rating, int k) {
        /* code here */
        return null; /* change here */
    }
}