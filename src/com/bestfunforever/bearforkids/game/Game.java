package com.bestfunforever.bearforkids.game;

public class Game {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private int velocity;

	// time in milliseconds
	private float generateTime;
	private int level;
	private boolean isStart;
	private int score;
	private int objectDeathTotal;
	private int objectDeathCount;
	private int scoreToPassLv;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Game() {
		this(1);
	}

	public Game(int level) {
		velocity = 30;
		generateTime = 1f;
		objectDeathTotal = 10;
		scoreToPassLv = 20;
		setLevel(level);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	private void setLevel(int level) {
		this.level = level;
		velocity += 10 * level;
		objectDeathCount = 0;
		scoreToPassLv += 15 * level;
		objectDeathTotal += 4;
		if (generateTime - 0.03f * level > 0.5f) {
			generateTime -= 0.03f * level;
		}

	}

	public int getLevel() {
		return level;
	}

	public boolean isStart() {
		return isStart;
	}

	public void start() {
		setState(true);
	}

	public void end() {
		setState(false);
	}

	private void setState(boolean isStart) {
		this.isStart = isStart;
	}

	public int getVelocity() {
		return velocity;
	}

	public float getGenerateTime() {
		return generateTime;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public boolean incressObjectDeathCount(int in) {
		objectDeathCount += in;
		if (objectDeathCount == objectDeathTotal) {
			return true;
		}
		return false;
	}

	public int getPercentObjectDeath() {
		return objectDeathCount * 100 / objectDeathTotal;
	}

	public boolean incressScore(int in) {
		score += in;
		if (score == scoreToPassLv) {
			setLevel(level + 1);
			return true;
		}
		return false;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
