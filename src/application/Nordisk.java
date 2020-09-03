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
import Util.Weight;

import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.fleet.FleetManager;
import com.kuka.nav.fleet.graph.GraphData;
import com.kuka.nav.fleet.graph.TopologyGraph;
import com.kuka.nav.rel.RelativeMotion;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.nav.robot.MobileRobotManager;
import com.kuka.resource.locking.LockException;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.Workpiece;
import com.kuka.roboticsAPI.geometricModel.math.ITransformation;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.LIN;
import com.kuka.roboticsAPI.persistenceModel.processDataModel.IProcessData;
import com.kuka.task.ITask;
import com.kuka.task.ITaskFunctionMonitor;
import com.kuka.task.ITaskInstance;
import com.kuka.task.ITaskLogger;
import com.kuka.task.ITaskManager;
import com.kuka.task.TaskFunctionMonitor;

public class Nordisk extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;
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

	@Inject
	@Named("Gripper")
	private Tool Gripper;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp2;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp3;

	@Inject
	@Named("WP")
	private Workpiece WP;

	@Inject
	private Weight weight;
	String refFrame;

	private LIN pos1;
	private ITransformation offset;
	private IMotionContainer moveConteiner;

	@Override
	public void initialize() {
		Gripper.attachTo(lbr.getFlange());
	}

	@Override
	public void run() {
		iTcpApi = getTaskFunction(ItcpApi.class);
		tcpServerMonitor = TaskFunctionMonitor.create(iTcpApi);
		System.out.println("tcpServerMonitor =" + tcpServerMonitor.isAvailable());

		if (tcpServerMonitor.isAvailable()) {
			tcpServer = iTcpApi.getTcpServerBackground().getTcpServer();
			System.out.println("TcpServer instance = " + tcpServer);
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

		tcp2 = Gripper.getFrame("/TCP/AngleOffset/ShiftTCP2");

		
		// Move iiwa from Home to St1 pos
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromHome/FromHomeToSt1/P1")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromHome/FromHomeToSt1/P2")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromHome/FromHomeToSt1/P3")).setJointVelocityRel(0.1));
		moveConteiner.await();
		
	// Move KMP OUT REL
		
		try {
			kmr.lock();
		} catch (LockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		moveTo.run(eMoveFrom.St3, eMoveTo.St2, null);
		
		refFrame = "/Station1/BaseShift";
		offset = Transformation.ofDeg(385, 0, 0, 0, 0, 0);
		pos1 = lin(getApplicationData().getFrame(refFrame).copyWithRedundancy().transform(getApplicationData().getFrame(refFrame), offset)).setJointVelocityRel(0.1);

		tcp2.move(pos1);

		refFrame = "/Station1/BaseShift";
		offset = Transformation.ofDeg(385, 0, 50, 0, 0, 0);
		pos1 = lin(getApplicationData().getFrame(refFrame).copyWithRedundancy().transform(getApplicationData().getFrame(refFrame), offset)).setJointVelocityRel(0.1);

		tcp2.move(pos1);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		WP.getLoadData().setMass(weight.getWeightZ(tcp2));

		WP.attachTo(Gripper.getRootFrame());
		tcp2.move(pos1);
		
		
		
		
		
		
	
		
		double x = -0.5; // In meters
		double y = 0.0; // In meters
		// Convert degrees to radians
		double theta = Math.toRadians(0);
		
		RelativeMotion motion = new RelativeMotion(x, y, theta);
		kmr.execute(motion.setVelocity(new XYTheta(0.05, 0.05, 0.05)));
		
		
		
		// Move iiwa to cary pos
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt1ToCarryPos/P1")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt1ToCarryPos/P8")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt1ToCarryPos/P9")).setJointVelocityRel(0.1));
		moveConteiner.await();
		
		moveTo.run(eMoveFrom.St1, eMoveTo.St3, null);
		
		tcp3 = Gripper.getFrame("/TCP/AngleOffset/ShiftTCP3");
		
		//moveConteiner = tcp3.moveAsync(ptp(getApplicationData().getFrame("/Station3/P1")).setJointVelocityRel(0.1));
		moveConteiner = tcp3.moveAsync(ptp(getApplicationData().getFrame("/Station3/P2")).setJointVelocityRel(0.1));
		//moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3/P3")).setJointVelocityRel(0.1));
		moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3/P4")).setJointVelocityRel(0.1));
		moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3/P5")).setJointVelocityRel(0.1));
		moveConteiner.await();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		
		moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3/P5")).setJointVelocityRel(0.1));
		moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3/P4")).setJointVelocityRel(0.1));
		//moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3/P3")).setJointVelocityRel(0.1));
		moveConteiner = tcp3.moveAsync(ptp(getApplicationData().getFrame("/Station3/P2")).setJointVelocityRel(0.1));
		//moveConteiner = tcp3.moveAsync(ptp(getApplicationData().getFrame("/Station3/P1")).setJointVelocityRel(0.1));
		moveConteiner.await();
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt1ToCarryPos/P9")).setJointVelocityRel(0.1));
		moveConteiner.await();
	
		moveTo.run(eMoveFrom.St3, eMoveTo.St2, null);
		kmr.unlock();

//		while (!tcpServer.getbClientConnected().get()) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("Client not connected");
//
//		}
//		Queue<String> clientQueue = tcpServer.getClientMessageQueue().getQueue();
//		while ((Boolean) RunApp.getValue()) {
//
//			if (!clientQueue.isEmpty()) {
//				System.out.println("recieved message =" + clientQueue.poll());
//			}
//
//		}
//		

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