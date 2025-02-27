package app.daos.impl;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieDAOTest
{
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private static final MovieDAO movieDAO = MovieDAO.getInstance(emf);


    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE movie_id_seq RESTART WITH 1");

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