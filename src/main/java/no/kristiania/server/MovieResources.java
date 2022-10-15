package no.kristiania.server;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class MovieResources {
    @Inject
    private MovieRepository repository;
    @Path("movies")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovies(){


        return Response.ok(repository.listAll()).build();
    }
    @Path("movie/{search}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieWhitId(@PathParam("search")String search){

        if (repository.getMovieById(search) != null)
            return Response.ok(repository.getMovieById(search)).build();
        if (repository.getMovieByTitle(search) != null)
            return Response.ok(repository.getMovieByTitle(search)).build();
        else
            return Response.ok(repository.listAll()).build();
    }
    @Path("movie/title/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieWithTitle(@PathParam("title")String title){


        return Response.ok(repository.getMovieById(title)).build();
    }
    @Path("movie")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieUsingParams(@QueryParam("title")String title,
                                        @QueryParam("id") String id){

        if (id != null){
            return Response.ok(repository.getMovieById(id)).build();
        }
        if (title != null){
            return Response.ok(repository.getMovieByTitle(title)).build();
        }
        return Response.ok(repository.listAll()).build();
    }

    @Path("movies")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(Movie movie) {
        repository.save(movie);
    }
}
