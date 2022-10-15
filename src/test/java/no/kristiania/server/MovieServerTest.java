package no.kristiania.server;

import jakarta.json.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
    @Test
    void shouldAddBook() throws IOException {
        var postConnection = openConnection("/api/movies");
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        postConnection.getOutputStream().write(
                Json.createObjectBuilder()
                        .add("title", "Test Book")
                        .build()
                        .toString()
                        .getBytes(StandardCharsets.UTF_8)
        );
        assertThat(postConnection.getResponseCode())
                .as(postConnection.getResponseMessage())
                .isEqualTo(204);

        var connection = openConnection("/api/movies");
        assertThat(connection.getInputStream())
                .asString(StandardCharsets.UTF_8)
                .contains("{\"title\":\"Test Book\"");
    }



    private HttpURLConnection openConnection(String path) throws IOException {
        return (HttpURLConnection) new URL(server.getURL(), path).openConnection();
    }
}