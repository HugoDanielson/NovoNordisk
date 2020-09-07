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

import com.kuka.roboticsAPI.conditionModel.ICallbackAction;
import com.kuka.roboticsAPI.conditionModel.MotionPathCondition;
import com.kuka.roboticsAPI.conditionModel.ReferenceType;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.executionModel.IFiredTriggerInfo;
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

public class NordiskG2 extends RoboticsAPIApplication {
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
	@Named("Gripper2")
	private Tool Gripper2;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp2;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp3;

	@Inject
	@Named("WP")
	private Workpiece WP;

	@Inject
	private Weight weight;
	private double wpWeight = 0.0;
	String refFrame;

	private LIN pos1;
	private ITransformation offset;
	private IMotionContainer moveConteiner;

	@Override
	public void initialize() {
		Gripper2.attachTo(lbr.getFlange());
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

		tcp2 = Gripper2.getFrame("/TCP2/AngleOffset/ShiftTCP");

		// Move iiwa from Home to St1 pos
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromHome/FromHomeToSt2/P1")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromHome/FromHomeToSt2/P2")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromHome/FromHomeToSt2/P3")).setJointVelocityRel(0.1));
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

	//	moveTo.run(eMoveFrom.St2, eMoveTo.St1, null);

		refFrame = "/Station2/BaseShift";
		offset = Transformation.ofDeg(0, 0, 500, 0, 0, 0);
		pos1 = lin(getApplicationData().getFrame(refFrame).copyWithRedundancy().transform(getApplicationData().getFrame(refFrame), offset)).setJointVelocityRel(0.1);

		tcp2.move(pos1);

		refFrame = "/Station2/BaseShift";
		offset = Transformation.ofDeg(0, 0, 0, 0, 0, 0);
		pos1 = lin(getApplicationData().getFrame(refFrame).copyWithRedundancy().transform(getApplicationData().getFrame(refFrame), offset)).setJointVelocityRel(0.1);

		tcp2.move(pos1);
		waitMs(1000);
		wpWeight = weight.getWeightZ(tcp2);
		WP.getLoadData().setMass(wpWeight);

		WP.attachTo(Gripper2.getRootFrame());
		tcp2.move(pos1);

		double x = -0.5; 
		double y = 0.0; 
		double theta = Math.toRadians(0);

		RelativeMotion motion = new RelativeMotion(x, y, theta);
		kmr.execute(motion.setVelocity(new XYTheta(0.05, 0.05, 0.05)));

		// Move iiwa to cary pos
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt2ToCarryPos/P1")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt2ToCarryPos/P2")).setJointVelocityRel(0.1));
		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt2ToCarryPos/P3")).setJointVelocityRel(0.1));
		moveConteiner.await();

		moveTo.run(eMoveFrom.St2, eMoveTo.St3, null);

		tcp3 = Gripper2.getFrame("/TCP2/AngleOffset/ShiftTCP3");

		// moveConteiner =
// tcp3.moveAsync(ptp(getApplicationData().getFrame("/Station3/P1")).setJointVelocityRel(0.1));

		MotionPathCondition starPosTrigger = new MotionPathCondition(ReferenceType.START, 5, 0);

		ICallbackAction changeWeight = new ICallbackAction() {
			@Override
			public void onTriggerFired(IFiredTriggerInfo triggerInformation) {
				double weightSubstraction = wpWeight / 3;
				WP.getLoadData().setMass(WP.getLoadData().getMass() - weightSubstraction);
				System.out.println("New weight = " + WP.getLoadData().getMass());
			}
		};

		moveConteiner = tcp3.move(ptp(getApplicationData().getFrame("/Station3_G2/P1")).setJointVelocityRel(0.1));
		moveConteiner.await();
		waitMs(8000);
		moveConteiner = tcp3.move(lin(getApplicationData().getFrame("/Station3_G2/P2")).setJointVelocityRel(0.05).triggerWhen(starPosTrigger, changeWeight));
		moveConteiner.await();
		waitMs(8000);
		moveConteiner = tcp3.move(lin(getApplicationData().getFrame("/Station3_G2/P3")).setJointVelocityRel(0.05).triggerWhen(starPosTrigger, changeWeight));
		moveConteiner.await();
		waitMs(8000);
		moveConteiner = tcp3.move(lin(getApplicationData().getFrame("/Station3_G2/P4")).setJointVelocityRel(0.05).triggerWhen(starPosTrigger, changeWeight));
		moveConteiner.await();
		waitMs(8000);

		WP.getLoadData().setMass(0.0);
		WP.detach();

		moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3_G2/P5")).setJointVelocityRel(0.2));
		moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3_G2/P4")).setJointVelocityRel(0.2));
		moveConteiner = tcp3.moveAsync(lin(getApplicationData().getFrame("/Station3_G2/P3")).setJointVelocityRel(0.2));
		moveConteiner = tcp3.moveAsync(ptp(getApplicationData().getFrame("/Station3_G2/P2")).setJointVelocityRel(0.2));
		// moveConteiner =
// tcp3.moveAsync(ptp(getApplicationData().getFrame("/Station3/P1")).setJointVelocityRel(0.1));
		moveConteiner.await();
//		moveConteiner = tcp2.moveAsync(ptp(getApplicationData().getFrame("/FromSt1ToCarryPos/P9")).setJointVelocityRel(0.2));
//		moveConteiner.await();

		moveTo.run(eMoveFrom.St3, eMoveTo.St2, null);
		kmr.unlock();

//	

	}

	private void waitMs(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}
}