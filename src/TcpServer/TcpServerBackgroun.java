package TcpServer;


import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.persistenceModel.processDataModel.IProcessData;
import com.kuka.sunrise.common.task.categories.BackgroundTaskCategory;
import com.kuka.task.properties.TaskFunctionProvider;


public class TcpServerBackgroun extends RoboticsAPICyclicBackgroundTask implements ItcpApi {
	
	@Inject
	private MobileRobot kmr;
	@Inject
	@Named("Error")
	IProcessData Error;
	public static TcpServerX tcpServer;
	private Integer port = 30001;
	public void initialize() {
		// initialize your task here
		initializeCyclic(0, 1, TimeUnit.DAYS, CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic() {
		if(tcpServer !=null && !tcpServer.isRunning()){
			tcpServer.connect();
			Error.setValue("e1");
		}else {
			tcpServer = new TcpServerX(getApplicationData(),port,kmr);
			tcpServer.connect();
			Error.setValue("e2");
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