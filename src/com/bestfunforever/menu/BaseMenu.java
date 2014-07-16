package com.bestfunforever.menu;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import android.view.MotionEvent;

public abstract class BaseMenu extends BaseHUD implements IOnAreaTouchListener, IOnSceneTouchListener {

	protected ArrayList<IMenuItem> mMenuItems = new ArrayList<IMenuItem>();
	protected IMenuItem mSelectedMenuItem;
	protected IOnMenuItemClickListener mOnMenuItemClickListener;

	public void addMenuItem(IMenuItem menuItem) {
		mMenuItems.add(menuItem);
		invalidate();
	}
	
	public void addMenuItem(ArrayList<IMenuItem> menuItems){
		mMenuItems.addAll(menuItems);
		invalidate();
	}

	public abstract void invalidate();

	public enum STAGE {
		SHOW, HIDE, ANIMATE
	};

	protected STAGE stage;

	public STAGE getStage() {
		return stage;
	}

	public void setStage(STAGE stage) {
		this.stage = stage;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final ITouchArea pTouchArea,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		final IMenuItem menuItem = ((IMenuItem) pTouchArea);
		switch (pSceneTouchEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.e("", "action ACTION_DOWN");
			menuItem.onPressState();
		case MotionEvent.ACTION_MOVE:
			Log.e("", "action ACTION_MOVE");
			if (this.mSelectedMenuItem != null && this.mSelectedMenuItem != menuItem) {
				this.mSelectedMenuItem.onNormalState();
			}
			this.mSelectedMenuItem = menuItem;
			this.mSelectedMenuItem.onPressState();
			break;
		case MotionEvent.ACTION_UP:
			Log.e("", "action ACTION_UP");
			if (this.mOnMenuItemClickListener != null && stage == STAGE.SHOW) {
				final boolean handled = this.mOnMenuItemClickListener.onMenuItemClicked(this, menuItem,
						pTouchAreaLocalX, pTouchAreaLocalY);
				menuItem.onNormalState();
				this.mSelectedMenuItem = null;
				hide();
				return handled;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.e("", "action cancel");
			menuItem.onNormalState();
			this.mSelectedMenuItem = null;
			break;
		}
		return true;
	}

	protected void hide() {

	}

	protected void show() {

	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		if (this.mSelectedMenuItem != null) {
			this.mSelectedMenuItem.onNormalState();
			this.mSelectedMenuItem = null;
		} else if (stage == STAGE.SHOW) {
			hide();
		}
		return false;
	}

	public static interface IOnMenuItemClickListener {
		public boolean onMenuItemClicked(final HUD pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX,
				final float pMenuItemLocalY);
	}

}
