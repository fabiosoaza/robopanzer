package robopanzer.bot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;
import robopanzer.commons.EnemyInfo;
import robopanzer.commons.RobocodeUtils;

public class Robao extends AdvancedRobot {

	private EnemyInfo enemy = new EnemyInfo();
	private Radar radar = new Radar(this, enemy);
	private Cannon gun = new Cannon(this, enemy);
	private BasicMovement movement = new BasicMovement(this);
	
	private void initalizeColors() {
		setBodyColor(RobocodeUtils.getRandomColor());
		setGunColor(RobocodeUtils.getRandomColor());
		setRadarColor(RobocodeUtils.getRandomColor());
		setBulletColor(RobocodeUtils.getRandomColor());
		setScanColor(RobocodeUtils.getRandomColor());
	}

	public void run() {
		
		
		int counter = 0; 
		
		enemy.reset();
		radar.init();
		gun.init();
				
		setTurnRadarRight(360);
		
		

		while (true) {
			radar.scan();
			movement.move();
			movement.update(this);
			gun.fire();
			// Importante: executa acoes enfileiradas 
			execute();	
			if(counter%10 == 0) {
				initalizeColors();
			}
			counter++;
			
		}
	}


	public void onScannedRobot(ScannedRobotEvent e) {
		radar.onScannedRobot(e);	
	}

	public void onRobotDeath(RobotDeathEvent e) {
		radar.onRobotDeath(e);
	}

	
	@Override
	public void onHitWall(HitWallEvent event) {
		movement.onHitWall(event);
	}
	
	@Override
	public void onHitRobot(HitRobotEvent event) {
		movement.onHitRobot(event);
	}
	

	private static class BasicMovement{
		
		private AdvancedRobot robot;
		
		private int axisXDirection = 1;
		private int axisYDirection = 1;
		
		private Rectangle2D.Double forceField;
		private static int FORCE_FIELD_DISTANCE = 20; 
		
				
		public BasicMovement(AdvancedRobot robot) {
			this.robot = robot;			
		}
		
		public void update(AdvancedRobot robot) {
			this.forceField = new Rectangle2D.Double(robot.getX(), robot.getY(), FORCE_FIELD_DISTANCE, FORCE_FIELD_DISTANCE);
			
			
		}
		
		public void move() {
			//robot.setTurnRight(5 * axisXDirection);
			robot.setAhead(50 * axisYDirection);
		}
				
		public void onHitWall(HitWallEvent event) {
			
				
		}
			
		public void onHitRobot(HitRobotEvent event) {
		//	turnRobot(robot.getHeading());	
		}
		
		private void changeYDirection() {
			axisYDirection *= -1;
		}

		private void changeXDirection() {
			axisXDirection *= -1;
		}
		
		private void turnRobot(double bearing) {
			
			
			if( RobocodeUtils.isHeadingAtBottom(bearing) || RobocodeUtils.isHeadingAtTop(bearing) ) {
				changeXDirection();
				robot.ahead(50 * axisXDirection);
			}
			else if(RobocodeUtils.isHeadingAtLeft(bearing) || RobocodeUtils.isHeadingAtRight(bearing)) {
				changeYDirection();
				robot.turnRight(50 * axisYDirection);
			}
		}
		
		
		
	}
	


}
