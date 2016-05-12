package com.example.slidingmenulib;

/**
 * Created by Jason on 2016/3/16.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.sql.Ref;

public class SlidingMenuActivity extends FragmentActivity {
    private SlidingMenu slidingMenu;
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.slidingMenu = new SlidingMenu(this);
        this.slidingMenu.setBackgroundColor(Color.WHITE);
        setContentView(this.slidingMenu);


        this.mFragmentManager = getSupportFragmentManager();
    }

    public SlidingMenu getSlidingMenu() {
        return this.slidingMenu;
    }

    public void initFragments(Fragment menuFragment, Fragment mainFragment) {
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        transaction.add(R.id.f1, menuFragment, getFragmentTag(menuFragment));
        transaction.add(R.id.f2, mainFragment, getFragmentTag(mainFragment));
        transaction.commit();
        this.mCurrentFragment = mainFragment;
    }

    private String getFragmentTag(Fragment fragment) {
        return fragment.getClass().getName();
    }

    protected void openMenu() {
        this.slidingMenu.openMenu();
    }

    protected void closeMenu() {
        this.slidingMenu.closeMenu();
    }

    public void switchFragment(Class<?> c) {
        closeMenu();
        FragmentTransaction transaction = this.mFragmentManager.beginTransaction();
        Fragment fragment = this.mFragmentManager.findFragmentByTag(c.getName());
        if (fragment == this.mCurrentFragment) {
            return;
        }
        if (fragment == null) {
            try {
                Fragment fragmentNew = (Fragment) c.newInstance();
                transaction.add(R.id.f2, fragmentNew, getFragmentTag(fragmentNew));
                transaction.hide(this.mCurrentFragment);
                this.mCurrentFragment = fragmentNew;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            transaction.hide(this.mCurrentFragment);
            transaction.show(fragment);
            this.mCurrentFragment = fragment;
        }
        transaction.commit();
    }

    public void setIntercept(boolean intercept) {
        this.slidingMenu.setIntercept(intercept);
    }

    public Fragment getCurFragment() {
        return this.mCurrentFragment;
    }

    public boolean menuIsOpen() {
        return this.slidingMenu.menuIsOpen();
    }
}
