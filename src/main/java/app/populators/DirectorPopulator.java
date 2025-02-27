package app.populators;

import app.entities.Director;
import app.entities.Movie;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DirectorPopulator
{
    public static Director[] populate()
    {
        Movie[] movies = MoviePopulator.populate();

        Set<Movie> d1Movies = new HashSet<>(Arrays.asList(movies[0], movies[1]));
        Set<Movie> d2Movies = new HashSet<>(Arrays.asList(movies[1], movies[2]));
        Set<Movie> d3Movies = new HashSet<>(Arrays.asList(movies[2], movies[3]));

        Director d1 = Director.builder()
                .name("Director 1")
                .movies(d1Movies)
                .build();

        Director d2 = Director.builder()
                .name("Director 2")
                .movies(d2Movies)
                .build();

        Director d3 = Director.builder()
                .name("Director 3")
                .movies(d3Movies)
                .build();

        Director[] directors = new Director[]{d1, d2, d3};
        return directors;
    }
}
