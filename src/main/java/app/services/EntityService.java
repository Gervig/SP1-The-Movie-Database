package app.services;

import app.daos.impl.ActorDAO;
import app.daos.impl.DirectorDAO;
import app.daos.impl.GenreDAO;
import app.daos.impl.MovieDAO;
import app.dtos.DirectorDTO;
import app.dtos.MovieDTO;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

public class EntityService
{
    private static EntityManagerFactory emf;

    @Transactional
    public static Movie persistMovie(MovieDTO movieDTO)
    {
        MovieDAO movieDAO = MovieDAO.getInstance(emf);
        GenreDAO genreDAO = GenreDAO.getInstance(emf);
        ActorDAO actorDAO = ActorDAO.getInstance(emf);
        DirectorDAO directorDAO = DirectorDAO.getInstance(emf);


        // Hent eller opret director
        Director director = null;
        if (movieDTO.getDirectorDTO() != null)
        {
            Director existingDirector = directorDAO.readByApiId(movieDTO.getDirectorDTO().getDirectorApiId());
            if (existingDirector != null)
            {
                existingDirector = directorDAO.update(existingDirector);
                director = existingDirector;
            } else if (movieDTO.getDirectorDTO() != null && existingDirector == null)
            {
                director = Director.builder()
                        .directorApiId(movieDTO.getDirectorDTO().getDirectorApiId())
                        .name(movieDTO.getDirectorDTO().getName())
                        .build();
                director = directorDAO.create(director);
            }
        }

        // Hent eller opret genrer
        Set<Genre> genres = movieDTO.getGenreDTOs().stream()
                .map(dto ->
                {
                    Genre exsistningGenre = genreDAO.readByApiId(dto.getGenreApiId());
                    if (exsistningGenre != null)
                    {
                        exsistningGenre = genreDAO.update(exsistningGenre);
                        return exsistningGenre;
                    }

                    Genre genre = Genre.builder()
                            .genreApiId(dto.getGenreApiId())
                            .name(dto.getGenre())
                            .build();
                    genre = genreDAO.create(genre);
                    return genre;
                })
                .collect(Collectors.toSet());

        // Hent eller opret skuespillere
        Set<Actor> actors = movieDTO.getActorDTOS().stream()
                .map(dto ->
                {
                    Actor existingActor = actorDAO.readByApiId(dto.getActorApiId());

                    if (existingActor != null)
                    {
                        existingActor = actorDAO.update(existingActor);
                        return existingActor;
                    } else {
                    Actor actor = Actor.builder()
                            .actorApiId(dto.getActorApiId())
                            .name(dto.getName())
                            .build();

                    actor = actorDAO.create(actor);
                    return actor;
                    }
                })
                .collect(Collectors.toSet());

        // Opret eller opdater film
        Movie movie = Movie.builder()
                .title(movieDTO.getTitle())
                .rating(movieDTO.getRating())
                .director(director)
                .actors(actors)
                .genres(genres)
                .movieApiId(movieDTO.getMovieApiID())
                .description(movieDTO.getDescription())
                .releaseDate(movieDTO.getReleaseDate())

                .build();
        return movieDAO.create(movie);
    }
}
    /*private static EntityManagerFactory emf;

    public static List<Actor> persistActors(MovieDTO movieDTO){

        List<Actor> actors = new ArrayList<>();
        ActorDAO actorDAO = ActorDAO.getInstance(emf);

        for(ActorDTO actorDTO : movieDTO.getActorDTOS())
        {
            Actor exsistingActor = actorDAO.read(actorDTO.getActorApiId());
            Actor actor;
            if (exsistingActor == null)
            {
                actor = new Actor();
                actor.setActorApiId(actorDTO.getActorApiId());
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

    public static Director persistDirector(MovieDTO movieDTO){

        DirectorDAO directorDAO = new DirectorDAO();
        DirectorDTO directorDTO = movieDTO.getDirectorDTO();

            Director exsistingDirector = directorDAO.read(directorDTO.getDirectorApiId());
            Director director;
            if (exsistingDirector == null)
            {
                director = new Director();
                director.setDirectorApiId(director.getDirectorApiId());
                director.setName(directorDTO.getName());
                directorDAO.create(director);
            } else {
                director = exsistingDirector;
            }
        return director;
    }

    public static List<Genre> persistGenres(MovieDTO movieDTO){

        List<Genre> genres = new ArrayList<>();
        GenreDAO genreDAO = GenreDAO.getInstance(emf);

        for(GenreDTO genreDTO : movieDTO.getGenreDTOs())
        {
            Genre exsistingGenre = genreDAO.read(genreDTO.getGenreApiId());
            Genre genre;
            if (exsistingGenre == null)
            {
                genre = new Genre();
                genre.setGenreApiId(genreDTO.getGenreApiId());
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

    public static Movie persistMovie(MovieDTO movieDTO){

        MovieDAO movieDAO = MovieDAO.getInstance(emf);

            Movie exsistingMovie = movieDAO.read(movieDTO.getMovieApiID());
            Movie movie;
            if (exsistingMovie == null)
            {
                movie = Movie.builder()
                        .title(movieDTO.getTitle())
                        .movieApiId(movieDTO.getMovieApiID())
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
}*/
