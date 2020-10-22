package application;


import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;

import HttpServer.HttpCommand_1;
import HttpServer.HttpServerIiwa;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.swing.internal.plaf.basic.resources.basic_zh_HK;

/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a 
 * {@link RoboticsAPITask#run()} method, which will be called successively in 
 * the application lifecycle. The application will terminate automatically after 
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the 
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an 
 * exception is thrown during initialization or run. 
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the 
 * {@link RoboticsAPITask#dispose()} method.</b> 
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */
public class HttpTest extends RoboticsAPIApplication implements HttpHandler {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kmr;
private HttpServerIiwa httpIiwa ;
private HttpCommand_1 httpHandler1;
	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		httpIiwa = new HttpServerIiwa<HttpHandler>(30005, this);
		
		
		httpIiwa.HttpServerStart();

		
		
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
		
		
		
	}
	@Override
	public void dispose(){
		httpIiwa.serverStop();
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		System.out.println("HttpExchange t = "+t.getHttpContext().getPath());
		System.out.println("Client connected -> Start HandShake");
        String response = "Iiwa will start command1";
       
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
		
	}
}