package KmrMoves;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import GlobalParameters.GlobalParam;

import com.kuka.nav.Location;
import com.kuka.nav.XYTheta;
import com.kuka.nav.data.LocationData;
import com.kuka.nav.line.VirtualLineMotion;
import com.kuka.nav.line.VirtualLineMotionContainer;
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
	
	List<Location> moves = new ArrayList();
	
	moves.add(location.get(GlobalParam.LCHECKPOINT5));
	moves.add(location.get(GlobalParam.LCHECKPOINT4));
	moves.add(location.get(GlobalParam.LCHECKPOINT2));
	moves.add(location.get(GlobalParam.LCHECKPOINT1));
	moves.add(location.get(GlobalParam.ST1));
	
	System.out.println("Location Charger = "+st5);
	System.out.println("KMR battery state = "+kmr.getBatteryState());
	
	VirtualLineMotionContainer vcm;
	
	for (int i = 0; i < moves.size(); i++) {
		vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), moves.get(i).getPose()).setVelocity(new XYTheta(0.1, 0.1, 0.1)));
		vcm.awaitFinished();
	}
	
	
	
	
}
	
}
