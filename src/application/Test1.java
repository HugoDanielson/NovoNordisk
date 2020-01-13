package application;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;


import com.kuka.generated.ioAccess.RaspberryIOGroup;
import com.kuka.nav.Location;
import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.line.VirtualLineMotion;
import com.kuka.nav.line.VirtualLineMotionContainer;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.resource.locking.LockException;
import com.kuka.roboticsAPI.annotations.AutomaticResumeManager;
import com.kuka.roboticsAPI.annotations.ResumeAfterPauseEvent;
import com.kuka.roboticsAPI.applicationModel.IApplicationControl;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.DispatchedEventData;
import com.kuka.roboticsAPI.controllerModel.ExecutionService;
import com.kuka.roboticsAPI.controllerModel.StatePortData;
import com.kuka.roboticsAPI.controllerModel.sunrise.ISunriseControllerStateListener;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.OperatorSafety;
import com.kuka.roboticsAPI.deviceModel.Device;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.task.ITaskLogger;

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

@ResumeAfterPauseEvent(delay = 1000, afterRepositioning = true)
public class Test1 extends RoboticsAPIApplication {
	private LBR lbr;
	private ITaskLogger logger;
	private LocationData location;
	private MobileRobot kmr;
	private Controller kuka_Sunrise_Cabinet;
	private RaspberryIOGroup raspberryControll;
	private Move iiwaMove;
	private ExecutorService es = Executors.newCachedThreadPool();
	private ExecutorService es1 = Executors.newCachedThreadPool();
	private ControllListener controllListener;
	private AutomaticResumeManager resumeManager;
	private RaspberryControll kmrManager;
	@Override
	public void initialize() {
		 iiwaMove = new Move(lbr, logger, getApplicationData());
		 kmrManager = new RaspberryControll(kmr, raspberryControll, location, logger, lbr);
		 	
	}

	@Override
	public void run() {

		/*
		 * New
		 */
		
		controllListener = new ControllListener(logger,getApplicationControl(),resumeManager,this.getClass().getCanonicalName());
		
		kuka_Sunrise_Cabinet.addControllerListener(controllListener);

		// kmr.getSafetyState();

		Location pos1 = location.get(12);
		Location pos2 = location.get(13);
		Location pos3 = location.get(14);
		Location pos4 = location.get(15);
        VirtualLineMotionContainer vcm;
//		logger.info("Pos1 = " + pos1.toString());
//		logger.info("Pos2 = " + pos2.toString());
//		logger.info("KMR = " + kmr.getName());
//		try {
//			kmr.lock();
//		} catch (LockException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			// e1.printStackTrace();
//		}
		vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), pos2.getPose()).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
		vcm.awaitFinalized();
		es.execute(iiwaMove);
		//es1.execute(kmrManager);
//		while(true){
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//			}
//		}
//		
		while(true){
			
			
		while (raspberryControll.getAuto()) {
			
			double KMR_vel = getApplicationData().getProcessData("KMR_vel").getValue();
			vcm = kmr.execute(new VirtualLineMotion(pos1, pos2).setVelocity(new XYTheta(KMR_vel, KMR_vel, 0.1)));
			vcm.awaitFinalized();

			vcm = kmr.execute(new VirtualLineMotion(pos2, pos3).setVelocity(new XYTheta(KMR_vel, KMR_vel, 0.1)));
			vcm.awaitFinalized();
			
			vcm = kmr.execute(new VirtualLineMotion(pos3, pos4).setVelocity(new XYTheta(KMR_vel, KMR_vel, 0.1)));
			vcm.awaitFinalized();
			
			vcm = kmr.execute(new VirtualLineMotion(pos4, pos1).setVelocity(new XYTheta(KMR_vel, KMR_vel, 0.1)));
			vcm.awaitFinalized();

		}
		
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		}
	}

	@Override
	public void dispose() {
		iiwaMove.bRunning.set(false);
		es.shutdownNow();
		kmr.unlock();
	}
}