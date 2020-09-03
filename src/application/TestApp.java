package application;


import javax.inject.Inject;
import javax.inject.Named;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;

/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a 
 * {@link RoboticsAPITask#run()} method, which will be called successively in 
 * the application lifecycle. The application will terminate automatically after 
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the 
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an 
 * exception is thrown during initialization or run. 
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the 
 * {@link RoboticsAPITask#dispose()} method.</b> 
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */
public class TestApp extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;
	@Inject
	@Named("Gripper")
	private Tool Gripper;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp3;
	@Override
	public void initialize() {
		Gripper.attachTo(lbr.getFlange());
	}

	@Override
	public void run() {
		// your application execution starts here
		tcp3 = Gripper.getFrame("/TCP/AngleOffset/ShiftTCP3");
		tcp3.move(ptp(getApplicationData().getFrame("/Station3/P1")).setJointVelocityRel(0.1));
		tcp3.move(ptp(getApplicationData().getFrame("/Station3/P2")).setJointVelocityRel(0.1));
		
		tcp3.move(lin(getApplicationData().getFrame("/Station3/P3")).setJointVelocityRel(0.1));
		tcp3.move(lin(getApplicationData().getFrame("/Station3/P4")).setJointVelocityRel(0.1));
		tcp3.move(lin(getApplicationData().getFrame("/Station3/P5")).setJointVelocityRel(0.1));
		
		tcp3.move(lin(getApplicationData().getFrame("/Station3/P4")).setJointVelocityRel(0.1));
		tcp3.move(lin(getApplicationData().getFrame("/Station3/P3")).setJointVelocityRel(0.1));
		tcp3.move(lin(getApplicationData().getFrame("/Station3/P2")).setJointVelocityRel(0.1));
		tcp3.move(ptp(getApplicationData().getFrame("/Station3/P1")).setJointVelocityRel(0.1));
		
	}
}