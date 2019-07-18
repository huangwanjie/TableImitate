package com.hwj.test.tabledrag.celldrag;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author huangwanjie
 * @date 2019/7/4
 */

public class Render {

  private Particle[] particles;
  private Rect rect;

  public Render(Particle[] particles, Rect rect){
    this.particles = particles;
    this.rect = rect;
  }

  public void calculatePosition( Particle particle, float factor){
      float x = particle.getX() + factor * (int)(Math.random() * rect.width()) * ((float) Math.random() - 0.5f);
      float y = particle.getY() + factor * (int)(Math.random() * rect.height() / 4);
      float alpha = 1f - factor;
      particle.updatePosition(x, y);
      particle.setAlpha(alpha);
  }

  public void draw(Canvas canvas, Paint paint, float factor){
    for (int i = 0; i < particles.length; i++){
      calculatePosition(particles[i], factor);
      particles[i].draw(canvas, paint);
    }
  }
}
