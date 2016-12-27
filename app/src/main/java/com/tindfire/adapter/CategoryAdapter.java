package com.tindfire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tindfire.R;
import com.tindfire.activity.ActivityMain;
import com.tindfire.model.RecomondationModel.RecomondationResult;
import com.tindfire.model.RecomondationModel.RecomondationnPhoto;

import java.util.List;

/**
 * Created by vcareall on 7/12/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ActivityMain activityMain;
    private final Context context;
    private final List<RecomondationResult> recomondationResultList;

    public CategoryAdapter(ActivityMain activityMain, Context context, List<RecomondationResult>recomondationResultList) {
        this.activityMain=activityMain;
        this.context=context;
        this.recomondationResultList=recomondationResultList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activity_main_item,null);
        RecyclerView.ViewHolder holder = new CategoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CategoryViewHolder viewHolder = (CategoryViewHolder) holder;

        try{
            List<RecomondationnPhoto>  recomondationnPhotoList=recomondationResultList.get(position).getPhotos();
            if(recomondationnPhotoList!=null){
                String userImage=recomondationnPhotoList.get(0).getUrl();
                if(userImage.length()>0){
                    Glide.with(context).load(userImage)
                            .thumbnail(0.5f)
                            .crossFade()
                            .placeholder(R.mipmap.app_icon)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(viewHolder.userImage);
                    Log.d("Android :","userImage :" +userImage);
                }else {
                    viewHolder.userImage.setBackgroundResource(R.mipmap.app_icon);
                    Log.d("Android :","userImage1 :" +userImage);
                }
            }

            if(recomondationResultList.get(position).getName().length()>0){
                viewHolder.userName.setText(recomondationResultList.get(position).getName());
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityMain.clickImage(position);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return recomondationResultList.size();
    }
    class CategoryViewHolder extends RecyclerView.ViewHolder{

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            userName = (TextView) itemView.findViewById(R.id.userName);
            userBt = (ImageView) itemView.findViewById(R.id.userBt);
            userImage = (ImageView) itemView.findViewById(R.id.userNmage);
        }
        View itemView;
        TextView userName;
        ImageView userBt,userImage;
    }
}
