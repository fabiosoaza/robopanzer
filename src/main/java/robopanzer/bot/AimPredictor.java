package robopanzer.bot;

import java.awt.geom.Point2D;

import robocode.AdvancedRobot;
import robopanzer.commons.EnemyInfo;
import robopanzer.commons.RobocodeUtils;

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
		// calcula a quantidade de graus para mover a mira
		double enemyAngle = RobocodeUtils.calculateAbsoluteBearing(new Point2D.Double(robot.getX(), robot.getY()),
				enemyPredictor.getPredictedPoint(bullet.getTime()));
		robot.setTurnGunRight(RobocodeUtils.calculateNormalizedBearing(enemyAngle - robot.getGunHeading()));
	}
	
	
	
}
