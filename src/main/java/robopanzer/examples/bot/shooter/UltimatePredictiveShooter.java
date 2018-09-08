package robopanzer.examples.bot.shooter;

import robocode.AdvancedRobot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robopanzer.bot.RobotUtils;
import robopanzer.examples.common.SimpleEnemy;

public class UltimatePredictiveShooter extends AdvancedRobot {

	private SimpleEnemy enemy = new SimpleEnemy();

	public void run() {
		// divorce radar movement from gun movement
		setAdjustRadarForGunTurn(true);
		// divorce gun movement from tank movement
		setAdjustGunForRobotTurn(true);
		// we have no enemy yet
		enemy.reset();
		// initial scan
		setTurnRadarRight(360);

		while (true) {
			// rotate the radar
			setTurnRadarRight(360);
			// sit & spin
			setTurnRight(5);
			setAhead(20);
			// doGun does predictive targeting
			doGun();
			// carry out all the queued up actions
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {

		// track if we have no enemy, the one we found is significantly
		// closer, or we scanned the one we've been tracking.
		if ( enemy.none() || e.getDistance() < enemy.getDistance() - 70 ||
				e.getName().equals(enemy.getName())) {

			// track him using the NEW update method
			enemy.update(e, this);
		}
	}

	public void onRobotDeath(RobotDeathEvent e) {
		// see if the robot we were tracking died
		if (e.getName().equals(enemy.getName())) {
			enemy.reset();
		}
	}   

	void doGun() {

		// don't shoot if I've got no enemy
		if (enemy.none())
			return;

		// calculate firepower based on distance
		double firePower = Math.min(500 / enemy.getDistance(), 3);
		// calculate speed of bullet
		double bulletSpeed = 20 - firePower * 3;
		// distance = rate * time, solved for time
		long time = (long)(enemy.getDistance() / bulletSpeed);

		// calculate gun turn to predicted x,y location
		double futureX = enemy.getFutureX(time);
		double futureY = enemy.getFutureY(time);
		double absDeg = absoluteBearing(getX(), getY(), futureX, futureY);
		// non-predictive firing can be done like this:
		//double absDeg = absoluteBearing(getX(), getY(), enemy.getX(), enemy.getY());

		// turn the gun to the predicted x,y location
		setTurnGunRight(normalizeBearing(absDeg - getGunHeading()));

		// if the gun is cool and we're pointed in the right direction, shoot!
		if (getGunHeat() == 0 && Math.abs(getGunTurnRemaining()) < 10) {
			setFire(firePower);
		}
	}

	// computes the absolute bearing between two points
	double absoluteBearing(double x1, double y1, double x2, double y2) {
		return RobotUtils.calculateAbsoluteBearing(x1, y1, x2, y2);	
	}

	// normalizes a bearing to between +180 and -180
	double normalizeBearing(double angle) {
		return RobotUtils.calculateNormalizedBearing(angle);
	}
}