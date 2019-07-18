package com.hwj.test.tabledrag.ui.activity;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import android.view.View;
import android.widget.FrameLayout;
import com.hwj.test.tabledrag.R;
import com.hwj.test.tabledrag.adapter.CellViewPagerApdater;
import com.hwj.test.tabledrag.celldrag.CellDragLayout;
import com.hwj.test.tabledrag.celldrag.DeleteCellHelper;
import com.hwj.test.tabledrag.entity.Cell;
import com.hwj.test.tabledrag.ui.fragment.CellFragment;
import com.hwj.test.tabledrag.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private FrameLayout flUnder;
    private CellViewPagerApdater cellViewPagerApdater;
    private List<Fragment> fragments;
    private CellDragLayout cellDragLayout;
    private static DeleteCellHelper deleteCellHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDeleteCellHelper();
    }

    private void initDeleteCellHelper(){
        deleteCellHelper = new DeleteCellHelper(cellDragLayout,flUnder);
        int statusBarHeight = ViewUtil.getStatusBarHeight(this);
        int viewPagerHeight = viewPager.getLayoutParams().height;
        Display display = getWindowManager().getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();
        deleteCellHelper.setDownActionRect(new Rect(0,statusBarHeight + viewPagerHeight, displayWidth, displayHeight));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_cells);
        cellDragLayout = (CellDragLayout) findViewById(R.id.cdl_drag);
        flUnder = (FrameLayout)findViewById(R.id.fl_under);
        fragments = new ArrayList<>();
        fragments.add(new CellFragment());
        fragments.add(new CellFragment());

        cellViewPagerApdater = new CellViewPagerApdater(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(cellViewPagerApdater);


    }

    public static DeleteCellHelper getDeleteCellHelper(){
        return deleteCellHelper;
    }

}
