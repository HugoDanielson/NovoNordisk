package application;

import com.kuka.nav.robot.MobileRobot;
import com.kuka.task.ITaskLogger;

public class SafetyListener implements Runnable{
	private MobileRobot kmr;
	private ITaskLogger logger;
	public SafetyListener(MobileRobot kmr,ITaskLogger logger){
		this.kmr = kmr;
		this.logger = logger;
	}
	@Override
	public void run() {
		while(true){
			if(kmr.getSafetyState().toString().contentEquals("WARNING_FIELD")){
				logger.warn("KMR SAFETY VIOLATED !!!");
			}
		}
		
	}

}
