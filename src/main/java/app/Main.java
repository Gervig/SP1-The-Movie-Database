package app;

import app.callable.DetailsServiceCallable;
import app.callable.DiscoverServiceCallable;
import app.config.HibernateConfig;
import app.daos.impl.ActorDAO;
import app.daos.impl.DirectorDAO;
import app.daos.impl.GenreDAO;
import app.daos.impl.MovieDAO;
import app.dtos.MovieDTO;
import app.entities.Movie;
import app.services.EntityService;
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
        DirectorDAO directorDAO = DirectorDAO.getInstance(emf);

        List<String> movieApiIds = Service.getMovieApiIds();

//        movieApiIds.stream().forEach(System.out::println);

        List<MovieDTO> movieDTOS = DetailsServiceCallable.getMovieDTOs(movieApiIds);

        movieDTOS.stream().forEach(EntityService::persistMovie);

        Movie movie = movieDAO.readWithDetails(4);
        movie.printMovieDetails(movie);

        // Close the database connection:
        em.close();
        emf.close();
    }

}
