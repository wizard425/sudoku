package backend;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.sun.net.httpserver.HttpServer;

public class Server {
	static int port = 40000;
	static String a = "http://localhost/";

	public static void main(String[] args) {
		URI uri = UriBuilder.fromUri(a).port(port).build();
		ResourceConfig config = ResourceConfig.forApplication(new SudokuApplication());
		HttpServer server = JdkHttpServerFactory.createHttpServer(uri, config);
		System.out.println("Server is running");
	}
}
