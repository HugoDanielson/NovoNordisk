package HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpServerIiwa<HttpHandle> {
	private HttpServer server;

	private int port;
	private HttpHandle handler1;

	public HttpServerIiwa(int port, HttpHandle handler1) {
		this.port = port;
		this.handler1 = handler1;
	}

	public void HttpServerStart() {

		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/iiwa_com1", (HttpHandler) handler1);

			// Thread control is given to executor service.
			server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
			System.out.println("Starting server");
			server.start();
			System.out.println(server.getAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	

	public void serverStop() {
		server.stop(0);
		System.out.println("Server stoped");
	}
}
