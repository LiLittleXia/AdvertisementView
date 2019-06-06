package com.lz.advertisementview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2019/6/6.
 */

public class AdImageViewVersion1 extends AppCompatImageView {

    private static final String TAG = "AdImageViewVersion1";

    private int mMinDy;
    private Bitmap mBitmap;
    private RectF mBitmapRectF;
    private int mDy;


    public AdImageViewVersion1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: ---" + w + "---" + h + "---" + oldw + "---" + oldh);
        mMinDy = h;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        mBitmap = drawableToBitamp(drawable);
        mBitmapRectF = new RectF(0, 0, w, mBitmap.getHeight() * w / mBitmap.getWidth());
        Log.i(TAG, "RectF: " + "---" + w + "---" + (mBitmap.getHeight() * w / mBitmap.getWidth()));

    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Log.i(TAG, "drawableToBitamp: "+w+"==="+h);
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }
        Log.i(TAG, "setDy: dy="+dy);
        mDy = dy - mMinDy;
        Log.i(TAG, "setDy: mDy="+mDy);
        if (mDy <= 0) {
            mDy = 0;
        }
        Log.i(TAG, "setDy: mBitmapRectF.height() - mMinDy="+(mBitmapRectF.height() - mMinDy));
        if (mDy > mBitmapRectF.height() - mMinDy) {
            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        canvas.save();
        canvas.translate(0, -mDy);
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }

}
