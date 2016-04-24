package tseng.min.c.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.ExifInterface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * https://github.com/maonanyue/bitmapfun/tree/master/src/com/example/android/bitmapfun/util
 * Created by Neo on 2016/3/21.
 */
public class BitmpaUtil {
	private static final String TAG = BitmpaUtil.class.getSimpleName();
	private static final int DEFAULT_COMPRESS_QUALITY = 90;
	private static final int INDEX_ORIENTATION = 0;

	//@formatter:off
	@SuppressLint("InlinedApi")
	private static final String[] EXIF_TAGS = new String[]{
			ExifInterface.TAG_APERTURE,
			ExifInterface.TAG_DATETIME,
			ExifInterface.TAG_EXPOSURE_TIME,
			ExifInterface.TAG_FLASH,
			ExifInterface.TAG_FOCAL_LENGTH,
			ExifInterface.TAG_GPS_ALTITUDE,
			ExifInterface.TAG_GPS_ALTITUDE_REF,
			ExifInterface.TAG_GPS_DATESTAMP,
			ExifInterface.TAG_GPS_LATITUDE,
			ExifInterface.TAG_GPS_LATITUDE_REF,
			ExifInterface.TAG_GPS_LONGITUDE,
			ExifInterface.TAG_GPS_LONGITUDE_REF,
			ExifInterface.TAG_GPS_PROCESSING_METHOD,
			ExifInterface.TAG_GPS_TIMESTAMP,
			ExifInterface.TAG_ISO,
			ExifInterface.TAG_MAKE,
			ExifInterface.TAG_MODEL,
			ExifInterface.TAG_WHITE_BALANCE,
	};

	public Bitmap textAsBitmap(String text, float textSize, int textColor) {
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		paint.setColor(textColor);
		paint.setTextAlign(Paint.Align.LEFT);
		float baseline = -paint.ascent(); // ascent() is negative
		int width = (int) (paint.measureText(text) + 0.5f); // round
		int height = (int) (baseline + paint.descent() + 0.5f);
		Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawText(text, 0, baseline, paint);
		return image;
	}

	public Bitmap textToBitmap(String text, float textSize, int textColor) {
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		paint.setColor(textColor);
		paint.setTextAlign(Paint.Align.LEFT);
		float baseline = -paint.ascent(); // ascent() is negative
		int width = (int) (paint.measureText(text) + 0.5f); // round
		int height = (int) (baseline + paint.descent() + 0.5f);
		Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.drawBitmap(b, 0, 0, null);
		TextPaint textPaint = new TextPaint();
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(16.0F);
		StaticLayout sl = new StaticLayout(text, textPaint, b.getWidth() - 8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
		c.translate(6, 40);
		sl.draw(c);
		return b;
	}

	public static Bitmap drawText(String text, int textWidth, int textSize) {
// Get text dimensions
		TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(textSize);
		StaticLayout mTextLayout = new StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
// Create bitmap and canvas to draw to
		Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.RGB_565);
		Canvas c = new Canvas(b);
// Draw background
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		c.drawPaint(paint);
// Draw text
		c.save();
		c.translate(0, 0);
		mTextLayout.draw(c);
		c.restore();
		return b;
	}

	public static String convertBitmapToBase64(Bitmap bitmap) {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		final byte[] byteArray = byteArrayOutputStream.toByteArray();
		return Base64.encodeToString(byteArray, Base64.DEFAULT);
	}

	public static String convertImageToBase64(String img_file) {
		return convertBitmapToBase64(BitmapFactory.decodeFile(img_file));
	}

	public static Bitmap createBitmap(Bitmap bitmap, Matrix m) {
		// TODO: Re-implement createBitmap to avoid ARGB -> RBG565 conversion on platforms
		// prior to honeycomb.
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
	}

//    public Bitmap getBitmap(Uri uri, int width, int height) {
//        Bitmap bitmap = decodeBitmap(uri, width, height);
//        // Rotate the decoded bitmap according to its orientation if it's necessary.
//        if (bitmap != null) {
//            int orientation = getOrientation(uri);
//            if (orientation != 0) {
//                Matrix m = new Matrix();
//                m.setRotate(orientation);
//                Bitmap transformed = createBitmap(bitmap, m);
//                bitmap.recycle();
//                return transformed;
//            }
//        }
//        return bitmap;
//    }

	public static Bitmap tryDecodeFile(File imageFile, BitmapFactory.Options options) {
		Log.d(TAG, "tryDecodeFile imageFile=" + imageFile);
		int trials = 0;
		while (trials < 4) {
			try {
				Bitmap res = BitmapFactory.decodeFile(imageFile.getPath(), options);
				if (res == null) {
					Log.d(TAG, "tryDecodeFile res=null");
				} else {
					Log.d(TAG, "tryDecodeFile res width=" + res.getWidth() + " height=" + res.getHeight());
				}
				return res;
			} catch (OutOfMemoryError e) {
				if (options == null) {
					options = new BitmapFactory.Options();
					options.inSampleSize = 1;
				}
				Log.w(TAG, "tryDecodeFile Could not decode file with inSampleSize=" + options.inSampleSize + ", try with inSampleSize=" + (options.inSampleSize + 1), e);
				options.inSampleSize++;
				trials++;
			}
		}
		Log.w(TAG, "tryDecodeFile Could not decode the file after " + trials + " trials, returning null");
		return null;
	}

	public static Bitmap asMutable(Bitmap bitmap) throws IOException {
		// This is the file going to use temporally to dump the bitmap bytes
		File tmpFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), null);
		Log.d(TAG, "getImmutable tmpFile=" + tmpFile);

		// Open it as an RandomAccessFile
		RandomAccessFile randomAccessFile = new RandomAccessFile(tmpFile, "rw");

		// Get the width and height of the source bitmap
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		// Dump the bytes to the file.
		// This assumes the source bitmap is loaded using options.inPreferredConfig = Config.ARGB_8888 (hence the value of 4 bytes per pixel)
		FileChannel channel = randomAccessFile.getChannel();
		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, width * height * 4);
		bitmap.copyPixelsToBuffer(buffer);

		// Recycle the source bitmap, this will be no longer used
		bitmap.recycle();

		// Create a new mutable bitmap to load the bitmap from the file
		bitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());

		// Load it back from the temporary buffer
		buffer.position(0);
		bitmap.copyPixelsFromBuffer(buffer);

		// Cleanup
		channel.close();
		randomAccessFile.close();
		tmpFile.delete();

		return bitmap;
	}

	public static void copyExifTags(File sourceFile, File destFile) throws IOException {
		Log.d(TAG, "copyExifTags sourceFile=" + sourceFile + " destFile=" + destFile);
		ExifInterface sourceExifInterface = new ExifInterface(sourceFile.getPath());
		ExifInterface destExifInterface = new ExifInterface(destFile.getPath());
		boolean atLeastOne = false;
		for (String exifTag : EXIF_TAGS) {
			String value = sourceExifInterface.getAttribute(exifTag);
			if (value != null) {
				atLeastOne = true;
				destExifInterface.setAttribute(exifTag, value);
			}
		}
		if (atLeastOne) destExifInterface.saveAttributes();
	}

	public static Point getDimensions(File bitmapFile) {
		Log.d(TAG, "getDimensions bitmapFile=" + bitmapFile);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(bitmapFile.getPath(), options);
		int width = options.outWidth;
		int height = options.outHeight;
		Point res = new Point(width, height);
		Log.d(TAG, "getDimensions res=" + res);
		return res;
	}

	public static int getExifRotation(File bitmapFile) {
		Log.d(TAG, "getExifRotation bitmapFile=" + bitmapFile);
		ExifInterface exifInterface;
		try {
			exifInterface = new ExifInterface(bitmapFile.getPath());
		} catch (IOException e) {
			Log.w(TAG, "getExifRotation Could not read exif info: returning 0", e);
			return 0;
		}
		int exifOrientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
		Log.d(TAG, "getExifRotation orientation=" + exifOrientation);
		int res = 0;
		switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				res = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				res = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				res = 270;
				break;
		}
		Log.d(TAG, "getExifRotation res=" + res);
		return res;
	}

	public static Bitmap createThumbnail(File bitmapFile, int maxWidth, int maxHeight) {
		Log.d(TAG, "createThumbnail imageFile=" + bitmapFile + " maxWidth=" + maxWidth + " maxHeight=" + maxHeight);
		// Get exif rotation
		int rotation = getExifRotation(bitmapFile);

		// Determine optimal inSampleSize
		Point originalDimensions = getDimensions(bitmapFile);
		int width = originalDimensions.x;
		int height = originalDimensions.y;
		int inSampleSize = 1;
		if (rotation == 90 || rotation == 270) {
			// In these 2 cases we invert the measured dimensions because the bitmap is rotated
			width = originalDimensions.y;
			height = originalDimensions.x;
		}
		int widthRatio = width / maxWidth;
		int heightRatio = height / maxHeight;

		// Take the max, because we don't care if one of the returned thumbnail's side is smaller
		// than the specified maxWidth/maxHeight.
		inSampleSize = Math.max(widthRatio, heightRatio);
		Log.d(TAG, "createThumbnail using inSampleSize=" + inSampleSize);

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = inSampleSize;

		Bitmap res = tryDecodeFile(bitmapFile, options);
		if (res == null) {
			Log.w(TAG, "createThumbnail Could not decode file, returning null");
			return null;
		}

		// Rotate if necessary
		if (rotation != 0) {
			Log.d(TAG, "createThumbnail rotating thumbnail");
			Matrix matrix = new Matrix();
			matrix.postRotate(rotation);
			Bitmap rotatedBitmap = null;
			try {
				rotatedBitmap = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, false);
				res.recycle();
				res = rotatedBitmap;
			} catch (OutOfMemoryError exception) {
				Log.w(TAG, "createThumbnail Could not rotate bitmap, keeping original orientation", exception);
			}
		}
		Log.d(TAG, "createThumbnail res width=" + res.getWidth() + " height=" + res.getHeight());

		return res;
	}


}
