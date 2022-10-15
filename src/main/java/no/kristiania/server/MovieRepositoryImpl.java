package no.kristiania.server;

import java.util.ArrayList;
import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {
    private  final List<Movie> movies = new ArrayList<>(
            List.of(sampleMovie())
    );

    private static Movie[] sampleMovie() {
        var movie = new Movie[2];
        movie[0] = new Movie();
        movie[1] = new Movie();
        movie[0].setTitle("Hello World");
        movie[0].setID("1");
        movie[1].setTitle("Not Hello World");
        movie[1].setID("2");
        return movie;
    }

    @Override
    public List<Movie> listAll() {
        return movies;
    }

    @Override
    public void save(Movie movie) {
        movies.add(movie);
    }

    @Override
    public Movie getMovieById(String id) {
        for (var movie : movies){
            if (movie.getId().equals(id))
                return movie;
        }
        return null;
    }

    @Override
    public Movie getMovieByTitle(String title) {
        for (var movie : movies){
            if (movie.getTitle().equals(title))
                return movie;
        }
        return null;
    }


}
