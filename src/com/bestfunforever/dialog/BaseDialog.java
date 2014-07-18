package com.bestfunforever.dialog;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.graphics.Typeface;
import android.util.Log;

import com.bestfunforever.andengine.uikit.entity.IClick;
import com.bestfunforever.bearforkids.Entity.BubbleSprite;

public class BaseDialog extends Dialog {

	private BitmapTextureAtlas mBtnBitmapTextureAtlas;
	private TextureRegion mBtnTextureRegion;
	private BitmapTextureAtlas mBgBitmapTextureAtlas;
	private TextureRegion mBgTextureRegion;
	private Font mTileFont;
	private BitmapTextureAtlas mTeddyBitmapTextureAtlas;
	private TextureRegion mTeddyTextureRegion;
	protected Font mFont;

	private float minHeight = 200;
	private float paddingTopBottom = 20 * ratio;
	private float paddingLeftRight = 13 * ratio;
	private float marginElement = 10 * ratio;
	private String title = "";
	private BubbleSprite mLeftButton;
	private BubbleSprite mRightButton;
	private Text mTitle;
	private float totalHeigt;

	public BaseDialog(SimpleBaseGameActivity context, Camera camera, float ratio) {
		super(context, camera, ratio);
		setOpenAnim(new ScaleModifier(1, 1, 1, 0, 1));
		setCloseAnim(new ScaleModifier(0.4f, 1, 1, 1, 0));
	}

	@Override
	public void onLoadResource() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBtnBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 229, 76,
				TextureOptions.BILINEAR);
		this.mBtnTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBtnBitmapTextureAtlas,
				context, "btn.png", 0, 0); // 64x32
		this.mBtnBitmapTextureAtlas.load();

		this.mBgBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 527, 388,
				TextureOptions.BILINEAR);
		this.mBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBgBitmapTextureAtlas,
				context, "dialogbg.png", 0, 0); // 64x32
		this.mBgBitmapTextureAtlas.load();

		this.mTeddyBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(), 640, 960,
				TextureOptions.BILINEAR);
		this.mTeddyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mTeddyBitmapTextureAtlas, context, "teddy.png", 0, 0); // 64x32
		this.mTeddyBitmapTextureAtlas.load();

		this.mTileFont = FontFactory.create(context.getFontManager(), context.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio), Typeface.create(Typeface.DEFAULT, Typeface.BOLD), (40 * ratio), true,
				android.graphics.Color.WHITE);
		this.mTileFont.load();

		this.mFont = FontFactory.create(context.getFontManager(), context.getTextureManager(), (int) (256 * ratio),
				(int) (256 * ratio), Typeface.create(Typeface.DEFAULT, Typeface.BOLD), (int) (30 * ratio));
		this.mFont.load();
	}

	@Override
	public void onDesTroy() {
		mBtnBitmapTextureAtlas.unload();
		mBgBitmapTextureAtlas.unload();
		mTeddyBitmapTextureAtlas.unload();
		mTileFont.unload();
		Log.d("", "destroy dialog ");
	}

	@Override
	public void onCreateDialog() {
		minHeight = 200;
		paddingTopBottom = 20 * ratio;
		paddingLeftRight = 13 * ratio;
		marginElement = 10 * ratio;
		totalHeigt = paddingTopBottom;
		bgSprite = new Sprite(0, 0, mBgTextureRegion.getWidth() * ratio, mBgTextureRegion.getHeight() * ratio,
				mBgTextureRegion, context.getVertexBufferObjectManager());
		float widhtMax = bgSprite.getWidth();
		//for check
		title = "a";
		mTitle = new Text(0, paddingTopBottom, mTileFont, title, 10, new TextOptions(AutoWrap.WORDS, widhtMax,
				HorizontalAlign.CENTER), context.getVertexBufferObjectManager());
		mTitle.setColor(Color.RED);
		totalHeigt += mTitle.getHeight() + marginElement;
		Rectangle mContentRect = new Rectangle(paddingLeftRight, totalHeigt, widhtMax - 2 * paddingLeftRight, 0,
				context.getVertexBufferObjectManager());
		mContentRect.setColor(Color.TRANSPARENT);
		totalHeigt += initContent(mContentRect) + paddingTopBottom;
		invalidate();

		Sprite teddyTop = new Sprite(-20, -20, mTeddyTextureRegion.getWidth() * ratio, mTeddyTextureRegion.getHeight()
				* ratio, mTeddyTextureRegion, context.getVertexBufferObjectManager());
		Sprite teddyBottom = new Sprite(-20, -20, mTeddyTextureRegion.getWidth() * ratio,
				mTeddyTextureRegion.getHeight() * ratio, mTeddyTextureRegion, context.getVertexBufferObjectManager());
		teddyBottom.setPosition(bgSprite.getWidth() - teddyBottom.getWidth() + 20,
				bgSprite.getHeight() - teddyBottom.getHeight() + 20);

		bgSprite.attachChild(teddyTop);
		bgSprite.attachChild(teddyBottom);
		bgSprite.attachChild(mTitle);
		bgSprite.attachChild(mContentRect);

		bgSprite.setPosition(mCamera.getWidth() / 2 - bgSprite.getWidth() / 2,
				mCamera.getHeight() / 2 - bgSprite.getHeight() / 2);
		attachChild(bgSprite);
	}

	public void setTitle(String title) {
		this.title = title;
		mTitle.setText(title);
	}

	public void setLeftButton(String label, final com.bestfunforever.dialog.IDialog.IClick iclick) {
		setLeftButton(label, mBtnTextureRegion, iclick);
	}

	@Override
	protected void invalidate() {
		super.invalidate();
		if (mLeftButton != null || mRightButton != null) {
			if (mLeftButton != null) {
				totalHeigt += mLeftButton.getHeight() / 2 - 5 * ratio;
				if (mRightButton != null) {
					mLeftButton.setPosition(bgSprite.getWidth() / 2 - mLeftButton.getWidth() - 10 * ratio, totalHeigt
							- mLeftButton.getHeight() / 2);
					mRightButton.setPosition(bgSprite.getWidth() / 2 + 10 * ratio,
							totalHeigt - mRightButton.getHeight() / 2);
				} else {
					mLeftButton.setPosition(bgSprite.getWidth() / 2 - mLeftButton.getWidth() / 2, totalHeigt
							- mLeftButton.getHeight() / 2);
				}
			} else if (mRightButton != null) {
				totalHeigt += mRightButton.getHeight() / 2 - 5 * ratio;
				if (mLeftButton != null) {
					mLeftButton.setPosition(bgSprite.getWidth() / 2 - mLeftButton.getWidth() - 10 * ratio, totalHeigt
							- mLeftButton.getHeight() / 2);
					mRightButton.setPosition(bgSprite.getWidth() / 2 + 10 * ratio,
							totalHeigt - mRightButton.getHeight() / 2);
				} else {
					mRightButton.setPosition(bgSprite.getWidth() / 2 - mLeftButton.getWidth() / 2, totalHeigt
							- mRightButton.getHeight() / 2);
				}
			}
		}
		totalHeigt = totalHeigt > (minHeight + 2 * paddingTopBottom + mTitle.getHeight()) ? totalHeigt : (minHeight + 2
				* paddingTopBottom + mTitle.getHeight());
		bgSprite.setHeight(totalHeigt);
	}

	public void setRightButton(String label, final com.bestfunforever.dialog.IDialog.IClick iclick) {
		setRightButton(label, mBtnTextureRegion, iclick);
	}

	public void setRightButton(String label, ITextureRegion textureRegion,
			final com.bestfunforever.dialog.IDialog.IClick iclick) {
		mRightButton = addButton(false, label, textureRegion, new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				// TODO Auto-generated method stub
				iclick.onClick(BaseDialog.this, view);
			}
		});
		registerTouchArea(mRightButton);
		invalidate();
	}

	public void setLeftButton(String label, ITextureRegion textureRegion,
			final com.bestfunforever.dialog.IDialog.IClick iclick) {
		mLeftButton = addButton(false, label, textureRegion, new IClick() {

			@Override
			public void onCLick(IAreaShape view) {
				// TODO Auto-generated method stub
				iclick.onClick(BaseDialog.this, view);
			}
		});
		registerTouchArea(mLeftButton);
		invalidate();
	}

	public BubbleSprite addButton(boolean left, String label, ITextureRegion textureRegion, IClick iclick) {
		return addButton(0, 0, 162 * ratio, 56 * ratio, bgSprite, left, label, textureRegion, mFont, iclick,
				context.getVertexBufferObjectManager());
	}

	protected float initContent(Rectangle mContentRect) {
		return 0;
	}

}
