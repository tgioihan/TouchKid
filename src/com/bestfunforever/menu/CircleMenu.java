package com.bestfunforever.menu;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.touchkids.Entity.BubbleSprite;

public class CircleMenu extends BaseMenu {

	private SimpleBaseGameActivity context;
	private float camera_height;
	private float camera_width;
	private float ratio;

	ArrayList<BitmapTextureAtlas> atlas = new ArrayList<BitmapTextureAtlas>();
	private TiledTextureRegion menuExitRegion;
	private TiledTextureRegion menuSettingsRegion;
	private TiledTextureRegion iconMapMenuRegion;
	private TiledTextureRegion menuPurpleTextureRegion;
	private TiledTextureRegion menuControlBgTextureRegion;
	private TiledTextureRegion menuControlDerectionTextureRegion;
	private TiledTextureRegion rulerMenuTextureRegion;
	private BubbleSprite controlBgSprite;
	private Sprite derectionMenuSprite;
	private Sprite holderBgSprite;

	public CircleMenu(SimpleBaseGameActivity context, Camera mCamera, float ratio) {
		this.context = context;
		this.camera_height = mCamera.getHeight();
		this.camera_width = mCamera.getHeight();
		this.stage = STAGE.HIDE;
		this.ratio = ratio;
		this.setOnSceneTouchListener(this);
		this.setOnAreaTouchListener(this);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.setCamera(mCamera);
		onLoadResource();
		onCreate();
	}

	@Override
	public void onLoadResource() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas menuArrowBitmapTexture = new BitmapTextureAtlas(context.getTextureManager(), (int) (128),
				(int) (106), TextureOptions.BILINEAR);
		BitmapTextureAtlas menuSettingsBitmapTexture = new BitmapTextureAtlas(context.getTextureManager(), (int) (160),
				(int) (120), TextureOptions.BILINEAR);
		menuExitRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuArrowBitmapTexture, context,
				"arrow.png", 0, 0, 1, 1);
		menuSettingsRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuSettingsBitmapTexture,
				context, "settings.png", 0, 0, 1, 1);
		BitmapTextureAtlas mapMenuAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (128), (int) (128),
				TextureOptions.BILINEAR);
		iconMapMenuRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mapMenuAtlas, context,
				"icon_map.png", 0, 0, 1, 1);
		mapMenuAtlas.load();
		menuArrowBitmapTexture.load();
		menuSettingsBitmapTexture.load();
		atlas.add(mapMenuAtlas);
		atlas.add(menuArrowBitmapTexture);
		atlas.add(menuSettingsBitmapTexture);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BitmapTextureAtlas menuBackgroundTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
				(int) (512), (int) (512), TextureOptions.BILINEAR);
		menuPurpleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				menuBackgroundTextureAtlas, context, "purple_circle.png", 0, 0, 1, 1);
		menuBackgroundTextureAtlas.load();
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

		BitmapTextureAtlas rulermenuControlDerectionTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
				(int) (2), (int) (256), TextureOptions.BILINEAR);
		rulerMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				rulermenuControlDerectionTextureAtlas, context, "purple_circle_ruler.png", 0, 0, 1, 1);
		rulermenuControlDerectionTextureAtlas.load();
		atlas.add(rulermenuControlDerectionTextureAtlas);
	}

	@Override
	public void onCreate() {
		controlBgSprite = new BubbleSprite(-menuControlBgTextureRegion.getWidth(), camera_height,ratio, "", null,
				menuControlBgTextureRegion, context.getVertexBufferObjectManager());
		controlBgSprite.setClickListenner(new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				// TODO Auto-generated method stub
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
				- menuControlDerectionTextureRegion.getHeight() / 2,menuControlDerectionTextureRegion.getWidth()*ratio,menuControlDerectionTextureRegion.getHeight()*ratio, menuControlDerectionTextureRegion,
				context.getVertexBufferObjectManager());
		controlBgSprite.attachChild(derectionMenuSprite);
		this.registerTouchArea(controlBgSprite);
		holderBgSprite = new Sprite(-menuPurpleTextureRegion.getWidth()*ratio, camera_height,menuPurpleTextureRegion.getWidth()*ratio,menuPurpleTextureRegion.getHeight()*ratio, menuPurpleTextureRegion,
				context.getVertexBufferObjectManager());
		this.attachChild(holderBgSprite);
		this.attachChild(controlBgSprite);

		ArrayList<IMenuItem> list = new ArrayList<IMenuItem>();
		MenuItem menuItem1 = new MenuItem(1, menuExitRegion.getWidth() * ratio, menuExitRegion.getHeight() * ratio,
				null, null, menuExitRegion, context.getVertexBufferObjectManager());
		MenuItem menuItem2 = new MenuItem(2, menuExitRegion.getWidth() * ratio, menuExitRegion.getHeight() * ratio,
				null, null, menuExitRegion, context.getVertexBufferObjectManager());
		MenuItem menuItem3 = new MenuItem(3, menuExitRegion.getWidth() * ratio, menuExitRegion.getHeight() * ratio,
				null, null, menuExitRegion, context.getVertexBufferObjectManager());
		list.add(menuItem1);
		list.add(menuItem2);
		list.add(menuItem3);

		addMenuItem(list);
		stage = STAGE.HIDE;

	}

	@Override
	protected void show() {
		super.show();
		if (stage == STAGE.HIDE) {
			holderBgSprite.registerEntityModifier(new MoveModifier(0.3f, holderBgSprite.getX(), 0, holderBgSprite
					.getY(), camera_height - holderBgSprite.getHeight(), new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					stage = STAGE.ANIMATE;
					pItem.setVisible(true);
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					stage = STAGE.SHOW;
				}
			}));
			derectionMenuSprite.registerEntityModifier(new RotationAtModifier(0.5f, 0, 180, derectionMenuSprite
					.getWidth() / 2, derectionMenuSprite.getHeight() / 2));
		}
	}

	@Override
	protected void hide() {
		super.hide();
		if (stage == STAGE.SHOW) {
			holderBgSprite.registerEntityModifier(new MoveModifier(0.3f, holderBgSprite.getX(), -holderBgSprite
					.getWidth(), holderBgSprite.getY(), camera_height, new IEntityModifierListener() {

				@Override
				public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
					stage = STAGE.ANIMATE;
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
					stage = STAGE.HIDE;
					pItem.setVisible(false);
				}
			}));
			derectionMenuSprite.registerEntityModifier(new RotationModifier(0.5f, 180, 0));
		}
	}

	@Override
	public void onDestroy() {

	}

	private float padding = 60;

	@Override
	public void invalidate() {
		padding = 60;
		float degreeOffset = 90 / (mMenuItems.size() );
		float R = holderBgSprite.getWidth() - padding;

		for (int i = 0; i < mMenuItems.size(); i++) {
			MenuItem menuItem = (MenuItem) mMenuItems.get(i);
			float x = (float) (R * Math.cos(Math.toRadians(degreeOffset * (i )+degreeOffset/2)));
			float y = (float) (R * Math.sin(Math.toRadians(degreeOffset * (i )+degreeOffset/2)));
			menuItem.setPosition(x - menuItem.getWidth() / 2, holderBgSprite.getHeight() - y - menuItem.getHeight() / 2);
			holderBgSprite.attachChild(menuItem);
			registerTouchArea(menuItem);
		}

	}

}
