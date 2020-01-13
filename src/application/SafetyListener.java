package application;

import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.annotations.AutomaticResumeManager;
import com.kuka.roboticsAPI.applicationModel.IApplicationControl;
import com.kuka.task.ITaskLogger;

public class SafetyListener implements Runnable{
	private MobileRobot kmr;
	private ITaskLogger logger;
	private AutomaticResumeManager resumeManager;
	private IApplicationControl application;
	private String appName;
	public SafetyListener(MobileRobot kmr,ITaskLogger logger,AutomaticResumeManager resumeManager,IApplicationControl application,String appName){
		this.kmr = kmr;
		this.logger = logger;
		this.resumeManager = resumeManager;
		this.application = application;
		this.appName = appName;
	}
	@Override
	public void run() {
		while(true){
			if(kmr.getSafetyState().toString().contentEquals("WARNING_FIELD")){
				logger.warn("KMR SAFETY VIOLATED !!!");
				//resumeManager.disableApplicationResuming(appName);
				application.halt();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}else {
				//resumeManager.enableApplicationResuming(appName);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		}
		
	}

}
