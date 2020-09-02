package TcpServer;

import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPIBackgroundTask;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.persistenceModel.processDataModel.IProcessData;
import com.kuka.sunrise.common.task.categories.BackgroundTaskCategory;
import com.kuka.task.properties.TaskFunctionProvider;

public class TcpServerBackgroun extends RoboticsAPIBackgroundTask implements ItcpApi {

	@Inject
	private MobileRobot kmr;
	@Inject
	@Named("Error")
	IProcessData Error;
	@Inject
	@Named("ServerRunning")
	IProcessData ServerRunning;
	private TcpServer tcpServer;
	private Integer port = 30005;
	public ExecutorService exServer;// = Executors.newCachedThreadPool();

	@Override
	public void run() {
		tcpServer = new TcpServer(getApplicationData(), port, kmr);
		while (true) {
			if (tcpServer != null && !tcpServer.isRunning()) {
				exServer = Executors.newCachedThreadPool();
				exServer.execute(tcpServer);

			}
			while (tcpServer != null && tcpServer.isRunning()) {
				ServerRunning.setValue(true);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
				
			}
			ServerRunning.setValue(false);
		}

	}

	@Override
	public TcpServer getTcpServer() {
		// TODO Auto-generated method stub
		return tcpServer;
	}

	@TaskFunctionProvider
	public ItcpApi getAppInfoFunction() {
		return this;
	}

	public void dispose() {
		if (tcpServer != null) {
			tcpServer.stop();
		}
		if (exServer != null) {
			exServer.shutdownNow();

		}
		super.dispose();
	}

}