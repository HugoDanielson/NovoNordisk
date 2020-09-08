package application;

import javax.inject.Inject;
import javax.inject.Named;

import GlobalParameters.GlobalParam.eMoveFrom;
import GlobalParameters.GlobalParam.eMoveTo;
import MoveTo.MoveTo.MoveTo;
import Util.Weight;

import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.line.VirtualLineMotion;
import com.kuka.nav.line.VirtualLineMotionContainer;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.resource.locking.LockException;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.Workpiece;

public class Nordisk_Gripper2 extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;

	@Inject
	@Named("WP")
	private Workpiece WP;

	@Inject
	private Weight weight;
	private double wpWeight = 0.0;

	@Inject
	@Named("Gripper2")
	private Tool Gripper2;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp3;

	@Inject
	private LocationData location;
	@Inject
	private MobileRobot kmr;
	@Inject
	private MoveTo moveTo;

	private JointPosition jPos1;

	@Override
	public void initialize() {
		Gripper2.attachTo(lbr.getFlange());

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

		VirtualLineMotionContainer vcm;

		vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), location.get(2).getPose()).setVelocity( new XYTheta(0.2, 0.2, 0.2)));
		vcm.awaitFinished();
		
		jPos1 = new JointPosition(Math.toRadians(85)
				,Math.toRadians(-44)
				,Math.toRadians(43)
				,Math.toRadians(80)
				,Math.toRadians(80)
				,Math.toRadians(-48)
				,Math.toRadians(-75));
		
		lbr.move(ptp(jPos1));
		
		

		tcp = Gripper2.getFrame("TCP2/AngleOffset/ShiftTCP1");
		System.out.println("Robot in moving to P1 -prepos");
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P1")).setJointVelocityRel(0.2));
		System.out.println("Robot in moving to P2 -pickup pus");
		tcp.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P2")).setJointVelocityRel(0.2));
		System.out.println("Robot in PickUp position");
		waitSec(2000);
		System.out.println("Robot in start motion");
		System.out.println("Robot in moving to P1 - prepos");
		tcp.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P1")).setJointVelocityRel(0.2));

		waitSec(1000);
		wpWeight = weight.getWeightZ(tcp);
		WP.getLoadData().setMass(wpWeight);

		System.out.println("Robot in moving to P3 - Platform prepos");
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P3")).setJointVelocityRel(0.2));

		System.out.println("Robot place to the platform");
		tcp.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P4")).setJointVelocityRel(0.2));

		System.out.println("Robot in lift from the platform");
		tcp.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P3")).setJointVelocityRel(0.2));

		moveTo.run(eMoveFrom.St2, eMoveTo.St4, null);
		
		
//		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P5")).setJointVelocityRel(0.2));
//		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P6")).setJointVelocityRel(0.2));
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P7")).setJointVelocityRel(0.2));
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P8")).setJointVelocityRel(0.2));
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P9")).setJointVelocityRel(0.2));
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P10")).setJointVelocityRel(0.2));
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P11")).setJointVelocityRel(0.2));
		
		tcp3 = Gripper2.getFrame("TCP2/AngleOffset/ShiftTCP3");
		
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P12")).setJointVelocityRel(0.2));
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P13")).setJointVelocityRel(0.2));
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P14")).setJointVelocityRel(0.2));
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P15")).setJointVelocityRel(0.2));
		
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P15")).setJointVelocityRel(0.2));
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P14")).setJointVelocityRel(0.2));
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P13")).setJointVelocityRel(0.2));
		tcp3.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P12")).setJointVelocityRel(0.2));
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P11")).setJointVelocityRel(0.2));
		
		
		
		kmr.unlock();
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