package app.entities;

import app.DTOs.ActorDTO;
import app.DTOs.GenreDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;

public class Movie
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private float rating;
    private LocalDate releaseDate;
    private Integer movieApiID;
    private List<Genre> genres;
    private List<Actor> actors;
}
