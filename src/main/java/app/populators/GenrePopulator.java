package app.populators;

import app.entities.Genre;
import app.entities.Movie;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GenrePopulator
{
    public static Genre[] populate()
    {
        Movie[] movies = MoviePopulator.populate();

        Set<Movie> g1Movies = new HashSet<>(Arrays.asList(movies[0], movies[1]));
        Set<Movie> g2Movies = new HashSet<>(Arrays.asList(movies[1], movies[2]));
        Set<Movie> g3Movies = new HashSet<>(Arrays.asList(movies[2], movies[3]));

        Genre g1 = Genre.builder()
                .name("Genre 1")
                .movies(g1Movies)
                .build();

        Genre g2 = Genre.builder()
                .name("Genre 2")
                .movies(g2Movies)
                .build();

        Genre g3 = Genre.builder()
                .name("Genre 3")
                .movies(g3Movies)
                .build();

        Genre[] genres = new Genre[]{g1, g2, g3};
        return genres;
    }
}
