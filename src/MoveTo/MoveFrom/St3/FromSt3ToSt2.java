package MoveTo.MoveFrom.St3;

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

public class FromSt3ToSt2 {
	@Inject
	private LocationData location;
	@Inject
	private MobileRobot kmr;
	public FromSt3ToSt2(){
		
	}
public void move(XYTheta xYTheta){
	
	
	List<Location> moves = new ArrayList();
	
	moves.add(location.get(GlobalParam.LCHECKPOINT3));
	moves.add(location.get(GlobalParam.LCHECKPOINT2));
	moves.add(location.get(GlobalParam.ST2));
	
	
	
	VirtualLineMotionContainer vcm;
	
	for (int i = 0; i < moves.size(); i++) {
		vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), moves.get(i).getPose()).setVelocity(xYTheta));
		vcm.awaitFinished();
	}
	
	
	
	
}
	
}