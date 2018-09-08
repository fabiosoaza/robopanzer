package robopanzer.examples.common;

import java.awt.geom.Point2D;
import java.util.Objects;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class SimpleEnemy {

	private Point2D.Double coordinates;
	private double bearing;
	private double distance;
	private double energy;
	private double heading;
	private double velocity;
	private String name;

	public SimpleEnemy() {
		reset();
	}

	public void reset() {
		coordinates = new Point2D.Double(0,0);
		bearing = 0;
		distance = 0;
		energy = 0;
		heading = 0;
		velocity = 0;
		name = "";
	}

	public void update(ScannedRobotEvent event) {
		this.bearing = event.getBearing();
		distance = event.getDistance();
		energy = event.getEnergy();
		heading = event.getHeading();
		velocity = event.getVelocity();
		name = event.getName();
	}

	public void update(ScannedRobotEvent event, AdvancedRobot robot) {
		update(event);
		double absBearingDeg = calculateAbsoluteBearing(event, robot);		
		double updatedX = getUpdatedX(event, robot, absBearingDeg);
		double updatedY = getUpdateY(event, robot, absBearingDeg);
		coordinates.setLocation(updatedX, updatedY);		

	}

	/*
	 * Computes the lentgh of the adjacent side of a triangle (which may actually be
	 * negative in some cases), and then offsets it by our robot's Y value.
	 */
	private double getUpdateY(ScannedRobotEvent event, AdvancedRobot robot, double absBearingDeg) {
		return robot.getY() + Math.cos(Math.toRadians(absBearingDeg)) * event.getDistance();
	}

	/*
	 * 
	 * Computes the lentgh of the opposite side of a triangle (which may actually be
	 * negative in some cases), and then offsets it by our robot's X value.
	 */
	private double getUpdatedX(ScannedRobotEvent event, AdvancedRobot robot, double absBearingDeg) {
		return robot.getX() + Math.sin(Math.toRadians(absBearingDeg)) * event.getDistance();
	}

	private double calculateAbsoluteBearing(ScannedRobotEvent event, AdvancedRobot robot) {
		double absBearingDeg = (robot.getHeading() + event.getBearing());
		return absBearingDeg < 0 ? absBearingDeg + 360 : absBearingDeg;
	}

	public double getFutureX(long when) {
		return getX() + Math.sin(Math.toRadians(getHeading())) * getVelocity() * when;
	}

	public double getFutureY(long when) {
		return getY() + Math.cos(Math.toRadians(getHeading())) * getVelocity() * when;

	}

	public boolean equals(Object obj) {
		if (obj instanceof SimpleEnemy) {
			return Objects.equals(name, ((SimpleEnemy) obj).getName());
		}
		return false;
	}

	public int hashCode() {
		return Objects.hash(name);
	}

	public boolean none() {
		return "".equals(name);
	}

	public double getX() {
		return coordinates.getX();
	}

	public double getY() {
		return coordinates.getY();
	}

	public double getBearing() {
		return bearing;
	}

	public double getDistance() {
		return distance;
	}

	public double getEnergy() {
		return energy;
	}

	public double getHeading() {
		return heading;
	}

	public double getVelocity() {
		return velocity;
	}

	public String getName() {
		return name;
	}

}
