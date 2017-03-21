package br.com.tiagohs.popmovies.ui.tools;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;

import br.com.tiagohs.popmovies.R;

/**
 * Created by Tiago on 20/03/2017.
 */

public class LetterGenerator {

    private static final int NUM_OF_TILE_COLORS = 8;

    private final TextPaint mPaint = new TextPaint();
    private final Rect mBounds = new Rect();
    private final Canvas mCanvas = new Canvas();
    private final char[] mFirstChar = new char[1];

    private final TypedArray mColors;
    private final int mTileLetterFontSize;
    private final Bitmap mDefaultBitmap;

    public LetterGenerator(Context context) {
        final Resources res = context.getResources();

        mPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);

        mColors = res.obtainTypedArray(R.array.letter_tile_colors);
        mTileLetterFontSize = res.getDimensionPixelSize(R.dimen.tile_letter_font_size);

        mDefaultBitmap = BitmapFactory.decodeResource(res, android.R.drawable.sym_def_app_icon);
    }

    /**
     * @param displayName The name used to create the letter for the tile
     * @param key The key used to generate the background color for the tile
     * @param width The desired width of the tile
     * @param height The desired height of the tile
     * @return A {@link Bitmap} that contains a letter used in the English
     *         alphabet or digit, if there is no letter or digit available, a
     *         default image is shown instead
     */
    public Bitmap getLetterTile(String displayName, String key, int width, int height) {
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final char firstChar = displayName.charAt(0);

        final Canvas c = mCanvas;
        c.setBitmap(bitmap);
        c.drawColor(pickColor(key));

        if (isEnglishLetterOrDigit(firstChar)) {
            mFirstChar[0] = Character.toUpperCase(firstChar);
            mPaint.setTextSize(mTileLetterFontSize);
            mPaint.getTextBounds(mFirstChar, 0, 1, mBounds);
            c.drawText(mFirstChar, 0, 1, 0 + width / 2, 0 + height / 2
                    + (mBounds.bottom - mBounds.top) / 2, mPaint);
        } else {
            c.drawBitmap(mDefaultBitmap, 0, 0, null);
        }
        return bitmap;
    }

    /**
     * @param c The char to check
     * @return True if <code>c</code> is in the English alphabet or is a digit,
     *         false otherwise
     */
    private static boolean isEnglishLetterOrDigit(char c) {
        return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z' || '0' <= c && c <= '9';
    }

    /**
     * @param key The key used to generate the tile color
     * @return A new or previously chosen color for <code>key</code> used as the
     *         tile background color
     */
    private int pickColor(String key) {
        // String.hashCode() is not supposed to change across java versions, so
        // this should guarantee the same key always maps to the same color
        final int color = Math.abs(key.hashCode()) % NUM_OF_TILE_COLORS;
        try {
            return mColors.getColor(color, Color.BLACK);
        } finally {
            mColors.recycle();
        }
    }
}
