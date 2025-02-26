package app.callable;
import app.dtos.MovieDTO;

import java.util.concurrent.Callable;


import java.util.concurrent.Callable;

public class ServiceCallable implements Callable<String>
{
    String movieId;

    public ServiceCallable(String movieId)
    {
        this.movieId = movieId;
    }

    @Override
    public String call() throws Exception {
        String json = Service.getDataFromApiId(movieId);
        return json;
    }
}
