package com.hwj.test.tabledrag.celldrag;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hwj.test.tabledrag.R;
import com.hwj.test.tabledrag.utils.ViewUtil;

/**
 * @author huangwanjie
 * @date 2019/7/3
 */
public class CellDragLayout extends FrameLayout {

  private Rect actionDownRect;
  private Rect deleteRect;
  private DeleteCellHelper deleteCellHelper;
  private View deleteBar;
  private AnimatorSet moveCenterAnimator;
  private ObjectAnimator deleteRectAnimator;
  private StageView stage;
  private ImageView moveView = null;
  private Bitmap bitmap;
  private boolean isCancle = true;

  private OnPreDrawListener onPreDrawListener = new OnPreDrawListener() {
    @Override
    public boolean onPreDraw() {
      deleteRect = new Rect(0, 0, deleteBar.getMeasuredWidth(), deleteBar.getMeasuredHeight());
      return true;
    }
  };

  public CellDragLayout(@NonNull Context context) {
    super(context);
    initView(null);
  }

  public CellDragLayout(@NonNull Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    initView(attrs);
  }

  public CellDragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(attrs);
  }

  private int deleteRectHeight = 200;
  private int deleteRectWidth = ViewGroup.LayoutParams.MATCH_PARENT ;

  private void initView(AttributeSet attrs){

    if (attrs != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CellDragLayout);
      deleteRectHeight = typedArray.getDimensionPixelSize(R.styleable.CellDragLayout_deleteRectHeight, deleteRectHeight);
      deleteRectWidth = typedArray.getDimensionPixelSize(R.styleable.CellDragLayout_deleteRectWidth, deleteRectWidth);
    }
    if (deleteBar == null){
      deleteBar = new View(getContext());
      LayoutParams layoutParams1 = new LayoutParams(deleteRectWidth, deleteRectHeight);
      deleteBar.setLayoutParams(layoutParams1);
      deleteBar.setBackgroundColor(Color.parseColor("#FF0000"));
      deleteBar.setAlpha(0);
      deleteBar.setTranslationY(-ViewUtil.getStatusBarHeight(getContext()));

      stage = new StageView(getContext());
      LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
      stage.setLayoutParams(layoutParams2);

      addView(deleteBar);
      addView(stage);

      ViewTreeObserver vto = deleteBar.getViewTreeObserver();
      vto.addOnPreDrawListener(onPreDrawListener);

      deleteRectAnimator = ObjectAnimator.ofFloat(deleteBar, "Alpha", 0.2f, 0.4f);
      deleteRectAnimator.setDuration(100);
      deleteRectAnimator.addListener(deleteRectAnimatorListener);

      deletingAnimator = ObjectAnimator.ofFloat(stage,"Factor", 0, 1f);
      deletingAnimator.setDuration(1000);
      deletingAnimator.addListener(deletingAnimatorListenerAdapter);
      setBackgroundColor(Color.parseColor("#00000000"));
    }
  }

  private AnimatorListenerAdapter deleteRectAnimatorListener = new AnimatorListenerAdapter() {
    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {
      isCancle = true;
    }
  };


  private AnimatorListenerAdapter moveViewAnimatorListener = new AnimatorListenerAdapter(){
    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
      startDeletingAnim();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }
  };


  private AnimatorListenerAdapter deletingAnimatorListenerAdapter = new AnimatorListenerAdapter() {
    @Override
    public void onAnimationEnd(Animator animation) {
      setBackgroundColor(Color.parseColor("#00000000"));
      deleteCellHelper.deleted();
      bitmap.recycle();
    }
  };

  private ObjectAnimator deletingAnimator;
  public void startDeletingAnim(){
    stage.ready(moveView, bitmap);
    deletingAnimator.start();
    removeView(moveView);
    moveView = null;
  }

  public void addMoveView(ImageView child, Bitmap bitmap) {
    super.addView(child);
    moveView = child;
    this.bitmap = bitmap;
    AnimatorSet animatorSet = new AnimatorSet();
    int centerX = moveView.getLayoutParams().width / 2 +  ((LayoutParams)moveView.getLayoutParams()).leftMargin;
    int centerY = moveView.getLayoutParams().height / 2 +  ((LayoutParams)moveView.getLayoutParams()).topMargin;
    ObjectAnimator animatorX = ObjectAnimator.ofFloat(moveView, "TranslationX", 0 , currentTouchX - centerX);
    ObjectAnimator animatorY = ObjectAnimator.ofFloat(moveView, "TranslationY", 0 , currentTouchY - centerY);
    ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(moveView, "ScaleX" ,1 , 0.8f ,1);
    ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(moveView, "ScaleY" ,1 , 0.8f ,1);
    animatorSet.playTogether(animatorX, animatorY, animatorScaleX, animatorScaleY);
    animatorSet.setDuration(200);
    animatorSet.start();
    deleteBar.setAlpha(0.2f);
  }

  private float currentTouchX = -1;
  private float currentTouchY = -1;

  @Override
  public boolean onTouchEvent(MotionEvent event) {

    Log.i("onTouchEvent", event.toString());
//    if (event != null){
//      return false;
//    }
    if (actionDownRect == null){
      return false;
    }

    currentTouchX = event.getX();
    currentTouchY = event.getY();

    if (event.getAction() == MotionEvent.ACTION_DOWN){
      if (!actionDownRect.contains((int) event.getRawX(), (int) event.getRawY())) {
        return false;
      }
    }

    if (moveView == null){
      deleteCellHelper.transferTouchEvent(event);
      return true;
    }

    switch (event.getAction()){
      case MotionEvent.ACTION_MOVE:
        moveView.setTranslationX(currentTouchX - (moveView.getX() + moveView.getWidth() / 2) + moveView.getTranslationX());
        moveView.setTranslationY(currentTouchY - (moveView.getY() + moveView.getHeight() / 2) + moveView.getTranslationY());
        onMoveState((int)(event.getX()), (int)(event.getY()));
        break;
      case MotionEvent.ACTION_UP:
        onUpState((int)(event.getX()), (int)(event.getY()));
        break;
      default:
        break;
    }
    return true;
  }

  private void onUpState(int x, int y) {
    cancleDeleteVisbilityAnim();
    deleteBar.setAlpha(0);

    if (!deleteRect.contains(x, y)){
      deleteCellHelper.cancle();
      removeView(moveView);
      bitmap.recycle();
      moveView = null;
      return;
    }
    moveToCenter();
    setBackgroundColor(Color.parseColor("#88000000"));
  }

  private void onMoveState(int x, int y){
    if (deleteRect.contains(x, y)){
      startDeleteVisbilityAnim();
    }else {
      cancleDeleteVisbilityAnim();
      deleteBar.setAlpha(0.2f);
    }
  }

  private void moveToCenter(){
    int centerX = getWidth() / 2;
    int centerY = getHeight() / 2;

    moveCenterAnimator = new AnimatorSet();
    moveCenterAnimator.playTogether(ObjectAnimator.ofFloat(moveView, "TranslationX" , moveView.getTranslationX() , centerX - (moveView.getX() + moveView.getWidth() / 2) + moveView.getTranslationX())
        ,ObjectAnimator.ofFloat(moveView, "TranslationY" , moveView.getTranslationY() , centerY - (moveView.getY() + moveView.getHeight() / 2) + moveView.getTranslationY()));
    moveCenterAnimator.setDuration(200);
    moveCenterAnimator.addListener(moveViewAnimatorListener);
    moveCenterAnimator.start();
  }

  private void startDeleteVisbilityAnim(){
    if (isCancle) {
      isCancle = false;
      deleteRectAnimator.start();
    }
  }

  private void cancleDeleteVisbilityAnim(){
    deleteRectAnimator.cancel();
    isCancle = true;
  }

  public Rect getActionDownRect() {
    return actionDownRect;
  }

  public void setActionDownRect(Rect rect) {
    this.actionDownRect = rect;
  }

  public Rect getDeleteRect() {
    return deleteRect;
  }

  public void setDeleteRect(Rect deleteRect) {
    this.deleteRect = deleteRect;
  }

  public DeleteCellHelper getDeleteCellHelper() {
    return deleteCellHelper;
  }

  public void setDeleteCellHelper(DeleteCellHelper deleteCellHelper) {
    this.deleteCellHelper = deleteCellHelper;
  }

}
