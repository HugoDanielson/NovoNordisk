package application;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import HttpServer.HttpEnum;
import HttpServer.HttpServerIiwa;

import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import com.kuka.roboticsAPI.capabilities.interfaces.ILEDStripControlCapability;
import com.kuka.roboticsAPI.capabilities.ledStrip.SegmentColor;
import com.kuka.roboticsAPI.capabilities.ledStrip.userEffects.BlinkingLedStripUserEffect;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.deviceModel.kmp.KmpOmniMove;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.swing.internal.plaf.basic.resources.basic_zh_HK;

public class HttpTest extends RoboticsAPIApplication implements HttpHandler {
	@Inject
	private LBR lbr;

	@Inject
	private KmpOmniMove kmr;
	private HttpServerIiwa httpIiwa;
	public ILEDStripControlCapability kmpLed;
	public BlinkingLedStripUserEffect warningLed;

	private volatile AtomicBoolean bRunning = new AtomicBoolean(false);

	@Override
	public void initialize() {
		bRunning.set(true);
	}

	@Override
	public void run() {
		httpIiwa = new HttpServerIiwa<HttpHandler>(30005, this);

		httpIiwa.HttpServerStart();

		while (bRunning.get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void dispose() {
		httpIiwa.serverStop();
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		String response = "";
		System.out.println("HttpExchange t = " + t.getHttpContext().getPath());

		if (t.getHttpContext().getPath().contentEquals(HttpEnum.eHttpPath.COM1.getValue())) {
			response = "Iiwa will start command1 >>> Yelow";
			kmpLed = kmr.getCapability(ILEDStripControlCapability.class);
			warningLed = new BlinkingLedStripUserEffect(SegmentColor.YELLOW, 100, true);
			kmpLed.showUserEffect(warningLed, 1000);
		}
		if (t.getHttpContext().getPath().contentEquals(HttpEnum.eHttpPath.COM2.getValue())) {
			response = "Iiwa will start command2 >>> Blue";
			kmpLed = kmr.getCapability(ILEDStripControlCapability.class);
			warningLed = new BlinkingLedStripUserEffect(SegmentColor.BLUE, 100, true);
			kmpLed.showUserEffect(warningLed, 1000);
		}
		if (t.getHttpContext().getPath().contentEquals(HttpEnum.eHttpPath.COM3.getValue())) {
			response = "Iiwa will start command3 >>> Red";
			kmpLed = kmr.getCapability(ILEDStripControlCapability.class);
			warningLed = new BlinkingLedStripUserEffect(SegmentColor.RED, 100, true);
			kmpLed.showUserEffect(warningLed, 1000);
		}
		if (t.getHttpContext().getPath().contentEquals(HttpEnum.eHttpPath.COM4.getValue())) {
			response = "Iiwa will start command4 >>> Green";
			kmpLed = kmr.getCapability(ILEDStripControlCapability.class);
			warningLed = new BlinkingLedStripUserEffect(SegmentColor.GREEN, 100, true);
			kmpLed.showUserEffect(warningLed, 1000);
		}
		if (t.getHttpContext().getPath().contentEquals(HttpEnum.eHttpPath.COM5.getValue())) {
			response = "Iiwa will quit program";
			bRunning.set(false);
		}

		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}
}