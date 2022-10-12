package no.kristiania.server;

import org.eclipse.jetty.server.CustomRequestLog;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class MovieServer {

    private static final Logger logger = LoggerFactory.getLogger(MovieServer.class);

    private final Server server;

    public MovieServer(int port) throws IOException {
        this.server = new Server(port);
        WebAppContext context = new WebAppContext();
        // what www.mygae.org/ to use for this context
        context.setContextPath("/");
        // and serv /webapp from resources or target/classes
        Resource resource = Resource.newClassPathResource("/webapp");
        File sourceDirectory = getSourceDirectory(resource);
        if (sourceDirectory != null) {
            context.setBaseResource(Resource.newResource(sourceDirectory));
            context.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            context.setBaseResource(resource);
        }

        server.setHandler(new HandlerList( createWebAppContext()));

        // add a logger for all requests
        server.setRequestLog(new CustomRequestLog());
    }

    private Handler createWebAppContext() throws IOException {
        WebAppContext context = new WebAppContext();
        // what www.mygae.org/ to use for this context
        context.setContextPath("/");
        // and serv /webapp from resources or target/classes
        Resource resource = Resource.newClassPathResource("/webapp");
        File sourceDirectory = getSourceDirectory(resource);

        if (sourceDirectory != null) {
            context.setBaseResource(Resource.newResource(sourceDirectory));
            context.setInitParameter(DefaultServlet.CONTEXT_INIT + "useFileMappedBuffer", "false");
        } else {
            context.setBaseResource(resource);
        }
        return context;
    }

    // swap out java classes with resources with webapp
    private File getSourceDirectory(Resource resource) throws IOException {
        if (resource == null)
            return null;
        else{
            File sourceDirectory = new File(resource.getFile().getAbsolutePath()
                    .replace('\\', '/')
                    .replace("target/classes", "src/main/resources"));
            return sourceDirectory.exists() ? sourceDirectory : null;
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String envPort = System.getenv("HTTP_PLATFORM_PORT");
        if (envPort != null)
            port = Integer.parseInt(envPort);

        var server = new MovieServer(port);
        server.start();
        logger.info(server.getURL().toString());
    }

    public void start() throws Exception {
        server.start();
    }

    public URL getURL() throws MalformedURLException {
        return server.getURI().toURL();
    }
}
