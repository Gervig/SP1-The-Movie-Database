package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@Entity
public class Movie
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private BigDecimal rating;
    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;
    @Column(name = "movie_api_id")
    private Integer movieApiId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    @ToString.Exclude
    private Director director;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @ToString.Exclude
    private Set<Genre> genres;

    @Setter
    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    @ToString.Exclude
    private Set<Actor> actors;
}
