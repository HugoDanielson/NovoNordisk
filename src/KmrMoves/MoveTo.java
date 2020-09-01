package KmrMoves;

import javax.inject.Inject;

public class MoveTo {
	@Inject
	private FromChargerToLoad1 fromChargerToLoad1;

	public MoveTo() {

	}

	public void run(Integer stationNr) {
		switch (stationNr) {
		case 1:
			fromChargerToLoad1.move();
			break;
		case 2:

			break;
		case 3:

			break;

		default:
			break;
		}
	}

}
