package com.hwj.test.tabledrag.celldrag;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;

/**
 * @author huangwanjie
 * @date 2019/7/4
 */
public class ParticleFactory {


  public final static int PARTICLE_WIDTH = 20;
  public static Particle[] createParticles(Bitmap bitmap, Rect bound){
    Particle[] particles;

    int count_w = bound.width() / PARTICLE_WIDTH;
    int count_h = bound.height() / PARTICLE_WIDTH;

    count_w = count_w > 0 ? count_w : 1;
    count_h = count_h > 0 ? count_h : 1;

    int particleInterval_w = bitmap.getWidth() / count_w;
    int particleInterval_h = bitmap.getHeight() / count_h;

    particles = new Particle[count_w *  count_h];
    for (int i = 0; i < count_h ; i++) {
      for (int i1 = 0; i1 < count_w; i1++) {
        int color = bitmap.getPixel(i1 * particleInterval_w, i * particleInterval_h);
        float x = bound.left + PARTICLE_WIDTH * i1;
        float y = bound.top + PARTICLE_WIDTH * i;
        particles[i * count_w + i1] = new Particle(x, y, color, PARTICLE_WIDTH);
      }
    }
    return particles;
  }
}
