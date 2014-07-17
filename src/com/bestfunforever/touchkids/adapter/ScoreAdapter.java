package com.bestfunforever.touchkids.adapter;

import java.util.ArrayList;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.bestfunforever.andengine.uikit.listview.SimpleAdapter;
import com.bestfunforever.touchkids.HighScore;
import com.bestfunforever.touchkids.Entity.ItemView;

public class ScoreAdapter extends SimpleAdapter{
	
	private ArrayList<HighScore> highScores ;
	private float width;
	private float height;
	private Font font;
	private VertexBufferObjectManager pVertexBufferObjectManager;
	
	

	public ScoreAdapter(ArrayList<HighScore> highScores, float width, float height, Font font,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super();
		this.highScores = highScores;
		this.width = width;
		this.height = height;
		this.font = font;
		this.pVertexBufferObjectManager = pVertexBufferObjectManager;
	}

	@Override
	public int getCount() {
		return highScores.size();
	}

	@Override
	public IAreaShape getView(int pos, IAreaShape view) {
		// TODO Auto-generated method stub
		if(view == null){
			view = new ItemView( font, width, height, pVertexBufferObjectManager);
		}
		((ItemView)view).setHighScore(highScores.get(pos));
		return view;
	}

	@Override
	public float getChildWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public float getChildHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 1;
	}

}
