package com.ezetapsdk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static Object createJsonObject(Intent data) {
        return data.getExtras().get("response");
    }

    public static String loadBitmapBase64EncodedFromView(Activity mActivity) {
        View view = mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.draw(c);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }
}