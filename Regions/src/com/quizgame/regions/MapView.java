package com.quizgame.regions;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MapView extends View implements OnTouchListener{

	private Bitmap bitmapMap, bitmapPin;
	private int pinX, pinY, pinWidth, pinHeight, x, y;
	private long lastClick;
	private boolean completed;

	private MapActivity context;

	Random random=new Random();

	public MapView(Context context){
		super(context);
		this.context=(MapActivity)context;
		this.setOnTouchListener(this);

		//		bitmapMap=BitmapFactory.decodeResource(getResources(), R.drawable.map);
		bitmapPin=BitmapFactory.decodeResource(getResources(), R.drawable.pin);
	}

	public MapView(Context context, int pinX, int pinY, int bitmapMapImageResourceId, boolean completed) {
		super(context);
		this.context=(MapActivity)context;
		this.pinX=pinX;
		this.pinY=pinY;
		this.bitmapMap=BitmapFactory.decodeResource(getResources(), bitmapMapImageResourceId);
		this.completed=completed;
		if(!completed){
			this.setOnTouchListener(this);
		}

		//bitmapMap=BitmapFactory.decodeResource(getResources(), R.drawable.map);
		bitmapPin=BitmapFactory.decodeResource(getResources(), R.drawable.pin);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(getResizedBitmap(bitmapMap,
				canvas.getHeight(), canvas.getWidth()), 0, 0, null);

		pinWidth=canvas.getWidth()/10;
		pinHeight=canvas.getHeight()/10;
		x=(pinX*canvas.getHeight())/100-pinHeight/2;
		y=(pinY*canvas.getWidth())/100 -pinWidth/2;

		canvas.drawBitmap(getResizedBitmap(bitmapPin, pinHeight, pinWidth),
				x, y , null);

		if(!completed){
			for(int i=0; i<2; i++){
				canvas.drawBitmap(getResizedBitmap(bitmapPin, pinHeight, pinWidth),
						random.nextInt(canvas.getHeight()-pinHeight), random.nextInt(canvas.getWidth()-pinWidth),
						null);
			}
		}
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP	 
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

		return resizedBitmap;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (System.currentTimeMillis() - lastClick > 1000) {
			//handle single touch event
			lastClick = System.currentTimeMillis();
			if(isCollision(event.getX(), event.getY())){
				context.correctAnswer();
			}
		}

		return true;
	}

	public boolean isCollision(float x2, float y2) {
		return x2 > x && x2 < x + pinHeight && y2 > y && y2 < y + pinWidth;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
