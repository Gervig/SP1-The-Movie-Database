package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
@JsonIgnoreProperties
public class GenreDTO
{
    @JsonProperty("id")
    private Integer genreApiId;
    @JsonProperty("name")
    private String genre;
}