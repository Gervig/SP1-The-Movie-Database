package app.populators;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MoviePopulator
{
    public static Movie[] populate()
    {
        Actor[] actors = ActorPopulator.populate();

        Set<Actor> m1Actors = new HashSet<>(Arrays.asList(actors[0], actors[1]));
        Set<Genre> m1Genres = new HashSet<>();
        Director m1Director = Director.builder()
                .name("Movie 1 Director")
                .build();

        Set<Actor> m2Actors = new HashSet<>(Arrays.asList(actors[1], actors[2]));
        Set<Genre> m2Genres = new HashSet<>();
        Director m2Director = Director.builder()
                .name("Movie 2 Director")
                .build();

        Set<Actor> m3Actors = new HashSet<>(Arrays.asList(actors[2], actors[3]));
        Set<Genre> m3Genres = new HashSet<>();
        Director m3Director = Director.builder()
                .name("Movie 3 Director")
                .build();

        Movie m1 = Movie.builder()
                .title("Movie1")
                .movieApiId(123)
                .actors(m1Actors)
                .genres(m1Genres)
                .director(m1Director)
                .build();

        Movie m2 = Movie.builder()
                .title("Movie2")
                .movieApiId(456)
                .actors(m2Actors)
                .genres(m2Genres)
                .director(m2Director)
                .build();

        Movie m3 = Movie.builder()
                .title("Movie3")
                .movieApiId(789)
                .actors(m3Actors)
                .genres(m3Genres)
                .director(m3Director)
                .build();

        Movie[] movies = new Movie[]{m1, m2, m3};
        return movies;
    }
}
