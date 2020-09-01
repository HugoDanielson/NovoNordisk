package TcpServer;


import javax.inject.Inject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.sunrise.common.task.categories.BackgroundTaskCategory;
import com.kuka.task.properties.TaskFunctionProvider;


public class TcpServerBackgroun extends RoboticsAPICyclicBackgroundTask implements ItcpApi {
	
	@Inject
	private MobileRobot kmr;
	public static TcpServerX tcpServer;
	private Integer port = 30001;
	public void initialize() {
		// initialize your task here
		initializeCyclic(0, 1000, TimeUnit.MILLISECONDS, CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic() {
		if(tcpServer !=null && !tcpServer.isRunning()){
			tcpServer.connect();
		}else {
			tcpServer = new TcpServerX(port,kmr);
			tcpServer.connect();
		}
		
	}

	@Override
	public TcpServerX getTcpServer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@TaskFunctionProvider
	public ItcpApi getAppInfoFunction() {
		return this;
	}

	public void dispose() {

		tcpServer.stop();
		super.dispose();
	}
	
}