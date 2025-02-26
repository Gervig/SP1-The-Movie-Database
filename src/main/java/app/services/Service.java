package app.services;

import app.daos.impl.MovieDAO;
import app.dtos.MovieDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Service
{
    //API_reader
    private final MovieDAO movieDAO;
    private final String API_KEY = System.getenv("api_key");

    //MovieService
    private static ObjectMapper objectMapper = new ObjectMapper();

    public Service(MovieDAO movieDAO){
        this.movieDAO = movieDAO;
    }

    //Chrisser - Callable
    //Casper - Alle ID'er fra api'et
    //Rikke - Går alle id'erne og henter informationerne fra dem (for-loop)


    public String getDataFromClientWithID(List<String> movieApiIds)
    {
        for (String movieApiId : movieApiIds)
        {
            String url = "https://api.themoviedb.org/3/movie/%%?append_to_response=credits&language=da&" + API_KEY;
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
                    String body = response.body();
                    return body;
                } else
                {
                    System.out.println("GET request failed. Status code: " + response.statusCode());
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    //MovieService
    public MovieDTO convertFromJsonToDTO(String json)
    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        try
        {
            MovieDTO movieDTO = objectMapper.readValue(
                    objectMapper.readTree(json).get("movie_results").toString(),MovieDTO[].class)[0];

            return movieDTO;
        } catch (JsonProcessingException jPE)
        {
            jPE.printStackTrace();
        }
        return null;
    }
}
