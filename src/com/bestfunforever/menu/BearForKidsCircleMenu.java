package com.bestfunforever.menu;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.bestfunforever.andengine.uikit.entity.BubbleSprite;
import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.andengine.uikit.menu.CircleMenu;
import com.bestfunforever.andengine.uikit.menu.MenuItem;

public class BearForKidsCircleMenu extends CircleMenu {

	public static final int MENU_SETTING = 1;
	public static final int MENU_EXIT = 2;
	public static final int MENU_HiGHSCORE = 3;

	private TiledTextureRegion menuExitRegion;
	private TiledTextureRegion menuSettingsRegion;
	private TiledTextureRegion iconHighScoreMenuRegion;
	private TiledTextureRegion menuPurpleTextureRegion;
	private TiledTextureRegion menuControlBgTextureRegion;
	private TiledTextureRegion menuControlDerectionTextureRegion;

	public BearForKidsCircleMenu(SimpleBaseGameActivity context, Camera mCamera, float ratio) {
		super(context, mCamera, ratio);
	}

	@Override
	public void onLoadResource() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas menuArrowBitmapTexture = new BitmapTextureAtlas(context.getTextureManager(), (int) (143),
				(int) (143), TextureOptions.BILINEAR);
		BitmapTextureAtlas menuSettingsBitmapTexture = new BitmapTextureAtlas(context.getTextureManager(), (int) (143),
				(int) (143), TextureOptions.BILINEAR);
		menuExitRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuArrowBitmapTexture, context,
				"back_left.png", 0, 0, 1, 1);
		menuSettingsRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuSettingsBitmapTexture,
				context, "settings.png", 0, 0, 1, 1);
		BitmapTextureAtlas highscoreMenuAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (140),
				(int) (140), TextureOptions.BILINEAR);
		iconHighScoreMenuRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(highscoreMenuAtlas,
				context, "high_score.png", 0, 0, 1, 1);
		clearITextureRegion(menuExitRegion);
		clearITextureRegion(menuSettingsRegion);
		clearITextureRegion(iconHighScoreMenuRegion);
		highscoreMenuAtlas.load();
		menuArrowBitmapTexture.load();
		menuSettingsBitmapTexture.load();
		atlas.add(highscoreMenuAtlas);
		atlas.add(menuArrowBitmapTexture);
		atlas.add(menuSettingsBitmapTexture);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas menuBackgroundTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
				(int) (512), (int) (512), TextureOptions.BILINEAR);
		menuPurpleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				menuBackgroundTextureAtlas, context, "purple_circle.png", 0, 0, 1, 1);
		menuBackgroundTextureAtlas.load();
		clearITextureRegion(menuPurpleTextureRegion);
		atlas.add(menuBackgroundTextureAtlas);

		BitmapTextureAtlas menuCloseTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (128),
				(int) (128), TextureOptions.BILINEAR);
		menuControlBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuCloseTextureAtlas,
				context, "yellow_menu.png", 0, 0, 1, 1);
		menuCloseTextureAtlas.load();
		atlas.add(menuCloseTextureAtlas);

		BitmapTextureAtlas menuControlDerectionTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
				(int) (128), (int) (128), TextureOptions.BILINEAR);
		menuControlDerectionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				menuControlDerectionTextureAtlas, context, "direction_menu.png", 0, 0, 1, 1);
		menuControlDerectionTextureAtlas.load();
		atlas.add(menuControlDerectionTextureAtlas);
	}

	public static void clearITextureRegion(final ITextureRegion mITextureRegion) {
		mITextureRegion.setTextureWidth(mITextureRegion.getWidth() - 1);
		mITextureRegion.setTextureHeight(mITextureRegion.getHeight() - 1);
	}

	@Override
	public void onCreate() {
		controlBgSprite = new BubbleSprite(-menuControlBgTextureRegion.getWidth(), camera_height, ratio, "", null,
				menuControlBgTextureRegion, context.getVertexBufferObjectManager());
		controlBgSprite.setClickListenner(new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				if (stage == STAGE.HIDE) {
					show();
				} else if (stage == STAGE.SHOW) {
					hide();
				}
			}
		});
		controlBgSprite.registerEntityModifier(new MoveModifier(1f, controlBgSprite.getX(), 0, controlBgSprite.getY(),
				camera_height - controlBgSprite.getHeight()));

		derectionMenuSprite = new Sprite(menuControlBgTextureRegion.getWidth() / 2
				- menuControlDerectionTextureRegion.getWidth() / 2, +menuControlBgTextureRegion.getHeight() / 2
				- menuControlDerectionTextureRegion.getHeight() / 2, menuControlDerectionTextureRegion.getWidth()
				* ratio, menuControlDerectionTextureRegion.getHeight() * ratio, menuControlDerectionTextureRegion,
				context.getVertexBufferObjectManager());
		controlBgSprite.attachChild(derectionMenuSprite);
		this.registerTouchArea(controlBgSprite);
		holderBgSprite = new Sprite(-menuPurpleTextureRegion.getWidth() * ratio, camera_height,
				menuPurpleTextureRegion.getWidth() * ratio, menuPurpleTextureRegion.getHeight() * ratio,
				menuPurpleTextureRegion, context.getVertexBufferObjectManager());
		this.attachChild(holderBgSprite);
		this.attachChild(controlBgSprite);

		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		MenuItem menuItem1 = new MenuItem(MENU_HiGHSCORE, iconHighScoreMenuRegion.getWidth() * ratio,
				iconHighScoreMenuRegion.getHeight() * ratio, null, null, iconHighScoreMenuRegion,
				context.getVertexBufferObjectManager());
		MenuItem menuItem2 = new MenuItem(MENU_SETTING, menuSettingsRegion.getWidth() * ratio,
				menuSettingsRegion.getHeight() * ratio, null, null, menuSettingsRegion,
				context.getVertexBufferObjectManager());
		MenuItem menuItem3 = new MenuItem(MENU_EXIT, menuExitRegion.getWidth() * ratio, menuExitRegion.getHeight()
				* ratio, null, null, menuExitRegion, context.getVertexBufferObjectManager());
		list.add(menuItem1);
		list.add(menuItem2);
		list.add(menuItem3);

		addMenuItem(list);
		stage = STAGE.HIDE;

	}

}
