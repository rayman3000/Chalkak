package bulgogi1216.gmail.photogenic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import bulgogi1216.gmail.photogenic.databinding.ActivityMainBinding;
import bulgogi1216.gmail.photogenic.fragment_in_main.HomeFragment;
import bulgogi1216.gmail.photogenic.geofencing.Geofencing;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private ActivityMainBinding mBinding;
    private ActionBar mActionBar;
    private Handler mHandler;
    private Runnable mRunnable;
    private Geofencing mGeofencing;

    private class CommitFragmentRunnable implements Runnable {
        private Fragment fragment;

        private CommitFragmentRunnable(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void run() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(mBinding.contentMain.getId(), fragment).commit();
        }
    }

    private void initToolbar() {
        setSupportActionBar(mBinding.toolbarShell.toolbar);
        mActionBar = getSupportActionBar();
        Log.e(TAG, "ActionBar is null");
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setTitle(getResources().getString(R.string.app_name));
    }

    private void initNavigationMenu() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout,
                mBinding.toolbarShell.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                mActionBar.setTitle(item.getTitle());
                Fragment fragment = getFragmentByDrawerTag(item);
                commitFragment(fragment);
                mBinding.drawerLayout.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        mBinding.drawerLayout.openDrawer(GravityCompat.START);
    }

    private Fragment getFragmentByDrawerTag(MenuItem _item) {
        Fragment fragment;
        String[] drawerTitles = getResources().getStringArray(R.array.drawer_titles_in_main);

        if(_item.getTitle().equals(drawerTitles[0])) {
            fragment = HomeFragment.newInstance();
        } else if(_item.getTitle().equals(drawerTitles[1])) {
            fragment = HomeFragment.newInstance();
        } else {
            Log.e(TAG, "fragment variable is null");
            fragment = null;
        }

        return fragment;
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private void initGeofencing() {
        mGeofencing = Geofencing.getGeofencingInstance(this);
        if (checkPermissions()) {
            mGeofencing.checkGeofencing();
        } else if (!checkPermissions()) {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void commitFragment(Fragment fragment) {
        // Using Handler class to avoid lagging while committing fragment in same time as closing navigation drawer
        mRunnable = new CommitFragmentRunnable(fragment);
        mHandler.post(mRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initToolbar();
        initNavigationMenu();
        initGeofencing();

        mHandler = new Handler();
        commitFragment(HomeFragment.newInstance());
    }

    @Override
    public void onDestroy() {
        if (mRunnable != null) mHandler.removeCallbacks(mRunnable);

        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission granted.");
                mGeofencing.checkGeofencing();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Build intent that displays the App settings screen.
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
            }
        }
    }
}
