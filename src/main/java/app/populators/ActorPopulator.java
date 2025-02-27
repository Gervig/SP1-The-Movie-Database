package app.populators;

import app.entities.Actor;
import app.entities.Movie;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ActorPopulator
{
    public static Actor[] populate()
    {
        Movie[] movies = MoviePopulator.populate();

        Set<Movie> a1Movies = new HashSet<>(Arrays.asList(movies[0], movies[1]));
        Set<Movie> a2Movies = new HashSet<>(Arrays.asList(movies[1], movies[2]));
        Set<Movie> a3Movies = new HashSet<>(Arrays.asList(movies[2], movies[3]));

        Actor a1 = Actor.builder()
                .name("Actor 1")
                .movies(a1Movies)
                .build();
        Actor a2 = Actor.builder()
                .name("Actor 2")
                .movies(a2Movies)
                .build();
        Actor a3 = Actor.builder()
                .name("Actor 3")
                .movies(a3Movies)
                .build();

        Actor[] actors = new Actor[]{a1, a2, a3};
        return actors;
    }
}
