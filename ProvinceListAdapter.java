package com.example.ckmj.interactivemap;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by CUONG on 8/29/2017.
 */
// Adapter for the Province List View
public class ProvinceListAdapter extends ArrayAdapter<Province> {
    private List<Province> listProv;
    private int viewOption;
    private Province prov;
    private TextView subText, nameText,outerRank;
    public ProvinceListAdapter (Context context, int resource, List<Province> listProv ,int viewOption, int order) {
        super(context, resource, listProv);
        this.viewOption= viewOption;
        this.listProv=listProv;
        Log.d("view", ""+viewOption);
        switch(viewOption)
        {

            case 0:
                ArrayList<Province> trans= new ArrayList<Province>();
                for (Province p: ProvinceList.listProvince)
               {
                    trans.add(p);
                    Log.d("prov", p.toString());
               }

              this.listProv=trans;
                break;
            case 1:
                Collections.sort(listProv, new Comparator<Province>() {
                    @Override
                    public int compare(Province o1, Province o2) {
                        int re= Integer.compare(o1.getPopulation(), o2.getPopulation());
                        if (re==0) re= o1.getName().compareTo(o2.getName());
                        return re;
                    }
                });
                Collections.reverse(listProv);

                break;
            case 2:
                Collections.sort(listProv, new Comparator<Province>() {
                    @Override
                    public int compare(Province o1, Province o2) {
                        int re= Double.compare(o1.getArea(), o2.getArea());
                        if (re==0) re= o1.getName().compareTo(o2.getName());
                        return re;
                    }
                });
                Collections.reverse(listProv);

                break;
            case 3:
                Collections.sort(listProv, new Comparator<Province>() {
                    @Override
                    public int compare(Province o1, Province o2) {
                        int re= Integer.compare(o1.getPopDensity(), o2.getPopDensity());
                        if (re==0) re= o1.getName().compareTo(o2.getName());
                        return re;
                    }
                });
                Collections.reverse(listProv);

                break;
            case 4:
                Collections.sort(listProv, new Comparator<Province>() {
                    @Override
                    public int compare(Province o1, Province o2) {
                        int re= Double.compare(o1.getPCI(), o2.getPCI());
                        if (re==0) re= o1.getName().compareTo(o2.getName());
                        return re;
                    }
                });
                Collections.reverse(listProv);

                break;
        }

        if (order==1) Collections.reverse(listProv);
    }

    // Convert Province Data into Individual List Form. ViewOption is used to determine what parameter is being used to sort the data
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item, parent, false);
        }



        prov = listProv.get(position);

         subText = (TextView) convertView.findViewById(R.id.subText);
        Typeface subTF= Typeface.createFromAsset(getContext().getAssets(),"fonts/OpenSans-SemiboldItalic.ttf");

         nameText = (TextView) convertView.findViewById(R.id.nameText);
        nameText.setText(prov.getName());
        nameText.setTypeface(subTF);
        nameText.setPaintFlags(nameText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        outerRank= (TextView) convertView.findViewById(R.id.outerRank);
        outerRank.setText(""+(position+1)+".");





        DecimalFormat df= new DecimalFormat("###,###"); //format to make numbers more natural
        //change the Subtext depending on what criterion is used
        switch(viewOption)
        {

            case 0:
                subText.setText("");
                break;
            case 1:
             int popText= prov.getPopulation();
                subText.setText(df.format(popText));
                break;
            case 2:
                double areaText= prov.getArea();
                subText.setText(df.format(areaText)+" KM"+(char)0x00B2);
                break;
            case 3:

                int densText= prov.getPopDensity();
                        subText.setText(df.format(densText)+"/KM"+(char)0x00B2);
                break;
            case 4:
            double PCItext= prov.getPCI();
                subText.setText(Double.toString(PCItext));
                break;
        }





        ImageView iv = (ImageView) convertView.findViewById(R.id.listIMG);
        Bitmap bitmap = getBitmapFromAsset(prov.getID());
        iv.setImageBitmap(bitmap);

        return convertView;
    }

    private Bitmap getBitmapFromAsset(String provinceId) {
        AssetManager assetManager = getContext().getAssets();
        InputStream stream = null;

        try {
            stream = assetManager.open(provinceId + "S.jpg");
            return BitmapFactory.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
