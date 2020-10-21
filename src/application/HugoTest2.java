
package application;


import javax.inject.Inject;

import com.kuka.Tool.Gripper;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;

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
public class HugoTest2 extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kmr;
	
	@Inject
	private Gripper gripper;
	
	

	
	@Override
	public void initialize() {

	}

	@Override
	public void run() {

		try{
			
		
		gripper.extendPin();
		System.out.println("Gripper is extended =" + gripper.isExtended());
		
		Thread.sleep(1000);
		
		gripper.retractPin();
		System.out.println("Gripper is Retracter = "+ gripper.isRetracted());
		Thread.sleep(1000);
		
		
		System.out.println("Gripper is extended =" + gripper.extendPin());
		Thread.sleep(1000);
		System.out.println("Gripper is extended =" + gripper.retractPin());
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
			
		
	}
}