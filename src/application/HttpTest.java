package application;


import javax.inject.Inject;

import HttpServer.HttpCommand_1;
import HttpServer.HttpServerIiwa;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.sun.swing.internal.plaf.basic.resources.basic_zh_HK;

/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a 
 * {@link RoboticsAPITask#run()} method, which will be called successively in 
 * the application lifecycle. The application will terminate automatically after 
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the 
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an 
 * exception is thrown during initialization or run. 
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the 
 * {@link RoboticsAPITask#dispose()} method.</b> 
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */
public class HttpTest extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kmr;
private HttpServerIiwa httpIiwa ;
private HttpCommand_1 httpHandler1;
	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		httpIiwa = new HttpServerIiwa();
		
		
		httpIiwa.HttpServerStart();
		httpHandler1 = httpIiwa.getHandlerCom1();
		
		
		while (!httpHandler1.getStart()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		System.out.println("Iiwa execute command 1");
		
		
		
		
		
	}
	@Override
	public void dispose(){
		httpIiwa.serverStop();
	}
}