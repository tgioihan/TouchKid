package com.bestfunforever.bearforkids;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.list.SmartList;
import org.andengine.util.adt.pool.MultiPool;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bestfunforever.andengine.uikit.activity.PortraitAdmobGameActivity;
import com.bestfunforever.andengine.uikit.dialog.Dialog;
import com.bestfunforever.andengine.uikit.dialog.IDialog;
import com.bestfunforever.andengine.uikit.entity.CheckBox.ICheckedChange;
import com.bestfunforever.andengine.uikit.entity.ProgessBarColor;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IMenuListenner;
import com.bestfunforever.andengine.uikit.menu.BaseMenu.IOnMenuItemClickListener;
import com.bestfunforever.andengine.uikit.menu.IMenuItem;
import com.bestfunforever.bearforkids.Pool.GameObjectGenerate;
import com.bestfunforever.bearforkids.Pool.SpriteWithBody;
import com.bestfunforever.bearforkids.Pool.SpriteWithBody.OnTouchBegin;
import com.bestfunforever.bearforkids.Pool.SpriteWithBody.State;
import com.bestfunforever.bearforkids.database.DatabaseHelper;
import com.bestfunforever.bearforkids.game.Game;
import com.bestfunforever.dialog.BaseDialog;
import com.bestfunforever.menu.BearForKidsCircleMenu;

public class MainActivity extends PortraitAdmobGameActivity implements IUpdateHandler, IOnMenuItemClickListener,
		IMenuListenner {

	private String[] bgs = new String[] { "bg1.png", "bg2.jpg", "bg3.jpg", "bg4.jpg", "bg5.jpg", "bg6.jpg" };

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
	private TextureRegion mShareFbTextureRegion;
	private BitmapTextureAtlas mBgBitmapTextureAtlas;
	private BitmapTextureAtlas mShareFbBitmapTextureAtlas;
	private TextureRegion mBgTextureRegion;

	private Font mFont;
	private Sound clickSound;
	private Music music;
	private Font bigFont;

	private DatabaseHelper databaseHelper;
	private boolean pause = false;

	private Sound finishSound;

	public static final String NAME_KEY = "name";

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
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		preferences = getSharedPreferences("BearforKid", 0);
		databaseHelper = new DatabaseHelper(getApplicationContext());
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		String bg = bgs[mRandom.nextInt(6)];
		this.mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 640, 960, TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBgBitmapTextureAtlas,
				this, bg, 0, 0); // 64x32
		this.mBgBitmapTextureAtlas.load();
	}

	private void loadResource() {
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		this.mBear1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mBitmapTextureAtlas, this, "bear1.png", 0, 0, 1, 1); // 64x32
		this.mBear2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mBitmapTextureAtlas, this, "bear2.png", 0, 86, 1, 1); // 64x32
		this.mBear3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mBitmapTextureAtlas, this, "bear3.png", 0, 170, 1, 1); // 64x32
		this.mBear4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mBitmapTextureAtlas, this, "bear4.png", 0, 256, 1, 1); // 64x32
		this.mBear5TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				this.mBitmapTextureAtlas, this, "bear5.png", 0, 340, 1, 1); // 64x32
		clearITextureRegion(mBear3TextureRegion);
		this.mBitmapTextureAtlas.load();

		this.mShareFbBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 190, 85, TextureOptions.BILINEAR);
		this.mShareFbTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mShareFbBitmapTextureAtlas, this, "sharefb_btn.png", 0, 0); // 64x32
		this.mShareFbBitmapTextureAtlas.load();

		final ITexture bigfontTexture = new BitmapTextureAtlas(this.getTextureManager(), (int) (512 * ratio),
				(int) (512 * ratio), TextureOptions.BILINEAR);
		this.bigFont = FontFactory.createFromAsset(this.getFontManager(), bigfontTexture, this.getAssets(),
				"font/UVNBanhMi.TTF", 100, true, android.graphics.Color.RED);
		this.bigFont.load();

		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio), Typeface.create(Typeface.DEFAULT, Typeface.BOLD), (int) (32 * ratio));
		this.mFont.load();
	}

	public static void clearITextureRegion(final ITextureRegion mITextureRegion) {
		mITextureRegion.setTextureWidth(mITextureRegion.getWidth() - 1);
		mITextureRegion.setTextureHeight(mITextureRegion.getHeight() - 1);
	}

	private void playFinish() {
		if (finishSound != null && SoundManger.isSoundEnable(preferences)) {
			finishSound.play();
		} else if (finishSound == null && SoundManger.isSoundEnable(preferences)) {
			loadFinishSound();
			finishSound.play();
		}
	}

	private void loadFinishSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			finishSound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"finish.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			clickSound = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(),
					"button_click.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void playCLick() {
		if (clickSound != null && SoundManger.isSoundEnable(preferences)) {
			clickSound.play();
		} else if (clickSound == null && SoundManger.isSoundEnable(preferences)) {
			loadSound();
			clickSound.play();
		}
	}

	private float padding = 20;
	private Text scoreText;
	private Text levelText;
	private ProgessBarColor progress;
	private BearForKidsCircleMenu circleMenu;

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (music != null && music.isPlaying()) {
			music.pause();
		}
	}

	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		if (SoundManger.isMusicEnable(preferences)) {
			playMusic();
		}
	}

	@Override
	protected Scene onCreateScene() {
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));
		this.mEngine.registerUpdateHandler(new FPSLogger());
		this.mEngine.registerUpdateHandler(mTimerHandler);
		return mScene;
	}

	TimerHandler mTimerHandler = new TimerHandler(0.6f, false, new ITimerCallback() {

		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			// TODO Auto-generated method stub
			MainActivity.this.mEngine.unregisterUpdateHandler(mTimerHandler);
			loadResource();
			init();
		}
	});

	public void init() {
		if (SoundManger.isMusicEnable(preferences)) {
			playMusic();
		}
		mGame = new Game(1);
		// this.mScene.setOnSceneTouchListener(this);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mEngine.registerUpdateHandler(this);
		this.spriteGroup = new SpriteGroup(this.mBitmapTextureAtlas, 1000, this.getVertexBufferObjectManager());
		mScene.attachChild(spriteGroup);

		circleMenu = new BearForKidsCircleMenu(this, mCamera, ratio);
		circleMenu.init();
		circleMenu.setOnMenuItemClickListener(this);
		circleMenu.setMenuListenner(this);
		circleMenu.attackScene(mScene);

		padding = padding * ratio;
		scoreText = new Text(padding, padding, mFont, getString(R.string.score) + 1000, getVertexBufferObjectManager());
		levelText = new Text(0, padding, mFont, getString(R.string.level) + 10, getVertexBufferObjectManager());
		progress = new ProgessBarColor(scoreText.getX() + scoreText.getWidth() + 10, padding, CAMERA_WIDTH - 2
				* padding - scoreText.getWidth() - levelText.getWidth() - 2 * 10, 30 * ratio, 100, Color.RED,
				2 * ratio, Color.GREEN, Color.WHITE, getVertexBufferObjectManager());
		levelText.setX(progress.getX() + progress.getWidth() + 10);
		scoreText.setText(getString(R.string.score) + mGame.getScore() + "");
		levelText.setText(getString(R.string.level) + mGame.getLevel());
		mScene.attachChild(scoreText);
		mScene.attachChild(progress);
		mScene.attachChild(levelText);
		createPool();
		onStartGame();
	}

	Text bigText;

	private void onStartGame() {
		if (bigText == null) {
			bigText = new Text(0, 0, bigFont, "", 13, getVertexBufferObjectManager());
		}
		bigText.setText(getString(R.string.startgame));
		bigText.setPosition(CAMERA_WIDTH / 2 - bigText.getWidth() / 2, CAMERA_HEIGHT / 2 - bigText.getHeight() / 2);
		mScene.attachChild(bigText);
		bigText.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {

			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mScene.detachChild(bigText);
						bigText.unregisterUpdateHandler(pTimerHandler);
						start();
					}
				});
			}
		}));
	}

	Random mRandom = new Random();

	private void start() {
		mGame.start();
	}

	protected void addGameObject() {
		int pID = mRandom.nextInt(5);
		final SpriteWithBody sprite = mPool.obtainPoolItem(pID);
		sprite.setPID(pID);
		sprite.setState(State.ACTIVE);
		sprite.setPosition((CAMERA_WIDTH - 32) * mRandom.nextFloat(), -2.5f * mGame.getLevel() * sprite.getHeight());
		sprite.setVelocity(mGame.getVelocity());
		sprite.setVisible(true);
		sprite.setDeathListenner(new IEntityModifierListener() {

			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				// TODO Auto-generated method stub
				removeGameObject(sprite);
			}
		});
		sprite.setOnTouchBegin(new OnTouchBegin() {

			@Override
			public void onTouchBegin() {
				if (!pause) {
					if (sprite.getState() == State.ACTIVE) {
						playCLick();
						sprite.setState(State.DEATH);
						// removeGameObject(sprite);
						boolean passLv = mGame.incressScore(1);
						scoreText.setText(getString(R.string.score) + mGame.getScore() + "");
						if (passLv) {
							levelText.setText(getString(R.string.level) + mGame.getLevel());
							onLevelUp(mGame.getLevel());
						}
					}
				}
			}
		});
		Log.d("", "onTimePassed pId " + pID + " " + sprite.getX() + " " + sprite.getY());
		mScene.registerTouchArea(sprite);
		spriteGroup.attachChild(sprite);
		mGameObject.add(sprite);
	}

	protected void onLevelUp(int level) {
		if (bigText == null) {
			bigText = new Text(0, 0, bigFont, "", 13, getVertexBufferObjectManager());
		}
		bigText.setText(getString(R.string.levelup));
		bigText.setPosition(CAMERA_WIDTH / 2 - bigText.getWidth() / 2, CAMERA_HEIGHT / 2 - bigText.getHeight() / 2);
		mScene.attachChild(bigText);
		mEngine.unregisterUpdateHandler(this);
		pause = true;
		playFinish();
		bigText.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {

			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				runOnUpdateThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mScene.detachChild(bigText);
						bigText.unregisterUpdateHandler(pTimerHandler);
						mEngine.registerUpdateHandler(MainActivity.this);
						pause = false;
					}
				});
			}
		}));
	}

	private void createPool() {
		mPool = new MultiPool<SpriteWithBody>();
		mPool.registerPool(0, new GameObjectGenerate(mBear1TextureRegion, ratio, getVertexBufferObjectManager()));
		mPool.registerPool(1, new GameObjectGenerate(mBear2TextureRegion, ratio, getVertexBufferObjectManager()));
		mPool.registerPool(2, new GameObjectGenerate(mBear3TextureRegion, ratio, getVertexBufferObjectManager()));
		mPool.registerPool(3, new GameObjectGenerate(mBear4TextureRegion, ratio, getVertexBufferObjectManager()));
		mPool.registerPool(4, new GameObjectGenerate(mBear5TextureRegion, ratio, getVertexBufferObjectManager()));
	}

	private float countGenerateTime = 0;

	@Override
	public void onUpdate(float pSecondsElapsed) {
		for (SpriteWithBody spriteWithBody : mGameObject) {
			if (spriteWithBody.getState() == State.ACTIVE && spriteWithBody.getY() > CAMERA_HEIGHT) {
				removeGameObject(spriteWithBody);
				boolean isDeath = mGame.incressObjectDeathCount(1);
				progress.setPercent(100 - mGame.getPercentObjectDeath());
				if (isDeath) {
					mEngine.unregisterUpdateHandler(MainActivity.this);
					pause = true;
					endGame();
					return;
				}
				continue;
			} else
				spriteWithBody.update(pSecondsElapsed);
		}
		countGenerateTime += pSecondsElapsed;
		if (mGame.isStart() && countGenerateTime > mGame.getGenerateTime()) {
			addGameObject();
			countGenerateTime = 0;
		}
	}

	RunnableHandler mHandler = new RunnableHandler();
	protected SharedPreferences preferences;

	private void removeGameObject(final SpriteWithBody spriteWithBody) {
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				spriteGroup.detachChild(spriteWithBody);
				spriteWithBody.setY(-2.5f * spriteWithBody.getHeight());
				spriteWithBody.setVisible(false);
				mGameObject.remove(spriteWithBody);
				mPool.recyclePoolItem(spriteWithBody.getpID(), spriteWithBody);
				mScene.unregisterTouchArea(spriteWithBody);
			}
		});
	}

	private void endGame() {
		mEngine.unregisterUpdateHandler(MainActivity.this);
		pause = true;
		// String accName = preferences.getString(NAME_KEY, null);
		// if(accName == null){
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				createInputNameDialog();
			}
		});

		// }else{
		// createEndGameDialog();
		// }
	}

	private void createInputNameDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(getString(R.string.accname));
		View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
		final EditText editText = (EditText) view.findViewById(R.id.edit);
		builder.setView(view).setPositiveButton(getString(R.string.oK), null);
		final AlertDialog dialog = builder.create();
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name = editText.getText().toString();
				if (name != null && !name.equals("")) {
					preferences.edit().putString(NAME_KEY, editText.getText().toString()).commit();
					dialog.dismiss();
					createEndGameDialog();
				}
			}
		});
	}

	protected void createEndGameDialog() {
		ScoreDialog dialog = new ScoreDialog(this, mCamera, ratio, mGame.getScore(), mGame.getLevel());
		dialog.setLeftButton("OK", new com.bestfunforever.andengine.uikit.dialog.IDialog.IClick() {

			@Override
			public void onClick(Dialog dialog, IEntity view) {
				playCLick();
				dialog.dismiss();
				dialog.setDialogListenner(new IDialog() {

					@Override
					public void onOpen() {

					}

					@Override
					public void onClose() {
						String name = preferences.getString(NAME_KEY, null);
						HighScore highScore = new HighScore(name, mGame.getScore(), mGame.getLevel(), new Date()
								.getTime());
						databaseHelper.insertHighScore(highScore);
						finish();
					}
				});
			}
		});
		dialog.setRightButton(null, mShareFbTextureRegion, new com.bestfunforever.andengine.uikit.dialog.IDialog.IClick() {

			@Override
			public void onClick(Dialog dialog, IEntity view) {
				playCLick();
				share(mGame.getScore(), mGame.getLevel());
			}
		});
		dialog.show(circleMenu);
	}

	protected void share(int score, int level) {
		// TODO Auto-generated method stub
		shareFacebook(getString(R.string.app_name), getString(R.string.sharedesciption), getString(R.string.sharemsg)
				+ " " + getString(R.string.level) + " " + level + " " + getString(R.string.score) + " " + score,
				"https://play.google.com/store/apps/details?id=" + getPackageName(),
				"https://raw.githubusercontent.com/tgioihan/TouchKid/master/bearforkidicon.png");
	}

	@Override
	public void reset() {

	}

	protected void createSettingDialog() {
		// TODO Auto-generated method stub
		mEngine.unregisterUpdateHandler(MainActivity.this);
		SettingDialog mDialog = new SettingDialog(this, mCamera, ratio, clickSound);
		mDialog.setDialogListenner(new IDialog() {

			@Override
			public void onOpen() {
				mEngine.unregisterUpdateHandler(MainActivity.this);
				pause = true;
			}

			@Override
			public void onClose() {
				mEngine.registerUpdateHandler(MainActivity.this);
				pause = false;
			}
		});
		mDialog.setMusicCheckedChangeListenner(new ICheckedChange() {

			@Override
			public void onCheckedChange(boolean checked) {
				SoundManger.setMusicEnable(preferences, checked);
				if (checked) {
					if (music != null && !music.isPlaying()) {
						playMusic();
					}
				} else {
					if (music != null && music.isPlaying()) {
						music.pause();
					}
				}
			}
		});
		mDialog.setSoundCheckedChangeListenner(new ICheckedChange() {

			@Override
			public void onCheckedChange(boolean checked) {
				// TODO Auto-generated method stub
				SoundManger.setSoundEnable(preferences, checked);
			}
		});
		mDialog.show(circleMenu);
	}

	private void playMusic() {
		// TODO Auto-generated method stub
		if (music == null) {
			loadMusic();
			music.play();
		} else {
			music.play();
		}
	}

	private void loadMusic() {
		MusicFactory.setAssetBasePath("sound/");
		try {
			music = MusicFactory.createMusicFromAsset(getMusicManager(), getApplicationContext(),
					"dora_and_dreamland_forever.mp3");
			music.setLooping(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onMenuItemClicked(HUD pMenuScene, IMenuItem pMenuItem) {
		int id = pMenuItem.getID();
		switch (id) {
		case BearForKidsCircleMenu.MENU_SETTING:
			playCLick();
			createSettingDialog();
			break;
		case BearForKidsCircleMenu.MENU_EXIT:
			playCLick();
			finish();
			break;
		case BearForKidsCircleMenu.MENU_HiGHSCORE:
			playCLick();
			createHighScoreDialog();
			break;

		default:
			break;
		}
		return true;
	}

	protected void createHighScoreDialog() {
		// TODO Auto-generated method stub
		mEngine.unregisterUpdateHandler(MainActivity.this);
		pause = true;
		BaseDialog mDialog = new HighScoreDialog(this, mCamera, ratio, clickSound);
		mDialog.setDialogListenner(new IDialog() {

			@Override
			public void onOpen() {
				mEngine.unregisterUpdateHandler(MainActivity.this);
				pause = true;
			}

			@Override
			public void onClose() {
				mEngine.registerUpdateHandler(MainActivity.this);
				pause = false;
			}
		});
		mDialog.show(circleMenu);
	}

	@Override
	public void onShow() {
		playCLick();
		mEngine.unregisterUpdateHandler(MainActivity.this);
		pause = true;
	}

	@Override
	public void onHide() {
		playCLick();
		mEngine.registerUpdateHandler(MainActivity.this);
		pause = false;
	}
}
