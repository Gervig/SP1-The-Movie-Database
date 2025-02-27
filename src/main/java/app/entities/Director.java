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
public class Director
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    private String name;

    @Setter
    @OneToMany(mappedBy = "director", cascade = CascadeType.ALL)
    private Set<Movie> movies;
}
