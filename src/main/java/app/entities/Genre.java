package app.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Genre
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "genre_api_id")
    private Integer genreApiId;
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies;
}
