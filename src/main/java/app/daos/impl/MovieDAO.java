package app.daos.impl;

import app.daos.IDAO;
import app.entities.Movie;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MovieDAO implements IDAO<Movie, Integer>
{
    private static EntityManagerFactory emf;
    private static MovieDAO instance;

    public MovieDAO()
    {
    }

    public static MovieDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new MovieDAO();
        }
        return instance;
    }


    @Override
    public Movie create(Movie movie)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating movie", e);
        }
    }

    @Override
    public Movie read(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Movie.class, id);
        }
    }

    @Override
    public List<Movie> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of movies", e);
        }
    }

    @Override
    public Movie update(Movie movie)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Movie updatedMovie = em.merge(movie);
            em.getTransaction().commit();
            return updatedMovie;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating movie", e);
        }
    }

    @Override
    public void delete(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Movie movie = em.find(Movie.class, id);
            if (movie == null)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting movie, movie was not found");
            }
            em.remove(movie);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error removing movie", e);
        }
    }
}
