package com.hwj.test.tabledrag.celldrag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author huangwanjie
 * @date 2019/7/4
 */
public class Particle {
  private float x;
  private float y;
  private int color;
  private int radius;
  private float alpha = 1;

  public Particle(float x, float y, int color, int radius) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.radius = radius;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public void setAlpha(float alpha) {
    this.alpha = alpha;
  }

  public void updatePosition(float x, float y){
    this.x = x;
    this.y = y;
  }

  public void draw(Canvas canvas, Paint paint){
    paint.setColor(color);
    paint.setAlpha((int)(Color.alpha(color) * alpha));
    canvas.drawCircle(x,y,radius,paint);
  }
}
