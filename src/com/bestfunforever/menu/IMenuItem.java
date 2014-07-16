package com.bestfunforever.menu;

import org.andengine.entity.scene.ITouchArea;

import com.bestfunforever.andengine.uikit.entity.ISelector;

public interface IMenuItem extends ISelector,ITouchArea{
	public int getID();
}
