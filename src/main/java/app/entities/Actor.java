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
public class Actor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    @Setter
    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;
}
