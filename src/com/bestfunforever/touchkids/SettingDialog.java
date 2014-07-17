package com.bestfunforever.touchkids;

import org.andengine.audio.sound.Sound;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.content.SharedPreferences;

import com.bestfunforever.dialog.BaseDialog;
import com.bestfunforever.dialog.Dialog;
import com.bestfunforever.touchkids.Entity.CheckBox;
import com.bestfunforever.touchkids.Entity.CheckBox.ICheckedChange;

public class SettingDialog extends BaseDialog {

	private BitmapTextureAtlas mSoundBitmapTextureAtlas;
	private TiledTextureRegion mSoundTextureRegion;
	private BitmapTextureAtlas mMusicBitmapTextureAtlas;
	private TiledTextureRegion mMusicTextureRegion;
	private Text musicSateText;
	private Text soundSateText;
	private Sound mclickSound;

	private ICheckedChange mSoundCheckedChange;
	private ICheckedChange mMusicCheckedChange;

	public SettingDialog(SimpleBaseGameActivity context, Camera camera,
			float ratio, Sound clickSound) {
		super(context, camera, ratio);
		mclickSound = clickSound;
		setTitle(context.getString(R.string.settings));
		setLeftButton(context.getString(R.string.oK),
				new com.bestfunforever.dialog.IDialog.IClick() {

					@Override
					public void onClick(Dialog dialog, IEntity view) {
						mclickSound.play();
						dialog.dismiss();
					}
				});
	}

	@Override
	public void onLoadResource() {
		// TODO Auto-generated method stub
		super.onLoadResource();
		this.mSoundBitmapTextureAtlas = new BitmapTextureAtlas(
				context.getTextureManager(), 527, 388, TextureOptions.BILINEAR);
		this.mSoundTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mSoundBitmapTextureAtlas, context,
						"sound.png", 0, 0, 2, 1); // 64x32
		this.mSoundBitmapTextureAtlas.load();

		this.mMusicBitmapTextureAtlas = new BitmapTextureAtlas(
				context.getTextureManager(), 527, 388, TextureOptions.BILINEAR);
		this.mMusicTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(this.mMusicBitmapTextureAtlas, context,
						"music.png", 0, 0, 2, 1); // 64x32
		this.mMusicBitmapTextureAtlas.load();
	}

	@Override
	public void onDesTroy() {
		// TODO Auto-generated method stub
		super.onDesTroy();
		mSoundBitmapTextureAtlas.unload();
		mMusicBitmapTextureAtlas.unload();
	}

	@Override
	protected float initContent(Rectangle mContentRect) {
		SharedPreferences preferences = context.getSharedPreferences(
				"BearforKid", 0);
		Rectangle rect1 = new Rectangle(0, ScoreDialog.margin * ratio, 0, 0,
				context.getVertexBufferObjectManager());
		rect1.setColor(Color.TRANSPARENT);
		Text soundText = new Text(0, 0, mFont,
				context.getString(R.string.sound) + " ",
				context.getVertexBufferObjectManager());
		boolean soundEnable = SoundManger.isSoundEnable(preferences);
		String soundState = soundEnable ? (context.getString(R.string.state_on) + " ")
				: (context.getString(R.string.state_off) + " ");
		soundSateText = new Text(300 * ratio, 0, mFont, soundState,
				context.getVertexBufferObjectManager());
		CheckBox soundCheckbox = new CheckBox(soundSateText.getX()
				+ soundSateText.getWidth(), 0, ratio, soundEnable,
				mSoundTextureRegion, context.getVertexBufferObjectManager());
		soundCheckbox.setOnCheckedChangeListenner(new ICheckedChange() {

			@Override
			public void onCheckedChange(boolean checked) {
				// TODO Auto-generated method stub
				if (mSoundCheckedChange != null) {
					mSoundCheckedChange.onCheckedChange(checked);
				}
				soundSateText.setText(checked ? context
						.getString(R.string.state_on) : context
						.getString(R.string.state_off));

			}
		});
		float rect1Width = soundCheckbox.getX() + soundCheckbox.getWidth();
		float rect1Height = Math.max(soundText.getHeight(),
				soundCheckbox.getHeight());
		soundText.setY(rect1Height / 2 - soundText.getHeight() / 2);
		soundSateText.setY(rect1Height / 2 - soundSateText.getHeight() / 2);
		soundCheckbox.setY(rect1Height / 2 - soundCheckbox.getHeight() / 2);
		rect1.setWidth(rect1Width);
		rect1.setHeight(rect1Height);
		rect1.attachChild(soundText);
		rect1.attachChild(soundSateText);
		rect1.attachChild(soundCheckbox);

		Rectangle rect2 = new Rectangle(0, rect1.getY() + rect1.getHeight()
				+ ScoreDialog.margin * ratio, 0, 0,
				context.getVertexBufferObjectManager());
		rect2.setColor(Color.TRANSPARENT);
		Text musicText = new Text(0, 0, mFont,
				context.getString(R.string.music) + " ",
				context.getVertexBufferObjectManager());
		boolean musicEnable = SoundManger.isMusicEnable(preferences);
		String musicState = musicEnable ? (context.getString(R.string.state_on) + " ")
				: (context.getString(R.string.state_off) + " ");
		musicSateText = new Text(300 * ratio, 0, mFont, musicState,
				context.getVertexBufferObjectManager());
		CheckBox musicCheckbox = new CheckBox(musicSateText.getX()
				+ musicSateText.getWidth(), 0, ratio, musicEnable,
				mMusicTextureRegion, context.getVertexBufferObjectManager());
		musicCheckbox.setOnCheckedChangeListenner(new ICheckedChange() {

			@Override
			public void onCheckedChange(boolean checked) {
				musicSateText.setText(checked ? context
						.getString(R.string.state_on) : context
						.getString(R.string.state_off));
				if (mMusicCheckedChange != null) {
					mMusicCheckedChange.onCheckedChange(checked);
				}
			}
		});
		float rect2Width = musicCheckbox.getX() + musicCheckbox.getWidth();
		float rect2Height = Math.max(musicText.getHeight(),
				musicCheckbox.getHeight());
		musicText.setY(rect2Height / 2 - musicText.getHeight() / 2);
		musicSateText.setY(rect2Height / 2 - musicSateText.getHeight() / 2);
		musicCheckbox.setY(rect2Height / 2 - musicCheckbox.getHeight() / 2);
		rect2.setWidth(rect2Width);
		rect2.setHeight(rect2Height);
		rect2.attachChild(musicText);
		rect2.attachChild(musicSateText);
		rect2.attachChild(musicCheckbox);

		mContentRect.attachChild(rect2);
		mContentRect.attachChild(rect1);
		registerTouchArea(musicCheckbox);
		registerTouchArea(soundCheckbox);

		return rect2.getY() + rect2.getHeight() + ScoreDialog.margin * ratio;
	}

	/**
	 * @return the mCheckedChange
	 */
	public ICheckedChange getSoundCheckedChangeListenner() {
		return mSoundCheckedChange;
	}

	/**
	 * @param mCheckedChange
	 *            the mCheckedChange to set
	 */
	public void setSoundCheckedChangeListenner(ICheckedChange mCheckedChange) {
		this.mSoundCheckedChange = mCheckedChange;
	}

	/**
	 * @return the mMusicCheckedChange
	 */
	public ICheckedChange getMusicCheckedChangeListenner() {
		return mMusicCheckedChange;
	}

	/**
	 * @param mMusicCheckedChange
	 *            the mMusicCheckedChange to set
	 */
	public void setMusicCheckedChangeListenner(
			ICheckedChange mMusicCheckedChange) {
		this.mMusicCheckedChange = mMusicCheckedChange;
	}

}
