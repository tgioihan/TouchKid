package com.bestfunforever.touchkids.Pool;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;


public class GameObjectGenerate extends GenericPool<SpriteWithBody>{
	
	private ITextureRegion mTiledTextureRegion;
	private VertexBufferObjectManager vertexBufferObject;
	private float ratio;

	public GameObjectGenerate(ITextureRegion mTiledTextureRegion,float ratio, VertexBufferObjectManager vertexBufferObject) {
		super();
		this.mTiledTextureRegion = mTiledTextureRegion;
		this.vertexBufferObject = vertexBufferObject;
		this.ratio = ratio;
	}

	@Override
	protected SpriteWithBody onAllocatePoolItem() {
		SpriteWithBody animatedSprite = new SpriteWithBody(0, 0, mTiledTextureRegion, vertexBufferObject);
		animatedSprite.setScale(ratio*1.5f);
//		animatedSprite.animate(200);
		return animatedSprite;
	}

}
