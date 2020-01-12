package application;

import javax.inject.Inject;

import com.kuka.nav.Location;
import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.line.VirtualLineMotion;
import com.kuka.nav.line.VirtualLineMotionContainer;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
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
public class Test1 extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;
@Inject
private ITaskLogger logger;
//	@Inject
//	private KmpOmniMove kmr;
	@Inject
	private LocationData location;
	private MobileRobot kmr;
	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		
			Location pos1 = location.get(7);
			Location pos2 = location.get(8);
			
			
			logger.info("Pos1 = "+pos1.toString());
			logger.info("Pos2 = "+pos2.toString());
			logger.info("KMR = "+kmr.getName());
			VirtualLineMotionContainer vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), pos1.getPose()).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
			vcm.awaitFinalized();
			while(true){
			
				vcm = kmr.execute(new VirtualLineMotion(pos1,pos2).setVelocity(new XYTheta(0.15, 0.15, 0.1)));
				vcm.awaitFinalized();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				vcm = kmr.execute(new VirtualLineMotion(pos2,pos1).setVelocity(new XYTheta(0.15, 0.15, 0.1)));
				vcm.awaitFinalized();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			
			
		}
	}
}