package com.hwj.test.tabledrag.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hwj.test.tabledrag.R;
import com.hwj.test.tabledrag.celldrag.DeleteCellHelper;
import com.hwj.test.tabledrag.entity.Cell;
import com.hwj.test.tabledrag.ui.activity.MainActivity;

import java.util.List;

public class CellAdapter extends RecyclerView.Adapter<CellAdapter.CellViewHolder> {

    private List<Cell> cells;
    private Context context;
    public CellAdapter(List<Cell> cells, Context context) {
        this.cells = cells;
        this.context = context;
    }

    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cell, viewGroup, false);
        return new CellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellViewHolder viewHolder, int i) {
        viewHolder.onBind(cells.get(i).getBitmap());
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    class CellViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener , View.OnTouchListener {

        ImageView imageView;
        Bitmap bitmap;
        public CellViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_item);
            imageView.setOnLongClickListener(this);
        }

        public void onBind(Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
            this.bitmap = bitmap;
        }

        @Override
        public boolean onLongClick(View v) {
            MainActivity.getDeleteCellHelper().onAction(v, null);
            return true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i("onTouch image", event.toString());
            return true;
        }
    }
}
