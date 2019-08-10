package robopanzer.commons;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.Rules;
import robocode.util.Utils;

public final class RobocodeUtils {

	private static final int FIRE_FACTOR = 500;

	private RobocodeUtils() {
	}

	public static double bulletVelocity(double power) {
		return 20 - 3 * power;
	}

	public static Point2D getPointTo(Point2D sourceLocation, double angle, double length) {
		return new Point2D.Double(sourceLocation.getX() + Math.sin(angle) * length,
				sourceLocation.getY() + Math.cos(angle) * length);
	}

	public static double absoluteBearing(Point2D source, Point2D target) {
		return Math.atan2(target.getX() - source.getX(), target.getY() - source.getY());
	}

	public static int sign(double v) {
		return v < 0 ? -1 : 1;
	}

	public static int minMax(int v, int min, int max) {
		return Math.max(min, Math.min(max, v));
	}

	public static Color getRandomColor() {
		return new Color(getRandomRgbColor(), getRandomRgbColor(), getRandomRgbColor());
	}

	private static int getRandomRgbColor() {
		return new Random().ints(0, 256).findAny().getAsInt();
	}

	public static boolean isHeadingAtTop(double position) {
		return position >= 270;
	}

	public static boolean isHeadingAtLeft(double position) {
		return position >= 180 && position < 270;
	}

	public static boolean isHeadingAtBottom(double position) {
		return position >= 90 && position < 180;
	}

	public static boolean isHeadingAtRight(double position) {
		return position >= 0 && position < 90;
	}

	public static double calculateAbsoluteBearing(Point2D.Double source, Point2D.Double target) {
		return calculateAbsoluteBearing(source.getX(), source.getY(), target.getX(), target.getY());
	}

	public static double calculateAbsoluteBearing(double sourceX, double sourceY, double targetX, double targetY) {
		double xo = targetX - sourceX;
		double yo = targetY - sourceY;
		double hyp = Point2D.distance(sourceX, sourceY, targetX, targetY);
		double arcSin = Math.toDegrees(Math.asin(xo / hyp));
		double bearing = 0;

		if (isLowerLeft(xo, yo)) {
			bearing = arcSin;
		} else if (isLowerRight(xo, yo)) {
			bearing = 360 + arcSin;
		} else if (isUpperLeft(xo, yo)) {
			bearing = 180 - arcSin;
		} else if (isUpperRight(xo, yo)) {
			bearing = 180 - arcSin;
		}

		return bearing;
	}

	private static boolean isUpperRight(double xo, double yo) {
		return xo < 0 && yo < 0;
	}

	private static boolean isUpperLeft(double xo, double yo) {
		return xo > 0 && yo < 0;
	}

	private static boolean isLowerRight(double xo, double yo) {
		return xo < 0 && yo > 0;
	}

	private static boolean isLowerLeft(double xo, double yo) {
		return xo > 0 && yo > 0;
	}

	public static double calculateNormalizedBearing(double angle) {
		while (angle > 180)
			angle -= 360;
		while (angle < -180)
			angle += 360;
		return angle;
	}

	/**
	 * Calcula a potencia do tiro baseada na distancia
	 * 
	 * @param distance
	 * @return
	 */
	public static double calculateFirePower(double distance) {
		return Math.min(FIRE_FACTOR / distance, Rules.MAX_BULLET_POWER);
	}

	/**
	 * Calcula a velocidade do tiro de acordo a potencia
	 * 
	 * @param firePower
	 * @return
	 */
	public static double calculateBulletSpeed(double firePower) {
		return 20 - firePower * 3;
	}

	/**
	 * Calcula o tempo que o tiro alcançara o alvo tempo = distancia / velocidade
	 * tiro
	 * 
	 * @param distance
	 * @param bulletSpeed
	 * @return
	 */
	public static long calculateTime(double distance, double bulletSpeed) {
		return (long) (distance / bulletSpeed);
	}
	

	
	
	
}
