package HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HttpCommand_1 implements HttpHandler {
	private boolean bStart = false;

	 @Override
     public void handle(HttpExchange t) throws IOException {
     	System.out.println("Client connected -> Start HandShake");
         String response = "Iiwa will start command1";
         bStart = true;
         t.sendResponseHeaders(200, response.length());
         OutputStream os = t.getResponseBody();
         os.write(response.getBytes());
         os.close();
     }
	 
	 public boolean getStart(){
		return bStart;
		 
	 }
}
	 
	       
