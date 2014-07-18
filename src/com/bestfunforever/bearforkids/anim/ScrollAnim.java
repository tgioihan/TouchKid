package com.bestfunforever.bearforkids.anim;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.util.modifier.IModifier;

public class ScrollAnim extends ScaleModifier{
	
	private static float duration;
	private IAreaShape entity;

	public ScrollAnim(IAreaShape entity){
		super(duration, 1, 1, 0, 1, new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				
			}
		});
		this.entity = entity;
	}

	public ScrollAnim(float pDuration, float pFromScaleX, float pToScaleX, float pFromScaleY, float pToScaleY,
			IEntityModifierListener pEntityModifierListener) {
		super(pDuration, pFromScaleX, pToScaleX, pFromScaleY, pToScaleY, pEntityModifierListener);
	}
	

}
