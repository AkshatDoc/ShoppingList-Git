package com.example.shoppinglistv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, ArrayList<Item> itemArrayList)
    {
        super(context,0,itemArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.temp_list_view, parent, false);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView qty = convertView.findViewById(R.id.qty);
        TextView cost = convertView.findViewById(R.id.cost);
        CheckBox bought = convertView.findViewById(R.id.isBought);

        assert item != null;
        name.setText(item.getItemName());
        qty.setText("#: " + item.getQty());
        cost.setText("$ " + item.getCost());
        bought.setChecked(item.isPurchased());
        return convertView;
    }
}
