package com.bestfunforever.dialog;

import org.andengine.entity.IEntity;

public interface IDialog {
	public void onOpen();
	public void onClose();
	
	public interface IClick{
		public void onClick(Dialog dialog,IEntity view);
	}
	
}
