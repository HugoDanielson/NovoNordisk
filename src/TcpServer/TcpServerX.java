package TcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kuka.nav.robot.MobileRobot;

public class TcpServerX implements Runnable {
	private volatile AtomicBoolean bRunning = new AtomicBoolean(false);
	private Integer serverPort;
	private ServerSocket serverSocket;
	private ClientMessageQueue clientQueue;
	public ExecutorService exClientQueue= Executors.newCachedThreadPool();
	private MobileRobot kmr;
	private AtomicBoolean bClientConnected = new AtomicBoolean(false);
	public TcpServerX(Integer serverPort,MobileRobot kmr){
		this.serverPort = serverPort;
		this.kmr = kmr;
	}

	@Override
	public void run() {
		while (!bRunning.get()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
				bClientConnected.set(true);
				clientQueue = new ClientMessageQueue(clientSocket, bClientConnected,kmr);
				exClientQueue.execute(clientQueue);
			} catch (IOException e1) {
				
			}
		
	}
	
	}
	public boolean isRunning(){
		return bRunning.get();
	}

	public void connect() {
		openServerSocket();
	}
	private void openServerSocket()  {
		try {
		
			this.serverSocket = new ServerSocket(serverPort);
			bRunning.set(true);
			Thread.sleep(50);
			

		} catch (IOException e1) {
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				serverSocket = null;
			}
		} catch (InterruptedException e1) {

		}
	}

	public synchronized void stop() {
	bRunning.set(false);
		try {
			serverSocket.close();
		} catch (IOException e1) {

		}
	}
	

}
