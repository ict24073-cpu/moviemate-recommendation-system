import java.util.*;

// MovieMate - Movie Recommendation System
// Data Structures used: ArrayList, Queue, Stack

class Movie {
    String title;
    String genre;
    int year;
    double rating;

    Movie(String title, String genre, int year, double rating) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public String toString() {
        return title + " (" + year + ") - " + genre + " - Rating: " + rating;
    }
}

public class MovieMate {

    static ArrayList<Movie> movieDatabase = new ArrayList<>();
    static Queue<Movie> recentlyWatched = new LinkedList<>();
    static Stack<Movie> watchHistory = new Stack<>();

    static Scanner sc = new Scanner(System.in);
    static final int QUEUE_LIMIT = 5;

    public static void main(String[] args) {
        loadMovies();

        int choice;
        do {
            System.out.println("\n===== MOVIEMATE =====");
            System.out.println("1. Watch a Movie");
            System.out.println("2. Get Recommendations");
            System.out.println("3. Search Movie by Title");
            System.out.println("4. View Watch History");
            System.out.println("5. View All Movies");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1: watchMovie(); break;
                case 2: getRecommendations(); break;
                case 3: searchMovie(); break;
                case 4: viewHistory(); break;
                case 5: viewAllMovies(); break;
                case 6: System.out.println("Thank you for using MovieMate. Bye!"); break;
                default: System.out.println("Invalid choice, please try again.");
            }

        } while (choice != 6);

        sc.close();
    }

    static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    static void loadMovies() {
        movieDatabase.add(new Movie("The Dark Knight", "Action", 2008, 9.0));
        movieDatabase.add(new Movie("Mad Max: Fury Road", "Action", 2015, 8.1));
        movieDatabase.add(new Movie("John Wick", "Action", 2014, 7.4));
        movieDatabase.add(new Movie("Gladiator", "Action", 2000, 8.5));
        movieDatabase.add(new Movie("Extraction", "Action", 2020, 6.7));

        movieDatabase.add(new Movie("The Hangover", "Comedy", 2009, 7.7));
        movieDatabase.add(new Movie("Superbad", "Comedy", 2007, 7.6));
        movieDatabase.add(new Movie("Dumb and Dumber", "Comedy", 1994, 7.3));
        movieDatabase.add(new Movie("Zombieland", "Comedy", 2009, 7.6));
        movieDatabase.add(new Movie("Jojo Rabbit", "Comedy", 2019, 7.9));

        movieDatabase.add(new Movie("The Shawshank Redemption", "Drama", 1994, 9.3));
        movieDatabase.add(new Movie("Forrest Gump", "Drama", 1994, 8.8));
        movieDatabase.add(new Movie("The Godfather", "Drama", 1972, 9.2));
        movieDatabase.add(new Movie("Fight Club", "Drama", 1999, 8.8));
        movieDatabase.add(new Movie("A Beautiful Mind", "Drama", 2001, 8.2));

        movieDatabase.add(new Movie("The Conjuring", "Horror", 2013, 7.5));
        movieDatabase.add(new Movie("Hereditary", "Horror", 2018, 7.3));
        movieDatabase.add(new Movie("It", "Horror", 2017, 7.3));
        movieDatabase.add(new Movie("Get Out", "Horror", 2017, 7.7));
        movieDatabase.add(new Movie("A Quiet Place", "Horror", 2018, 7.5));

        movieDatabase.add(new Movie("Interstellar", "Sci-Fi", 2014, 8.6));
        movieDatabase.add(new Movie("Inception", "Sci-Fi", 2010, 8.8));
        movieDatabase.add(new Movie("The Matrix", "Sci-Fi", 1999, 8.7));
        movieDatabase.add(new Movie("Arrival", "Sci-Fi", 2016, 7.9));
        movieDatabase.add(new Movie("Edge of Tomorrow", "Sci-Fi", 2014, 7.9));

        movieDatabase.add(new Movie("La La Land", "Romance", 2016, 8.0));
        movieDatabase.add(new Movie("The Notebook", "Romance", 2004, 7.8));
        movieDatabase.add(new Movie("Titanic", "Romance", 1997, 7.9));
        movieDatabase.add(new Movie("Pride and Prejudice", "Romance", 2005, 7.8));
        movieDatabase.add(new Movie("500 Days of Summer", "Romance", 2009, 7.7));
    }

    static void watchMovie() {
        viewAllMovies();
        System.out.print("\nEnter the movie number you watched: ");
        int idx = readInt();

        if (idx < 1 || idx > movieDatabase.size()) {
            System.out.println("Invalid movie number.");
            return;
        }

        Movie watched = movieDatabase.get(idx - 1);

        if (recentlyWatched.size() >= QUEUE_LIMIT) {
            recentlyWatched.poll();
        }
        recentlyWatched.offer(watched);
        watchHistory.push(watched);

        System.out.println("Added \"" + watched.title + "\" to your watch history.");
    }

    static void getRecommendations() {
        if (recentlyWatched.isEmpty()) {
            System.out.println("Watch a few movies first so we can recommend something!");
            return;
        }

        // count genre occurrences in the recent queue
        ArrayList<String> genreNames = new ArrayList<>();
        ArrayList<Integer> genreCounts = new ArrayList<>();

        for (Movie m : recentlyWatched) {
            int pos = genreNames.indexOf(m.genre);
            if (pos == -1) {
                genreNames.add(m.genre);
                genreCounts.add(1);
            } else {
                genreCounts.set(pos, genreCounts.get(pos) + 1);
            }
        }

        String favGenre = "";
        int max = -1;
        for (int i = 0; i < genreNames.size(); i++) {
            if (genreCounts.get(i) > max) {
                max = genreCounts.get(i);
                favGenre = genreNames.get(i);
            }
        }

        System.out.println("\nBased on your recent watches, you seem to like: " + favGenre);

        ArrayList<Movie> candidates = new ArrayList<>();
        for (Movie m : movieDatabase) {
            if (m.genre.equalsIgnoreCase(favGenre) && !watchHistory.contains(m)) {
                candidates.add(m);
            }
        }

        if (candidates.isEmpty()) {
            System.out.println("Looks like you already watched everything in this genre!");
            return;
        }

        selectionSortByRating(candidates);

        System.out.println("Recommended movies for you:");
        int count = Math.min(3, candidates.size());
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + candidates.get(i));
        }
    }

    // selection sort - highest rating first
    static void selectionSortByRating(ArrayList<Movie> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (list.get(j).rating > list.get(maxIndex).rating) {
                    maxIndex = j;
                }
            }
            Movie temp = list.get(maxIndex);
            list.set(maxIndex, list.get(i));
            list.set(i, temp);
        }
    }

    static void searchMovie() {
        System.out.print("Enter movie title to search: ");
        String title = sc.nextLine();

        for (Movie m : movieDatabase) {
            if (m.title.equalsIgnoreCase(title)) {
                System.out.println("Movie found: " + m);
                return;
            }
        }
        System.out.println("Movie not found in the database.");
    }

    static void viewHistory() {
        if (watchHistory.isEmpty()) {
            System.out.println("No movies watched yet.");
            return;
        }

        System.out.println("\nYour Watch History (most recent first):");
        Stack<Movie> temp = new Stack<>();
        temp.addAll(watchHistory);

        while (!temp.isEmpty()) {
            System.out.println("- " + temp.pop());
        }
    }

    static void viewAllMovies() {
        System.out.println("\nAll Movies in Database:");
        for (int i = 0; i < movieDatabase.size(); i++) {
            System.out.println((i + 1) + ". " + movieDatabase.get(i));
        }
    }
}
