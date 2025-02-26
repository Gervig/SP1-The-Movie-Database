package app;

import app.config.HibernateConfig;
import app.daos.impl.ActorDAO;
import app.daos.impl.DirectorDAO;
import app.daos.impl.GenreDAO;
import app.daos.impl.MovieDAO;
import app.entities.Director;
import app.services.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        MovieDAO movieDAO = MovieDAO.getInstance(emf);
        ActorDAO actorDAO = ActorDAO.getInstance(emf);
        GenreDAO genreDAO = GenreDAO.getInstance(emf);
        DirectorDAO director = DirectorDAO.getInstance(emf);

        Service service = new Service(movieDAO);

        List<String> movieApiIds = service.getMovieApiIds();

        

        // Close the database connection:
        em.close();
        emf.close();
    }

}
