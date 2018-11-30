package com.rpgapp.devapp.rpgapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.rpgapp.devapp.rpgapp.Model.Adventure;
import com.rpgapp.devapp.rpgapp.Model.User;
import com.rpgapp.devapp.rpgapp.Screens.AddAdventure.AddAdventureFragment;
import com.rpgapp.devapp.rpgapp.Screens.AdventureDetails.AdventureDetailsFragment;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresBtnOnClick;
import com.rpgapp.devapp.rpgapp.Screens.Adventures.AdventuresFragment;
import com.rpgapp.devapp.rpgapp.Screens.Auth.Login;
import com.rpgapp.devapp.rpgapp.Screens.Notifications.Notifications;
import com.rpgapp.devapp.rpgapp.Screens.Session.SessionFragment;
import com.rpgapp.devapp.rpgapp.Utils.BackableFragment;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AdventuresFragment.OnListFragmentInteractionListener,
        AddAdventureFragment.OnFragmentInteractionListener,
        AdventureDetailsFragment.OnFragmentInteractionListener, EventListener<DocumentSnapshot> {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private ImageView mDotMenuBtn;
    private ImageView mOpenDrawerBtn;
    private ImageView mFab;
    private User mCurrentUser;
    private NavigationView mNavigationView;
    private TextView mNotificationCount;
    private FrameLayout mNotificationView;
    private View mNotificationIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mCurrentUser = (User) intent.getSerializableExtra("userProfile");


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNotificationIndicator = findViewById(R.id.not_pop_up);
        mNotificationIndicator.setVisibility(View.GONE);
        mDotMenuBtn = findViewById(R.id.menu_dots);
        mDotMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.main, popup.getMenu());
                popup.show();
            }
        });

        mOpenDrawerBtn = findViewById(R.id.drawer_togle);
        mOpenDrawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.openDrawer(GravityCompat.START);
                } else {
                    mDrawer.closeDrawer(Gravity.START);
                }
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new AdventuresBtnOnClick(mFab, this));

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNotificationView = (FrameLayout) mNavigationView
                .getMenu().findItem(R.id.nav_notifications)
                .getActionView();
        mNotificationCount = (TextView) mNotificationView
                .findViewById(R.id.notification_number);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("Users")
                .document(mCurrentUser.getId())
                .addSnapshotListener(this);

        Fragment fragment = AdventuresFragment.newInstance();
        int commit;
        commit = getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                commit();

    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if(documentSnapshot.exists())  {
            mCurrentUser = documentSnapshot.toObject(User.class);

            if(mCurrentUser.userHasNotification()){
                showNotifications(true);
            } else {
                showNotifications(false);
            }
        }
    }
    public void showNotifications(boolean show) {
        String text;
        int visibility;
        if(show) {
            text = Integer.toString(mCurrentUser.getNotifications().size());
            visibility = View.VISIBLE;
        } else {
            text = "";
            visibility = View.GONE;
        }

        mNotificationCount.setText(text);
        mNotificationView.setVisibility(visibility);
        mNotificationIndicator.setVisibility(visibility);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
            BackableFragment bf;
            if (f != null) {
                bf = (BackableFragment) f;
                if (f instanceof AdventuresFragment) {
                    super.onBackPressed();
                } else if (f instanceof AdventureDetailsFragment) {
                    mFab.setVisibility(View.VISIBLE);
                    bf.onBack();
                } else {
                    bf.onBack();
                }
            } else {
                super.onBackPressed();
            }


        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_adventures) {
            Fragment fragment = AdventuresFragment.newInstance();
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, fragment).
                    commit();
        } else if (id == R.id.nav_books) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_notifications) {
            Fragment fragment = Notifications.newInstance(mCurrentUser.getNotifications());

           getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, fragment).
                    commit();

        } else if (id == R.id.nav_config) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Adventure item) {
        mFab.setVisibility(View.GONE);
        AdventureDetailsFragment fragment = AdventureDetailsFragment.newInstance(item, AdventureDetailsFragment.PROGRESS_FRAG);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).
                commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public User getCurrentUser() {
        return mCurrentUser;
    }

    public void hideActionBtn() {
        mFab.setVisibility(View.GONE);
    }

    public void showActionBtn() {
        mFab.setVisibility(View.VISIBLE);
    }
}
