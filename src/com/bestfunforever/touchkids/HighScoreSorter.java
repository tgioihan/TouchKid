package com.bestfunforever.touchkids;

import java.util.Comparator;

public class HighScoreSorter implements Comparator<HighScore> {

	@Override
	public int compare(HighScore lhs, HighScore rhs) {
		if (lhs.getScore() > rhs.getScore()) {
			return -1;
		} else if (lhs.getScore() < rhs.getScore()) {
			return +1;
		} else {
			if (lhs.getCreatedTime() > rhs.getCreatedTime()) {
				return -1;
			} else if (lhs.getCreatedTime() < rhs.getCreatedTime()) {
				return 1;
			} else {
				return 0;
			}
		}

	}

}
