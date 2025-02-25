package app.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Actor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;
}
