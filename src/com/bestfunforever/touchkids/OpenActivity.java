package com.bestfunforever.touchkids;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.dialog.BaseDialog;
import com.bestfunforever.dialog.Dialog;
import com.bestfunforever.touchkids.Entity.BubbleSprite;
import com.bestfunforever.touchkids.Entity.CheckBox.ICheckedChange;

public class OpenActivity extends SimpleBaseGameActivity {

	private float ratio;
	private int CAMERA_WIDTH;
	private int CAMERA_HEIGHT;
	private BitmapTextureAtlas mBgBitmapTextureAtlas;
	private TextureRegion mBgTextureRegion;
	private Scene mScene;
	private BitmapTextureAtlas mBtnBitmapTextureAtlas;
	private TextureRegion mBtnTextureRegion;
	private Font mFont;
	private Camera mCamera;

	private ArrayList<BubbleSprite> mButtons = new ArrayList<BubbleSprite>();
	private Sound clickSound;
	private Music music;
	private SharedPreferences preferences;

	private static final float distance_button = 30f;
	private static final float mAnimDuration = 1;

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		preferences = getSharedPreferences("BearforKid", 0);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		ratio = RatioUtils.calculatorRatioScreen(this, true);
		Log.d("", "ratio " + ratio);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engineOptions.getAudioOptions().getMusicOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().getSoundOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBgBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 640, 960, TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBgBitmapTextureAtlas, this,
						"bg_app.png", 0, 0); // 64x32
		this.mBgBitmapTextureAtlas.load();

		this.mBtnBitmapTextureAtlas = new BitmapTextureAtlas(
				this.getTextureManager(), 229, 76, TextureOptions.BILINEAR);
		this.mBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mBtnBitmapTextureAtlas, this, "btn.png",
						0, 0); // 64x32
		this.mBtnBitmapTextureAtlas.load();

		this.mFont = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio),
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
				(int) (32 * ratio));
		this.mFont.load();
		loadSound();
		loadMusic();
	}
	
	private void loadSound() {
		SoundFactory.setAssetBasePath("sound/");
		try {
			clickSound = SoundFactory.createSoundFromAsset(this.getSoundManager(),
					this.getApplicationContext(), "button_click.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void playCLick() {
		if(clickSound!=null&&SoundManger.isSoundEnable(preferences)){
			clickSound.play();
		}else if(clickSound == null&&SoundManger.isSoundEnable(preferences)){
			loadSound();
			clickSound.play();
		}
	}

	private void loadMusic() {
		MusicFactory.setAssetBasePath("sound/");
		try {
			music = MusicFactory.createMusicFromAsset(getMusicManager(), getApplicationContext(), "dora_and_dreamland_forever.mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(music!=null && music.isPlaying()){
			music.pause();
		}
	}
	
	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
		if(SoundManger.isMusicEnable(preferences)){
			playMusic();
		}
		for (int i = 0; i < mButtons.size(); i++) {
			BubbleSprite bubbleSprite = mButtons.get(i);
			if (i % 2 == 0) {
				bubbleSprite.setX(-bubbleSprite.getWidth());
			} else {
				bubbleSprite.setX(CAMERA_WIDTH + bubbleSprite.getWidth());
			}
			bubbleSprite.registerEntityModifier(new MoveXModifier(
					mAnimDuration, bubbleSprite.getX(), 216 * ratio,
					new IEntityModifierListener() {

						@Override
						public void onModifierStarted(
								IModifier<IEntity> pModifier, IEntity pItem) {
							((BubbleSprite) pItem).setEnabled(false);
						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							((BubbleSprite) pItem).setEnabled(true);
						}
					}));
		}
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		if(SoundManger.isMusicEnable(preferences)){
			playMusic();
		}
		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0,
				CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));

		Rectangle mLayer = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT,
				getVertexBufferObjectManager());
		mLayer.setColor(Color.TRANSPARENT);
		float tmp = initButton(216 * ratio, 412 * ratio,
				getString(R.string.new_game), mLayer, new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						playCLick();
						startActivity(new Intent(getApplicationContext(),
								MainActivity.class));
					}
				}, true);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio,
				getString(R.string.highscore), mLayer, new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						playCLick();
						createHighScoreDialog();
					}
				}, false);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio,
				getString(R.string.settings), mLayer, new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						playCLick();
						createSettingDialog();
					}
				}, true);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio,
				getString(R.string.moregame), mLayer, new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						playCLick();
						createDialog(getString(R.string.moregame));
					}
				}, false);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio,
				getString(R.string.exit), mLayer, new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						playCLick();
						finish();
					}
				}, true);
		mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.attachChild(mLayer);
		return mScene;
	}

	protected void createHighScoreDialog() {
		// TODO Auto-generated method stub
		BaseDialog mDialog = new HighScoreDialog(this, mCamera, ratio,clickSound);
		mDialog.show(mScene);
	}

	protected void createSettingDialog() {
		// TODO Auto-generated method stub
		SettingDialog mDialog = new SettingDialog(this, mCamera, ratio,clickSound);
		mDialog.setMusicCheckedChangeListenner(new ICheckedChange() {
			
			@Override
			public void onCheckedChange(boolean checked) {
				SoundManger.setMusicEnable(preferences, checked);
				if(checked){
					if(music!=null && !music.isPlaying()){
						playMusic();
					}
				}else{
					if(music!=null && music.isPlaying()){
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
		mDialog.show(mScene);
	}
	
	private void playMusic() {
		// TODO Auto-generated method stub
		if(music == null){
			loadMusic();
			music.play();
		}else{
			music.play();
		}
	}

	protected void createDialog(String string) {
		// TODO Auto-generated method stub
		BaseDialog mDialog = new BaseDialog(this, mCamera, ratio);
		mDialog.setTitle(string);
		mDialog.setLeftButton("OK",
				new com.bestfunforever.dialog.IDialog.IClick() {

					@Override
					public void onClick(Dialog dialog, IEntity view) {
						playCLick();
						dialog.dismiss();
					}
				});
		mDialog.setOpenAnim(new ScaleModifier(1, 1, 1, 0, 1));
		mDialog.setCloseAnim(new ScaleModifier(0.4f, 1, 1, 1, 0));
		mDialog.show(mScene);
	}

	private float initButton(float pX, float pY, String label,
			Rectangle mLayer, IClick iClick, boolean left) {
		BubbleSprite mBtn = new BubbleSprite(0, pY,
				mBtnTextureRegion.getWidth() * ratio,
				mBtnTextureRegion.getHeight() * ratio, label, mFont,
				mBtnTextureRegion, getVertexBufferObjectManager());
		mBtn.setClickListenner(iClick);
		float startX = 0;
		if (left) {
			startX = -mBtn.getWidth();

		} else {
			startX = mLayer.getWidth() + mBtn.getWidth();
		}

		mBtn.setX(startX);
		mBtn.registerEntityModifier(new MoveXModifier(mAnimDuration, startX,
				pX, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> pModifier,
							IEntity pItem) {
						((BubbleSprite) pItem).setEnabled(false);
					}

					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier, IEntity pItem) {
						((BubbleSprite) pItem).setEnabled(true);
					}
				}));
		mButtons.add(mBtn);
		mLayer.attachChild(mBtn);
		mScene.registerTouchArea(mBtn);
		return mBtn.getY() + mBtn.getHeight();
	}

}
