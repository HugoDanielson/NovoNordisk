package application;



import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import com.kuka.roboticsAPI.annotations.AutomaticResumeManager;
import com.kuka.roboticsAPI.annotations.IAutomaticResumeFunction;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.task.properties.TaskFunctionProvider;

public class BT_AutoResume extends RoboticsAPICyclicBackgroundTask {
	@Inject
	private AutomaticResumeManager _automaticResumeManager;

	@Override
	public void initialize() {
		initializeCyclic(0, 500, TimeUnit.MILLISECONDS,
				CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic() {
	}
	
	@TaskFunctionProvider
	public IAutomaticResumeFunction getAutomaticResumeFunction() {
		return _automaticResumeManager;
	}
}
