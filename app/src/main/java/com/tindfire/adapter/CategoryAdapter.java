package com.tindfire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tindfire.R;
import com.tindfire.activity.ActivityMain;
import com.tindfire.model.RecomondationModel.RecomondationResult;
import com.tindfire.model.RecomondationModel.RecomondationnPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vcareall on 7/12/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ActivityMain activityMain;
    private final Context context;
    private final List<RecomondationResult> recomondationResultList;
    private ArrayList<String> recomondationRejected=new ArrayList<>();;


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
            final RecomondationResult model = recomondationResultList.get(position);
            final List<RecomondationnPhoto> recomondationnPhotoList=recomondationResultList.get(position).getPhotos();
            if(recomondationResultList!=null){
                if(recomondationResultList.get(position).isLike()){
                    viewHolder.userBt.setImageResource(R.mipmap.like_colors);
                }else{
                    viewHolder.userBt.setImageResource(R.mipmap.like);
                }
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
                        viewHolder.userImage.setImageResource(R.mipmap.app_icon);
                        Log.d("Android :","userImage1 :" +userImage);
                    }
                }else{
                    viewHolder.userImage.setImageResource(R.mipmap.pic1);
                }


                if(recomondationResultList.get(position).getName()!=null){
                    viewHolder.userName.setText(recomondationResultList.get(position).getName());
                }else{
                    viewHolder.userName.setText("Tinder");
                }

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Android :","recomondationnPhotoList photo :" +recomondationnPhotoList);
                        activityMain.clickImage(position,recomondationnPhotoList,
                                recomondationResultList.get(position).getName(),
                                recomondationResultList.get(position).getBio(),
                                recomondationResultList.get(position).getPingTime(),
                                recomondationResultList.get(position).getBirthDate(),
                                recomondationResultList.get(position).getDistanceMi(),
                                recomondationResultList.get(position).getId(),
                                recomondationResultList.get(position).isLike());
                    }
                });
                viewHolder.linarlayoutimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.setSelected(!model.isSelected());
                        viewHolder.userBt.setImageResource(model.isSelected() ? R.mipmap.like_colors : R.mipmap.reject);
                        if(model.isSelected()){
                            activityMain.selectedLike(model.getId(),position);
                            recomondationRejected.add(model.getId());
                            Log.d("ANdroid :","recomondationRejected ::" +recomondationRejected.size());
                        }else{
                            activityMain.selectedDislike(model.getId(),position);
                            recomondationRejected.remove(model.getId());
                            Log.d("ANdroid :","recomondationRejected_remove ::" +recomondationRejected.size());
                        }
                    }
                });
            }

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
            linarlayoutimage = (LinearLayout) itemView.findViewById(R.id.linarlayoutimage);
        }
        View itemView;
        TextView userName;
        ImageView userBt,userImage;
        LinearLayout linarlayoutimage;
    }
    public List<String> getAllId(){
        List<String> recomondationResults=new ArrayList<>();
        for(int i=0;i<recomondationResultList.size();i++){
            recomondationResults.add(recomondationResultList.get(i).getId());
        }
        Log.d("Android :","list of recom all :" +recomondationResults.size());
        return recomondationResults;
//        List<String> recomondationResults=new ArrayList<>();
//        if(recomondationRejected.size()>0){
//            for(int i=0;i<recomondationResultList.size();i++){
//                for(int j=0;j<recomondationRejected.size();j++){
//                    if(!recomondationResultList.get(i).getId().equalsIgnoreCase(recomondationRejected.get(j))){
//                        recomondationResults.add(recomondationResultList.get(i).getId());
//                    }
//                }
//            }
//            Log.d("Android :","list of recom :" +recomondationResults.size());
//            return recomondationResults;
//        }else{
//            for(int i=0;i<recomondationResultList.size();i++){
//                recomondationResults.add(recomondationResultList.get(i).getId());
//            }
//            Log.d("Android :","list of recom all :" +recomondationResults.size());
//            return recomondationResults;
//        }

    }
    public List<String>getRejectedId(){
        if(recomondationRejected!=null){
            return recomondationRejected;
        }else{

            for(int i=0;i<recomondationResultList.size();i++){
                recomondationRejected.add(recomondationResultList.get(i).getId());
            }
            return recomondationRejected;
        }
    }


}
