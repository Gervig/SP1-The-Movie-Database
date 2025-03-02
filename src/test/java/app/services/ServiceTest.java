package app.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest
{
    static String api_key = System.getenv("api_key");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient client = HttpClient.newHttpClient();

    // Tests if the list of Movie API IDs is between 2 ranges
    @Test
    void getMovieApiIds()
    {
        List<String> movieApiIds = Service.getMovieApiIds();

        int lowerLimit = 500;
        int upperLimit = 1500;

        assertTrue(movieApiIds.size() >= lowerLimit && movieApiIds.size() <= upperLimit,
                "Size should be between " + lowerLimit + "and" + upperLimit + " , but was " + movieApiIds.size());
    }

    @Test
    void fetchMoviesFromPage()
    {
    }

    @Test
    void buildDiscoverUri()
    {
    }

    @Test
    void getDataFromApiId()
    {
    }
}