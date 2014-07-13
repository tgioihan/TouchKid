package com.bestfunforever.touchkids.game;

public class Game {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private int velocity;
	
	//time in milliseconds
	private float generateTime;
	private int level;
	private boolean isStart;

	// ===========================================================
	// Constructors
	// ===========================================================
	public Game(){
		this(1);
	}
	
	public Game(int level){
		velocity = 30;
		generateTime = 1f;
		setLevel(level);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================
	private void setLevel(int level) {
		this.level = level;
		velocity = velocity*level;
		generateTime = generateTime/level;
	}
	
	public int getLevel(){
		return level;
	}
	
	public boolean isStart(){
		return isStart;
	}
	
	public void start(){
		setState(true);
	}
	
	public void end(){
		setState(false);
	}
	
	private void setState(boolean isStart){
		this.isStart = isStart;
	}
	public int getVelocity(){
		return velocity;
	}
	
	public float getGenerateTime(){
		return generateTime;
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
