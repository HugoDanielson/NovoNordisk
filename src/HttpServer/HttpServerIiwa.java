package HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;



public class HttpServerIiwa {
private HttpServer server;
	private HttpCommand_1 handler1 =  new HttpCommand_1();
	public void HttpServerStart() {
		
		try {
			server = HttpServer.create(new InetSocketAddress(30002), 0);
			server.createContext("/iiwa_com1", handler1);
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
	
	public HttpCommand_1 getHandlerCom1(){
		return handler1;
		
	}
	
	public void serverStop(){
		server.stop(0);
		System.out.println("Server stoped");
	}
}
