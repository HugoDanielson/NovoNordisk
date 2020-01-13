package application;

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
import com.kuka.task.ITaskLogger;

public class kmrMoveAuto implements Runnable{

	private ITaskLogger logger;
	private LocationData location;
	private MobileRobot kmr;
	private RaspberryIOGroup raspberryControll;
	
	public kmrMoveAuto (ITaskLogger logger,LocationData location,MobileRobot kmr,RaspberryIOGroup raspberryControll){
		this.logger  = logger;
		this.location = location;
		this.kmr = kmr;
		this.raspberryControll = raspberryControll;
	}

	@Override
	public void run() {
		Location pos1 = location.get(11);
		Location pos2 = location.get(10);

		logger.info("Pos1 = " + pos1.toString());
		logger.info("Pos2 = " + pos2.toString());
		logger.info("KMR = " + kmr.getName());
//		try {
//			kmr.lock();
//		} catch (LockException e1) {
//			// TODO Auto-generated catch block
//			//e1.printStackTrace();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			// e1.printStackTrace();
//		}
		VirtualLineMotionContainer vcm;// =kmr.execute(new VirtualLineMotion(pos2, pos1).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
		
		while(Thread.currentThread().isAlive()){
		
		vcm = kmr.execute(new VirtualLineMotion(pos2, pos1).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
		vcm.awaitFinalized();
		raspberryControll.setKMR_Pos1(true);
		raspberryControll.setKMR_Pos2(false);

		vcm = kmr.execute(new VirtualLineMotion(pos1, pos2).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
		vcm.awaitFinalized();
		raspberryControll.setKMR_Pos1(false);
		raspberryControll.setKMR_Pos2(true);
		}
		
	}
}
