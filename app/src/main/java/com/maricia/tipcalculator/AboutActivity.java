package com.maricia.tipcalculator;


import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.MessageFormat;

/**
 * Created by maricia on 2/1/2018.
 */

public class AboutActivity extends AppCompatActivity{

    private TextView tpversion;
    private TextView tpauthor;

    int versionCode = BuildConfig.VERSION_CODE;  //build
    String versionName = BuildConfig.VERSION_NAME;   //version

    ListView myaboutlistview;
    public static String[] myMedia = {"You Tube", "Twitter", "Google+", "Tumbler"};

    //store logos in drawable and make array to hold images
    //int[] images



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        tpversion = this.findViewById(R.id.tpversion);
        tpauthor = this.findViewById(R.id.tpauthor);

        tpversion.setText(MessageFormat.format("version:{0} Build: {1}",versionName,versionCode));

        tpauthor.setText(MessageFormat.format("Author: {0} ", String.format("Maricia Alleman")));

        myaboutlistview = (ListView) findViewById(R.id.aboutlistview);

    }


public class CustomListAdapter extends BaseAdapter {
    @Override
    public int getCount() {

        return myMedia.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = getLayoutInflater().inflate(R.layout.listview_layout, myaboutlistview);


        TextView textView= (TextView) view.findViewById(R.id.textView);

       textView.setText(myMedia[position]);


        return view;

    }
}

}