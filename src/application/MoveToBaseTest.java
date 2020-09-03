package application;


import javax.inject.Inject;
import javax.inject.Named;

import Util.Weight;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.geometricModel.Workpiece;
import com.kuka.roboticsAPI.geometricModel.World;
import com.kuka.roboticsAPI.geometricModel.math.ITransformation;
import com.kuka.roboticsAPI.geometricModel.math.Transformation;
import com.kuka.roboticsAPI.motionModel.LIN;


public class MoveToBaseTest extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kMR_omniMove_200_CR_1;
	@Inject 
	@Named("Gripper") 
	private Tool Gripper;
	private com.kuka.roboticsAPI.geometricModel.ObjectFrame tcp;
	
	@Inject
	@Named("WP")
	private Workpiece WP;
	
	@Inject
	private double weight;
	String refFrame;

	private LIN pos1;
	private ITransformation offset;
	double N_to_KG = 9.81;

	@Override
	public void initialize() {
		Gripper.attachTo(lbr.getFlange());
	}

	@Override
	public void run() {
//		tcp = Gripper.getFrame("/TCP/AngleOffset/ShiftTCP2");
//		
//		refFrame = "/Station1/BaseShift";
//		offset = Transformation.ofDeg(385, 0, 0, 0, 0, 0);
//		pos1 = lin(getApplicationData().getFrame(refFrame).copyWithRedundancy().transform(getApplicationData().getFrame(refFrame), offset)).setJointVelocityRel(0.1);
//	
//		tcp.move(pos1);
//		
//		refFrame = "/Station1/BaseShift";
//		offset = Transformation.ofDeg(385, 0, 50, 0, 0, 0);
//		pos1 = lin(getApplicationData().getFrame(refFrame).copyWithRedundancy().transform(getApplicationData().getFrame(refFrame), offset)).setJointVelocityRel(0.1);
//	
//		tcp.move(pos1);
//		
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//		//	e.printStackTrace();
//		}
//		WP.getLoadData().setMass(weight.getWeightZ(tcp));
//		
//		WP.attachTo(Gripper.getRootFrame());
//		tcp.move(pos1);
//		
		
		
		weight = Math.abs(lbr.getExternalForceTorque(lbr.getFlange(), World.Current.getRootFrame()).getForce().invert().getZ() / N_to_KG);
		System.out.println("*********** Weight of product world inverse =" + weight+"*********** ");
		
		weight = Math.abs(lbr.getExternalForceTorque(lbr.getFlange()).getForce().invert().getZ() / N_to_KG);
		System.out.println("*********** Weight of product inverse =" + weight+"*********** ");
	
	
		weight = Math.abs(lbr.getExternalForceTorque(lbr.getFlange()).getForce().getZ() / N_to_KG);
		System.out.println("*********** Weight of product not inverse =" + weight+"*********** ");	
		
		
	}
}