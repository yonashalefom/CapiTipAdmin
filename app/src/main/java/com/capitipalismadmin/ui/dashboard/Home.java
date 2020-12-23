package com.capitipalismadmin.ui.dashboard;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capitipalismadmin.R;
import com.capitipalismadmin.classes.CapiUserManager;
import com.capitipalismadmin.ui.dashboard.fragments.DashboardFragment;
import com.capitipalismadmin.ui.helpers.AlertCreator;
import com.capitipalismadmin.ui.login.Login;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Home extends AppCompatActivity {
    // static {
    //     AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    // }
    // region Initializations
    // region Drawer Items

    // endregion

    // region UI Elements
    @BindView(R.id.activity_home_material_view_pager)
    MaterialViewPager materialViewPagerMainContainer;

    // endregion
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapiUserManager.loadUserData(getApplicationContext());
        if (CapiUserManager.userDataExists()) {
            if (!CapiUserManager.getUserType().equals("Super Admin")) {
                AlertCreator.showErrorAlert(this, "Invalid User", "You don't have the privileges to access this page!");
                startActivity(new Intent(Home.this, Login.class));
                finish();
            }
        }
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        initUI(savedInstanceState);
    }

    // region Initialize UI
    private void initUI(Bundle savedInstanceState) {
        initMaterialViewPagerMainContainer();
    }
    // endregion

    // region Separate Component Initializations
    // region Init Material View Pager
    private void initMaterialViewPagerMainContainer() {
        materialViewPagerMainContainer.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @NonNull
            @Override
            public Fragment getItem(int position) {
                System.out.println("POSITION: " + position); // debug
                if (position % 4 == 0) {
                    return DashboardFragment.newInstance();
                    // case 1:
                    //     return DashboardFragment.newInstance();
                    // case 2:
                    //     return DashboardFragment.newInstance();
                    // case 3:
                    //     return DashboardFragment.newInstance();
                    // default:
                    //     return DashboardFragment.newInstance();
                }
                return DashboardFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position % 4 == 0) {
                    // return "Dashboard";
                    // case 1:
                    //     return "Activity";
                    // case 2:
                    //     return "Report";
                    // case 3:
                    //     return "Settings";
                }
                // return "Dashboard";
                return "";
            }
        });
        materialViewPagerMainContainer.setMaterialViewPagerListener(page -> {
            if (page == 0) {
                return HeaderDesign.fromColorResAndDrawable(
                        R.color.colorPrimary, getResources().getDrawable(R.drawable.bg_home_screen_img_1));
                // case 1:
                //     return HeaderDesign.fromColorResAndDrawable(
                //             R.color.colorPrimary, getResources().getDrawable(R.drawable.bg_home_screen_img_2));
                // case 2:
                //     return HeaderDesign.fromColorResAndDrawable(
                //             R.color.colorPrimary, getResources().getDrawable(R.drawable.bg_home_screen_img_3));
                // case 3:
                //     return HeaderDesign.fromColorResAndDrawable(
                //             R.color.colorPrimary, getResources().getDrawable(R.drawable.bg_home_screen_img_3));
            }
            //execute others actions if needed (ex : modify your header logo)

            return null;
        });

        // materialViewPagerMainContainer.getToolbar().g

        // materialViewPagerMainContainer.getPagerTitleStrip().set

        Toolbar toolbar = materialViewPagerMainContainer.getToolbar();
        materialViewPagerMainContainer.getPagerTitleStrip().setIndicatorHeight(0);
        // materialViewPagerMainContainer.getPagerTitleStrip().setUnderlineColorResource(R.color.colorBlackTransparent);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        materialViewPagerMainContainer.getViewPager().setOffscreenPageLimit(Objects.requireNonNull(materialViewPagerMainContainer.getViewPager().getAdapter()).getCount());
        materialViewPagerMainContainer.getPagerTitleStrip().setViewPager(materialViewPagerMainContainer.getViewPager());

        final View logo = findViewById(R.id.activity_home_text_view_title);
        if (logo != null) {
            logo.setOnClickListener(v -> {
                // materialViewPagerMainContainer.notifyHeaderChanged();
                // Toast.makeText(getApplicationContext(), "Manage Items Related with Bank Accounts", Toast.LENGTH_SHORT).show();
            });
        }
    }
    // endregion
    // endregion


}
