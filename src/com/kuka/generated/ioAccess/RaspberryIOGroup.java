package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>Raspberry</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * ./.
 */
@Singleton
public class RaspberryIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'Raspberry'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'Raspberry'
	 */
	@Inject
	public RaspberryIOGroup(Controller controller)
	{
		super(controller, "Raspberry");

		addInput("App_Start", IOTypes.BOOLEAN, 1);
		addInput("App_Resume", IOTypes.BOOLEAN, 1);
		addInput("App_Restart", IOTypes.BOOLEAN, 1);
		addInput("Move_Pos1", IOTypes.BOOLEAN, 1);
		addInput("Move_Pos2", IOTypes.BOOLEAN, 1);
		addInput("Move_Home", IOTypes.BOOLEAN, 1);
		addInput("Auto", IOTypes.BOOLEAN, 1);
		addDigitalOutput("KMR_Pos1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("KMR_Pos2", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital input '<i>App_Start</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'App_Start'
	 */
	public boolean getApp_Start()
	{
		return getBooleanIOValue("App_Start", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>App_Resume</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'App_Resume'
	 */
	public boolean getApp_Resume()
	{
		return getBooleanIOValue("App_Resume", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>App_Restart</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'App_Restart'
	 */
	public boolean getApp_Restart()
	{
		return getBooleanIOValue("App_Restart", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>Move_Pos1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'Move_Pos1'
	 */
	public boolean getMove_Pos1()
	{
		return getBooleanIOValue("Move_Pos1", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>Move_Pos2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'Move_Pos2'
	 */
	public boolean getMove_Pos2()
	{
		return getBooleanIOValue("Move_Pos2", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>Move_Home</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'Move_Home'
	 */
	public boolean getMove_Home()
	{
		return getBooleanIOValue("Move_Home", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>Auto</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'Auto'
	 */
	public boolean getAuto()
	{
		return getBooleanIOValue("Auto", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>KMR_Pos1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'KMR_Pos1'
	 */
	public boolean getKMR_Pos1()
	{
		return getBooleanIOValue("KMR_Pos1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>KMR_Pos1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'KMR_Pos1'
	 */
	public void setKMR_Pos1(java.lang.Boolean value)
	{
		setDigitalOutput("KMR_Pos1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>KMR_Pos2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'KMR_Pos2'
	 */
	public boolean getKMR_Pos2()
	{
		return getBooleanIOValue("KMR_Pos2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>KMR_Pos2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'KMR_Pos2'
	 */
	public void setKMR_Pos2(java.lang.Boolean value)
	{
		setDigitalOutput("KMR_Pos2", value);
	}

}
