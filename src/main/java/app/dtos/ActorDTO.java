package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
@AllArgsConstructor
@Builder
@JsonIgnoreProperties
public class ActorDTO
{
    @JsonProperty("id")
    private Integer actorApiId;
    private String name;
}
