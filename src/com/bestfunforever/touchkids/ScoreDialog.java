package com.bestfunforever.touchkids;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.bestfunforever.dialog.BaseDialog;

public class ScoreDialog extends BaseDialog {

	private int score;
	private int level;
	static final float margin = 20;

	public ScoreDialog(SimpleBaseGameActivity context, Camera camera, float ratio, int score, int level) {
		super(context, camera, ratio);
		this.score = score;
		this.level = level;
		setTitle(context.getString(R.string.lose));
	}

	@Override
	protected float initContent(Rectangle mContentRect) {
		Text scoreText = new Text(0, margin * ratio, mFont, context.getString(R.string.score) + " " + score,
				context.getVertexBufferObjectManager());
		Text lvText = new Text(0, margin * ratio + scoreText.getY() + scoreText.getHeight(), mFont,
				context.getString(R.string.level) + " " + level, context.getVertexBufferObjectManager());
		scoreText.setX(mContentRect.getWidth() / 2 - scoreText.getWidth() / 2);
		lvText.setX(mContentRect.getWidth() / 2 - lvText.getWidth() / 2);
		mContentRect.attachChild(scoreText);
		mContentRect.attachChild(lvText);

		return lvText.getY() + lvText.getHeight() + margin;
	}

}