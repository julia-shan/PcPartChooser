package com.example.pcpartchooser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pcpartchooser.Data.PcPart;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public static final String LIST_ITEM_KEY = "listItem";

    private List<PcPart> itemsList;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImageView;
        private TextView itemNameTextView;
        private TextView itemPriceTextView;
        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this); //Setting an onclick listener for every part
            //Setting the views from the xml
            itemImageView = (ImageView) v.findViewById(R.id.list_item_image);
            itemNameTextView = (TextView) v.findViewById(R.id.list_item_name);
            itemPriceTextView = (TextView) v.findViewById(R.id.list_item_price);
        }

        @Override
        public void onClick(View v) {
            //Setting the context
            Activity activity = (Activity) context;

            //Creating new intent to go to the details activity
            Intent intent = new Intent(context, DetailsActivity.class);

            //Passing in the selected part into the intent for the new activity
            intent.putExtra(LIST_ITEM_KEY, itemsList.get(getAdapterPosition()));

            //Start activity and sets the slide transitions
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
    }

    public ListAdapter(List<PcPart> itemsList) {this.itemsList = itemsList;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        //Inflates the views in list_item.xml with the data
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(listItemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        //Sets the name, price and image of the part into the recycler view
        PcPart thisPart = itemsList.get(position);
        holder.itemNameTextView.setText(thisPart.getName());
        holder.itemPriceTextView.setText("Price: $"+ String.valueOf(thisPart.getPrice()) + " ");
        int imageID = context.getResources().getIdentifier(
               thisPart.getImageIDs().get(0), "drawable", context.getPackageName());
        holder.itemImageView.setImageResource(imageID);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
