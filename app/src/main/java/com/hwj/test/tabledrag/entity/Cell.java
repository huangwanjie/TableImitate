package com.hwj.test.tabledrag.entity;

import android.graphics.Bitmap;

public class Cell {
    private Bitmap bitmap;

    public Cell(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
