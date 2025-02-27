package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@DynamicUpdate
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
    @Column(name = "genre_api_id")
    private Integer genreApiId;
    @Setter
    private String name;

    @Setter
    @ManyToMany(mappedBy = "genres")
    private Set<Movie> movies;
}
