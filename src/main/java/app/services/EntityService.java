package app.services;

import app.daos.impl.ActorDAO;
import app.daos.impl.DirectorDAO;
import app.daos.impl.GenreDAO;
import app.daos.impl.MovieDAO;
import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.GenreDTO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityService
{

    public List<Actor> persistActors(MovieDTO movieDTO){

        List<Actor> actors = new ArrayList<>();
        ActorDAO actorDAO = new ActorDAO();

        for(ActorDTO actorDTO : movieDTO.getActorDTOS())
        {
            Actor exsistingActor = actorDAO.read(actorDTO.getActorApiId());
            Actor actor;
            if (exsistingActor == null)
            {
                actor = new Actor();
                actor.setName(actorDTO.getName());
                actorDAO.create(actor);
                actors.add(actor);
            } else {
                actor = exsistingActor;
                actors.add(actor);
            }
        }
        return actors;
    }

    public Director persistDirector(MovieDTO movieDTO){

        DirectorDAO directorDAO = new DirectorDAO();
        DirectorDTO directorDTO = movieDTO.getDirectorDTO();

            Director exsistingDirector = directorDAO.read(directorDTO.getDirectorApiId());
            Director director;
            if (exsistingDirector == null)
            {
                director = new Director();
                director.setName(directorDTO.getName());
                directorDAO.create(director);
            } else {
                director = exsistingDirector;
            }
        return director;
    }

    public List<Genre> persistGenres(MovieDTO movieDTO){

        List<Genre> genres = new ArrayList<>();
        GenreDAO genreDAO = new GenreDAO();

        for(GenreDTO genreDTO : movieDTO.getGenreDTOs())
        {
            Genre exsistingGenre = genreDAO.read(genreDTO.getGenreApiId());
            Genre genre;
            if (exsistingGenre == null)
            {
                genre = new Genre();
                genre.setName(genreDTO.getGenre());
                genreDAO.create(genre);
                genres.add(genre);
            } else {
                genre = exsistingGenre;
                genres.add(genre);
            }
        }
        return genres;
    }

    public Movie persistMovie(MovieDTO movieDTO){

        MovieDAO movieDAO = new MovieDAO();

            Movie exsistingMovie = movieDAO.read(movieDTO.getMovieApiID());
            Movie movie;
            if (exsistingMovie == null)
            {
                movie = Movie.builder()
                        .title(movieDTO.getTitle())
                        .description(movieDTO.getDescription())
                        .rating(movieDTO.getRating())
                        .releaseDate(Date.valueOf(movieDTO.getReleaseDate()))
                        .movieApiId(movieDTO.getMovieApiID())
                        .genres(persistGenres(movieDTO).stream().collect(Collectors.toSet()))
                        .actors(persistActors(movieDTO).stream().collect(Collectors.toSet()))
                        .director(persistDirector(movieDTO))
                        .build();
                movieDAO.create(movie);
                return movie;
            } else {
                movie = exsistingMovie;
                return movie;
            }
    }


}
