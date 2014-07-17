package com.bestfunforever.menu;

import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.input.touch.TouchEvent;

import com.bestfunforever.andengine.uikit.entity.IClick;

import android.util.Log;
import android.view.MotionEvent;

public abstract class BaseMenu extends BaseHUD implements IOnSceneTouchListener {

	protected ArrayList<IMenuItem> mMenuItems = new ArrayList<IMenuItem>();
	protected IMenuItem mSelectedMenuItem;
	protected IOnMenuItemClickListener mOnMenuItemClickListener;

	public IOnMenuItemClickListener getOnMenuItemClickListener() {
		return mOnMenuItemClickListener;
	}

	public void setOnMenuItemClickListener(IOnMenuItemClickListener mOnMenuItemClickListener) {
		this.mOnMenuItemClickListener = mOnMenuItemClickListener;
	}

	public void addMenuItem(MenuItem menuItem) {
		mMenuItems.add(menuItem);
		menuItem.setClickListenner(new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				hide();
				if (mOnMenuItemClickListener != null) {
					mOnMenuItemClickListener.onMenuItemClicked(BaseMenu.this, (IMenuItem) view);
				}
			}
		});
		invalidate();
	}

	public void addMenuItem(ArrayList<MenuItem> menuItems) {
		mMenuItems.addAll(menuItems);
		for (MenuItem iMenuItem : menuItems) {
			iMenuItem.setClickListenner(new IClick() {

				@Override
				public void onCLick(IAreaShape view) {
					hide();
					if (mOnMenuItemClickListener != null) {
						mOnMenuItemClickListener.onMenuItemClicked(BaseMenu.this, (IMenuItem) view);
					}
				}
			});
		}

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

	public IMenuListenner getMenuListenner() {
		return menuListenner;
	}

	public void setMenuListenner(IMenuListenner menuListenner) {
		this.menuListenner = menuListenner;
	}

	public static interface IOnMenuItemClickListener {
		public boolean onMenuItemClicked(final HUD pMenuScene, final IMenuItem pMenuItem);
	}

	protected IMenuListenner menuListenner;

	public interface IMenuListenner {
		public void onShow();

		public void onHide();
	}

}
