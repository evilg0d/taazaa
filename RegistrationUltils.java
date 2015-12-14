package com.taazaa.registerationapp;

import java.io.ByteArrayOutputStream;

import com.taazaa.registerationapp.utils.Users;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.util.Base64;

public class RegistrationUltils {

	public Bitmap getCircleBitmapFromDB(Users users) {

		Bitmap bitmap = base64ToBitmap(users.getUserImage());

//		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//
////		BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
////		Paint paint = new Paint();
////		paint.setShader(shader);
//		
//
//		Canvas canvas = new Canvas(circleBitmap);
//		
//		final int color = 0xff424242;
//	    final Paint paint = new Paint();
//	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//	    paint.setAntiAlias(true);
//	    canvas.drawARGB(0, 0, 0, 0);
//	    paint.setColor(color);
//	    // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
////	    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
//	    canvas.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, paint);
//	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//	    canvas.drawBitmap(bitmap, rect, rect, paint);
		

		return bitmap;


	}

	public Bitmap base64ToBitmap(String b64) {

		byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);

		return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
	}


	public String bitmapToBase64(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);

		byte[] byteArray = byteArrayOutputStream .toByteArray();

		return (Base64.encodeToString(byteArray, Base64.DEFAULT));

	}

}
