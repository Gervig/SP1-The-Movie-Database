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
public class Actor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "actor_api_id")
    private Integer actorApiId;

    @Setter
    private String name;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;
}
