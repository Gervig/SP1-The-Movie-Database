package app.daos.impl;

import app.daos.IDAO;
import app.entities.Director;
import app.entities.Genre;
import app.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

import java.util.List;

public class GenreDAO implements IDAO<Genre, Integer>
{
    private static EntityManagerFactory emf;
    private static GenreDAO instance;

    public GenreDAO()
    {
    }

    public static GenreDAO getInstance(EntityManagerFactory _emf)
    {
        if (emf == null)
        {
            emf = _emf;
            instance = new GenreDAO();
        }
        return instance;
    }

    @Override
    public Genre create(Genre genre)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(genre);
            em.getTransaction().commit();
            return genre;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating genre", e);
        }
    }

    @Override
    public Genre read(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Genre.class, id);
        }
    }

    @Override
    public List<Genre> readAll()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error finding list of genres", e);
        }
    }

    public Genre readByApiId(Integer apiID) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                return em.createQuery("SELECT g FROM Genre g WHERE g.genreApiId = :genreApiId", Genre.class)
                        .setParameter("genreApiId", apiID)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null; // Returnér null hvis ingen genre findes
            }
        }
    }


    @Override
    public Genre update(Genre genre)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Genre updatedGenre = em.merge(genre);
            em.getTransaction().commit();
            return updatedGenre;
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
            Genre genre = em.find(Genre.class, id);
            if (genre == null)
            {
                em.getTransaction().rollback();
                throw new ApiException(401, "Error deleting genre, genre was not found");
            }
            em.remove(genre);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error removing genre", e);
        }
    }
}
