package app.entities;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Director
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private Set<Movie> movies;
}
