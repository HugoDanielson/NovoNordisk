package TcpServer;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kuka.nav.robot.MobileRobot;


public class ClientMessageQueue implements Runnable {

	private Queue<String> commandQueue = new LinkedBlockingQueue<String>();
	private Socket clientSocket = null;
	private BufferedInputStream input;
	private AtomicBoolean bClientConnected;
	private MobileRobot kmr;
	public ClientMessageQueue(Socket clientSocket, AtomicBoolean bClientConnected,MobileRobot kmr,BufferedInputStream input) {
		this.clientSocket = clientSocket;
		this.bClientConnected = bClientConnected;
		this.kmr = kmr;
		this.input = input;
		Thread.currentThread().setName("CustomerMessageQueueThread");
		
	}

	@Override
	public void run() {
		try {

			String inpMessage;
			
			byte[] readedBytesCount = new byte[4096];
			while (bClientConnected.get()) {
				int bytesRead = 0;
				bytesRead = input.read(readedBytesCount);

				if (bytesRead > 0) {
					inpMessage = new String(readedBytesCount, 0, bytesRead);
					
				} else {
					bClientConnected.set(false);
					inpMessage = null;
				}

				bytesRead = 0;

				if (inpMessage != null) {
					if(inpMessage.contains("STOP")){
						kmr.cancelAllRequests();
						kmr.cancelAllCommands();
					}else{
						commandQueue.add(inpMessage);
					}

				}

			}

		} catch (IOException e) {

		}

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public Queue<String> getQueue() {
		return this.commandQueue;

	}
	

}
