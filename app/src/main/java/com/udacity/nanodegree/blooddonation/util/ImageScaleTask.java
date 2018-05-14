package com.udacity.nanodegree.blooddonation.util;

import android.graphics.*;
import android.media.ExifInterface;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by BloodyBadboy on Jul, 2017.
 */

public class ImageScaleTask extends AsyncTask<Void, Void, Void> {
  private static final float MAX_HEIGHT = 1024.0f;
  private static final float MAX_WIDTH = 1024.0f;

  private File mImageFile;
  private File mCompressedFile;
  private Bitmap mScaledBitmap = null;
  private boolean compressSuccess = false;
  private OnCompletedListener listener;

  public ImageScaleTask(File imageFile, File outputFile, OnCompletedListener listener) {
    this.mCompressedFile = outputFile;
    this.mImageFile = imageFile;
    this.listener = listener;
  }

  @Override
  protected Void doInBackground(Void... voids) {
    compressImage();
    return null;
  }

  private void compressImage() {

    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    Bitmap mBitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(), options);

    int actualHeight = options.outHeight;
    int actualWidth = options.outWidth;
    float maxHeight = MAX_HEIGHT;
    float maxWidth = MAX_WIDTH;
    float imgRatio = actualWidth / actualHeight;
    float maxRatio = maxWidth / maxHeight;

    if (actualHeight > maxHeight || actualWidth > maxWidth) {
      if (imgRatio < maxRatio) {
        imgRatio = maxHeight / actualHeight;
        actualWidth = (int) (imgRatio * actualWidth);
        actualHeight = (int) maxHeight;
      } else if (imgRatio > maxRatio) {
        imgRatio = maxWidth / actualWidth;
        actualHeight = (int) (imgRatio * actualHeight);
        actualWidth = (int) maxWidth;
      } else {
        actualHeight = (int) maxHeight;
        actualWidth = (int) maxWidth;
      }
    }

    options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
    options.inJustDecodeBounds = false;
    options.inDither = false;
    options.inPurgeable = true;
    options.inInputShareable = true;
    options.inTempStorage = new byte[16 * 1024];

    try {
      mBitmap = BitmapFactory.decodeFile(mImageFile.getAbsolutePath(), options);
      mScaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
    } catch (OutOfMemoryError exception) {
      exception.printStackTrace();
    }

    if (mBitmap != null && mScaledBitmap != null) {

      float ratioX = actualWidth / (float) options.outWidth;
      float ratioY = actualHeight / (float) options.outHeight;
      float middleX = actualWidth / 2.0f;
      float middleY = actualHeight / 2.0f;

      Matrix scaleMatrix = new Matrix();
      scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

      Canvas canvas = new Canvas(mScaledBitmap);
      canvas.setMatrix(scaleMatrix);
      canvas.drawBitmap(mBitmap, middleX - mBitmap.getWidth() / 2,
          middleY - mBitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

      ExifInterface exif;
      try {
        exif = new ExifInterface(mImageFile.getAbsolutePath());

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (orientation == 6) {
          matrix.postRotate(90);
        } else if (orientation == 3) {
          matrix.postRotate(180);
        } else if (orientation == 8) {
          matrix.postRotate(270);
        }
        mScaledBitmap = Bitmap.createBitmap(mScaledBitmap, 0, 0, mScaledBitmap.getWidth(),
            mScaledBitmap.getHeight(), matrix, true);
      } catch (IOException e) {
        e.printStackTrace();
      }
      FileOutputStream mFileOutputStream = null;
      try {
        mFileOutputStream = new FileOutputStream(mCompressedFile);
        compressSuccess = mScaledBitmap.compress(Bitmap.CompressFormat.JPEG, 90, mFileOutputStream);
        mFileOutputStream.flush();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (mFileOutputStream != null) {
            mFileOutputStream.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {
      final int heightRatio = Math.round((float) height / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }
    final float totalPixels = width * height;
    final float totalReqPixelsCap = reqWidth * reqHeight * 2;

    while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
      inSampleSize++;
    }

    return inSampleSize;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    listener.onCompleted(compressSuccess, mScaledBitmap);
  }

  public interface OnCompletedListener {
    void onCompleted(boolean success, Bitmap scaledBitmap);
  }
}

