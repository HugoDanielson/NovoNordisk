package com.kuka.Tool;

import javax.inject.Singleton;

import com.kuka.roboticsAPI.geometricModel.Tool;

@Singleton
public class Gripper extends Tool{

	public Gripper(String name) {
		super(name);
		
	}


}
