package robopanzer.examples.bot;

import robocode.AdvancedRobot;
import robocode.Rules;
import robocode.ScannedRobotEvent;

public class SimpleWalker extends AdvancedRobot {
	public void run() {
		while (true) {
					
			turnRadarLeft(360);

			double position = getHeading();

			adjust(position);

		}

	}

	private void adjust(double position) {
		if (isAtRight(position)) {
			adjustRight();

			if (canGoRight()) {
				goRight();
			}
		} else if (isAtDown(position)) {
			adjustDown();

			if (canGoDown()) {
				goDown();
			}
		}
		 else if (isAtLeft(position)) {
				adjustLeft();

				if (canGoLeft()) {
					goLeft();
				}
			}
		else if (isAtUp(position)) {
			adjustUp();

			if (canGoUp()) {
				goUp();
			}
		}
	}

	private boolean isAtUp(double position) {
		return position >= 270;
	}

	private boolean isAtLeft(double position) {
		return position >= 180 && position < 270;
	}

	private boolean isAtDown(double position) {
		return position >= 90 && position < 180;
	}

	private boolean isAtRight(double position) {
		return position >= 0 && position < 90;
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		 fire(Rules.MAX_BULLET_POWER);

	}

	private void adjustUp() {
		turnRight(360 - getHeading());
	}

	private void adjustLeft() {
		turnRight(270 - getHeading());
	}

	private void adjustDown() {
		turnRight(180 - getHeading());
	}

	private void adjustRight() {
		turnRight(90 - getHeading());
	}

	private boolean canGoLeft() {
		return getX() - getWidth() > 0;
	}

	private boolean canGoDown() {
		return getY() - getHeight() > 0;
	}

	private boolean canGoRight() {
		return getLimitedWidth() - getX() > 0;
	}
	
	private boolean canGoUp() {
		return getLimitedHeight() - getY() > 0;
	}
	
	private void goDown() {
		ahead(getY() - getHeight());
	}
	
	private void goUp() {
		ahead(getLimitedHeight() - getY());
	}

	private void goRight() {
		ahead(getLimitedWidth() - getX());
	}
	
	private void goLeft() {
		ahead(getX() - getWidth());
	}


	private double getLimitedWidth() {
		return getBattleFieldWidth() - (getWidth());
	}

	private double getLimitedHeight() {
		return getBattleFieldHeight() - (getHeight());
	}


}
