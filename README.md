# MovieMate - Movie Recommendation System

This is a simple console based Java project I made for the Data Structures and Algorithms (ICT 143-2) practical assignment.

## What this project does

MovieMate keeps a list of 30 movies from different genres. The user can watch movies from the list, and the program will remember what genre they like the most based on their recent watches. Then it will recommend more movies from that genre, starting with the highest rated ones. The user can also search for a movie by name and check their watch history.

## Data structures used

1. ArrayList - used to store the full list of movies (movieDatabase). This lets the program access any movie fast using its index number.

2. Queue - used to store the last 5 movies the user watched (recentlyWatched). Since it works first in first out, old watches get removed automatically and only recent ones are kept. This is used to figure out the favourite genre.

3. Stack - used to store the complete watch history (watchHistory). Since it works last in first out, the most recently watched movie always comes up first when viewing history.

## Algorithms used

- Genre counting - counts how many times each genre appears in the recently watched queue to find the favourite genre.
- Linear search - used to find movies of a certain genre, and to search a movie by its title.
- Selection sort - used to sort the recommended movies by rating, from highest to lowest.

## How to run this project

Make sure Java JDK is installed on your computer. Then open a terminal in the project folder and run:

javac MovieMate.java
java MovieMate

## Menu options

1. Watch a Movie
2. Get Recommendations
3. Search Movie by Title
4. View Watch History
5. View All Movies
6. Exit

## Example usage

First pick option 1 and watch a movie, for example an action movie. Then pick option 2 to get recommendations, it will show 3 action movies sorted by rating. You can also pick option 3 to search for any movie by typing its name, or option 4 to see everything you watched so far.

## Author

This project was made by me for the ICT 143-2 Data Structures and Algorithms module at Uva Wellassa University of Sri Lanka.
