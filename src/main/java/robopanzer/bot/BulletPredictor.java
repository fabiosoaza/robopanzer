package robopanzer.bot;

public class BulletPredictor {

	private double power;
	private double speed;
	private long time;
	private double targetDistance;

	public BulletPredictor(double targetDistance) {
		this.targetDistance = targetDistance;
		this.power = RobotUtils.calculateFirePower(targetDistance);
		this.speed = RobotUtils.calculateBulletSpeed(this.power);
		this.time = RobotUtils.calculateTime(targetDistance, this.speed);
	}

	public double getPower() {
		return power;
	}

	public double getSpeed() {
		return speed;
	}

	public long getTime() {
		return time;
	}

	public double getTargetDistance() {
		return targetDistance;
	}

}
