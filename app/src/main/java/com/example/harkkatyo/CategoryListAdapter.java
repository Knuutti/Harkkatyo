/* CategoryListAdapter.java

This code file is for displaying categories in a ListView in a desired way (extending ArrayAdapter)

*/

package com.example.harkkatyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CategoryListAdapter extends ArrayAdapter<Category> {

    private final Context mContext;
    private final int mResource;

    private static class ViewHolder {
        TextView tvCategoryName;
    }

    public CategoryListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Category> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String categoryName = getItem(position).getCategoryName();

        CategoryListAdapter.ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new CategoryListAdapter.ViewHolder();
            holder.tvCategoryName = (TextView) convertView.findViewById(R.id.tv_player_name_listview);

            convertView.setTag(holder);

        }
        else {
            holder = (CategoryListAdapter.ViewHolder) convertView.getTag();
        }

        // Setting up correct name for each game
        holder.tvCategoryName.setText(categoryName);

        return convertView;
    }
}
