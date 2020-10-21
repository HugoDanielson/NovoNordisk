package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>EtherCat</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * ./.
 */
@Singleton
public class EtherCatIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'EtherCat'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'EtherCat'
	 */
	@Inject
	public EtherCatIOGroup(Controller controller)
	{
		super(controller, "EtherCat");

		addInput("ClampedSensor", IOTypes.BOOLEAN, 1);
		addInput("ButtonPushed", IOTypes.BOOLEAN, 1);
		addDigitalOutput("ClampedLight", IOTypes.BOOLEAN, 1);
		addDigitalOutput("ExtendPin", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital input '<i>ClampedSensor</i>'</b>.<br>
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
	 * @return current value of the digital input 'ClampedSensor'
	 */
	public boolean getClampedSensor()
	{
		return getBooleanIOValue("ClampedSensor", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>ButtonPushed</i>'</b>.<br>
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
	 * @return current value of the digital input 'ButtonPushed'
	 */
	public boolean getButtonPushed()
	{
		return getBooleanIOValue("ButtonPushed", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>ClampedLight</i>'</b>.<br>
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
	 * @return current value of the digital output 'ClampedLight'
	 */
	public boolean getClampedLight()
	{
		return getBooleanIOValue("ClampedLight", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>ClampedLight</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'ClampedLight'
	 */
	public void setClampedLight(java.lang.Boolean value)
	{
		setDigitalOutput("ClampedLight", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>ExtendPin</i>'</b>.<br>
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
	 * @return current value of the digital output 'ExtendPin'
	 */
	public boolean getExtendPin()
	{
		return getBooleanIOValue("ExtendPin", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>ExtendPin</i>'</b>.<br>
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
	 *            the value, which has to be written to the digital output 'ExtendPin'
	 */
	public void setExtendPin(java.lang.Boolean value)
	{
		setDigitalOutput("ExtendPin", value);
	}

}
