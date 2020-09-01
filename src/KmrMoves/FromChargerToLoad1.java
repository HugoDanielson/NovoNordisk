package KmrMoves;

import javax.inject.Inject;

import com.kuka.nav.Location;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.robot.MobileRobot;

public class FromChargerToLoad1 {
	@Inject
	private LocationData location;
	@Inject
	private MobileRobot kmr;
	private Integer LOCID = 5;
	public FromChargerToLoad1(){
		
	}
public void move(){
	Location charger = location.get(LOCID);
	System.out.println("Location Charger = "+charger);
	
}
	
}
