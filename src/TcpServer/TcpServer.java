package TcpServer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.applicationModel.IApplicationData;

public class  TcpServer implements Runnable {
	private volatile AtomicBoolean bRunning = new AtomicBoolean(false);
	private Integer serverPort;
	private ServerSocket serverSocket;
	private ClientMessageQueue clientQueue;
	public ExecutorService esClientQueue = Executors.newCachedThreadPool();
	private MobileRobot kmr;
	private AtomicBoolean bClientConnected = new AtomicBoolean(false);
	private IApplicationData appData;
	private Socket clientSocket = null;
	private BufferedWriter output;
	private BufferedInputStream input;

	public TcpServer(IApplicationData iApplicationData, Integer serverPort, MobileRobot kmr) {
		this.serverPort = serverPort;
		this.kmr = kmr;
		this.appData = iApplicationData;
	}

	@Override
	public void run() {
		appData.getProcessData("Error").setValue("OpenServerPort");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		connect();

		while (bRunning.get()) {

			try {
				clientSocket = this.serverSocket.accept();
				bClientConnected.set(true);
				output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
				input = new BufferedInputStream(this.clientSocket.getInputStream());
				clientQueue = new ClientMessageQueue(clientSocket, bClientConnected, kmr,input);
				esClientQueue.execute(clientQueue);
			} catch (IOException e1) {

			}

		}

	}

	public boolean isRunning() {
		return bRunning.get();
	}

	public void connect() {
		openServerSocket();
	}

	private void openServerSocket() {
		try {

			this.serverSocket = new ServerSocket(serverPort);
			bRunning.set(true);
			appData.getProcessData("Error").setValue("PortOpen");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}

		} catch (IOException e1) {
			appData.getProcessData("Error").setValue("Waitring for connection Err");
		}
	}
	
	public  void sendMessage(String message){
		if (clientSocket != null && bRunning.get()) {
			try {

				output.write(message + "\r");
				output.flush();
				System.out.println("Sent :" + message);
			} catch (IOException e1) {
				System.out.println("ERROR: " + e1.getMessage() + "\n");
			}
		} else {
			System.out.println("ERROR: " + "Camera Server not open Or Client not Connected" + "\n");
		}
	}

	public synchronized void stop() {
		bRunning.set(false);
		try {
			serverSocket.close();
			if (clientSocket != null) {
				clientSocket.close();
			}
			if(input != null){
				input.close();
			}
			if(output != null){
				output.close();
			}
			if(esClientQueue != null){
				esClientQueue.shutdownNow();
				esClientQueue = null;
			}
			bClientConnected.set(false);
		} catch (IOException e1) {
			appData.getProcessData("Error").setValue("Try to close errr");
		}
	}

}
