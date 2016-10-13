package pl.noskilljustfun.dwakrokistad.adapters;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.noskilljustfun.dwakrokistad.model.ClueItem;
import pl.noskilljustfun.dwakrokistad.R;

/**
 * Created by zeno on 2016-05-06.
 */
public class ClueAdapter extends ArrayAdapter<ClueItem> {

    public ClueAdapter(Context context, List<ClueItem> item) {
        super(context, R.layout.list_item,item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_item,parent,false);


            holder=new ViewHolder();
            holder.textViewItem=(TextView)convertView.findViewById(R.id.label);
            holder.Image=(ImageView)convertView.findViewById(R.id.icon2);
            holder.textViewItemBelow=(TextView)convertView.findViewById(R.id.label2);
            convertView.setTag(holder);


        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        ClueItem item=getItem(position);
        holder.textViewItem.setText(item.title);
        holder.Image.setImageDrawable(item.icon);
        holder.textViewItemBelow.setText(item.desc);
        return convertView;
        }

    static class ViewHolder{
        private TextView textViewItem;
        private ImageView Image;
        private TextView textViewItemBelow;
    }
}
