package com.bintangnaufal.uas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdminFragmentAdapter extends FragmentPagerAdapter {

    public static final int numberOfFragments = 3;

    public AdminFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return ProductsFragment.newInstance();
            case 1: return CustomersFragment.newInstance();
            case 2: return MapsFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberOfFragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Products";
            case 1: return "Customers";
            case 2: return "Lokasi";
            default: return super.getPageTitle(position);
        }
    }
}
