package com.bestfunforever.touchkids;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.bestfunforever.andengine.uikit.listview.ListView;
import com.bestfunforever.dialog.BaseDialog;
import com.bestfunforever.dialog.Dialog;
import com.bestfunforever.touchkids.adapter.ScoreAdapter;
import com.bestfunforever.touchkids.database.DatabaseHelper;

public class HighScoreDialog extends BaseDialog {
	private float mHeight;
	private DatabaseHelper databaseHelper;

	public HighScoreDialog(SimpleBaseGameActivity context, Camera camera, float ratio) {
		super(context, camera, ratio);
		mHeight = 300 * ratio;
		setTitle(context.getString(R.string.highscore));
		setLeftButton(context.getString(R.string.oK), new com.bestfunforever.dialog.IDialog.IClick() {

			@Override
			public void onClick(Dialog dialog, IEntity view) {
				dialog.dismiss();
			}
		});
		databaseHelper = new DatabaseHelper(context);
		if(databaseHelper.getHighScore().size()==0){
			for (int i = 0; i < 30; i++) {
				HighScore highScore = new HighScore(i, "tuan", 10+i, 3, 213123);
				databaseHelper.insertHighScore(highScore);
			}
		}
	}

	@Override
	protected float initContent(Rectangle mContentRect) {
		// TODO Auto-generated method stub
		// float height = height*ratio;
		ListView mListView = new ListView(context, 0, 0, mContentRect.getWidth(), mHeight,
				context.getVertexBufferObjectManager());
		ArrayList<HighScore> highScores = databaseHelper.getHighScore();
		ScoreAdapter adapter = new ScoreAdapter(highScores, mContentRect.getWidth(), mHeight / 5, mFont,
				context.getVertexBufferObjectManager());
		mListView.setAdapter(adapter);
		mContentRect.attachChild(mListView);
		registerTouchArea(mListView);

		return mHeight;
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		databaseHelper.close();
		super.onClose();
	}

}