package application;


import javax.inject.Inject;
import javax.inject.Named;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.Workpiece;
import com.kuka.roboticsAPI.geometricModel.math.ITransformation;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.LIN;


public class MoveToBaseTest extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;
	@Inject 
	@Named("TCP") 
	private Tool TCP;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp;
	@Inject
	@Named("WP")
	private Workpiece WP;
	
	String refFrame;

	private LIN pos1;
	private ITransformation offset;
	@Override
	public void initialize() {
		TCP.attachTo(lbr.getFlange());
	}

	@Override
	public void run() {
		tcp = TCP.getFrame("/TCP/AngleOffset/ShiftTCP2");
		
		refFrame = "/Station1/BaseShift";
		offset = Transformation.ofDeg(385, 0, 0, 0, 0, 0);
		pos1 = lin(getApplicationData().getFrame(refFrame).copyWithRedundancy().transform(getApplicationData().getFrame(refFrame), offset)).setJointVelocityRel(0.3);
	
		tcp.move(pos1);
		
	}
}