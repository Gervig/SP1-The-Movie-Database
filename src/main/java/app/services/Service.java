package app.services;

import app.daos.impl.MovieDAO;
import app.dtos.ActorDTO;
import app.dtos.DirectorDTO;
import app.dtos.GenreDTO;
import app.dtos.MovieDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service
{
    //API_reader
    private final MovieDAO movieDAO;
    private final static String API_KEY = System.getenv("api_key");

    //MovieService
    private static ObjectMapper objectMapper = new ObjectMapper();

    public Service(MovieDAO movieDAO)
    {
        this.movieDAO = movieDAO;
    }

    public List<String> getMovieApiIds()
    {
        HttpResponse<String> response;
        ObjectMapper objectMapper = new ObjectMapper();

        // Henter API IDs for danske film
        String uri = "https://api.themoviedb.org/3/discover/" +
                "movie?include_adult=false&include_video=false&language=da" +
                "&release_date.gte=2020-02-26&release_date.lte=2025-02-26with_original_language=da&" + API_KEY;

        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                JsonNode rootNode = objectMapper.readTree(response.body());
                List<String> IDs = new ArrayList<>();

                // Iterate through the "results" array and extract "id"
                for (JsonNode movieNode : rootNode.path("results"))
                {
                    IDs.add(movieNode.path("id").asText());
                }

                return IDs;
            } else
            {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public MovieDTO getDataFromApiId(String movieApiId)
    {
        String url = "https://api.themoviedb.org/3/movie/%%?append_to_response=credits%2C%20overview&language=da&" + API_KEY;
        String movieURL = url.replace("%%", movieApiId);
        try
        {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(movieURL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());

                // Extract actors from "credits" -> "cast" where "known_for_department" == "Acting"
                List<ActorDTO> actors = new ArrayList<>();
                JsonNode castNode = rootNode.path("credits").path("cast");
                if (castNode.isArray()) {
                    for (JsonNode actorNode : castNode) {
                        if ("Acting".equals(actorNode.path("known_for_department").asText())) {
                            actors.add(new ActorDTO(
                                    actorNode.path("name").asText()
                            ));
                        }
                    }
                }

                // Extract directors from "credits" -> "crew" where "job" == "Director"
                DirectorDTO director = null;
                JsonNode crewNode = rootNode.path("credits").path("crew");
                if (crewNode.isArray()) {
                    for (JsonNode crewMember : crewNode) {
                        if ("Director".equals(crewMember.path("job").asText())) { //TODO test at den virker selvom nøglen "job" måske ikke findes
                            director = new DirectorDTO(
                                    crewMember.path("name").asText()
                            );
                            break; // Assume one director per movie
                        }
                    }
                }

                // Extract genres from "genres"
                List<GenreDTO> genres = new ArrayList<>();
                JsonNode genresArray = rootNode.path("genres"); // Get the genres array
                for (JsonNode genreNode : genresArray) {
                    int id = genreNode.path("id").asInt();
                    String name = genreNode.path("name").asText();
                    genres.add(new GenreDTO(id, name));
                }

                // Build the MovieDTO
                return MovieDTO.builder()
                        .title(rootNode.path("original_title").asText())
                        .description(rootNode.path("overview").asText())
                        .rating(BigDecimal.valueOf(rootNode.path("vote_average").asDouble()))
                        .releaseDate(LocalDate.parse(rootNode.path("release_date").asText()))
                        .movieApiID(rootNode.path("id").asInt())
                        .genreDTOs(genres)
                        .actorDTOS(actors)
                        .directorDTO(director)
                        .build();
            } else
            {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
