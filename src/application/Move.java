package application;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.lin;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.ptp;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import com.kuka.roboticsAPI.applicationModel.IApplicationData;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.motionModel.LIN;
import com.kuka.roboticsAPI.motionModel.PTP;
import com.kuka.task.ITaskLogger;

public class Move implements Runnable {
	private LBR lbr;
	private ITaskLogger logger;
	public AtomicBoolean bRunning = new AtomicBoolean(true);
	private IApplicationData data;
	private double vel = 0.1;
	private double acc = 0.1;
	
	public Move(LBR lbr,ITaskLogger logger,IApplicationData data){
		this.lbr = lbr;
		this.logger = logger;
		this.data = data;
	}
	@Override
	public void run() {
		lbr.move(lin(data.getFrame("/P1")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
		while(bRunning.get()){
			lbr.move(lin(data.getFrame("/P2")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P3")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P4")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P5")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P6")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P5")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P4")).setJointVelocityRel(vel).setJointAccelerationRel(acc));		
			lbr.move(lin(data.getFrame("/P3")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P2")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
			lbr.move(lin(data.getFrame("/P1")).setJointVelocityRel(vel).setJointAccelerationRel(acc));
		}
		
	}

}
