package application;

import sun.util.logging.resources.logging;

import com.kuka.roboticsAPI.annotations.AutomaticResumeManager;
import com.kuka.roboticsAPI.applicationModel.IApplicationControl;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.DispatchedEventData;
import com.kuka.roboticsAPI.controllerModel.StatePortData;
import com.kuka.roboticsAPI.controllerModel.sunrise.ISunriseControllerStateListener;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.OperatorSafety;
import com.kuka.roboticsAPI.deviceModel.Device;
import com.kuka.task.ITaskLogger;

public class ControllListener implements ISunriseControllerStateListener {

	private ITaskLogger logger;
	private IApplicationControl application;
	private AutomaticResumeManager resumeManager;
	private String appName;

	public ControllListener(ITaskLogger logger, IApplicationControl application, AutomaticResumeManager resumeManager, String appName) {
		this.logger = logger;
		this.application = application;
		this.resumeManager = resumeManager;
		this.appName = appName;
	}

	@Override
	public void onFieldBusDeviceConfigurationChangeReceived(String controllerName, DispatchedEventData dispatchedEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFieldBusDeviceIdentificationRequestReceived(String controllerName, DispatchedEventData dispatchedEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIsReadyToMoveChanged(Device device, boolean isReadyToMove) {
		//logger.info("IIWA READY TO MOVE");
		resumeManager.enableApplicationResuming(appName);
	}

	@Override
	public void onShutdown(Controller controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatePortChangeReceived(Controller controller, StatePortData statePort) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionLost(Controller controller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSafetyStateChanged(Device device, SunriseSafetyState safetyState) {
		OperatorSafety operatorSafety = safetyState.getOperatorSafetyState();
		//logger.warn("SAFETY VIOLATED!");
		//resumeManager.disableApplicationResuming(appName);
		//application.halt();

	}

}