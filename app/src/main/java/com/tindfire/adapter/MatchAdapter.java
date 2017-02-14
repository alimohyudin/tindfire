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
import com.tindfire.activity.MatchActivity;
import com.tindfire.model.GetMatchModel.GetMatchMatch;
import com.tindfire.model.GetMatchModel.GetMatchPerson;
import com.tindfire.model.GetMatchModel.GetMatchPhoto;

import java.util.List;

/**
 * Created by vcareall on 7/12/16.
 */
public class MatchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final MatchActivity activityMatch;
    private final Context context;
    private final List<GetMatchMatch> getMatchMatchList;


    public MatchAdapter(MatchActivity activityMatch, Context context, List<GetMatchMatch>getMatchMatchList) {
        this.activityMatch=activityMatch;
        this.context=context;
        this.getMatchMatchList=getMatchMatchList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activity_match_item,null);
        RecyclerView.ViewHolder holder = new MatchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MatchViewHolder viewHolder = (MatchViewHolder) holder;

        try{
            final GetMatchPerson getMatchPerson=getMatchMatchList.get(position).getPerson();
           final List<GetMatchPhoto> getMatchPhotoList=getMatchPerson.getPhotos();
            if(getMatchPhotoList!=null){
                String userImage=getMatchPhotoList.get(0).getUrl();
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
            if(getMatchPerson.getName().length()>0){
                viewHolder.userName.setText(getMatchPerson.getName());
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activityMatch.setMatch(position,getMatchPhotoList,getMatchPerson.getName(),
                            getMatchPerson.getBio(),getMatchPerson.getPingTime(),getMatchPerson.getBirthDate());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return getMatchMatchList.size();
    }
    class MatchViewHolder extends RecyclerView.ViewHolder{

        public MatchViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            userName = (TextView) itemView.findViewById(R.id.userName);
            userBt = (ImageView) itemView.findViewById(R.id.likeIV);
            userImage = (ImageView) itemView.findViewById(R.id.userNmage);
        }
        View itemView;
        TextView userName;
        ImageView userBt,userImage;
    }


}
