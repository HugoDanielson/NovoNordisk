package application;

import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.annotations.AutomaticResumeManager;
import com.kuka.roboticsAPI.annotations.IAutomaticResumeFunction;
import com.kuka.roboticsAPI.applicationModel.IApplicationControl;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.task.ITaskLogger;
import com.kuka.task.ITaskManager;

public class SafetyListener implements Runnable{
	private MobileRobot kmr;
	private ITaskLogger logger;
	private AutomaticResumeManager resumeManager;
	private IApplicationControl application;
	private String appName;
	private LBR lbr;
	private ITaskManager taskManager;
	
	public SafetyListener(MobileRobot kmr,ITaskLogger logger,AutomaticResumeManager resumeManager,IApplicationControl application,String appName,LBR lbr,ITaskManager taskManager){
		this.kmr = kmr;
		this.logger = logger;
		this.resumeManager = resumeManager;
		this.application = application;
		this.appName = appName;
		this.lbr = lbr;
		this.taskManager = taskManager;
	}
	@Override
	public void run() {
		while(true){
			if(kmr.getSafetyState().toString().contentEquals("PROTECTIVE_STOP")){
				logger.warn("KMR PROTECTIVE STOP !!!");
				resumeManager.disableApplicationResuming(appName);
				//taskManager.getTaskFunction(IAutomaticResumeFunction.class).disableApplicationResuming(appName);
				application.setApplicationOverride(0.1);
				//application.halt();
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}else {
				//taskManager.getTaskFunction(IAutomaticResumeFunction.class).enableApplicationResuming(appName);
				application.setApplicationOverride(1);
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
