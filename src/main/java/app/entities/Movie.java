package app.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate releaseDate;
    @Column(name = "movie_api_id")
    private Integer movieApiId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "director_id", nullable = true)
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

    public void printMovieDetails(Movie movie){
        System.out.println("Film Information:");
        System.out.println("ID: " + movie.getId());
        System.out.println("Titel: " + movie.getTitle());
        System.out.println("Udgivelsesdato: " + movie.getReleaseDate());

        System.out.println("\nGenrer:");
        for (Genre genre : movie.getGenres()) {
            System.out.println("  - " + genre.getName());
        }

        System.out.println("\nInstruktører:");
        if(getDirector()!=null)
        {
            System.out.println("  - " + director.getName());
        } else {
            System.out.println("Denne film har ikke nogen instruktør registret");
        }

        System.out.println("\nSkuespillere:");
        for (Actor actor : movie.getActors()) {
            System.out.println("  - " + actor.getName());
        }
        System.out.println(System.lineSeparator());

    }


}
