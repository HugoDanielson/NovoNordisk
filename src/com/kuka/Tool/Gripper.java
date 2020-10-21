package com.kuka.Tool;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.generated.ioAccess.EtherCatIOGroup;
import com.kuka.roboticsAPI.geometricModel.Tool;

@Singleton
public class Gripper extends Tool {

	@Inject
	public EtherCatIOGroup Ios;

	public Gripper(String name) {
		super(name);

	}

	public boolean extendPin() {
		Ios.setExtendPin(true);
		waitMs(500);

		if (Ios.getClampedSensor()) {
			Ios.setClampedLight(true);
			return true;
		} else {
			Ios.setClampedLight(false);
			Ios.setExtendPin(false);
			return false;
		}

	}

	public boolean retractPin() {

		Ios.setExtendPin(false);
		waitMs(500);
		if (!Ios.getClampedSensor()) {
			Ios.setClampedLight(true);
			return false;
		} else {
			Ios.setClampedLight(false);
			return true;
		}

	}

	public boolean isExtended() {
		return !Ios.getClampedSensor();
	}

	public boolean isRetracted() {
		return Ios.getClampedSensor();
	}

	private void waitMs(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {

		}
	}

}
