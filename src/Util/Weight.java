package Util;

import javax.inject.Inject;

import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.AbstractFrame;
import com.kuka.task.ITaskLogger;

public class Weight {
	@Inject
	private LBR lbr;
	double N_to_KG = 9.81;
	double weight = 0.0;

	public Weight() {

	}

	public double getWeightZ(AbstractFrame frame) {
		weight = Math.abs(lbr.getExternalForceTorque(frame).getForce().invert().getZ() / N_to_KG);
		System.out.println("*********** Weight of product =" + weight+"*********** ");
		return weight;
	}
}
