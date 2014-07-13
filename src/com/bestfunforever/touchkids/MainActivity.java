package com.bestfunforever.touchkids;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.list.SmartList;
import org.andengine.util.adt.pool.MultiPool;

import android.util.DisplayMetrics;
import android.util.Log;

import com.bestfunforever.touchkids.Pool.GameObjectGenerate;
import com.bestfunforever.touchkids.Pool.SpriteWithBody;
import com.bestfunforever.touchkids.Pool.SpriteWithBody.OnTouchBegin;
import com.bestfunforever.touchkids.game.Game;

public class MainActivity extends SimpleBaseGameActivity implements IUpdateHandler{

	// ===========================================================
	// Constants
	// ===========================================================
	private int CAMERA_WIDTH = 320;
	public int CAMERA_HEIGHT = 480;
	
	
	// ===========================================================
	// Fields
	// ===========================================================
	private Scene mScene;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mBear1TextureRegion;
	private MultiPool<SpriteWithBody> mPool;
	private Game mGame;
	
	private SpriteGroup spriteGroup;
	
	private SmartList<SpriteWithBody> mGameObject = new SmartList<SpriteWithBody>();
	private TiledTextureRegion mBear2TextureRegion;
	private TiledTextureRegion mBear3TextureRegion;
	private TiledTextureRegion mBear4TextureRegion;
	private TiledTextureRegion mBear5TextureRegion;
	private BitmapTextureAtlas mBgBitmapTextureAtlas;
	private TextureRegion mBgTextureRegion;
	
	private float ratio;
	
	
	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		ratio = RatioUtils.calculatorRatioScreen(this, true);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);

	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),640, 960, TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBgBitmapTextureAtlas, this, "bg1.png", 0, 0); // 64x32
		this.mBgBitmapTextureAtlas.load();

		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),512, 512, TextureOptions.BILINEAR);
		this.mBear1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bear1.png", 0, 0,1,1); // 64x32
		this.mBear2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bear2.png", 0, 44,1,1); // 64x32
		this.mBear3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bear3.png", 0, 88,1,1); // 64x32
		this.mBear4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bear4.png", 0, 132,1,1); // 64x32
		this.mBear5TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "bear5.png", 0, 176,1,1); // 64x32
		this.mBitmapTextureAtlas.load();
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,mBgTextureRegion.getWidth()*ratio,mBgTextureRegion.getHeight()*ratio, mBgTextureRegion, getVertexBufferObjectManager())));
//		this.mScene.setOnSceneTouchListener(this);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mEngine.registerUpdateHandler(this);
		this.spriteGroup = new SpriteGroup( this.mBitmapTextureAtlas, 4 * 20,this.getVertexBufferObjectManager());
		mScene.attachChild(spriteGroup);
		createPool();
		
		mGame = new Game(1);
		
		start();
		return mScene;
	}
	
	Random mRandom = new Random();

	private void start() {
		mGame.start();
	}

	protected void addGameObject() {
		int pID = mRandom.nextInt(4);
		final SpriteWithBody sprite = mPool.obtainPoolItem(pID);
		sprite.setPID(pID);
		sprite.setPosition((CAMERA_WIDTH - 32) * mRandom.nextFloat(), - 2.5f*mGame.getLevel()*sprite.getHeight());
		sprite.setVelocity(mGame.getVelocity());
		sprite.setVisible(true);
		sprite.setOnTouchBegin(new OnTouchBegin() {
			
			@Override
			public void onTouchBegin() {
				removeGameObject(sprite);
			}
		});
		Log.d("", "onTimePassed pId "+pID +" "+sprite.getX()+" "+sprite.getY());
		mScene.registerTouchArea(sprite);
		spriteGroup.attachChild(sprite);
		mGameObject.add(sprite);
	}

	private void createPool() {
		mPool = new MultiPool<SpriteWithBody>();
		mPool.registerPool(0, new GameObjectGenerate(mBear1TextureRegion,ratio, getVertexBufferObjectManager()));
		mPool.registerPool(1, new GameObjectGenerate(mBear2TextureRegion,ratio, getVertexBufferObjectManager()));
		mPool.registerPool(2, new GameObjectGenerate(mBear3TextureRegion,ratio, getVertexBufferObjectManager()));
		mPool.registerPool(3, new GameObjectGenerate(mBear4TextureRegion,ratio, getVertexBufferObjectManager()));
	}
	
	private float countGenerateTime = 0;

	@Override
	public void onUpdate(float pSecondsElapsed) {
		for (SpriteWithBody spriteWithBody : mGameObject) {
			if(spriteWithBody.getY()>CAMERA_HEIGHT){
				removeGameObject(spriteWithBody);
				continue;
			}else
			spriteWithBody.update(pSecondsElapsed);
		}
		countGenerateTime+= pSecondsElapsed;
		if(mGame.isStart() && countGenerateTime>mGame.getGenerateTime()){
			addGameObject();
			countGenerateTime = 0;
		}
	}
	
	RunnableHandler mHandler = new RunnableHandler();

	private void removeGameObject(final SpriteWithBody spriteWithBody) {
		runOnUpdateThread(new Runnable() {
			
			@Override
			public void run() {
				spriteGroup.detachChild(spriteWithBody);
				spriteWithBody.setY(-2.5f*spriteWithBody.getHeight());
				spriteWithBody.setVisible(false);
				mGameObject.remove(spriteWithBody);
				mPool.recyclePoolItem(spriteWithBody.getpID(), spriteWithBody);
				mScene.unregisterTouchArea(spriteWithBody);
			}
		});
	}

	@Override
	public void reset() {
		
	}
}
