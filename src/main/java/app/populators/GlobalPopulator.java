package app.populators;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;

import java.util.HashSet;
import java.util.Set;

public class GlobalPopulator
{
    public static Movie[] populate(){
        // Create Directors
        Director d1 = Director.builder().name("Director 1").build();
        Director d2 = Director.builder().name("Director 2").build();
        Director d3 = Director.builder().name("Director 3").build();

        // Create Genres
        Genre g1 = Genre.builder().name("Action").build();
        Genre g2 = Genre.builder().name("Comedy").build();
        Genre g3 = Genre.builder().name("Drama").build();

        // Create Movies and Assign Directors & Genres
        Movie m1 = Movie.builder()
                .title("Movie1")
                .movieApiId(123)
                .director(d1) // Assign director
                .genres(new HashSet<>(Set.of(g1, g2))) // Assign genres
                .build();

        Movie m2 = Movie.builder()
                .title("Movie2")
                .movieApiId(456)
                .director(d1) // Same director
                .genres(new HashSet<>(Set.of(g2, g3))) // Assign different genres
                .build();

        Movie m3 = Movie.builder()
                .title("Movie3")
                .movieApiId(789)
                .director(d2) // Different director
                .genres(new HashSet<>(Set.of(g1, g3))) // Assign genres
                .build();

        Movie[] movies = new Movie[]{m1, m2, m3};

        // Create Actors
        Actor a1 = Actor.builder().name("Actor 1").build();
        Actor a2 = Actor.builder().name("Actor 2").build();
        Actor a3 = Actor.builder().name("Actor 3").build();

        // Set Movies on Actors
        a1.setMovies(new HashSet<>(Set.of(m1,m2)));
        a2.setMovies(new HashSet<>(Set.of(m2,m3)));
        a3.setMovies(new HashSet<>(Set.of(m3)));

        // Now Set Actors on Movies
        m1.setActors(new HashSet<>(Set.of(a1)));
        m2.setActors(new HashSet<>(Set.of(a1,a2)));
        m3.setActors(new HashSet<>(Set.of(a2,a3)));

        // Add Movies to Director
        d1.setMovies(new HashSet<>(Set.of(m1,m2)));
        d2.setMovies(new HashSet<>(Set.of(m3)));

        // Add Movies to Genres
        g1.setMovies(new HashSet<>(Set.of(m1,m3)));
        g2.setMovies(new HashSet<>(Set.of(m1,m2)));
        g3.setMovies(new HashSet<>(Set.of(m2,m3)));

        // Store in arrays
        Actor[] actors = new Actor[]{a1, a2, a3};
        Director[] directors = new Director[]{d1, d2};
        Genre[] genres = new Genre[]{g1, g2, g3};

        return movies;
    }


}
