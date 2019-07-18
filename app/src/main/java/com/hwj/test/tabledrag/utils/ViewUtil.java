package com.hwj.test.tabledrag.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ViewUtil {

  public static int dpToPx(Context context, int dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  public static int pxToDp(Context context, int px) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (px / scale + 0.5f);
  }

  /** 把view转成bitmap */
  public static Bitmap getViewBitmap(View addViewContent) {

    addViewContent.setDrawingCacheEnabled(true);
    Bitmap cacheBitmap = addViewContent.getDrawingCache();
    Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
    cacheBitmap.recycle();
    addViewContent.destroyDrawingCache();

    return bitmap;
  }

  public static int getStatusBarHeight(Context context) {
    Resources resources = context.getResources();
    int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
    int height = resources.getDimensionPixelSize(resourceId);
    return height;
  }

}
