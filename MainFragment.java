package com.example.ckmj.interactivemap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.Locale;

import static android.R.attr.visibility;
import static android.R.attr.visible;



public class MainFragment extends Fragment {
    private ImageView iv;
    private PhotoViewAttacher mAttacher;
    private boolean barHide=true;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }
        // Set View to Interactive Map with onClickListener
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View mainView= inflater.inflate(R.layout.fragment_main, container, false);
        FloatingActionButton fab= (FloatingActionButton) mainView.findViewById(R.id.fabMain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainDrawer.drawer.openDrawer(Gravity.START);

            }
        });




        iv= (ImageView) mainView.findViewById(R.id.mapView);
        iv.setImageResource(R.drawable.vnmap4);

        mAttacher = new PhotoViewAttacher(iv);
        mAttacher.setMinimumScale(1f);
        mAttacher.setMediumScale(2f);
        mAttacher.setMaximumScale(4f);
        mAttacher.setOnPhotoTapListener(new MainFragment.PhotoTapListener());

        // Set language to whatever the  user chooses

        return mainView;



    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    private class PhotoTapListener implements OnPhotoTapListener {


        @Override
        public void onPhotoTap(ImageView view, float x, float y) {

            int densityDpi = getResources().getDisplayMetrics().densityDpi;
            int posX, posY;
            if (densityDpi< DisplayMetrics.DENSITY_XHIGH) {
                posX = (int) (x * 1600);
                posY = (int) (y * 2500);

            }
            else
            {
                posX = (int) (x * 2560);
                posY = (int) (y * 4000);
            }
            AppBarLayout abl= (AppBarLayout) getActivity().findViewById(R.id.appbar2);
            Toolbar tbl= (Toolbar) getActivity().findViewById(R.id.toolbar2);
            tbl.setTitle("");
            ((AppCompatActivity)getActivity()).setSupportActionBar(tbl);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


            //get position of the touch
            int pixel = MainDrawer.hotspots.getPixel(posX, posY); //get corresponding pixel in the mask picture
            Intent intent = new Intent(getActivity(), ProvDetail.class);

            int r = Color.red(pixel); //get color of the pixel
            int b = Color.blue(pixel);
            int g = Color.green(pixel);
            intent.putExtra("PROVINCE_ID",""+r+g+b); // put the color as an extra for intent
            Log.d("Color", "" + r + " " + g + " " + b);
            Log.d("Location: ", "" + posX + " " + posY);

            // If click hits province, go to province Activity, otherwise toggle the toolbar
            if (ProvinceList.provRef.containsKey(""+r+g+b)) {
                startActivity(intent);
            }
            else
            {
                if (barHide)
                {
                   abl.setVisibility(view.VISIBLE);
                    abl.setAlpha(0f);
                    abl.animate().alpha(1f).setDuration(200);
                    abl.setVisibility(view.VISIBLE);
                    barHide=false;
                }
                else
                {
                    abl.animate().alpha(0f).setDuration(200);
            //      abl.setVisibility(view.INVISIBLE);
                    barHide=true;
                }
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_main_frag, menu);
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


}
