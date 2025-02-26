package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@Builder
@JsonIgnoreProperties
public class MovieDTO
{
    @JsonProperty("original_title")
    private String title;
    @JsonProperty("overview")
    private String description;
    @JsonProperty("vote_average")
    private BigDecimal rating;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("id")
    private Integer movieApiID;
    @JsonProperty("genres")
    private List<GenreDTO> genreDTOs;
    @JsonProperty("cast")
    private List<ActorDTO> actorDTOS;
    //Vi skal nok bruge noget JsonNode til det her, Jackson går kun til credits, og JSonNode kan gå helt ind under credits
    private DirectorDTO directorDTO;
}
