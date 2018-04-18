package com.medmanager.android.views.activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.persistence.room.Database;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.medmanager.android.ConstantClass;
import com.medmanager.android.R;
import com.medmanager.android.model.storage.MedInfo;
import com.medmanager.android.presenter.adapter.MedViewPagerAdapter;
import com.medmanager.android.presenter.adapter.SearchListAdapter;
import com.medmanager.android.presenter.adapter.SearchQueryAdapter;
import com.medmanager.android.presenter.services.NotificationDispatcherService;
import com.medmanager.android.presenter.utils.MedsSingleton;
import com.medmanager.android.presenter.utils.UserProfileUtils;
import com.medmanager.android.presenter.viewpresenters.ActiveMedFragmentPresenter;
import com.medmanager.android.presenter.viewpresenters.NotificationPresenter;
import com.medmanager.android.views.fragments.EditProfileFragment;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, EditProfileFragment.ProfileEditingInterface{


    private TextView mNameTextView, mAvatarTextView, mTitleTextvIew;
    private ImageView mUserImage;
    private Toolbar mToolbar;
    private AppBarLayout mAppBar;


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SearchView searchView;
    private RecyclerView mRecyclerView;

    private SearchQueryAdapter mAdapter;

    private Bitmap mBitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setUpPermissions();


        mAppBar = findViewById(R.id.app_bar_layout);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        searchView = findViewById(R.id.search_view);
        mTitleTextvIew = findViewById(R.id.text_title);
        mRecyclerView = findViewById(R.id.recycler_view_search_medication);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MedInfo> medInfos = MedsSingleton.getInstance().getAllMedicationsInfo();

        if (medInfos!=null){
            mAdapter = new SearchQueryAdapter(this, medInfos);
            mRecyclerView.setAdapter(mAdapter);
        }








        new ActiveMedFragmentPresenter(this).loadActiveMedications();

        mTabLayout.addTab(mTabLayout.newTab().setText("All"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Active"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Monthly"));

        mViewPager.setAdapter(new MedViewPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount()));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }
                }
        );

        searchView.setOnSearchClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTitleTextvIew.animate()
                                .alpha(0.0f)
                                .setDuration(300)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        mTitleTextvIew.setVisibility(View.GONE);
                                        searchView.animate()
                                                .translationX(searchView.getMaxWidth())
                                                .setDuration(250);
                                    }
                                });

                    }
                }
        );

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter.filter(query);
                        mTitleTextvIew.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBar.setElevation(24);
                        }
                        mAdapter.filter(newText);
                        mTitleTextvIew.setVisibility(View.GONE);
                        return false;
                    }

                }
        );
        searchView.setOnCloseListener(
                new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        mRecyclerView.setVisibility(View.GONE);
                        mTitleTextvIew.animate()
                                .alpha(1.0f)
                                .setDuration(200)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        mTitleTextvIew.setVisibility(View.VISIBLE);
                                    }
                                });
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            mAppBar.setElevation(0);
                        }
                        return false;
                    }
                }
        );


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mNameTextView = navigationView.getHeaderView(0).findViewById(R.id.text_user_name);
        mAvatarTextView = navigationView.getHeaderView(0).findViewById(R.id.text_avatar);
        mUserImage = navigationView.getHeaderView(0).findViewById(R.id.nav_header_image);

        if (firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().getDisplayName()!=null){
            String displayName = firebaseAuth.getCurrentUser().getDisplayName();
            StringBuilder stringBuilder = new StringBuilder(displayName);
            mNameTextView.setText(displayName);
            mAvatarTextView.setText(stringBuilder.substring(0, 1));
            //Picasso.with(this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(mUserImage);
            Log.v("TAG", "photo uri is " + firebaseAuth.getCurrentUser().getPhotoUrl());
//            try {
//               // mUserImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), firebaseAuth.getCurrentUser().getPhotoUrl()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_profile) {

            EditProfileFragment editProfileFragment = new EditProfileFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frag_container, editProfileFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_sign_out) {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantClass.OPEN_GALLERY_CODE && resultCode == RESULT_OK
                && data!=null && data.getData()!=null){
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
//                ContentResolver resolver = getContentResolver();
//                    resolver.takePersistableUriPermission(data.getData(), takeFlags);
//            }
            filePath = data.getData();
            try{
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                editProfileFragment.setBitmap(mBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public Bitmap openImageGallery() {
        Intent intent;
        //intent.setType("image/*");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        else intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Gallery"), ConstantClass.OPEN_GALLERY_CODE);
        if (mBitmap!=null) return mBitmap;
        return null;
    }

    @Override
    public void handleEditProfile(String displayName) {
        UserProfileUtils.handleProfileEdit(this, firebaseAuth, displayName,filePath );
    }


    public void handleNewMedication(View view) {

        startActivity(new Intent(this, AddMedicationActivity.class));
    }

    private void setUpPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.MANAGE_DOCUMENTS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.MANAGE_DOCUMENTS},1);
            }}
    }
}
