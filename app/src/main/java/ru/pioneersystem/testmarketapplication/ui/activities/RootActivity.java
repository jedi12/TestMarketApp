package ru.pioneersystem.testmarketapplication.ui.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;
import ru.pioneersystem.testmarketapplication.BuildConfig;
import ru.pioneersystem.testmarketapplication.R;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.scopes.RootScope;
import ru.pioneersystem.testmarketapplication.mvp.presenters.RootPresenter;
import ru.pioneersystem.testmarketapplication.mvp.views.IRootView;
import ru.pioneersystem.testmarketapplication.ui.fragments.AccountFragment;
import ru.pioneersystem.testmarketapplication.ui.fragments.CatalogFragment;

public class RootActivity extends AppCompatActivity implements IRootView, NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavigationView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.coordinator_container) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;

    @Inject
    RootPresenter mRootPresenter;

    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(RootActivity.Component.class, component);
        }
        component.inject(this);
        mRootPresenter.takeView(this);

        initToolbar();
        initDrawer();

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, new CatalogFragment())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        mRootPresenter.dropView();
        super.onDestroy();
    }

    private void initToolbar() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initDrawer() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.nav_account:
                fragment = new AccountFragment();
                break;
            case R.id.nav_catalog:
                fragment = new CatalogFragment();
                break;
            case R.id.nav_favorite:
                break;
            case R.id.nav_orders:
                break;
            case R.id.nav_notification:
                break;
        }

        if (fragment != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.error_message));
        }
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    private Component createDaggerComponent() {
        return DaggerRootActivity_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @RootScope
        RootPresenter provideRootPresenter() {
            return new RootPresenter();
        }
    }

    @dagger.Component(modules = RootActivity.Module.class)
    @RootScope
    public interface Component {
        void inject(RootActivity mRootActivity);
        RootPresenter getRootPresenter();
    }
}
