package com.bestfunforever.touchkids;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
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

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.dialog.BaseDialog;
import com.bestfunforever.dialog.Dialog;
import com.bestfunforever.touchkids.Entity.BubbleSprite;

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

	private static final float distance_button = 30f;
	private static final float mAnimDuration = 1;

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		ratio = RatioUtils.calculatorRatioScreen(this, true);
		Log.d("", "ratio " + ratio);
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH,
				CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBgBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 640, 960, TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBgBitmapTextureAtlas,
				this, "bg_app.png", 0, 0); // 64x32
		this.mBgBitmapTextureAtlas.load();

		this.mBtnBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 229, 76, TextureOptions.BILINEAR);
		this.mBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBtnBitmapTextureAtlas,
				this, "btn.png", 0, 0); // 64x32
		this.mBtnBitmapTextureAtlas.load();

		this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio), Typeface.create(Typeface.DEFAULT, Typeface.BOLD), (int) (32 * ratio));
		this.mFont.load();

	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.mScene = new Scene();
		this.mScene.setBackground(new SpriteBackground(new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBgTextureRegion,
				getVertexBufferObjectManager())));

		Rectangle mLayer = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, getVertexBufferObjectManager());
		mLayer.setColor(Color.TRANSPARENT);
		float tmp = initButton(216 * ratio, 412 * ratio, getString(R.string.new_game), mLayer, new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		}, true);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio, getString(R.string.highscore), mLayer,
				new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						createDialog(getString(R.string.highscore));
					}
				}, false);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio, getString(R.string.settings), mLayer,
				new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						createDialog(getString(R.string.settings));
					}
				}, true);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio, getString(R.string.moregame), mLayer,
				new IClick() {

					@Override
					public void onCLick(IAreaShape view) {
						createDialog(getString(R.string.moregame));
					}
				}, false);
		tmp = initButton(216 * ratio, tmp + distance_button * ratio, getString(R.string.exit), mLayer, new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				finish();
			}
		}, true);
		mScene.setTouchAreaBindingOnActionMoveEnabled(true);
		mScene.setTouchAreaBindingOnActionDownEnabled(true);
		mScene.attachChild(mLayer);
		return mScene;
	}

	protected void createDialog(String string) {
		// TODO Auto-generated method stub
		BaseDialog mDialog = new BaseDialog(this, mCamera, ratio);
		mDialog.setTitle(string);
		mDialog.setLeftButton("OK", new com.bestfunforever.dialog.IDialog.IClick() {

			@Override
			public void onClick(Dialog dialog, IEntity view) {
				dialog.dismiss();
			}
		});
		mDialog.setOpenAnim(new ScaleModifier(1, 1, 1, 0, 1));
		mDialog.setCloseAnim(new ScaleModifier(0.4f, 1, 1, 1, 0));
		mDialog.show(mScene);
	}

	private float initButton(float pX, float pY, String label, Rectangle mLayer, IClick iClick, boolean left) {
		BubbleSprite mBtn = new BubbleSprite(0, pY, mBtnTextureRegion.getWidth() * ratio, mBtnTextureRegion.getHeight()
				* ratio, label, mFont, mBtnTextureRegion, getVertexBufferObjectManager());
		mBtn.setClickListenner(iClick);
		float startX = 0;
		if (left) {
			startX = -mBtn.getWidth();

		} else {
			startX = mLayer.getWidth() + mBtn.getWidth();
		}

		mBtn.setX(startX);
		mBtn.registerEntityModifier(new MoveXModifier(mAnimDuration, startX, pX));
		mLayer.attachChild(mBtn);
		mScene.registerTouchArea(mBtn);
		return mBtn.getY() + mBtn.getHeight();
	}

}
