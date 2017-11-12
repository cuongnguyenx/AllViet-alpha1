package com.example.ckmj.interactivemap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;


public class ProvDetail extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    // Province Detail Activity, transition from MainDrawer
    private ViewPager mViewPager;
    private static String provinceId;
    private static Province province;
    private static TextView tv,tv2,tv3;
    private static ImageView iv;
    private static RelativeLayout rl1;
    private static Bitmap bit;
    private static StringBuilder builder;
    private static int posArea, posPop, posPopDens, posPCI;
    private static Typeface tf1, tf2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prov_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Main Part
        provinceId= getIntent().getStringExtra("PROVINCE_ID");
        Log.d("das", ""+provinceId);
        Province province = ProvinceList.provRef.get(provinceId);


        if (province.getIsCity())
        {
            this.setTitle(province.getCapital());

        }

        else
        {
            this.setTitle(province.getName());
        }

        iv= (ImageView) findViewById(R.id.provIMG);
        bit= getBitmapFromAsset(provinceId,this);
        iv.setImageBitmap(bit);

        CollapsingToolbarLayout clt= (CollapsingToolbarLayout) findViewById(R.id.collapse);
        Typeface toolType= Typeface.createFromAsset(getAssets(),"fonts/NotoSerif-BoldItalic.ttf");
        tf1= Typeface.createFromAsset(getAssets(), "fonts/OpenSansBold.ttf");
        tf2= Typeface.createFromAsset(getAssets(), "fonts/OpenSansRegular.ttf");

        clt.setExpandedTitleTypeface(toolType);
        clt.setCollapsedTitleTypeface(toolType);
        }

    @Override // At start of Activity, attach appropriate Base Context with the Locale
    protected void attachBaseContext(Context newBase) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String localeX= mSharedPreferences.getString("language_list","en");
        Log.d("locale", localeX);
        Locale locale= new Locale(localeX);

        final Resources res = newBase.getResources();
        final Configuration config = res.getConfiguration();
        config.setLocale(locale); // getLocale() should return a Locale
        final Context newContext = newBase.createConfigurationContext(config);
        super.attachBaseContext(newContext);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prov_detail, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            province = ProvinceList.provRef.get(provinceId);



    View rootView = inflater.inflate(R.layout.fragment_prov_detail, container, false);

            // tv is the info text and tv2 is the title text
            tv= (TextView) rootView.findViewById(R.id.textView);
            tv2= (TextView) rootView.findViewById(R.id.titleText);
            tv3= (TextView) rootView.findViewById(R.id.rankingText);



            tv.setTypeface(tf2);
            tv2.setTypeface(tf1);
            tv3.setTypeface(tf2);

            if (getArguments().getInt(ARG_SECTION_NUMBER)==1)
            {
                builder= new StringBuilder();

                // rank each of the fields of the province
                posArea= Math.abs(Collections.binarySearch(ProvinceList.AreaComp, Double.valueOf(province.getArea()))-63) ;
                posPop= Math.abs(Collections.binarySearch(ProvinceList.PopComp, Integer.valueOf(province.getPopulation()))-63) ;
                posPopDens= Math.abs(Collections.binarySearch(ProvinceList.PopDensComp, Integer.valueOf(province.getPopDensity()))-63) ;
                posPCI= Math.abs(Collections.binarySearch(ProvinceList.PCIComp, Double.valueOf(province.getPCI())) -63);

                if (province.getIsCity())
                {

                    String license = Arrays.toString(province.getVehicleCode());
                    license= license.substring(1,license.length()-1);
                    builder.append("\n"+province.getRegion()+"\n"+"\n"+"\n");
                    builder.append(""+province.getArea()+" KM"+(char)0x00B2+"\n"+"\n"+"\n");
                    builder.append(""+province.getPopulation()+ "\n"+"\n"+"\n");
                    builder.append(""+province.getPopDensity()+"/KM"+(char)0x00B2+ "\n"+"\n"+"\n");
                    builder.append(""+province.getPCI()+"\n"+"\n"+"\n");
                    builder.append(""+ province.getTeleCode()+"\n"+"\n"+"\n");
                    builder.append(""+ license +"\n"+"\n"+"\n");

                    tv.setText(builder.toString());
                    builder.setLength(0);

                    builder.append("\n"+getString(R.string.title_reg)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_area)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_pop)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_popDens)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_PCI)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_tele)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_vehicle)+"\n"+"\n"+"\n");
                    tv2.setText(builder.toString());
                    builder.setLength(0);

                    builder.append("\n\n\n\n");
                    builder.append("   (#"+posArea+")"+"\n"+"\n"+"\n");
                    builder.append("   (#"+posPop+")"+"\n"+"\n"+"\n");
                    builder.append("   (#"+posPopDens+")"+"\n"+"\n"+"\n");
                    builder.append("   (#"+posPCI+")"+"\n"+"\n"+"\n");
                    tv3.setText(builder.toString());

                }

                else
                {

                    builder.append("\n"+province.getCapital()+"\n"+"\n"+"\n");
                    builder.append(""+province.getRegion()+"\n"+"\n"+"\n");
                    builder.append(""+province.getArea()+" KM"+(char)0x00B2+"\n"+"\n"+"\n");
                    builder.append(""+province.getPopulation()+ "\n"+"\n"+"\n");
                    builder.append(""+province.getPopDensity()+"/KM"+(char)0x00B2+ "\n"+"\n"+"\n");
                    builder.append(""+province.getPCI()+"\n"+"\n"+"\n");
                    builder.append(""+ province.getTeleCode()+"\n"+"\n"+"\n");
                    builder.append(""+ Arrays.toString(province.getVehicleCode())+"\n"+"\n"+"\n"+"\n");


                    tv.setText(builder.toString());
                    builder.setLength(0);

                    builder.append("\n"+getString(R.string.title_cap)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_reg)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_area)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_pop)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_popDens)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_PCI)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_tele)+"\n"+"\n"+"\n");
                    builder.append(getString(R.string.title_vehicle)+"\n"+"\n"+"\n");
                    tv2.setText(builder.toString());
                    builder.setLength(0);

                    builder.append("\n\n\n\n\n\n\n");
                    builder.append("   (#"+posArea+")"+"\n"+"\n"+"\n");
                    builder.append("   (#"+posPop+")"+"\n"+"\n"+"\n");
                    builder.append("   (#"+posPopDens+")"+"\n"+"\n"+"\n");
                    builder.append("   (#"+posPCI+")"+"\n"+"\n"+"\n");
                    tv3.setText(builder.toString());
                }
            }
            return rootView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.ProvDet1);
                case 1:
                    return getString(R.string.ProvDet2);
                case 2:
                    return getString(R.string.ProvDet3);
                case 3:
                    return getString(R.string.ProvDet4);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }


    }

    private static Bitmap getBitmapFromAsset(String productId, Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream stream = null;

        try {
            stream = assetManager.open(productId + ".jpg");
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
