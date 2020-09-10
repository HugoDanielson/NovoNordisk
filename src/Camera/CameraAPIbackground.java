package Camera;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;

import Camera.CameraCommands.eCamCommand;

import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPIBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.persistenceModel.processDataModel.IProcessData;
import com.kuka.sunrise.common.task.categories.BackgroundTaskCategory;
import com.kuka.task.properties.TaskFunctionProvider;

@BackgroundTaskCategory(autoStart = true)
public class CameraAPIbackground extends RoboticsAPIBackgroundTask implements IcameraAPI {

	@Inject
	@Named("bCameraConnected")
	IProcessData bCameraConnected;
	@Inject
	@Named("sCameraStatus")
	IProcessData sCameraStatus;

	@Inject
	private Controller kUKA_Sunrise_Cabinet_1;

	private boolean bConnected = false;
	private String ipAddress = "172.31.1.130";
	private int port = 23;
	public Socket clientSocket;
	private InetSocketAddress inetAddress;
	private InetAddress hostname;
	private BufferedInputStream bufferedInput;
	private BufferedWriter output;
	private byte[] toBytes = new byte[1024];
	private boolean bLoggedIn = false;
	private String inMessage = "NA";
	private Thread tInMessage;
	private boolean bNewMessage = false;

	private ExecutorService esConnection = Executors.newSingleThreadExecutor();
	private boolean bCameraIsConnected = false;
	CameraResult camResult = new CameraResult();

	@Override
	public void run() {

		if (!bCameraIsConnected) {
			try {
				hostname = InetAddress.getByName(ipAddress);
				inetAddress = new InetSocketAddress(hostname, port);
				clientSocket = new Socket();
				sCameraStatus.setValue("try To Connect");
				clientSocket.connect(inetAddress);
				bConnected = true;
				sCameraStatus.setValue("Connected");
				bCameraConnected.setValue(true);
				bufferedInput = new BufferedInputStream(clientSocket.getInputStream());
				output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			} catch (UnknownHostException e) {
				bCameraIsConnected = false;
				sCameraStatus.setValue(e.getMessage());
				bCameraConnected.setValue(false);
			} catch (IOException e) {
				bCameraIsConnected = false;
				sCameraStatus.setValue(e.getMessage());
				bCameraConnected.setValue(false);
			}

			tInMessage = new Thread(new Runnable() {

				@Override
				public void run() {
					while (clientSocket != null && bConnected) {
						int bytesRead = 0;
						// String message = "NA";
						try {
							bytesRead = bufferedInput.read(toBytes);
							if (bytesRead > 0) {
								bNewMessage = true;
								inMessage = new String(toBytes, 0, bytesRead);
								System.out.println("Received from camera : " + inMessage);
								getMessage();
							} else {
								bCameraIsConnected = false;
								bConnected = false;
								clientSocket.close();
								bufferedInput.close();
								output.close();
								sCameraStatus.setValue("Err 3");
								bCameraConnected.setValue(false);
							}
						} catch (IOException e) {
							bCameraIsConnected = false;
							bConnected = false;
							sCameraStatus.setValue("Err 4");
							bCameraConnected.setValue(false);
						}
					}
				}

			});
			esConnection.execute(tInMessage);

			while (isConnected()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

				}

			}
			bCameraConnected.setValue(false);

		}
		if (esConnection != null && !esConnection.isShutdown()) {
			esConnection.shutdownNow();
		}
		sCameraStatus.setValue("Disconected");
		bCameraConnected.setValue(false);

	}

	public boolean isConnected() {
		return this.bConnected;
	}

	public String getMessage() {
		final String USER = "User";
		final String PASSWORD = "Password";
		final String LOGGEDIN = "Logged";

		if (!bLoggedIn && inMessage.contains(USER)) {
			inMessage = "NA";
			sendMessage("admin");
			return "NA";
		}
		if (!bLoggedIn && inMessage.contains(PASSWORD)) {
			inMessage = "NA";
			sendMessage("");
			return "NA";
		}
		if (!bLoggedIn && inMessage.contains(LOGGEDIN)) {
			inMessage = "NA";
			bLoggedIn = true;
			return "NA";
		}
		return inMessage;
	}

	public void sendMessage(String message) {
		if (clientSocket != null && bConnected) {
			try {

				output.write(message + "\r\n");
				output.flush();
				System.out.println("Sent :" + message);
			} catch (IOException e1) {
				System.out.println("ERROR: " + e1.getMessage() + "\n");
			}
		} else {
			System.out.println("ERROR: " + "Camera Server not open Or Client not Connected" + "\n");
		}

	}

	public boolean trigger() {
		sendMessage(eCamCommand.Trigger.getValue());
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Trigger >rec =" + inMessage);
		inMessage = "NA";

		return true;

	}

	public boolean changeProgramNr(int prgNr) {
		inMessage = "NA";
		sendMessage(eCamCommand.SetOffline.getValue());
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		// System.out.println("SetOffline >rec =" + inMessage);
		inMessage = "NA";
		sendMessage(eCamCommand.ChangeProgram.getValue() + prgNr);
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		// System.out.println("ChangeProgram >rec =" + inMessage);
		inMessage = "NA";
		sendMessage(eCamCommand.SetOnline.getValue());
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		// System.out.println("SetOnline >rec =" + inMessage);
		inMessage = "NA";
		camResult.setProgramNr(prgNr);
		return true;

	}

	public CameraResult getResult() {
		
		inMessage = "NA";
		// System.out.println("Send >" + eCamCommand.GetResultX.getValue());
		sendMessage(eCamCommand.GetResultX.getValue());
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}

		camResult.setResX(Double.valueOf(inMessage.substring(3)));
		inMessage = "NA";
		// System.out.println("Send >" + eCamCommand.GetResultY.getValue());
		sendMessage(eCamCommand.GetResultY.getValue());
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		camResult.setResY(Double.valueOf(inMessage.substring(3)));
		inMessage = "NA";
		// System.out.println("Send >" + eCamCommand.GetResultA.getValue());
		sendMessage(eCamCommand.GetResultA.getValue());
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
	System.out.println("Camera A string ="+inMessage.substring(3));
	
		camResult.setResA(Double.valueOf(inMessage.substring(3)));	
	
	
		
		inMessage = "NA";
		// System.out.println("Send >" + eCamCommand.GetError.getValue());
		sendMessage(eCamCommand.GetError.getValue());
		while (inMessage.contentEquals("NA")) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		camResult.setError(Double.valueOf(inMessage.substring(3)));
		inMessage = "NA";

		return camResult;

	}

	@Override
	public CameraAPIbackground getCameraApi() {
		return this;
	}

	@TaskFunctionProvider
	public IcameraAPI getAppInfoFunction() {
		return this;
	}

	public void dispose() {

		if (clientSocket != null) {
			try {
				clientSocket.close();
			} catch (IOException e) {

			}
		}
		if (bufferedInput != null) {
			try {
				bufferedInput.close();
			} catch (IOException e) {
			}
		}
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
			}
		}

		super.dispose();
	}
}