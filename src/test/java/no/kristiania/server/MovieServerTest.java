package no.kristiania.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;


class MovieServerTest {

    private MovieServer server;
    @BeforeEach
    public void setupServer() throws Exception {
        this.server = new MovieServer(0);
        server.start();
    }
    @Test
    public void getIndexHtmlTest() throws Exception {


        var connection = openConnection("/");

        assertThat(connection.getResponseCode())
                .as(connection.getResponseMessage()).isEqualTo(200);
    }



    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }
}