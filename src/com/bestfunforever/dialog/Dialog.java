package com.bestfunforever.dialog;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.util.Log;

public abstract class Dialog extends HUD {

	private IEntityModifier mOpenAnimation;
	private IEntityModifier mCloseAnimation;
	private IDialog mDialogListenner;

	public void setOpenAnim(IEntityModifier mOpenAnimation) {
		this.mOpenAnimation = mOpenAnimation;
	}

	public void setCloseAnim(IEntityModifier mCloseAnimation) {
		this.mCloseAnimation = mCloseAnimation;
	}

	protected SimpleBaseGameActivity context;
	protected float ratio;
	protected Sprite bgSprite;

	public Dialog(SimpleBaseGameActivity context, Camera camera, float ratio) {
		setCamera(camera);
		Rectangle bg = new Rectangle(0, 0, camera.getWidth(), camera.getHeight(),
				context.getVertexBufferObjectManager());
		bg.setColor(18f / 255, 18f / 255, 18f / 255, 0.5f);
		attachChild(bg);
		// setBackgroundEnabled(true);
		// setBackground(new Background(18f/255, 18f/255, 18f/255, 0.5f));
		this.context = context;
		this.ratio = ratio;
		onLoadResource();
		onCreateDialog();
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		setOnSceneTouchListener(new IOnSceneTouchListener() {
			
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				return true;
			}
		});
	}

	public void show(Scene scene) {
		scene.setChildScene(this);
		if (mOpenAnimation != null) {
			setVisible(false);
			final IEntityModifier openAnim = mOpenAnimation.deepCopy();
			openAnim.setAutoUnregisterWhenFinished(true);
			openAnim.addModifierListener(new IModifierListener<IEntity>() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					setVisible(true);
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					onOpen();
				}
			});
			bgSprite.clearEntityModifiers();
			bgSprite.registerEntityModifier(openAnim);
		} else {
			onOpen();
		}
	}

	public void dismiss() {
		if (mCloseAnimation != null) {
			final IEntityModifier closeAnim = mCloseAnimation.deepCopy();
			closeAnim.setAutoUnregisterWhenFinished(true);
			closeAnim.addModifierListener(new IModifierListener<IEntity>() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					onClose();
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					if (mParentScene != null) {
						(mParentScene).clearChildScene();
					}
					onDesTroy();
				}
			});
			bgSprite.clearEntityModifiers();
			bgSprite.registerEntityModifier(closeAnim);
		} else {
			Log.d("", "dismiss " + mParentScene);
			if (mParentScene != null) {
				(mParentScene).clearChildScene();
			}
			onDesTroy();
		}
	}

	public void onOpen() {
		if (mDialogListenner != null) {
			mDialogListenner.onOpen();
		}
	}

	public void onClose() {
		if (mDialogListenner != null) {
			mDialogListenner.onClose();
		}
	}

	public abstract void onLoadResource();

	public abstract void onDesTroy();

	public abstract void onCreateDialog();

	public IDialog getDialogListenner() {
		return mDialogListenner;
	}

	public void setDialogListenner(IDialog mDialogListenner) {
		this.mDialogListenner = mDialogListenner;
	}

}
