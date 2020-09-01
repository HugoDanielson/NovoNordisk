package MoveTo.MoveFrom.St4;

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

public class FromSt4ToSt5 {
	@Inject
	private LocationData location;
	@Inject
	private MobileRobot kmr;
	public FromSt4ToSt5(){
		
	}
public void move(XYTheta xYTheta){
	
	
	List<Location> moves = new ArrayList();
	
	moves.add(location.get(GlobalParam.LCHECKPOINT4));
	moves.add(location.get(GlobalParam.LCHECKPOINT5));
	moves.add(location.get(GlobalParam.ST5));
	
	
	
	VirtualLineMotionContainer vcm;
	
	for (int i = 0; i < moves.size(); i++) {
		vcm = kmr.execute(new VirtualLineMotion(kmr.getPose(), moves.get(i).getPose()).setVelocity(xYTheta));
		vcm.awaitFinished();
	}
	
	
	
	
}
	
}
