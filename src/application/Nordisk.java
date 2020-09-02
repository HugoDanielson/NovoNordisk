package application;

import java.util.Collection;
import java.util.Queue;

import javax.inject.Inject;
import javax.inject.Named;

import GlobalParameters.GlobalParam.eMoveFrom;
import GlobalParameters.GlobalParam.eMoveTo;
import MoveTo.MoveTo.MoveTo;
import TcpServer.ClientMessageQueue;
import TcpServer.ItcpApi;
import TcpServer.TcpServer;
import TcpServer.TcpServerBackground;

import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.fleet.FleetManager;
import com.kuka.nav.fleet.graph.GraphData;
import com.kuka.nav.fleet.graph.TopologyGraph;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.nav.robot.MobileRobotManager;
import com.kuka.resource.locking.LockException;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.persistenceModel.processDataModel.IProcessData;
import com.kuka.task.ITask;
import com.kuka.task.ITaskFunctionMonitor;
import com.kuka.task.ITaskInstance;
import com.kuka.task.ITaskLogger;
import com.kuka.task.ITaskManager;
import com.kuka.task.TaskFunctionMonitor;

public class Nordisk extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;
	@Inject
	@Named("RunApp")
	IProcessData RunApp;
	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;
	@Inject
	private ITaskLogger logger;
	@Inject
	private MobileRobotManager robotManager;

	@Inject
	private GraphData graphData;
	@Inject
	private FleetManager fleetManager;
	@Inject
	private LocationData locData;
	@Inject
	private ITaskManager taskmanager;

	private TopologyGraph topologyGraph;

	private ItcpApi iTcpApi;
	private ITaskFunctionMonitor tcpServerMonitor;
	private Collection<ITaskInstance> instances;
	private ITask task;
	@Inject
	private ITaskManager taskManager;
	private TcpServer tcpServer;
	@Inject
	private MoveTo moveTo;
	@Inject
	private MobileRobot kmr;

	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		iTcpApi = getTaskFunction(ItcpApi.class);
		tcpServerMonitor = TaskFunctionMonitor.create(iTcpApi);
		System.out.println("tcpServerMonitor =" + tcpServerMonitor.isAvailable());
		
		
		if (tcpServerMonitor.isAvailable()) {
			tcpServer =iTcpApi.getTcpServerBackground().getTcpServer();
		
		} else {
			System.out.println("Creating new instance of tcpServer");
			task = taskManager.getTask(TcpServerBackground.class);
			instances = task.getInstances();

			if (instances.isEmpty()) {
				try {
					task.startInstance();
					System.out.println("Start tcpServer Background");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			tcpServer = iTcpApi.getTcpServerBackground().getTcpServer();
			System.out.println("TcpServer instance = " + tcpServer);
		}

//		Queue<String> clientQueue = tcpServer.getClientMessageQueue().getQueue();
//		while ((Boolean) RunApp.getValue()) {
//
//			if (!clientQueue.isEmpty()) {
//				System.out.println("recieved message =" + clientQueue.poll());
//			}
//
//		}

//		try {
//			kmr.lock();
//		} catch (LockException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		moveTo.run(eMoveFrom.St5,eMoveTo.St1, null);
//		moveTo.run(eMoveFrom.St1,eMoveTo.St5, null);
//		
//		moveTo.run(eMoveFrom.St5,eMoveTo.St2, null);
//		moveTo.run(eMoveFrom.St2,eMoveTo.St5, null);
//		
//		moveTo.run(eMoveFrom.St5,eMoveTo.St3, null);
//		moveTo.run(eMoveFrom.St3,eMoveTo.St5, null);
//		
//		moveTo.run(eMoveFrom.St5,eMoveTo.St4, null);
//		moveTo.run(eMoveFrom.St4,eMoveTo.St5, null);
//		
//		kmr.unlock();

	}
}