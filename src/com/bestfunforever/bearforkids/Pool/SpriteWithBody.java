package com.bestfunforever.bearforkids.Pool;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ParallelModifier;

import android.util.Log;

public class SpriteWithBody extends Sprite{
	
	private float ratio;
	
	public enum State{
		ACTIVE,DEATH
	}
	
	private State state = State.ACTIVE;
	
	private int velocity;
	private int pID;
	private OnTouchBegin onTouchBegin;
	public void setPID(int pID){
		this.pID = pID;
	}
	
	public void setVelocity(int velocity){
		this.velocity = velocity;
	}

	public SpriteWithBody(float pX, float pY,float ratio,ITextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pTiledSpriteVertexBufferObject) {
		super(pX, pY,pTiledTextureRegion,pTiledSpriteVertexBufferObject);
		this.ratio = ratio;
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

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	
	private IEntityModifierListener mListenner ;

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
		if(state == State.DEATH){
			clearEntityModifiers();
			registerEntityModifier(new ParallelEntityModifier(
					new MoveYModifier(0.6f, getY(), getY()-30*ratio,mListenner),
					new AlphaModifier(0.6f, 1, 0)
					));
		}else{
			setAlpha(1);
		}
	}

	/**
	 * @return the mListenner
	 */
	public IEntityModifierListener getDeathListenner() {
		return mListenner;
	}

	/**
	 * @param mListenner the mListenner to set
	 */
	public void setDeathListenner(IEntityModifierListener mListenner) {
		this.mListenner = mListenner;
	}

	public interface OnTouchBegin{
		public void onTouchBegin();
	}

}
