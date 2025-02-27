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

import java.util.Arrays;

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