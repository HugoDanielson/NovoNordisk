package application;

import javax.inject.Inject;
import javax.inject.Named;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;

public class BaseST2Test extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;

	@Inject
	@Named("Gripper2")
	private Tool Gripper2;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp;

	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		// your application execution starts here

		Gripper2.attachTo(lbr.getFlange());

		tcp = Gripper2.getFrame("TCP2/AngleOffset/ShiftTCP1");
		System.out.println("Robot in moving to P1");
		tcp.move(ptp(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P1")).setJointVelocityRel(0.2));
		System.out.println("Robot in moving to P2");
		tcp.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P2")).setJointVelocityRel(0.2));
		System.out.println("Robot in PickUp position");
		waitSec(2000);
		System.out.println("Robot in start motion");
		System.out.println("Robot in moving to P1");
		tcp.move(lin(getApplicationData().getFrame("/Station2/BaseShift/CameraOffset/ZCalibration/P1")).setJointVelocityRel(0.2));

	}
	
	public void waitSec (long waitTime){
		
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
}