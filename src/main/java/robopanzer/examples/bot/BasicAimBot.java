package robopanzer.examples.bot;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robopanzer.commons.RobocodeUtils;

public class BasicAimBot extends AdvancedRobot {
	public void run() {
		while (true) {
			turnRadarLeft(360); 
		}

	}
	
	
	public void onScannedRobot(ScannedRobotEvent e) {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		setTurnGunRight(getHeading() - getGunHeading() + RobocodeUtils.calculateNormalizedBearing(e.getBearing()));
	//	fire( RobotUtils.calculateFirePower(e.getDistance()) );
	}


}
