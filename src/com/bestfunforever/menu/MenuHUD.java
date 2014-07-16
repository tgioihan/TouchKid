//package com.bestfunforever.menu;
//
//import java.util.ArrayList;
//
//import org.andengine.audio.sound.SoundManager;
//import org.andengine.engine.camera.ZoomCamera;
//import org.andengine.engine.camera.hud.HUD;
//import org.andengine.entity.IEntity;
//import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
//import org.andengine.entity.modifier.MoveModifier;
//import org.andengine.entity.modifier.RotationAtModifier;
//import org.andengine.entity.modifier.RotationModifier;
//import org.andengine.entity.scene.IOnAreaTouchListener;
//import org.andengine.entity.scene.IOnSceneTouchListener;
//import org.andengine.entity.scene.ITouchArea;
//import org.andengine.entity.scene.Scene;
//import org.andengine.entity.scene.menu.animator.IMenuAnimator;
//import org.andengine.entity.scene.menu.item.IMenuItem;
//import org.andengine.entity.shape.IAreaShape;
//import org.andengine.entity.sprite.Sprite;
//import org.andengine.input.touch.TouchEvent;
//import org.andengine.opengl.font.FontFactory;
//import org.andengine.opengl.texture.TextureOptions;
//import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
//import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
//import org.andengine.opengl.texture.region.TiledTextureRegion;
//import org.andengine.ui.activity.SimpleBaseGameActivity;
//import org.andengine.util.modifier.IModifier;
//
//import android.util.Log;
//import android.view.MotionEvent;
//
//import com.example.uikitandengine.R;
//
//public class MenuHUD extends BaseHUD implements IOnAreaTouchListener, IOnSceneTouchListener {
//
//	public enum STAGE {
//		SHOW, HIDE, ANIMATE
//	};
//
//	private STAGE stage;
//
//	public STAGE getStage() {
//		return stage;
//	}
//
//	public void setStage(STAGE stage) {
//		this.stage = stage;
//	}
//
//	private int currentDegress = 0;
//
//	private int degreeOffset = 0;
//
//	private float padding = 30;
//
//	public int getDegreeOffset() {
//		return degreeOffset;
//	}
//
//	public void setDegreeOffset(int degreeOffset) {
//		this.degreeOffset = degreeOffset;
//	}
//
//	private float radius = -1;
//
//	public float getRadius() {
//		return radius;
//	}
//
//	public void setRadius(float radius) {
//		this.radius = radius;
//	}
//
//	private int marginItem = 10;
//
//	public int getMarginItem() {
//		return marginItem;
//	}
//
//	public void setMarginItem(int marginItem) {
//		this.marginItem = marginItem;
//	}
//
//	private SimpleBaseGameActivity context;
//	Sprite openSprite;
//	ButtonBubleSprite closeSprite;
//	Sprite derectionMenuSprite;
//
//	protected ArrayList<IMenuItem> mMenuItems = new ArrayList<IMenuItem>();
//
//	private IOnMenuItemClickListener mOnMenuItemClickListener;
//
//	private IMenuAnimator mMenuAnimator = IMenuAnimator.DEFAULT;
//
//	private IMenuItem mSelectedMenuItem;
//
//	private float camera_height;
//
//	float camera_width;
//
//	public ButtonBubleSprite quatangSprite;
//
//	public int giftType = 0;
//
//	private TiledTextureRegion menuPurpleTextureRegion;
//
//	private TiledTextureRegion menuControlBgTextureRegion;
//
//	private TiledTextureRegion menuControlDerectionTextureRegion;
//
//	private TiledTextureRegion quatangRegion;
//
//	private TiledTextureRegion rulerMenuTextureRegion;
//
//	private TiledTextureRegion iconMapMenuRegion;
//
//	private TiledTextureRegion menuExitRegion;
//
//	private TiledTextureRegion menuSettingsRegion;
//
//	public MenuHUD(SimpleBaseGameActivity context, ZoomCamera camera, float ratio) {
//		this.context = context;
//		this.camera_height = camera.getHeight();
//		this.camera_width = camera.getHeight();
//		this.stage = STAGE.HIDE;
//		this.ratio = ratio;
//		this.setOnSceneTouchListener(this);
//		this.setOnAreaTouchListener(this);
//		this.setTouchAreaBindingOnActionDownEnabled(true);
//		this.setCamera(camera);
//	}
//	
//	@Override
//	public void onAttackScene(){
//		super.onAttackScene();
//	}
//
//	public IOnMenuItemClickListener getOnMenuItemClickListener() {
//		return this.mOnMenuItemClickListener;
//	}
//
//	public void setOnMenuItemClickListener(final IOnMenuItemClickListener pOnMenuItemClickListener) {
//		this.mOnMenuItemClickListener = pOnMenuItemClickListener;
//	}
//
//	public int getMenuItemCount() {
//		return this.mMenuItems.size();
//	}
//
//	ArrayList<BitmapTextureAtlas> atlas = new ArrayList<BitmapTextureAtlas>();
//
//	private float ratio;
//
//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		context.getEngine().runOnUpdateThread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				clearChildScene();
//				detachChildren();
//				detachSelf();
//
//				for (BitmapTextureAtlas bitmapTextureAtlas : atlas) {
//					bitmapTextureAtlas.unload();
//				}
//
//			}
//		});
//	}
//
//	@Override
//	public void onLoadResource() {
//		// TODO Auto-generated method stub
//		FontFactory.setAssetBasePath("font/");
//		BitmapTextureAtlas menuArrowBitmapTexture = new BitmapTextureAtlas(context.getTextureManager(),
//				(int) (128 * ratio), (int) (106 * ratio), TextureOptions.BILINEAR);
//		BitmapTextureAtlas menuSettingsBitmapTexture = new BitmapTextureAtlas(context.getTextureManager(),
//				(int) (512 * ratio), (int) (120 * ratio), TextureOptions.BILINEAR);
//		menuExitRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(menuArrowBitmapTexture,
//				context, R.drawable.arrow, 0, 0, 1, 1);
//		menuSettingsRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(menuSettingsBitmapTexture,
//				context, R.drawable.settings, 0, 0, 1, 1);
//		BitmapTextureAtlas mapMenuAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (128 * ratio),
//				(int) (128 * ratio), TextureOptions.BILINEAR);
//		iconMapMenuRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(mapMenuAtlas, context,
//				R.drawable.icon_map, 0, 0, 1, 1);
//		BitmapTextureAtlas quatangAtlas = new BitmapTextureAtlas(context.getTextureManager(), (int) (128 * ratio),
//				(int) (128 * ratio), TextureOptions.BILINEAR);
//		quatangRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(quatangAtlas, context,
//				R.drawable.quatang, 0, 0, 1, 1);
//		quatangAtlas.load();
//		mapMenuAtlas.load();
//		menuArrowBitmapTexture.load();
//		menuSettingsBitmapTexture.load();
//		atlas.add(quatangAtlas);
//		atlas.add(mapMenuAtlas);
//		atlas.add(menuArrowBitmapTexture);
//		atlas.add(menuSettingsBitmapTexture);
//
//		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
//		BitmapTextureAtlas menuBackgroundTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
//				(int) (ratio * 512), (int) (ratio * 512), TextureOptions.BILINEAR);
//		menuPurpleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(
//				menuBackgroundTextureAtlas, context, R.drawable.purple_circle, 0, 0, 1, 1);
//		menuBackgroundTextureAtlas.load();
//		atlas.add(menuBackgroundTextureAtlas);
//
//		BitmapTextureAtlas menuCloseTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
//				(int) (ratio * 128), (int) (ratio * 128), TextureOptions.BILINEAR);
//		menuControlBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(
//				menuCloseTextureAtlas, context, R.drawable.yellow_menu, 0, 0, 1, 1);
//		menuCloseTextureAtlas.load();
//		atlas.add(menuCloseTextureAtlas);
//
//		BitmapTextureAtlas menuControlDerectionTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
//				(int) (ratio * 128), (int) (ratio * 128), TextureOptions.BILINEAR);
//		menuControlDerectionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(
//				menuControlDerectionTextureAtlas, context, R.drawable.direction_menu, 0, 0, 1, 1);
//		menuControlDerectionTextureAtlas.load();
//		atlas.add(menuControlDerectionTextureAtlas);
//
//		BitmapTextureAtlas rulermenuControlDerectionTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),
//				(int) (ratio * 2), (int) (ratio * 256), TextureOptions.BILINEAR);
//		rulerMenuTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromResource(
//				rulermenuControlDerectionTextureAtlas, context, R.drawable.purple_circle_ruler, 0, 0, 1, 1);
//		rulermenuControlDerectionTextureAtlas.load();
//		atlas.add(rulermenuControlDerectionTextureAtlas);
//	}
//
//	public static final int MENU_MAP = 0;
//	public static final int MENU_SETTINGS = 1;
//	public static final int MENU_EXIT = 2;
//
//	private IButtonClick quatangClickListenner;
//
//	public void initMenu() {
//		MenuItemSprite mapSprite = new MenuItemSprite(MENU_MAP, iconMapMenuRegion,
//				context.getVertexBufferObjectManager());
//		mapSprite.onUnselected();
//		MenuItemSprite exitSprite = new MenuItemSprite(MENU_EXIT, menuExitRegion,
//				context.getVertexBufferObjectManager());
//		exitSprite.onUnselected();
//		MenuItemSprite settingsSprite = new MenuItemSprite(MENU_SETTINGS, menuSettingsRegion,
//				context.getVertexBufferObjectManager());
//		settingsSprite.onUnselected();
//		ArrayList<IMenuItem> list = new ArrayList<IMenuItem>();
//		list.add(exitSprite);
//		list.add(mapSprite);
//		list.add(settingsSprite);
//		radius = menuPurpleTextureRegion.getWidth();
//		degreeOffset = (90) / (list.size()) / 2;
//		closeSprite = new ButtonBubleSprite(-menuControlBgTextureRegion.getWidth(), camera_height, null, null,
//				menuControlBgTextureRegion, context.getVertexBufferObjectManager());
//		closeSprite.setOnClickListenner(new IButtonClick() {
//
//			@Override
//			public void onclick(IAreaShape view) {
//				// TODO Auto-generated method stub
//				if (stage == STAGE.HIDE) {
//					openMenu();
//				} else if (stage == STAGE.SHOW) {
//					closeMenu();
//				}
//			}
//		});
//		closeSprite.registerEntityModifier(new MoveModifier(1f, closeSprite.getX(), 0, closeSprite.getY(),
//				camera_height - menuControlBgTextureRegion.getHeight()));
//
//		derectionMenuSprite = new Sprite(menuControlBgTextureRegion.getWidth() / 2
//				- menuControlDerectionTextureRegion.getWidth() / 2, +menuControlBgTextureRegion.getHeight() / 2
//				- menuControlDerectionTextureRegion.getHeight() / 2, menuControlDerectionTextureRegion,
//				context.getVertexBufferObjectManager());
//		closeSprite.attachChild(derectionMenuSprite);
//		this.registerTouchArea(closeSprite);
//		quatangSprite = new ButtonBubleSprite(camera_width - quatangRegion.getWidth(), camera_height
//				- quatangRegion.getHeight(), null, null, quatangRegion, context.getVertexBufferObjectManager());
//		quatangSprite.setOnClickListenner(quatangClickListenner);
//		openSprite = new Sprite(-menuPurpleTextureRegion.getWidth(), camera_height, menuPurpleTextureRegion,
//				context.getVertexBufferObjectManager());
//		padding = (radius - rulerMenuTextureRegion.getHeight()) / 2;
//		for (int i = 0; i < list.size(); i++) {
//			addMenuItem(list.get(i), i);
//		}
//		quatangSprite.setVisible(false);
//		quatangSprite.setEnable(false);
//		closeMenu();
//		this.attachChild(quatangSprite);
//		this.registerTouchArea(quatangSprite);
//		this.attachChild(closeSprite);
//		this.attachChild(openSprite);
//
//	}
//
//	private void addMenuItem(final IMenuItem pMenuItem, int i) {
//		if (i == 0)
//			currentDegress += degreeOffset;
//		else
//			currentDegress += 2 * degreeOffset;
//		float x = +(float) ((radius - padding) * Math.cos(Math.toRadians(currentDegress))) - pMenuItem.getWidth() / 2;
//		float y = radius - (float) ((radius - padding) * Math.sin(Math.toRadians(currentDegress)))
//				- pMenuItem.getHeight() / 2;
//		pMenuItem.setPosition(x, y);
//		this.mMenuItems.add(pMenuItem);
//		openSprite.attachChild(pMenuItem);
//		this.registerTouchArea(pMenuItem);
//	}
//
//	public void clearMenuItems() {
//		for (int i = this.mMenuItems.size() - 1; i >= 0; i--) {
//			final IMenuItem menuItem = this.mMenuItems.remove(i);
//			this.detachChild(menuItem);
//			this.unregisterTouchArea(menuItem);
//		}
//	}
//
//	@Override
//	public Scene getChildScene() {
//		return super.getChildScene();
//	}
//
//	@Override
//	public void setChildScene(final Scene pChildScene, final boolean pModalDraw, final boolean pModalUpdate,
//			final boolean pModalTouch) throws IllegalArgumentException {
//		super.setChildScene(pChildScene, pModalDraw, pModalUpdate, pModalTouch);
//	}
//
//	@Override
//	public void clearChildScene() {
//		if (this.getChildScene() != null) {
//			this.getChildScene().reset();
//			super.clearChildScene();
//		}
//	}
//
//	public void setMenuAnimator(final IMenuAnimator pMenuAnimator) {
//		this.mMenuAnimator = pMenuAnimator;
//	}
//
//	@Override
//	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final ITouchArea pTouchArea,
//			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
//		final IMenuItem menuItem = ((IMenuItem) pTouchArea);
//
//		switch (pSceneTouchEvent.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			Log.e("", "action ACTION_DOWN");
//		case MotionEvent.ACTION_MOVE:
//			Log.e("", "action ACTION_MOVE");
//			if (this.mSelectedMenuItem != null && this.mSelectedMenuItem != menuItem) {
//				this.mSelectedMenuItem.onUnselected();
//			}
//			this.mSelectedMenuItem = menuItem;
//			this.mSelectedMenuItem.onSelected();
//			break;
//		case MotionEvent.ACTION_UP:
//			Log.e("", "action ACTION_UP");
//			if (this.mOnMenuItemClickListener != null && stage == STAGE.SHOW) {
//				SoundManager.playClick();
//				final boolean handled = this.mOnMenuItemClickListener.onMenuItemClicked(this, menuItem,
//						pTouchAreaLocalX, pTouchAreaLocalY);
//				menuItem.onUnselected();
//				this.mSelectedMenuItem = null;
//				closeMenu();
//				return handled;
//			}
//			break;
//		case MotionEvent.ACTION_CANCEL:
//			Log.e("", "action cancel");
//			menuItem.onUnselected();
//			this.mSelectedMenuItem = null;
//			break;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
//		if (this.mSelectedMenuItem != null) {
//			this.mSelectedMenuItem.onUnselected();
//			this.mSelectedMenuItem = null;
//		} else if (stage == STAGE.SHOW) {
//			closeMenu();
//		}
//		return false;
//	}
//
//	@Override
//	public void back() {
//	}
//
//	public void closeMenu() {
//		if (openSprite != null && stage == STAGE.SHOW) {
//			openSprite.registerEntityModifier(new MoveModifier(0.3f, openSprite.getX(), -openSprite.getWidth(),
//					openSprite.getY(), camera_height, new IEntityModifierListener() {
//
//						@Override
//						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
//							stage = STAGE.ANIMATE;
//						}
//
//						@Override
//						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
//							stage = STAGE.HIDE;
//							pItem.setVisible(false);
//						}
//					}));
//			if (derectionMenuSprite != null)
//				derectionMenuSprite.registerEntityModifier(new RotationModifier(0.5f, 180, 0));
//		}
//	}
//
//	public void openMenu() {
//		currentDegress = 0;
//		if (openSprite != null)
//			openSprite.registerEntityModifier(new MoveModifier(0.3f, openSprite.getX(), 0, openSprite.getY(),
//					camera_height - menuPurpleTextureRegion.getHeight(), new IEntityModifierListener() {
//
//						@Override
//						public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
//							stage = STAGE.ANIMATE;
//							pItem.setVisible(true);
//						}
//
//						@Override
//						public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
//							stage = STAGE.SHOW;
//						}
//					}));
//		if (derectionMenuSprite != null)
//			derectionMenuSprite.registerEntityModifier(new RotationAtModifier(0.5f, 0, 180, derectionMenuSprite
//					.getWidth() / 2, derectionMenuSprite.getHeight() / 2));
//		if (getChildCount() > 1) {
//
//		} else {
//			if (openSprite != null)
//				this.attachChild(openSprite);
//		}
//		this.stage = STAGE.SHOW;
//
//	}
//
//	public void hideMe() {
//		if (this.stage == STAGE.SHOW) {
//			closeMenu();
//		}
//		if (derectionMenuSprite != null && closeSprite != null) {
//			derectionMenuSprite.setVisible(false);
//			closeSprite.setVisible(false);
//		}
//		this.unregisterTouchArea(closeSprite);
//	}
//
//	public void showMe() {
//		if (derectionMenuSprite != null && closeSprite != null) {
//			derectionMenuSprite.setVisible(true);
//			closeSprite.setVisible(true);
//		}
//		this.registerTouchArea(closeSprite);
//	}
//
//	public static interface IOnMenuItemClickListener {
//		// ===========================================================
//		// Constants
//		// ===========================================================
//
//		// ===========================================================
//		// Methods
//		// ===========================================================
//
//		public boolean onMenuItemClicked(final HUD pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX,
//				final float pMenuItemLocalY);
//	}
//
//	public void setEventClickGiftType(int giftType) {
//		this.giftType = giftType;
//		if (giftType != 0) {
//			quatangSprite.setVisible(true);
//			quatangSprite.setEnable(true);
//		}
//	}
//
//	public IButtonClick getQuatangClickListenner() {
//		return quatangClickListenner;
//	}
//
//	public void setQuatangClickListenner(IButtonClick quatangClickListenner) {
//		this.quatangClickListenner = quatangClickListenner;
//	}
//
//	@Override
//	public void onCreate() {
//		// TODO Auto-generated method stub
//		
//	}
//}