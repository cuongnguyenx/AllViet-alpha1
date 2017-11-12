package com.example.ckmj.interactivemap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass
 * Activities that cotain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int viewOption=0;
    private static int order=0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static ListView lv;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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
        super.onCreate(savedInstanceState);

    }

    // Create the Province List Interface
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View provListView= inflater.inflate(R.layout.fragment_list, container, false);
        final ArrayList<Province> localProvList= new ArrayList<Province>();
        // Create local province list to preserve the original province List
        for (Province p: ProvinceList.listProvince)
        {
            localProvList.add(p);
        }

        getActivity().onBackPressed();


        lv= (ListView) provListView.findViewById(R.id.provListView);
        ProvinceListAdapter provLA= new ProvinceListAdapter(getActivity(),R.layout.list_item, localProvList,viewOption,0);
        lv.setAdapter(provLA);
        getActivity().setTitle("Province List");


        //Create and populate the drop down list on toolbar
        Spinner spin= (Spinner) provListView.findViewById(R.id.ListDrop);
        Spinner spin2= (Spinner) provListView.findViewById(R.id.ListDrop2);

        ArrayAdapter<CharSequence> adap= ArrayAdapter.createFromResource(getContext(), R.array.Categories,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adap2= ArrayAdapter.createFromResource(getContext(), R.array.Order,R.layout.spinner_item);

        adap.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        adap2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spin.setAdapter(adap);
        spin2.setAdapter(adap2);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewOption=i;
                lv.invalidate();
                ProvinceListAdapter newLA= new ProvinceListAdapter(getActivity(),R.layout.list_item, localProvList,viewOption,order);
                lv.setAdapter(newLA);
            }

           @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                viewOption=0;
               lv.invalidate();
                ProvinceListAdapter newLA= new ProvinceListAdapter(getActivity(),R.layout.list_item, localProvList,viewOption,order);
              lv.setAdapter(newLA);

           }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                order=i;
                lv.invalidate();
                ProvinceListAdapter newLA= new ProvinceListAdapter(getActivity(),R.layout.list_item, localProvList,viewOption,order);
                lv.setAdapter(newLA);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                order=0;
                lv.invalidate();
                ProvinceListAdapter newLA= new ProvinceListAdapter(getActivity(),R.layout.list_item, localProvList,viewOption,order);
                lv.setAdapter(newLA);

            }
        });

        Log.d("array", localProvList.toString());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProvDetail.class);
                Province prov;
                if (viewOption!=0) {prov = localProvList.get(i);}
                else {prov = ProvinceList.listProvince.get(i);}
                // go to province detail with ID as intent
                intent.putExtra("PROVINCE_ID", prov.getID());

                startActivity(intent);
            }
        });


        return provListView;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_prov_detail, menu);
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
