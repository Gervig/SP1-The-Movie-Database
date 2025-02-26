package app.services;

import app.daos.impl.MovieDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Service
{
    private final MovieDAO movieDAO;
    private final String API_KEY = System.getenv("api_key");

    public Service(MovieDAO movieDAO)
    {
        this.movieDAO = movieDAO;
    }

    public List<Integer> getMovieApiIds()
    {
        HttpResponse<String> response;
        ObjectMapper objectMapper = new ObjectMapper();

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
                List<Integer> IDs = new ArrayList<>();

                // Iterate through the "results" array and extract "id"
                for (JsonNode movieNode : rootNode.path("results"))
                {
                    IDs.add(movieNode.path("id").asInt());
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
}
