package application;


import javax.inject.Inject;

import com.kuka.nav.honk.HonkAction;
import com.kuka.nav.honk.HonkCommand;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.resource.locking.LockException;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;


public class Honk extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;
	@Inject
	private MobileRobot kmp;
	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;

	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		HonkAction honkAction = new HonkAction(HonkCommand.ON). setContinuous(true).setHonkTimeDuration(2000);
		

			try {
				kmp.lock();
			} catch (LockException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			}
			
			kmp.execute(honkAction);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kmp.unlock();
		
	}
}