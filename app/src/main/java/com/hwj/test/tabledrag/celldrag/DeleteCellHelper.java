package com.hwj.test.tabledrag.celldrag;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hwj.test.tabledrag.utils.ViewUtil;

/**
 * @author huangwanjie
 * @date 2019/7/2
 */
public class DeleteCellHelper<T> {

  private CellDragLayout dragLayout;
  private View underLayout;
  private T content;
  View view;

  public DeleteCellHelper(CellDragLayout dragLayout,View underLayout){

    this.dragLayout = dragLayout;
    this.underLayout = underLayout;
    dragLayout.setDeleteCellHelper(this);
  }

  public void setDownActionRect(Rect rect){
    dragLayout.setActionDownRect(rect);
  }

  public void onAction(View view, T content){
    this.view = view;
    this.content = content;
    viewCopy(view);
    view.setVisibility(View.INVISIBLE);
  }

  private void viewCopy(View view){
    Bitmap bitmap = ViewUtil.getViewBitmap(view);
    ImageView ivCopy = new ImageView(view.getContext());
    ivCopy.setClickable(false);
    Rect rect = new Rect();
    view.getGlobalVisibleRect(rect);
    LayoutParams lp = new LayoutParams(view.getWidth(), view.getHeight());
    lp.leftMargin = rect.left;
    lp.topMargin = rect.top - (int) dragLayout.getX();
    ivCopy.setLayoutParams(lp);
    ivCopy.setImageBitmap(bitmap);
    dragLayout.addMoveView(ivCopy, bitmap);
  }

  public void cancle(){
    view.setVisibility(View.VISIBLE);
    if (deleteListenter != null){
      deleteListenter.onCancle();
    }
  }

  public void deleted(){
    if (deleteListenter != null){
      deleteListenter.onDeteled(content);
    }
  }

  private DeleteListenter deleteListenter;

  public void setDeleteListenter(DeleteListenter deleteListenter){
    this.deleteListenter = deleteListenter;
  }

  public void transferTouchEvent(MotionEvent event){
    underLayout.dispatchTouchEvent(event);
  }

  public interface DeleteListenter<T>{
    void onCancle();
    void onDeteled(T content);
  }
}
