package com.zorbando.harit.zorbandocontests;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by harit on 3/7/2016.
 */
public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.MyviewHolder>  {
    private LayoutInflater inflater;
    private List<Contestsinfo> contestsinfoList;
    private Context mContext;
    private AQuery aQuery;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public ContestAdapter (Context context, List<Contestsinfo> data)
    {
        inflater = LayoutInflater.from(context);
        this.contestsinfoList = data;
        this.mContext=context;


    }
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contests_list_row, parent, false);
        MyviewHolder myviewHolder = new MyviewHolder(view);
        return myviewHolder;

    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {
        Contestsinfo current = contestsinfoList.get(position);
        holder.title.setText(current.title);
        holder.name.setText(current.name);
      //aQuery.image(current.src,false,false);



        //    holder.src.setImageUrl(current.src, mImageLoader);
        //mRequestQueue = Volley.newRequestQueue(mContext);
        //mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
          //  private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            //public void putBitmap(String url, Bitmap bitmap) {
       //         mCache.put(url, bitmap);
         //   }
            //public Bitmap getBitmap(String url) {
           //     return mCache.get(url);
           // }
        //});
        int pos = getItemViewType(position);
        if(contestsinfoList.get(pos).src == null) {

            holder.src.setVisibility(View.GONE);
        } else {

            Picasso.with(mContext).load(current.src)
                    .error(R.drawable.loading_file)
                    .placeholder(R.drawable.loading_file)
                    .into(MyviewHolder.src);

        }

       // holder.date_expiry.setText(current.date_expiry);
        //holder.date_added.setText(current.date_added);
       //holder.participate_src.setText(current.name);
        holder.win_string.setText(current.win_string);
        holder.seo_description.setText(current.seo_description);
//        holder.participate_src.setOnClickListener(new View.OnClickListener() {
  //          @Override
    //        public void onClick(View v) {
//
  //          }
    //    });


    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (null != contestsinfoList ? contestsinfoList.size() : 0);
    }


    public static class MyviewHolder extends  RecyclerView.ViewHolder {


        TextView title;
        TextView win_string;
        TextView seo_description;
       static ImageView src;
        Button participate_src;
        TextView date_added;
        TextView date_expiry;
        TextView name;

        public MyviewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            win_string = (TextView) itemView.findViewById(R.id.win_string);
            seo_description = (TextView) itemView.findViewById(R.id.description);
          //  date_added = (TextView) itemView.findViewById(R.id.date_added);
            //date_expiry = (TextView) itemView.findViewById(R.id.date_expiry);
            name = (TextView) itemView.findViewById(R.id.name);
            src = (ImageView) itemView.findViewById(R.id.src);
            //participate_src = (Button) itemView.findViewById(R.id.participate);
        }
    }
}
