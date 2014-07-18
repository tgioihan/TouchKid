package com.bestfunforever.bearforkids.Entity;

import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.bestfunforever.bearforkids.HighScore;

public class ItemView extends Rectangle {

	private Font mFont;
	private Text mNameText;
	private Text scoreText;
	private Text levelText;

	public ItemView(Font font, float pWidth, float pHeight,float ratio,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(0, 0, pWidth, pHeight, pVertexBufferObjectManager);
		setColor(Color.TRANSPARENT);
		this.mFont = font;

		mNameText = new Text(0, 0, mFont, "", 8, getVertexBufferObjectManager());
		mNameText.setColor(Color.RED);

		scoreText = new Text(200*ratio, 0, mFont, "", 4, getVertexBufferObjectManager());
		scoreText.setColor(Color.RED);

		levelText = new Text(300*ratio, 0, mFont, "", 4, getVertexBufferObjectManager());
		levelText.setColor(Color.RED);

		mNameText.setY(pHeight / 2 - mNameText.getHeight() / 2);
		scoreText.setY(pHeight / 2 - scoreText.getHeight() / 2);
		levelText.setY(pHeight / 2 - levelText.getHeight() / 2);

		attachChild(levelText);
		attachChild(scoreText);
		attachChild(mNameText);
		
		Line line = new Line(0, pHeight-2, pWidth,pHeight , getVertexBufferObjectManager());
		line.setColor(Color.GREEN);
		attachChild(line);

	}

	public void setHighScore(HighScore highScore) {
		if(highScore.getId() == -1){
			mNameText.setText("---");
			scoreText.setText( "-");
			levelText.setText("-");
		}else{
			mNameText.setText(highScore.getName());
			scoreText.setText(highScore.getScore() + "");
			levelText.setText(highScore.getLevel() + "");
		}
		
	}

}
