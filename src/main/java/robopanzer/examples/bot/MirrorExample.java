package robopanzer.examples.bot;

import java.awt.geom.Point2D;

import robocode.AdvancedRobot;
import robocode.Rules;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;
import robopanzer.examples.common.Intercept;

public class MirrorExample extends AdvancedRobot {
 
    static final int UP=0, RIGHT=1, DOWN=2, LEFT=3;
	
	public void run() {
        while (true) {        
        	turnRadarLeft(160);            	         
       	 
        }
    }
 
	
	
	private void goTo(double x, double y) {
	    /* Transform our coordinates into a vector */
	    x -= getX();
	    y -= getY();
	 
	    /* Calculate the angle to the target position */
	    double angleToTarget = Math.atan2(x, y);
	 
	    /* Calculate the turn required get there */
	    double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());
	 
	    /* 
	     * The Java Hypot method is a quick way of getting the length
	     * of a vector. Which in this case is also the distance between
	     * our robot and the target location.
	     */
	    double distance = Math.hypot(x, y);
	 
	    /* This is a simple method of performing set front as back */
	    double turnAngle = Math.atan(Math.tan(targetAngle));
	    setTurnRightRadians(turnAngle);
	    if(targetAngle == turnAngle) {
	        setAhead(distance);
	    } else {
	        setBack(distance);
	    }
	}
	
	
    public void onScannedRobot(ScannedRobotEvent e) {
    	
    	double enemyX = getX() + e.getDistance() * Math.sin(getHeadingRadians() + e.getBearingRadians());
        double enemyY = getY() + e.getDistance() * Math.cos(getHeadingRadians() + e.getBearingRadians());
    	
      
        
    	//verical
        goTo(getBattleFieldWidth() - enemyX, enemyY);
        
    	attack(e);
    	
      
                
    }


	private void attack(ScannedRobotEvent e) {
		double angle = Math.toRadians((getHeading() + e.getBearing()) % 360);

    	 // Calculate the coordinates of the robot
    	 int scannedX = (int)(getX() + Math.sin(angle) * e.getDistance());
    	 int scannedY = (int)(getY() + Math.cos(angle) * e.getDistance());
    	
    	 
    	 Point2D.Double enemyCord = new Point2D.Double(scannedX, scannedY);
    	
    	
    	 
    	 circularAttack(e, enemyCord);
	}
    
    private void circularAttack(ScannedRobotEvent e, Point2D.Double enemyCord) {
    	
    	
    	double radiusSquare = 0;
    	radiusSquare = this.getX() * Math.PI * Math.sqrt(2);
    	
    	Intercept intercept = new Intercept(radiusSquare);
    	intercept.calculate
    	(
    	  getX(),
    	  getY(),
    	  enemyCord.getX(),
    	  enemyCord.getY(),
    	  e.getHeading(),
    	  e.getVelocity(),
    	  Rules.MIN_BULLET_POWER,
    	  0 // Angular velocity
    	);
    	 
    	// Helper function that converts any angle into  
    	// an angle between +180 and -180 degrees.
    	double turnAngle = Utils.normalRelativeAngleDegrees(intercept.bulletHeading_deg - this.getGunHeading());
    	 
    	// Move gun to target angle
    	this.turnGunRight(turnAngle);
    	 
    	if (Math.abs(turnAngle) <= intercept.angleThreshold) {
    	  // Ensure that the gun is pointing at the correct angle
    	  if (
    	    (intercept.impactPoint.x > 0) &&
    	 (intercept.impactPoint.x < getBattleFieldWidth()) &&
    	 (intercept.impactPoint.y > 0) &&
    	 (intercept.impactPoint.y < getBattleFieldHeight())
    	) {
    	    // Ensure that the predicted impact point is within 
    	    // the battlefield
    	    fire(Math.min(400 / e.getDistance(), Rules.MAX_BULLET_POWER));
    	  }
    	}
    	
    	
    }
    

    
}

