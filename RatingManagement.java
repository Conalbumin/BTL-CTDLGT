import java.util.*;
import java.util.stream.Collectors;

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
            // Skip the header
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
            // Skip the header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                int movieId = Integer.parseInt(data[0]);
                String movieName = data[1];
                // nhieu genres trong cung 1 phim dc phan ra bang - nen thay cho dung yeu cau
                String genresString = data[2].replace("-", ",");
                ArrayList<String> genres = new ArrayList<>(Arrays.asList(genresString.split(",")));

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
                for (Movie movie : movies) {
                    if (movie.getId() == movieId) {
                        result.add(movie);
                        break;
                    }
                }
            }
        }
        result.sort(Comparator.comparing((Movie movie) -> movie.getName()));
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
                if (r.getMovieId() == movieId &&
                        r.getRatingStar() == userRating &&
                        r.getViewerId() != userId) {
                    int viewerId = r.getViewerId();
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

        // Count the number of ratings greater than 3 for each movie (favored)
        // dung Map de tim kiem cac key (hieu qua hon ArrayList)
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
        // Iterate through ratings and match criteria
        for (Rating r : ratings) {
            if (r.getRatingStar() == rating) {
                User user = findUserById(r.getViewerId());
                if (user != null && user.getOccupation().equals(occupation) && user.getGender().equals(gender)) {
                    Movie movie = findMovieById(r.getMovieId());
                    if (movie != null) {
                        result.add(movie.getName());
                    }
                }
            }
        }

        // Remove duplicates
        ArrayList<String> distinctResult = new ArrayList<>(new HashSet<>(result));

        // Sort alphabetically
        Collections.sort(distinctResult);

        // Limit to k results
        if (distinctResult.size() > k) {
            return new ArrayList<>(distinctResult.subList(0, k));
        } else {
            return distinctResult;
        }
    }

    // @Requirement 6
    // tra ve danh sach chua
    // toi da k movie titles dc ng xem cung occupation
    // danh gia thap hon diem danh gia
    // ket qua phai dc sap xep theo alphabetically
    public ArrayList<String> findMoviesByOccupationAndLessThanRating(String occupation, int k, int rating) {
        List<String> result = new ArrayList<>();

        // Iterate through ratings and match criteria
        for (Rating r : ratings) {
            if (r.getRatingStar() < rating) {
                User user = findUserById(r.getViewerId());
                if (user != null && user.getOccupation().equals(occupation)) {
                    Movie movie = findMovieById(r.getMovieId());
                    if (movie != null) {
                        result.add(movie.getName());
                    }
                }
            }
        }

        // Remove duplicates
        ArrayList<String> distinctResult = new ArrayList<>(new HashSet<>(result));

        // Sort alphabetically
        Collections.sort(distinctResult);

        // Limit to k results
        if (distinctResult.size() > k) {
            return new ArrayList<>(distinctResult.subList(0, k));
        } else {
            return distinctResult;
        }

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
        List<String> result = new ArrayList<>();
        String userGender = findUserById(userId).getGender();
        Movie latestMovie = findLatestMovieReviewedByUser(userId, rating);

        if (latestMovie == null) {
            return new ArrayList<>(); // No latest movie found
        }

        Set<String> genresOfLatestMovie = new HashSet<>(latestMovie.getGenres());

        // Iterate through ratings and match criteria
        for (Rating r : ratings) {
            if (r.getRatingStar() >= rating) {
                User user = findUserById(r.getViewerId());
                if (user != null && user.getGender().equals(userGender)) {
                    Movie movie = findMovieById(r.getMovieId());
                    if (movie != null && movie.getId() != latestMovie.getId()
                            && sameGenres(movie, genresOfLatestMovie)) {
                        result.add(movie.getName());
                    }
                }
            }
        }

        // Remove duplicates
        ArrayList<String> distinctResult = new ArrayList<>(new HashSet<>(result));

        // Sort alphabetically
        Collections.sort(distinctResult);

        // Limit to k results
        if (distinctResult.size() > k) {
            return new ArrayList<>(distinctResult.subList(0, k));
        } else {
            return distinctResult;
        }
    }

    // Helper method to find a user by their ID
    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    // Helper method to find a movie by its ID
    private Movie findMovieById(int movieId) {
        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                return movie;
            }
        }
        return null;
    }

    // Helper method to find the latest movie reviewed by a user
    private Movie findLatestMovieReviewedByUser(int userId, int rating) {
        Rating latestRating = null;
        for (Rating r : ratings) {
            if (r.getViewerId() == userId && r.getRatingStar() >= rating) {
                if (latestRating == null || r.getTimestamp() > latestRating.getTimestamp()) {
                    latestRating = r;
                }
            }
        }
        return latestRating != null ? findMovieById(latestRating.getMovieId()) : null;
    }

    // Helper method to check if a movie shares at least one genre with a set of
    // genres
    private boolean sameGenres(Movie movie, Set<String> genres) {
        for (String genre : movie.getGenres()) {
            if (genres.contains(genre)) {
                return true;
            }
        }
        return false;
    }
}