package application;


import javax.inject.Inject;
import javax.inject.Named;

import Util.Weight;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.Workpiece;
import com.kuka.roboticsAPI.geometricModel.math.ITransformation;
import com.kuka.roboticsAPI.motionModel.IMotionContainer;
import com.kuka.roboticsAPI.motionModel.LIN;

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
public class MoveTest2 extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kmr;
	
	
	@Inject
	@Named("EmptyGripper")
	private Tool EmptyGripper;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp1;
	




	@Override
	public void initialize() {
		// initialize your application here
		EmptyGripper.attachTo(lbr.getFlange());
	}

	@Override
	public void run() {
		// your application execution starts here
		
		
		tcp1 = EmptyGripper.getFrame("/AngleChange/TCP");
		// Move iiwa from Home to St1 pos
		
		 tcp1.move(ptp(getApplicationData().getFrame("/St2_New/BaseShift/P1")).setJointVelocityRel(0.1));
		 tcp1.move(lin(getApplicationData().getFrame("/St2_New/BaseShift/P2")).setJointVelocityRel(0.1));
		 tcp1.move(lin(getApplicationData().getFrame("/St2_New/BaseShift/P3")).setJointVelocityRel(0.1));
		 tcp1.move(lin(getApplicationData().getFrame("/St2_New/BaseShift/P4")).setJointVelocityRel(0.1));
		


	}
}