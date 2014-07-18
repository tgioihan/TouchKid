package com.bestfunforever.touchkids;

public class HighScore {

	private int id;
	private String name;
	private int score;
	private int level;
	private long createdTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public HighScore(int id, String name, int score, int level, long createTime) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.level = level;
		this.createdTime = createTime;
	}

	public HighScore(String name, int score, int level, long createTime) {
		super();
		this.name = name;
		this.score = score;
		this.level = level;
		this.createdTime = createTime;
	}

}
