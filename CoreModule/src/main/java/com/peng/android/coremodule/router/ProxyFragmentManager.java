package com.peng.android.coremodule.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

/**
 * 静态代理的FragmentManager
 * Created by pyt on 2017/7/10.
 */

    public class ProxyFragmentManager extends FragmentManager {

    /**
     * 创建这个Activity
     *
     * @param fragmentManager
     * @param intent
     * @return 代理的FragmentManager
     */
    public static ProxyFragmentManager create(FragmentManager fragmentManager, Intent intent) {
        return new ProxyFragmentManager(fragmentManager, intent);
    }

    private FragmentManager fragmentManager;

    private Intent intent;

    private ProxyFragmentManager(FragmentManager fragmentManager, Intent intent) {
        this.fragmentManager = fragmentManager;
        this.intent = intent;
    }

    @Override
    public FragmentTransaction beginTransaction() {
        return ProxyFragmentTransaction.create(fragmentManager.beginTransaction(), intent);
    }

    @Override
    public boolean executePendingTransactions() {
        return this.fragmentManager.executePendingTransactions();
    }

    @Override
    public Fragment findFragmentById(@IdRes int id) {
        return this.fragmentManager.findFragmentById(id);
    }

    @Override
    public Fragment findFragmentByTag(String tag) {
        return this.fragmentManager.findFragmentByTag(tag);
    }

    @Override
    public void popBackStack() {
        this.fragmentManager.popBackStack();
    }

    @Override
    public boolean popBackStackImmediate() {
        return this.fragmentManager.popBackStackImmediate();
    }

    @Override
    public void popBackStack(String name, int flags) {
        this.fragmentManager.popBackStack(name, flags);
    }

    @Override
    public boolean popBackStackImmediate(String name, int flags) {
        return this.fragmentManager.popBackStackImmediate(name, flags);
    }

    @Override
    public void popBackStack(int id, int flags) {
        this.fragmentManager.popBackStack(id, flags);
    }

    @Override
    public boolean popBackStackImmediate(int id, int flags) {
        return this.fragmentManager.popBackStackImmediate(id, flags);
    }

    @Override
    public int getBackStackEntryCount() {
        return this.fragmentManager.getBackStackEntryCount();
    }

    @Override
    public BackStackEntry getBackStackEntryAt(int index) {
        return this.fragmentManager.getBackStackEntryAt(index);
    }

    @Override
    public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {
        this.fragmentManager.addOnBackStackChangedListener(listener);
    }

    @Override
    public void removeOnBackStackChangedListener(OnBackStackChangedListener listener) {
        this.fragmentManager.removeOnBackStackChangedListener(listener);
    }

    @Override
    public void putFragment(Bundle bundle, String key, Fragment fragment) {
        this.fragmentManager.putFragment(bundle, key, fragment);
    }

    @Override
    public Fragment getFragment(Bundle bundle, String key) {
        return this.fragmentManager.getFragment(bundle, key);
    }

    @Override
    public List<Fragment> getFragments() {
        return this.fragmentManager.getFragments();
    }

    @Override
    public Fragment.SavedState saveFragmentInstanceState(Fragment f) {
        return this.fragmentManager.saveFragmentInstanceState(f);
    }

    @Override
    public boolean isDestroyed() {
        return this.fragmentManager.isDestroyed();
    }

    @Override
    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb, boolean recursive) {
        this.fragmentManager.registerFragmentLifecycleCallbacks(cb, recursive);
    }

    @Override
    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb) {
        this.fragmentManager.unregisterFragmentLifecycleCallbacks(cb);
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        this.fragmentManager.dump(prefix, fd, writer, args);
    }


}
