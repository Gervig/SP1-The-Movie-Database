package app.daos.impl;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import app.populators.ActorPopulator;
import app.populators.DirectorPopulator;
import app.populators.GenrePopulator;
import app.populators.MoviePopulator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class MovieDAOTest
{
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final MovieDAO movieDAO = MovieDAO.getInstance(emf);

    private static Movie m1;
    private static Movie m2;
    private static Movie m3;

    private static Actor a1;
    private static Actor a2;
    private static Actor a3;

    private static Director d1;
    private static Director d2;
    private static Director d3;

    private static Genre g1;
    private static Genre g2;
    private static Genre g3;

    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE movie_id_seq RESTART WITH 1");
            Movie[] populatedMovies = MoviePopulator.populate();
            Actor[] populatedActors = ActorPopulator.populate();
            Director[] populatedDirectors = DirectorPopulator.populate();
            Genre[] populatedGenres = GenrePopulator.populate();

            // sets movies from populate
            m1 = populatedMovies[0];
            m2 = populatedMovies[1];
            m3 = populatedMovies[2];
            Movie[] movies = new Movie[]{m1, m2};

            // persists the first 2 movies
            Arrays.stream(movies).forEach(em::persist);

            // sets actors from populate
            a1 = populatedActors[0];
            a2 = populatedActors[1];
            a3 = populatedActors[2];
            Actor[] actors = new Actor[]{a1, a2};

            // persists the first 2 actors
            Arrays.stream(actors).forEach(em::persist);

            // sets directors from populate
            d1 = populatedDirectors[0];
            d2 = populatedDirectors[1];
            d3 = populatedDirectors[2];
            Director[] directors = new Director[]{d1, d2};

            // persists the first 2 directors
            Arrays.stream(directors).forEach(em::persist);

            // sets genres from populate
            g1 = populatedGenres[0];
            g2 = populatedGenres[1];
            g3 = populatedGenres[2];
            Genre[] genres = new Genre[]{g1, g2};

            // persists the first 2 genres
            Arrays.stream(genres).forEach(em::persist);

            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void getInstance()
    {
    }

    @Test
    void create()
    {
    }

    @Test
    void read()
    {
    }

    @Test
    void readAll()
    {
    }

    @Test
    void update()
    {
    }

    @Test
    void delete()
    {
    }
}