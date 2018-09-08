package robopanzer.examples.bot;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robopanzer.bot.RobotUtils;

public class BasicAimBot extends AdvancedRobot {
	public void run() {
		while (true) {
			turnRadarLeft(360); 
		}

	}
	
	
	public void onScannedRobot(ScannedRobotEvent e) {
		setAdjustGunForRobotTurn(true);
		setTurnGunRight(getHeading() - getGunHeading() + RobotUtils.calculateNormalizedBearing(e.getBearing()));
		fire( RobotUtils.calculateFirePower(e.getDistance()) );
	}


}
