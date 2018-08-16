package com.zhangwei.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TileContentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        // onCreateView 里面加载布局文件
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.recycle_view,container,false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        int tilePadding =  getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding,tilePadding,tilePadding,tilePadding);
        //加载GridManger 布局管理器 设置 2列
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        return recyclerView;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView picture;
        public TextView name;
        public ViewHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.item_tile,parent,false));
            picture = (ImageView)itemView.findViewById(R.id.title_picture);
            name = (TextView)itemView.findViewById(R.id.tile_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context,DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION,getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>{
        private static final int LENGTH = 18;
        private final String[] mPlaces;
        private final Drawable[] mPlacePictures;
        public ContentAdapter(Context context){
            // 从string 中加载资源
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            // 加载 drawble 里面的图片资源
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for(int i = 0; i < mPlacePictures.length; ++i){
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.picture.setImageDrawable(mPlacePictures[position%mPlacePictures.length]);
            holder.name.setText(mPlaces[position%mPlaces.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
