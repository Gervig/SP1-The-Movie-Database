package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Entity
public class Genre
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "genre_api_id")
    private Integer genreApiId;
    @Setter
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies;
}
