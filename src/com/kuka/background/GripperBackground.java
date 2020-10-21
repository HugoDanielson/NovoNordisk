package com.kuka.background;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kuka.generated.ioAccess.EtherCatIOGroup;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.persistenceModel.processDataModel.IProcessData;

public class GripperBackground extends RoboticsAPICyclicBackgroundTask {
	@Inject
	private Controller kUKA_Sunrise_Cabinet_1;
	@Inject
	private EtherCatIOGroup Ios;
	@Inject
	@Named("bButtonPushed")
	IProcessData bButtonPushed;

	private volatile AtomicBoolean bRunning = new AtomicBoolean(false);

	@Override
	public void initialize() {
		initializeCyclic(0, 1, TimeUnit.DAYS, CycleBehavior.BestEffort);
		bRunning.set(true);
	}

	@Override
	public void runCyclic() {
		boolean lbOK;
		while (bRunning.get()) {

			lbOK = Ios.getButtonPushed();
			lbOK = !(Boolean) bButtonPushed.getValue();

			if (lbOK) {		
				Ios.setExtendPin(true);
				waitMs(500);
				if (!Ios.getClampedSensor()) {
					bButtonPushed.setValue(true);
					Ios.setClampedLight(true);
				} else {
					Ios.setClampedLight(false);
					bButtonPushed.setValue(false);
					Ios.setExtendPin(false);
				}
				while (Ios.getButtonPushed()) {
					waitMs(50);
				}
				waitMs(100);
			}

			
			lbOK = Ios.getButtonPushed();
			lbOK = (Boolean) bButtonPushed.getValue();

			if (lbOK) {
				Ios.setExtendPin(false);
				waitMs(500);
				if (Ios.getClampedSensor()) {
					bButtonPushed.setValue(false);
					Ios.setClampedLight(false);
				} else {
					Ios.setClampedLight(true);
					bButtonPushed.setValue(true);
					Ios.setExtendPin(false);
				}

			}
			while (Ios.getButtonPushed()) {
				waitMs(50);
			}
			waitMs(100);
		}
	}

	@Override
	public void dispose() {
		bRunning.set(false);
	}

	private void waitMs(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {

		}
	}
}