package app;

import app.callable.DetailsServiceCallable;
import app.callable.DiscoverServiceCallable;
import app.config.HibernateConfig;
import app.daos.impl.ActorDAO;
import app.daos.impl.DirectorDAO;
import app.daos.impl.GenreDAO;
import app.daos.impl.MovieDAO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Genre;
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

        //Find en film via film-id
//        Movie movie = movieDAO.readWithDetails(4);
//        movie.printMovieDetails(movie);

//        //Man kan søge på dele af titlen
//        List<Movie> movies = movieDAO.readWithDetailsByTitle("den ");
//        movies.forEach(movie -> movie.printMovieDetails(movie));

        //Printer alle film med titler
//        List<Movie> movies = movieDAO.readAll();
//        movies.forEach(movie -> System.out.println(movie.getTitle()));

//        //Printer alle genre
//        List<Genre> genres = genreDAO.readAll();
//        genres.forEach(genre -> System.out.println(genre.getName()));
//
//        //Printer alle skuespillere
//        List<Actor> actors = actorDAO.readAll();
//        actors.forEach(actor -> System.out.println(actor.getName()));


        // Close the database connection:
        em.close();
        emf.close();
    }

}
