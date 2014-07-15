package com.bestfunforever.dialog;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;

public class Dialog extends HUD{

	public Dialog(Camera camera){
		setCamera(camera);
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
	}
	
	public void onLoadResource(){
		
	}
	
	public void onDesTroy(){
		
	}
	
	public void onCreateDialog(){
		
	}
}
