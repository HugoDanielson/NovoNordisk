package application;

import javax.inject.Inject;


import GlobalParameters.GlobalParam.eMoveFrom;
import GlobalParameters.GlobalParam.eMoveTo;
import MoveTo.MoveTo.MoveTo;

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
import com.kuka.task.ITaskLogger;
import com.kuka.task.ITaskManager;

public class Nordisk extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;

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
		try {
			kmr.lock();
		} catch (LockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		moveTo.run(eMoveFrom.St5,eMoveTo.St1, null);
		moveTo.run(eMoveFrom.St1,eMoveTo.St5, null);
		
		moveTo.run(eMoveFrom.St5,eMoveTo.St2, null);
		moveTo.run(eMoveFrom.St2,eMoveTo.St5, null);
		
		moveTo.run(eMoveFrom.St5,eMoveTo.St3, null);
		moveTo.run(eMoveFrom.St3,eMoveTo.St5, null);
		
		moveTo.run(eMoveFrom.St5,eMoveTo.St4, null);
		moveTo.run(eMoveFrom.St4,eMoveTo.St5, null);
		
		kmr.unlock();

	}
}