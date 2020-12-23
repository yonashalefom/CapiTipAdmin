package com.capitipalismadmin.ui.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capitipalismadmin.R;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment {
    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 2;

    @BindView(R.id.fragment_home_dashboard_main_container)
    RecyclerView mainContainer;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final List<Object> items = new ArrayList<>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new Object());
        }

        // region Material View Pager
        if (GRID_LAYOUT) {
            mainContainer.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mainContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mainContainer.setHasFixedSize(true);

        // Use this now
        mainContainer.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mainContainer.setAdapter(new DashboardFragmentAdapter(items));
        // endregion
    }
}
