package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by andres on 9/09/16.
 */
public class ViewPageAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String> tabTitles = new ArrayList<>();

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragments(Fragment fragment, String tabTitle){
        this.fragments.add(fragment);
        this.tabTitles.add(tabTitle);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
