package com.example.gisela.worldworld;

import android.content.Context;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gisela on 1/19/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private ClickListener clickListener;
    private LayoutInflater inflater;
    private Context context;
    List<Item> data = Collections.emptyList();

    public MyAdapter(Context context, List<Item> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.icon.setImageDrawable(current.getImage());

    }

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView);

            //set font ComicSans
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "Fonts/ComicSans.ttf");
            title.setTypeface(custom_font);

            icon = (ImageView) itemView.findViewById(R.id.dogs);
            icon.setOnClickListener(this);
        }

        //when user clicks on the ImageView
        @Override
        public void onClick(View v) {
            if(clickListener != null)
            {
                clickListener.itemClicked(v,getPosition());
            }

        }
    }

    public interface ClickListener
    {
        public void itemClicked(View view, int position);

    }


}


