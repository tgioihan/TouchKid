package com.bestfunforever.touchkids.Pool;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class SpriteWithBody extends Sprite{
	
	private int velocity;
	private int pID;
	private OnTouchBegin onTouchBegin;
	public void setPID(int pID){
		this.pID = pID;
	}
	
	public void setVelocity(int velocity){
		this.velocity = velocity;
	}

	public SpriteWithBody(float pX, float pY,ITextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pTiledSpriteVertexBufferObject) {
		super(pX, pY,pTiledTextureRegion,pTiledSpriteVertexBufferObject);
	}
	
	public void update(float pSecondsElapsed){
		setY(getY()+velocity*pSecondsElapsed);
	}

	public int getpID() {
		return pID;
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if(pSceneTouchEvent.getAction()==(TouchEvent.ACTION_DOWN)){
			Log.e("", "onAreaTouched");
			if(onTouchBegin!=null){
				onTouchBegin.onTouchBegin();
			}
		}
		return true;
	}
	
	public OnTouchBegin getOnTouchBegin() {
		return onTouchBegin;
	}

	public void setOnTouchBegin(OnTouchBegin onTouchBegin) {
		this.onTouchBegin = onTouchBegin;
	}

	public interface OnTouchBegin{
		public void onTouchBegin();
	}

}
