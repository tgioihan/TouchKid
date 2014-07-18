package com.bestfunforever.bearforkids;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.bestfunforever.dialog.BaseDialog;

public class ScoreDialog extends BaseDialog {

	private int score;
	private int level;
	private Text scoreText;
	private Text lvText;
	static final float margin = 20;

	public ScoreDialog(SimpleBaseGameActivity context, Camera camera, float ratio, int score, int level) {
		super(context, camera, ratio);
		this.score = score;
		this.level = level;
		setTitle(context.getString(R.string.lose));
		setInfo(score, level);
	}
	
	private void setInfo(int score, int level ){
		scoreText.setText(context.getString(R.string.score) + " " + score);
		lvText.setText(context.getString(R.string.level) + " " + level);
	}

	@Override
	protected float initContent(Rectangle mContentRect) {
		scoreText = new Text(0, margin * ratio, mFont, context.getString(R.string.score) + " " + score,12,
				context.getVertexBufferObjectManager());
		lvText = new Text(0, margin * ratio + scoreText.getY() + scoreText.getHeight(), mFont,
				context.getString(R.string.level) + " " + level,12, context.getVertexBufferObjectManager());
		scoreText.setX(mContentRect.getWidth() / 2 - scoreText.getWidth() / 2);
		lvText.setX(mContentRect.getWidth() / 2 - lvText.getWidth() / 2);
		mContentRect.attachChild(scoreText);
		mContentRect.attachChild(lvText);

		return lvText.getY() + lvText.getHeight() + margin;
	}

}
