package application;


import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;

import Camera.CameraAPIbackground;
import Camera.CameraInit;
import Camera.IcameraAPI;
import GlobalParameters.GlobalParam.eMoveFrom;
import GlobalParameters.GlobalParam.eMoveTo;
import MoveTo.MoveTo.MoveTo;
import TcpServer.ItcpApi;
import TcpServer.TcpServer;
import Util.BaseManipulation;
import Util.Weight;


import com.kuka.nav.Location;
import com.kuka.nav.Pose;
import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.fineloc.FineLocalizationContainer;
import com.kuka.nav.fineloc.FineLocalizationRequest;
import com.kuka.nav.fleet.FleetManager;
import com.kuka.nav.fleet.graph.GraphData;
import com.kuka.nav.fleet.graph.TopologyGraph;
import com.kuka.nav.line.VirtualLineMotion;
import com.kuka.nav.line.VirtualLineMotionContainer;
import com.kuka.nav.rel.RelativeMotion;
import com.kuka.nav.rel.RelativeMotionContainer;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.nav.robot.MobileRobotManager;
import com.kuka.resource.locking.LockException;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.Workpiece;
import com.kuka.roboticsAPI.geometricModel.math.ITransformation;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.LIN;
import com.kuka.roboticsAPI.persistenceModel.processDataModel.IProcessData;
import com.kuka.task.ITask;
import com.kuka.task.ITaskFunctionMonitor;
import com.kuka.task.ITaskInstance;
import com.kuka.task.ITaskLogger;
import com.kuka.task.ITaskManager;


public class CopyOfHEPA_autoloading extends RoboticsAPIApplication {
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
	@Named("ZCQY")
	private Tool ZCQY;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp2;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp3;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp4;
	@Inject
	@Named("WP")
	private Workpiece WP;
	
	@Inject
	private BaseManipulation base;

	@Inject
	private Weight weight;
	private double wpWeight = 0.0;
	String refFrame;

	private LIN pos1;
	private ITransformation offset;
	private IMotionContainer moveConteiner;
	
	
	private ExecutorService esCameraInit = Executors.newSingleThreadExecutor();
	@Override
	public void initialize() {
		ZCQY.attachTo(lbr.getFlange());

		try {
			kmr.lock();
		} catch (LockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		
		
		//tcp1 = ZCQY.getFrame("/ZCQY_Text/AngleChange/Shift1");
		tcp2 = ZCQY.getFrame("/ZCQY_Text/AngleChange/Shift2");	
		tcp3 = ZCQY.getFrame("/ZCQY_Text/AngleChange/Shift3");
		tcp4 = ZCQY.getFrame("/ZCQY_Text/AngleChange/Shift4");
		base.DeleteOldData(getFrame("/Station1/BaseShift/CameraOffset/ZCalibration"));
		
		
		
		//back to HEPA Trolley(St1)
		moveTo.run(eMoveFrom.St2, eMoveTo.St1, null);
		moveFineLocation(locData.get(1), 0.9, kmr);
		
		
		//put down support box
		tcp2.move(lin(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P1")).setJointVelocityRel(0.2));

		
		//Grip the pistons box
		tcp2.move(ptp(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P2")).setJointVelocityRel(0.2));
		tcp2.move(ptp(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P10")).setJointVelocityRel(0.2));
		tcp2.move(ptp(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P11")).setJointVelocityRel(0.2));
		tcp2.move(ptp(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P10")).setJointVelocityRel(0.2));
		tcp2.moveAsync(lin(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P3")).setJointVelocityRel(0.2));
		tcp2.moveAsync(lin(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P4")).setJointVelocityRel(0.05));
		tcp2.moveAsync(ptp(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P5")).setJointVelocityRel(0.1));
		tcp2.moveAsync(ptp(getApplicationData().getFrame("/Station1/BaseShift/CameraOffset/ZCalibration/P6")).setJointVelocityRel(0.2));

		
		//move AGV from HEPA Trolley(St1) to pistons loading place(st4)
		moveTo.run(eMoveFrom.St1, eMoveTo.St4, null);
		moveFineLocation(locData.get(4), 0.9, kmr);
		
		
		//loading caps
		kmr.unlock();
		
	}
	
	
	public void moveFineLocation(Location destination, double velocity, MobileRobot robot) {
		XYTheta vel = new XYTheta(velocity, velocity, velocity);
		XYTheta acc = new XYTheta(0.1, 0.1, 0.1);

		logger.info("******************************************");
		logger.info("*********     FINE LOCATION     **********");
		logger.info("******************************************");

		int i = 0;
		if (!destination.hasSensorData()){
			logger.info("ERROR FINELOCATION NO SENSOR DATA");
			return;
		}
		
		FineLocalizationContainer fineConteiner = robot.execute(new FineLocalizationRequest(destination));
		fineConteiner.awaitFinished();
		RelativeMotionContainer containerRel;
		if (fineConteiner.getError() != null) {
			System.out.println("ERROR FINELOCATION CONTAINER");

		} else {
			Pose offset = null;
			try {
				offset = fineConteiner.getRobotPose().invert();
				System.out.println("FINELOCATION OFFSET ="+offset);
				containerRel = robot.execute(new RelativeMotion(offset).setVelocity(vel).setAcceleration(acc));
				containerRel.awaitFinished();
			
			} catch (Exception e) {
				
				System.out.println("FINELOCATION containerRel canceled");

			}
		}
		System.out.println("FineLocation Executed");
		
		
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		esCameraInit.shutdownNow();
		esCameraInit = null;
		System.gc();

	}
	public void waitSec(long waitTime) {

		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}
}