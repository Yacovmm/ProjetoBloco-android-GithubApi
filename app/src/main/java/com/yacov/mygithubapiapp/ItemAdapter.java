package com.yacov.mygithubapiapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yacov.mygithubapiapp.controlller.DetailActivity;
import com.yacov.mygithubapiapp.model.Item;

import java.util.List;

/**
 * Created by YacovR on 23-Nov-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> items;
    private Context context;

    public ItemAdapter(Context aplicationContext, List<Item> itemArrayList){
        this.context = aplicationContext;
        this.items = itemArrayList;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_user, viewGroup , false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i){
        viewHolder.title.setText(items.get(i).getLogin());
        viewHolder.githublink1.setText(items.get(i).getHtmlUrl());

        Picasso.with(context)
                .load(items.get(i).getAvatarUrl())
                .placeholder(setCircularImage(R.drawable.load))
                .into(viewHolder.imageView);

    }

    private RoundedBitmapDrawable setCircularImage(int id) {
        Resources res = context.getApplicationContext().getResources();
        Bitmap src = BitmapFactory.decodeResource(res, id);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(res, src);
        roundedBitmapDrawable.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
        return roundedBitmapDrawable;
    }


    @Override
    public int getItemCount(){
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, githublink1;
        private ImageView imageView;

        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.titleID);
            githublink1 = (TextView) view.findViewById(R.id.githubLinkID);
            imageView = (ImageView) view.findViewById(R.id.coverID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Item clickdDataItem = items.get(position);
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("login", items.get(position).getLogin());
                        intent.putExtra("html_url", items.get(position).getHtmlUrl());
                        intent.putExtra("avatar_url", items.get(position).getAvatarUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "User: " + clickdDataItem.getLogin(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }


}

