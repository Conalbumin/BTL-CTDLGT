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
                String genresString = data[2].replace("-", ",");

                ArrayList<String> genres = new ArrayList<>(Arrays.asList(genresString.split(",")));
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
        ArrayList<User> result = new ArrayList<>();

        // Find the rating given by the user with userId for the specified movieId
        int userRating = -1;
        for (Rating r : ratings) {
            if (r.getViewerId() == userId && r.getMovieId() == movieId) {
                userRating = r.getRatingStar();
                break;
            }
        }

        // If userRating is -1, it means the user hasn't rated the specified movieId
        if (userRating != -1) {
            // Find users who rated the same movie with the same number of stars
            for (Rating r : ratings) {
                if (r.getMovieId() == movieId && r.getRatingStar() == userRating && r.getViewerId() != userId) {
                    int viewerId = r.getViewerId();
                    // Find the user with the corresponding viewerId
                    for (User user : users) {
                        if (user.getId() == viewerId) {
                            result.add(user);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    // Requirement 4
    public ArrayList<String> findMoviesNameHavingSameReputation() {
        ArrayList<String> result = new ArrayList<>();

        // Count the number of ratings greater than 3 for each movie
        Map<Integer, Integer> movieRatingCount = new HashMap<>();
        for (Rating r : ratings) {
            if (r.getRatingStar() > 3) {
                movieRatingCount.put(r.getMovieId(), movieRatingCount.getOrDefault(r.getMovieId(), 0) + 1);
            }
        }

        // Collect movies with at least two favorable ratings
        for (Map.Entry<Integer, Integer> entry : movieRatingCount.entrySet()) {
            if (entry.getValue() >= 2) {
                int movieId = entry.getKey();
                // Find the movie with the corresponding movieId
                for (Movie movie : movies) {
                    if (movie.getId() == movieId) {
                        result.add(movie.getName());
                        break;
                    }
                }
            }
        }
        // Sort the result alphabetically
        result.sort(Comparator.naturalOrder());
        return result;
    }

    // @Requirement 5
    // tra ve danh sach chua
    // ten cua toi da k bo phim ma ng dung co cung occupation va gender da
    // danh gia cung mot diem so
    // ket qua phai dc sap xep theo alphabetically
    public ArrayList<String> findMoviesMatchOccupationAndGender(String occupation, String gender, int k,
            int rating) {
        ArrayList<String> result = new ArrayList<>();

        // Find users with the same occupation and gender
        ArrayList<Integer> matchingUserIds = new ArrayList<>();
        for (User user : users) {
            if (user.getOccupation().equalsIgnoreCase(occupation) && user.getGender().equalsIgnoreCase(gender)) {
                matchingUserIds.add(user.getId());
            }
        }

        // Count the number of ratings for each movie by users with the same occupation
        // and gender
        Map<Integer, Integer> movieRatingCount = new HashMap<>();
        for (Rating r : ratings) {
            if (matchingUserIds.contains(r.getViewerId()) && r.getRatingStar() == rating) {
                movieRatingCount.put(r.getMovieId(), movieRatingCount.getOrDefault(r.getMovieId(), 0) + 1);
            }
        }
        // Collect up to k movies with the highest ratings
        List<Map.Entry<Integer, Integer>> sortedMovies = new ArrayList<>(movieRatingCount.entrySet());
        sortedMovies.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        int count = 0;
        for (Map.Entry<Integer, Integer> entry : sortedMovies) {
            if (count >= k) {
                break; // Exit loop after collecting k movies
            }
            int movieId = entry.getKey();
            // Find the movie with the corresponding movieId
            for (Movie movie : movies) {
                if (movie.getId() == movieId) {
                    result.add(movie.getName());
                    count++;
                    break;
                }
            }
        }

        // Sort the result alphabetically
        result.sort(Comparator.naturalOrder());

        return result;
    }

    // @Requirement 6
    // tra ve danh sach chua
    // toi da k movie titles dc ng xem cung occupation
    // danh gia thap hon diem danh gia
    // ket qua phai dc sap xep theo alphabetically
    public ArrayList<String> findMoviesByOccupationAndLessThanRating(String occupation, int k, int rating) {
        /* code here */
        return null; /* change here */
    }

    // @Requirement 7
    // tra ve danh sach chua
    // toi da k movie titles dc ng xem cung gender va
    // ng xem co userId danh gia >= rating
    // va nhung movies nay share it nhat 1 the loai voi bo phim cuoi cung
    // dc ng dung voi userId danh gia
    // (thoi gian cuoi cung dc xem xet theo thuoc tinh timestamp)
    // ma diem danh gia cua no >= rating
    public ArrayList<String> findMoviesMatchLatestMovieOf(int userId, int rating, int k) {
        /* code here */
        return null; /* change here */
    }
}