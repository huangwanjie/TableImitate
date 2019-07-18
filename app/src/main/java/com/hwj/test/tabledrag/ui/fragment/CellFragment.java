package com.hwj.test.tabledrag.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwj.test.tabledrag.R;
import com.hwj.test.tabledrag.adapter.CellAdapter;
import com.hwj.test.tabledrag.entity.Cell;

import java.util.ArrayList;
import java.util.List;

public class CellFragment extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private CellAdapter cellAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cell, container, false);
        initData();
        initView();
        return view;
    }


    private List<Cell> cells;
    private void initData() {
        cells = new ArrayList<>();
        Cell cell = new Cell(BitmapFactory.decodeResource(getResources(), R.drawable.bg_cell_item));
        cells.add(cell);
    }

    private void initView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_cells);

        cellAdapter = new CellAdapter(cells, getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setAdapter(cellAdapter);

    }
}
