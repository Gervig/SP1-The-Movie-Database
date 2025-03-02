package app.daos.impl;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import app.populators.GlobalPopulator;
import app.populators.PopulatedData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest
{
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final MovieDAO movieDAO = MovieDAO.getInstance(emf);
    private static Director[] directors;
    private static Genre[] genres;
    private static Actor[] actors;
    private static Movie[] movies;

    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            // Clear previous data
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createQuery("DELETE FROM Actor").executeUpdate();
            em.createQuery("DELETE FROM Genre").executeUpdate();
            em.createQuery("DELETE FROM Director").executeUpdate();

            // Reset ID sequences (for PostgresSQL & databases that support sequences)
            em.createNativeQuery("ALTER SEQUENCE movie_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE actor_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE genre_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE director_id_seq RESTART WITH 1").executeUpdate();

            // Populate all entities
            PopulatedData data = GlobalPopulator.populate();

            directors = data.directors;
            genres = data.genres;
            actors = data.actors;
            movies = data.movies;

            // Persist entities in the correct order
            Arrays.stream(data.directors).forEach(em::persist);
            Arrays.stream(data.genres).forEach(em::persist);
            Arrays.stream(data.actors).forEach(em::persist);
            Arrays.stream(data.movies).forEach(em::persist);

            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void getInstance()
    {
        MovieDAO instanceTest = MovieDAO.getInstance(emf);
        assertNotNull(instanceTest);
        assertEquals(instanceTest, movieDAO);
    }

    @Test
    void create()
    {
        Movie m1 = Movie.builder()
                .director(directors[0])
                .actors(new HashSet<>(Set.of(actors[0])))
                .genres(new HashSet<>(Set.of(genres[1])))
                .build();

        m1 = movieDAO.create(m1);

        assertEquals(4, m1.getId(), "Movie IDs aren't the same");

    }

    @Test
    void read()
    {
        int expected = movies[0].getId();
        Movie m1 = movieDAO.read(movies[0].getId());

        assertEquals(expected, m1.getId(), "Movies have different IDs");
    }

    @Test
    void readAll()
    {
        List<Movie> expectedList = List.of(movies);
        List<Movie> actualList = movieDAO.readAll();

        assertEquals(expectedList.size(), actualList.size(), "Lists have different sizes");
    }

    @Test
    void update()
    {
        String newTitle = "New Title";
        Movie m1 = movies[0];
        m1.setTitle(newTitle);

        m1 = movieDAO.update(m1);

        Movie m1Test = movieDAO.read(m1.getId());

        assertEquals(newTitle, m1Test.getTitle(), "Movies have different titles");
    }

    @Test
    void readWithDetails()
    {
        Movie m1 = movies[0];
        Movie m1Test = movieDAO.readWithDetails(m1.getId());

        Actor[] m1Actors = m1.getActors().toArray(new Actor[0]);
        Actor[] m1TestActors = m1Test.getActors().toArray(new Actor[0]);

        Actor a1 = m1Actors[0];
        Actor a2 = m1TestActors[0];

        assertEquals(a1.getName(), a2.getName(), "Actors have different names");

    }

    @Test
    void delete()
    {
        Movie m1 = movies[0];

        movieDAO.delete(m1.getId());

        Movie deletedMovie = movieDAO.read(m1.getId());

        assertNull(deletedMovie, "Movie isn't null");
    }
}