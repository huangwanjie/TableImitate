package com.hwj.test.tabledrag.celldrag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * @author huangwanjie
 * @date 2019/7/4
 */
public class StageView extends View {


  private float factor;
  private Render render;
  private Paint paint;
  private Rect rect;

  public StageView(Context context) {
    super(context);
    init();
  }

  public StageView(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public StageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public Render getRender() {
    return render;
  }

  public void setRender(Render render) {
    this.render = render;
  }

  public float getFactor() {
    return factor;
  }

  public void setFactor(float factor) {
    this.factor = factor;
    invalidate();
  }

  private void init(){
    paint = new Paint();
    paint.setStyle(Style.FILL);
  }

  private Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
    }
  };
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (render != null){
      render.draw(canvas, paint, factor);
    }
  }

  public void ready(ImageView view, Bitmap bitmap){
    rect = new Rect();
    view.getGlobalVisibleRect(rect);
    Rect current = new Rect();
    getGlobalVisibleRect(current);
    rect.offset(0, -current.top);
    Particle[] particles = ParticleFactory.createParticles(bitmap, rect);
    render = new Render(particles, rect);
  }
}
