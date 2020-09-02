package application;

import com.kuka.nav.Location;
import com.kuka.nav.Pose;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.robot.MobileRobot;

public final class KmrLocator {
	private static double ThresholdDistance = 0.1;
	private LocationData locationData;
	private MobileRobot kmr;
	
	public KmrLocator(MobileRobot kmr, LocationData locationData){
		this.kmr = kmr;
		this.locationData= locationData;
	}
	
	public int GetNearestPostionId()
	{
		int nId = -1;
		
		double minDist = 0;
		Pose curLoc = kmr.getPose();
		for(Location loc: locationData.getAll()){
			double dist = getDistance(curLoc, loc.getPose());
			if(dist<= ThresholdDistance && dist < minDist){
				nId = loc.getId();
				minDist = dist;
			}
		}
		
		return nId;
	}
	
	private double getDistance(Pose p1, Pose p2)
	{
		double xDist = p1.getX()-p2.getX();
		double yDist = p1.getY()-p2.getY();
		
		return Math.sqrt(Math.pow(xDist, 2)+Math.pow(yDist,2));
	}
}

