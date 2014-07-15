package com.bestfunforever.dialog;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

public abstract class Dialog extends HUD {
	
	private IEntityModifier mOpenAnimation;
	private IEntityModifier mCloseAnimation;
	private IDialog mDialogListenner;
	
	public void setOpenAnim(IEntityModifier mOpenAnimation){
		this.mOpenAnimation = mOpenAnimation;
	}
	
	public void setCloseAnim(IEntityModifier mCloseAnimation){
		this.mCloseAnimation = mOpenAnimation;
	}
	
	protected SimpleBaseGameActivity context;
	protected float ratio;

	public Dialog(SimpleBaseGameActivity context,Camera camera,float ratio){
		setCamera(camera);
		setBackgroundEnabled(true);
		setBackground(new Background(18, 18, 18, 0.5f));
		this.context = context;
		this.ratio = ratio;
		onLoadResource();
		onCreateDialog();
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
	}
	
	public void show(Scene scene){
		scene.setChildScene(this);
		if(mOpenAnimation !=null){
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
			registerEntityModifier(openAnim );
		}else{
			onOpen();
		}
	}
	
	public void dismiss(){
		if(mCloseAnimation!=null){
			final IEntityModifier closeAnim = mCloseAnimation.deepCopy();
			closeAnim.setAutoUnregisterWhenFinished(true);
			closeAnim.addModifierListener(new IModifierListener<IEntity>() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					onClose();
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					final IEntity parrent = getParent();
					if(parrent!=null){
						((Scene)parrent).clearChildScene();;
					}
					onDesTroy();
				}
			});
			registerEntityModifier(closeAnim);
		}else{
			final IEntity parrent = getParent();
			if(parrent!=null){
				((Scene)parrent).clearChildScene();;
			}
			onDesTroy();
		}
	}
	
	public void onOpen() {
		if(mDialogListenner!=null){
			mDialogListenner.onOpen();
		}
	}
	
	public void onClose() {
		if(mDialogListenner!=null){
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
