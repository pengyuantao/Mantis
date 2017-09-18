package com.peng.android.coremodule.router;


import android.content.Intent;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

/**
 * Created by pyt on 2017/7/10.
 */

public class ProxyFragmentTransaction extends FragmentTransaction {

    private static final String TAG = "ProxyFragmentTransactio";

    private FragmentTransaction fragmentTransaction;
    private Intent intent;



    private ProxyFragmentTransaction(FragmentTransaction fragmentTransaction, Intent intent) {
        this.fragmentTransaction = fragmentTransaction;
        this.intent = intent;
    }

    public static ProxyFragmentTransaction create(FragmentTransaction fragmentTransaction, Intent intent) {
        return new ProxyFragmentTransaction(fragmentTransaction, intent);
    }


    @Override
    public FragmentTransaction add(Fragment fragment, String tag) {
        Log.d(TAG, "add() called with: fragment = [" + fragment + "], tag = [" + tag + "]");
        setFragmentArguments(fragment, this.intent);
        return this.fragmentTransaction.add(fragment, tag);
    }

    @Override
    public FragmentTransaction add(@IdRes int containerViewId, Fragment fragment) {
        Log.d(TAG, "add() called with: containerViewId = [" + containerViewId + "], fragment = [" + fragment + "]");
        setFragmentArguments(fragment, this.intent);
        return this.fragmentTransaction.add(containerViewId, fragment);
    }

    @Override
    public FragmentTransaction add(@IdRes int containerViewId, Fragment fragment, @Nullable String tag) {
        Log.d(TAG, "add() called with: containerViewId = [" + containerViewId + "], fragment = [" + fragment + "], tag = [" + tag + "]");
        setFragmentArguments(fragment, this.intent);
        return this.fragmentTransaction.add(containerViewId, fragment, tag);
    }

    @Override
    public FragmentTransaction replace(@IdRes int containerViewId, Fragment fragment) {
        Log.d(TAG, "replace() called with: containerViewId = [" + containerViewId + "], fragment = [" + fragment + "]");
        setFragmentArguments(fragment, this.intent);
        return this.fragmentTransaction.replace(containerViewId, fragment);
    }

    @Override
    public FragmentTransaction replace(@IdRes int containerViewId, Fragment fragment, @Nullable String tag) {
        Log.d(TAG, "replace() called with: containerViewId = [" + containerViewId + "], fragment = [" + fragment + "], tag = [" + tag + "]");
        setFragmentArguments(fragment, this.intent);
        return this.fragmentTransaction.replace(containerViewId, fragment, tag);
    }

    @Override
    public FragmentTransaction remove(Fragment fragment) {
        return this.fragmentTransaction.remove(fragment);
    }

    @Override
    public FragmentTransaction hide(Fragment fragment) {
        return this.fragmentTransaction.hide(fragment);
    }

    @Override
    public FragmentTransaction show(Fragment fragment) {
        return this.fragmentTransaction.show(fragment);
    }

    @Override
    public FragmentTransaction detach(Fragment fragment) {
        return this.fragmentTransaction.detach(fragment);
    }

    @Override
    public FragmentTransaction attach(Fragment fragment) {
        Log.d(TAG, "attach() called with: fragment = [" + fragment + "]");
        setFragmentArguments(fragment, this.intent);
        return this.fragmentTransaction.attach(fragment);
    }

    @Override
    public boolean isEmpty() {
        return this.fragmentTransaction.isEmpty();
    }

    @Override
    public FragmentTransaction setCustomAnimations(@AnimRes int enter, @AnimRes int exit) {
        return this.fragmentTransaction.setCustomAnimations(enter, exit);
    }

    @Override
    public FragmentTransaction setCustomAnimations(@AnimRes int enter, @AnimRes int exit, @AnimRes int popEnter, @AnimRes int popExit) {
        return this.fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit);
    }

    @Override
    public FragmentTransaction addSharedElement(View sharedElement, String name) {
        return this.fragmentTransaction.addSharedElement(sharedElement, name);
    }

    @Override
    public FragmentTransaction setTransition(int transit) {
        return this.fragmentTransaction.setTransition(transit);
    }

    @Override
    public FragmentTransaction setTransitionStyle(@StyleRes int styleRes) {
        return this.fragmentTransaction.setTransitionStyle(styleRes);
    }

    @Override
    public FragmentTransaction addToBackStack(@Nullable String name) {
        return this.fragmentTransaction.addToBackStack(name);
    }

    @Override
    public boolean isAddToBackStackAllowed() {
        return this.fragmentTransaction.isAddToBackStackAllowed();
    }

    @Override
    public FragmentTransaction disallowAddToBackStack() {
        return this.fragmentTransaction.disallowAddToBackStack();
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(@StringRes int res) {
        return this.fragmentTransaction.setBreadCrumbTitle(res);
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(CharSequence text) {
        return this.fragmentTransaction.setBreadCrumbTitle(text);
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(@StringRes int res) {
        return this.fragmentTransaction.setBreadCrumbShortTitle(res);
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(CharSequence text) {
        return this.fragmentTransaction.setBreadCrumbShortTitle(text);
    }

    @Override
    public FragmentTransaction setAllowOptimization(boolean allowOptimization) {
        return this.fragmentTransaction.setAllowOptimization(allowOptimization);
    }

    @Override
    public int commit() {
        return this.fragmentTransaction.commit();
    }

    @Override
    public int commitAllowingStateLoss() {
        return this.fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void commitNow() {
        this.fragmentTransaction.commitNow();
    }

    @Override
    public void commitNowAllowingStateLoss() {
        this.fragmentTransaction.commitNowAllowingStateLoss();
    }

    /**
     * 设置Fragment的参数
     *
     * @param fragment
     * @param intent
     */
    private void setFragmentArguments(Fragment fragment, Intent intent) {
        fragment.setArguments(intent.getExtras());
    }
}
