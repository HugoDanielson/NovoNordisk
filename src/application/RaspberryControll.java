package application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kuka.generated.ioAccess.RaspberryIOGroup;
import com.kuka.nav.Location;
import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.line.VirtualLineMotion;
import com.kuka.nav.line.VirtualLineMotionContainer;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.resource.locking.LockException;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.task.ITaskLogger;

public class RaspberryControll implements Runnable {
	private MobileRobot kmr;
	private RaspberryIOGroup raspberryControll;
	private LocationData location;
	private ITaskLogger logger;
	private LBR lbr;
	private Thread kmrAuto;
	private ExecutorService es = Executors.newCachedThreadPool();

	public RaspberryControll(MobileRobot kmr, RaspberryIOGroup raspberryControll, LocationData location, ITaskLogger logger, LBR lbr) {
		this.kmr = kmr;
		this.raspberryControll = raspberryControll;
		this.location = location;
		this.logger = logger;
		this.lbr = lbr;

	}

	@Override
	public void run() {

		Location pos1 = location.get(11);
		Location pos2 = location.get(10);
		VirtualLineMotionContainer vcm;
		try {
			kmr.lock();
		} catch (LockException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}
		while (true) {

			if (raspberryControll.getAuto()) {
				if (kmrAuto == null) {
					kmrAuto = new Thread(new kmrMoveAuto(logger, location, kmr,raspberryControll));
					es.execute(kmrAuto);
				}
			}
			if (raspberryControll.getMove_Pos1() || raspberryControll.getMove_Pos1()) {
				if (kmrAuto == null) {
					if (raspberryControll.getMove_Pos1()) {
						vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), pos1.getPose()).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
						vcm.awaitFinalized();
						raspberryControll.setKMR_Pos1(true);
						raspberryControll.setKMR_Pos2(false);
						

					} else if (raspberryControll.getMove_Pos2()) {
						vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), pos2.getPose()).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
						vcm.awaitFinalized();
						raspberryControll.setKMR_Pos1(false);
						raspberryControll.setKMR_Pos2(true);
					}
				} else if (kmrAuto != null) {
					kmr.cancelAll();
					es.shutdownNow();
					kmrAuto = null;
				}
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

	}

}
