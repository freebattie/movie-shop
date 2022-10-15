package no.kristiania.server;

import java.util.List;

public interface MovieRepository {

    List<Movie> listAll();

    void save(Movie movie);

    Movie getMovieById(String id);

    Movie getMovieByTitle(String title);
}
