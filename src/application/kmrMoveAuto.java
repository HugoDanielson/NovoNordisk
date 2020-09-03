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
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.World;
import com.kuka.task.ITaskLogger;

public class kmrMoveAuto implements Runnable{

	private ITaskLogger logger;
	private LocationData location;
	private MobileRobot kmr;
	private RaspberryIOGroup raspberryControll;
	@Inject
	private LBR lbr;
	double N_to_KG = 9.81;
	double weight = 0.0;
	
	public kmrMoveAuto (ITaskLogger logger,LocationData location,MobileRobot kmr,RaspberryIOGroup raspberryControll){
		this.logger  = logger;
		this.location = location;
		this.kmr = kmr;
		this.raspberryControll = raspberryControll;
	}

	@Override
	public void run() {
		weight = Math.abs(lbr.getExternalForceTorque(lbr.getFlange(), World.Current.getRootFrame()).getForce().invert().getX() / N_to_KG);
		System.out.println("*********** Weight of product world inverse =" + weight+"*********** ");
		
		weight = Math.abs(lbr.getExternalForceTorque(lbr.getFlange()).getForce().invert().getX() / N_to_KG);
		System.out.println("*********** Weight of product inverse =" + weight+"*********** ");
	
	
		weight = Math.abs(lbr.getExternalForceTorque(lbr.getFlange()).getForce().getX() / N_to_KG);
		System.out.println("*********** Weight of product not inverse =" + weight+"*********** ");
	}
}
