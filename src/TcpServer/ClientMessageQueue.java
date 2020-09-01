package TcpServer;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.net.www.protocol.http.InMemoryCookieStore;

import com.kuka.nav.data.LocationData;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.nav.robot.MobileRobotManager;


public class ClientMessageQueue implements Runnable {

	private Queue<String> commandQueue = new LinkedBlockingQueue<String>();
	private Socket clientSocket = null;
	private BufferedReader input;
	private AtomicBoolean bClientConnected;
	private MobileRobot kmr;
	public ClientMessageQueue(Socket clientSocket, AtomicBoolean bClientConnected,MobileRobot kmr) {
		this.clientSocket = clientSocket;
		this.bClientConnected = bClientConnected;
		this.kmr = kmr;
		Thread.currentThread().setName("CustomerMessageQueueThread");
		try {
			input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
		} catch (IOException e) {

		}

	}

	@Override
	public void run() {
		try {

			String inpMessage;
			BufferedInputStream bufferedInput = new BufferedInputStream(this.clientSocket.getInputStream());
			byte[] readedBytesCount = new byte[4096];
			while (bClientConnected.get()) {
				int bytesRead = 0;
				bytesRead = bufferedInput.read(readedBytesCount);

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
