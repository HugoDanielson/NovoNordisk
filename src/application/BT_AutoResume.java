package application;



import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.annotations.AutomaticResumeManager;
import com.kuka.roboticsAPI.annotations.IAutomaticResumeFunction;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.task.properties.TaskFunctionProvider;

public class BT_AutoResume extends RoboticsAPICyclicBackgroundTask {
	@Inject
	private AutomaticResumeManager resumeManager;
	@Inject
	private MobileRobot kmr;
	@Override
	public void initialize() {
		initializeCyclic(0, 500, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic() {
		
		if(!kmr.getSafetyState().toString().contentEquals("PROTECTIVE_STOP")){
			resumeManager.enableApplicationResuming("Test1.class");
		
		}
		

	}
	
	@TaskFunctionProvider
	public IAutomaticResumeFunction getAutomaticResumeFunction() {
		return resumeManager;
	}
}
