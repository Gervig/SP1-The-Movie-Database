package app.services;

import app.daos.impl.MovieDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
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

        String url = "https://api.themoviedb.org/3/discover/" +
                "movie?include_adult=false&include_video=false&language=da" +
                "&release_date.gte=2020-02-26&release_date.lte=2025-02-26with_original_language=da&" + API_KEY;

        try
        {

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
