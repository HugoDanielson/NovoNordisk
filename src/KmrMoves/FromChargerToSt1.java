package KmrMoves;

import javax.inject.Inject;

import GlobalParameters.GlobalParam;

import com.kuka.nav.Location;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.robot.MobileRobot;

public class FromChargerToSt1 {
	@Inject
	private LocationData location;
	@Inject
	private MobileRobot kmr;
	public FromChargerToSt1(){
		
	}
public void move(){
	Location st5 = location.get(GlobalParam.ST5);
	
	System.out.println("Location Charger = "+st5);
	System.out.println("KMR battery state = "+kmr.getBatteryState());
	
	
	
	
}
	
}
