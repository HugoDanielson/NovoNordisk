package KmrMoves;

import javax.inject.Inject;

import GlobalParameters.GlobalParam;
import MoveTo.MoveFrom.St1.FromSt1ToSt2;
import MoveTo.MoveFrom.St1.FromSt1ToSt3;
import MoveTo.MoveFrom.St1.FromSt1ToSt4;
import MoveTo.MoveFrom.St1.FromSt1ToSt5;
import MoveTo.MoveFrom.St2.FromSt2ToSt1;
import MoveTo.MoveFrom.St2.FromSt2ToSt3;
import MoveTo.MoveFrom.St2.FromSt2ToSt4;
import MoveTo.MoveFrom.St2.FromSt2ToSt5;
import MoveTo.MoveFrom.St3.FromSt3ToSt1;
import MoveTo.MoveFrom.St3.FromSt3ToSt2;
import MoveTo.MoveFrom.St3.FromSt3ToSt4;
import MoveTo.MoveFrom.St3.FromSt3ToSt5;
import MoveTo.MoveFrom.St4.FromSt4ToSt1;
import MoveTo.MoveFrom.St4.FromSt4ToSt2;
import MoveTo.MoveFrom.St4.FromSt4ToSt3;
import MoveTo.MoveFrom.St4.FromSt4ToSt5;
import MoveTo.MoveFrom.St5.FromSt5ToSt1;
import MoveTo.MoveFrom.St5.FromSt5ToSt2;
import MoveTo.MoveFrom.St5.FromSt5ToSt3;
import MoveTo.MoveFrom.St5.FromSt5ToSt4;

import com.kuka.nav.XYTheta;

public class MoveTo {

	@Inject
	private FromSt1ToSt2 fromSt1ToSt2;
	@Inject
	private FromSt1ToSt3 fromSt1ToSt3;
	@Inject
	private FromSt1ToSt4 fromSt1ToSt4;
	@Inject
	private FromSt1ToSt5 fromSt1ToSt5;

	@Inject
	private FromSt2ToSt1 fromSt2ToSt1;
	@Inject
	private FromSt2ToSt3 fromSt2ToSt3;
	@Inject
	private FromSt2ToSt4 fromSt2ToSt4;
	@Inject
	private FromSt2ToSt5 fromSt2ToSt5;
	
	@Inject
	private FromSt3ToSt1 fromSt3ToSt1;
	@Inject
	private FromSt3ToSt2 fromSt3ToSt2;
	@Inject
	private FromSt3ToSt4 fromSt3ToSt4;
	@Inject
	private FromSt3ToSt5 fromSt3ToSt5;
	
	@Inject
	private FromSt4ToSt1 fromSt4ToSt1;
	@Inject
	private FromSt4ToSt2 fromSt4ToSt2;
	@Inject
	private FromSt4ToSt3 fromSt4ToSt3;
	@Inject
	private FromSt4ToSt5 fromSt4ToSt5;
	
	@Inject
	private FromSt5ToSt1 fromSt5ToSt1;
	@Inject
	private FromSt5ToSt2 fromSt5ToSt2;
	@Inject
	private FromSt5ToSt3 fromSt5ToSt3;
	@Inject
	private FromSt5ToSt4 fromSt5ToSt4;

	XYTheta xYtheta;

	public MoveTo() {

	}

	public void run(GlobalParam.eMoveFrom eFrom, GlobalParam.eMoveTo eTo, XYTheta xYTheta) {
		if (xYTheta == null) {
			xYTheta = new XYTheta(0.1, 0.1, 0.1);
		}

		switch (eFrom) {
		case St1:
			// Start of St 2
			switch (eTo) {
			case St2:
				fromSt1ToSt2.move(xYTheta);
				break;
			case St3:
				fromSt1ToSt3.move(xYTheta);
				break;
			case St4:
				fromSt1ToSt4.move(xYTheta);
				break;
			case St5:
				fromSt1ToSt5.move(xYTheta);
				break;
			default:
				break;
			}
			break;
		// End of St 2
		case St2:
			// Start of St 2
			switch (eTo) {
			case St1:
				fromSt2ToSt1.move(xYTheta);
				break;
			case St3:
				fromSt2ToSt3.move(xYTheta);
				break;
			case St4:
				fromSt2ToSt4.move(xYTheta);
				break;
			case St5:
				fromSt2ToSt5.move(xYTheta);
				break;
			default:
				break;
			}
			break;
		// End of St 2
		case St3:
			// Start of St 3
			switch (eTo) {
			case St1:
				fromSt3ToSt1.move(xYTheta);
				break;
			case St2:
				fromSt3ToSt2.move(xYTheta);
				break;
			case St4:
				fromSt3ToSt4.move(xYTheta);
				break;
			case St5:
				fromSt3ToSt5.move(xYTheta);
				break;
			default:
				break;
			}
			break;
		// End of St 3
		case St4:
			// Start of St 4
			switch (eTo) {
			case St1:
				fromSt4ToSt1.move(xYTheta);
				break;
			case St2:
				fromSt4ToSt2.move(xYTheta);
				break;
			case St3:
				fromSt4ToSt3.move(xYTheta);
				break;
			case St5:
				fromSt4ToSt5.move(xYTheta);
				break;
			default:
				break;
			}
			break;
		// End of St 4
		case St5:
			// Start of St 3
			switch (eTo) {
			case St1:
				fromSt5ToSt1.move(xYTheta);
				break;
			case St2:
				fromSt5ToSt2.move(xYTheta);
				break;
			case St3:
				fromSt5ToSt3.move(xYTheta);
				break;
			case St4:
				fromSt5ToSt4.move(xYTheta);
				break;

			default:
				break;
			// End of St 5
			}
		default:
			break;
		}
	}

}
