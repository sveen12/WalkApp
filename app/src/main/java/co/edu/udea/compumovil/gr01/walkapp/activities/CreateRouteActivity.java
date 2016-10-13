package co.edu.udea.compumovil.gr01.walkapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr01.walkapp.R;
import co.edu.udea.compumovil.gr01.walkapp.fragments.createroute.AutomaticFragment;
import co.edu.udea.compumovil.gr01.walkapp.fragments.createroute.ManualFragment;

public class CreateRouteActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPageAdapter viewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        toolbar = (Toolbar)findViewById(R.id.toolbar_element);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager= (ViewPager) findViewById(R.id.viewPager);

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragments(new AutomaticFragment(), "Automático");
        viewPageAdapter.addFragments(new ManualFragment(), "Manual");
        viewPager.setAdapter(viewPageAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

}
