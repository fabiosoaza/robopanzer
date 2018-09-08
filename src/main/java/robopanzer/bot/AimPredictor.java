package robopanzer.bot;

import java.awt.geom.Point2D;

import robocode.AdvancedRobot;

public class AimPredictor {

	private AdvancedRobot robot;
	private EnemyInfo enemy;
	
	public AimPredictor(AdvancedRobot robot, EnemyInfo enemy) {
		super();
		this.robot = robot;
		this.enemy = enemy;
	}
	
	public void point(BulletPredictor bullet) {
		EnemyPredictor enemyPredictor = new EnemyPredictor(enemy);
		// calculate gun turn to predicted x,y location
		double enemyAngle = RobotUtils.calculateAbsoluteBearing(new Point2D.Double(robot.getX(), robot.getY()),
				enemyPredictor.getPredictedPoint(bullet.getTime()));
		robot.setTurnGunRight(RobotUtils.calculateNormalizedBearing(enemyAngle - robot.getGunHeading()));
	}
	
	
	
}