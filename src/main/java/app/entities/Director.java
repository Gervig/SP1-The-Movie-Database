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
