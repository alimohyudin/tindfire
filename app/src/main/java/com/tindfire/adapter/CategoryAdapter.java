package com.tindfire.adapter;

import android.content.Context;
import android.os.Handler;
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
                    viewHolder.likeIvs.setImageResource(R.mipmap.like_colors);
                }else{
                    viewHolder.likeIvs.setImageResource(R.mipmap.like);
                }
                if(recomondationResultList.get(position).isSuperlike()){
                    viewHolder.superLikeIv.setImageResource(R.mipmap.superlike);
                }else{
                    viewHolder.superLikeIv.setImageResource(R.mipmap.superlike_deactive);
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
                viewHolder.likeIvLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.setSelected(!model.isSelected());
                        viewHolder.likeIvs.setImageResource(model.isSelected() ? R.mipmap.like_colors : R.mipmap.like);
                        if(model.isSelected()){
                            activityMain.selectedLike(model.getId(),position);
                            recomondationRejected.add(model.getId());

                            Log.d("ANdroid :","recomondationRejected ::" +recomondationRejected.size());
                        }else{
//                            activityMain.selectedDislike(model.getId(),position);
//                            recomondationRejected.remove(model.getId());
//                            Log.d("ANdroid :","recomondationRejected_remove ::" +recomondationRejected.size());

                        }

                        if(recomondationResultList.size()==1){
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    activityMain.refershAgainRecsApi();
                                }
                            },3000);

                        }
                    }
                });

                //for pass the
                viewHolder.passll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.setPassSelected(!model.isPassSelected());
                        if(model.isPassSelected()){
                            activityMain.selectedDislike(model.getId(),position);

                        }
                        if(recomondationResultList.size()==1){
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    activityMain.refershAgainRecsApi();
                                }
                            },3000);

                        }
                    }
                });
                viewHolder.superlikeLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.setSuperLikeSelected(!model.isSuperLikeSelected());
                        viewHolder.superLikeIv.setImageResource(model.isSuperLikeSelected() ? R.mipmap.superlike : R.mipmap.superlike_deactive);
                        if(model.isSuperLikeSelected()){
                            activityMain.selectedSuperLike(model.getId(),position);
//                           recomondationRejected.add(model.getId());
                            Log.d("ANdroid :","recomondationRejected ::" +recomondationRejected.size());
                        }else{
//                            activityMain.selectedDislike(model.getId(),position);
//                            recomondationRejected.remove(model.getId());
                            Log.d("ANdroid :","recomondationRejected_remove ::" +recomondationRejected.size());

                        }
                        if(recomondationResultList.size()==1){
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    activityMain.refershAgainRecsApi();
                                }
                            },3000);
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
            likeIvs = (ImageView) itemView.findViewById(R.id.likeIV);
            passIv = (ImageView) itemView.findViewById(R.id.passIv);
            superLikeIv = (ImageView) itemView.findViewById(R.id.superLikeIv);
            userImage = (ImageView) itemView.findViewById(R.id.userNmage);
            likeIvLL = (LinearLayout) itemView.findViewById(R.id.likeIvLL);
            superlikeLL = (LinearLayout) itemView.findViewById(R.id.superlikeLL);
            passll = (LinearLayout) itemView.findViewById(R.id.passll);
        }
        View itemView;
        TextView userName;
        ImageView likeIvs,userImage,superLikeIv,passIv;
        LinearLayout likeIvLL,superlikeLL,passll;
    }
    public List<String> getAllId(){
        List<String> recomondationResults=new ArrayList<>();
        for(int i=0;i<recomondationResultList.size();i++){
            recomondationResults.add(recomondationResultList.get(i).getId());
        }
        Log.d("Android :","list of recom all :" +recomondationResults.size());
        return recomondationResults;

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
    public void likeProfile(int position) {
        recomondationResultList.get(position).setLike(true);
        Log.d("Android :","likeprofile :" +recomondationResultList.get(position).isLike());
        notifyDataSetChanged();
    }
    public void passProfile(int position) {
        recomondationResultList.get(position).setLike(false);
        Log.d("Android :","passProfile :" +recomondationResultList.get(position).isLike());
        notifyDataSetChanged();
    }
    public void removePositonFromProfile(int position){
        recomondationResultList.remove(position);
        notifyDataSetChanged();
    }


}
