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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

            // Reset ID sequences (for PostgreSQL & databases that support sequences)
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

    assertEquals(4, m1.getId());

}

    @Test
    void read()
    {
    Movie m1 = movieDAO.read(movies[0].getId());

    assertEquals(1, m1.getId());
    }

    @Test
    void readAll()
    {
        List<Movie> moviesList = List.of(movies);
        List<Movie> testMovies = movieDAO.readAll();  // Assuming readAll() returns the list of movies

        // Ensure both lists have the same size before comparing IDs
        assertEquals(moviesList.size(), testMovies.size(), "Lists have different sizes");

        // Iterate through both lists and check that the ids are equal
        for (int i = 0; i < moviesList.size(); i++) {
            Movie movie1 = moviesList.get(i);
            Movie movie2 = testMovies.get(i);
            assertEquals(movie1.getId(), movie2.getId(), "IDs do not match at index " + i);
        }
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