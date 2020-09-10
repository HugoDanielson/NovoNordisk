package application;


import java.util.Collection;

import javax.inject.Inject;

import com.kuka.nav.MapZone;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.data.MapZoneData;
import com.kuka.nav.robot.MobileRobot;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;


public class HugoTest extends RoboticsAPIApplication {
	@Inject
	private LBR lBR_iiwa_14_R820_1;
	@Inject
	private MobileRobot kmp;
	@Inject
	private LocationData locations;
	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;
	
@Inject
private MapZoneData mapData;
	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		// your application execution starts here
		Collection<MapZone> data = mapData.getAll();
		
		for (MapZone mapZone : data) {
			System.out.println("MapData = "+mapZone.getName());
			System.out.println("Robot is inside =" +mapZone.isInside(kmp));
			System.out.println("Zone shape ="+mapZone.getShape());
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}