package com.tindfire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tindfire.R;
import com.tindfire.activity.FacebookFriendsActivity;
import com.tindfire.model.FacebookFriendsModel.FacebookFriendsPhoto;
import com.tindfire.model.FacebookFriendsModel.FacebookFriendsProcessedFile;
import com.tindfire.model.FacebookFriendsModel.FacebookFriendsResult;

import java.util.List;

/**
 * Created by vcareall on 15/2/17.
 */

public class FacebookFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final FacebookFriendsActivity facebookFriendsActivity;
    private final List<FacebookFriendsResult> facebookFriendsResultList;

    public FacebookFriendsAdapter(FacebookFriendsActivity facebookFriendsActivity, Context context, List<FacebookFriendsResult> facebookFriendsResultList) {
        this.facebookFriendsActivity=facebookFriendsActivity;
        this.context=context;
        this.facebookFriendsResultList=facebookFriendsResultList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activity_facebookfriend_item,null);
        RecyclerView.ViewHolder holder = new FacebookFriendsAdapter.FacebookFriendViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final FacebookFriendsAdapter.FacebookFriendViewHolder viewHolder = (FacebookFriendsAdapter.FacebookFriendViewHolder) holder;
        try{
            viewHolder.facebookUserName.setText(facebookFriendsResultList.get(position).getName());
            List<FacebookFriendsPhoto> facebookFriendsPhotoList=facebookFriendsResultList.get(position).getPhoto();
            if(facebookFriendsPhotoList!=null){
                List<FacebookFriendsProcessedFile> facebookFriendsProcessedFileList=facebookFriendsPhotoList.get(0).getProcessedFiles();
                if(facebookFriendsProcessedFileList!=null){
                    String userImage=facebookFriendsProcessedFileList.get(0).getUrl();
                    Glide.with(context).load(userImage)
                            .thumbnail(0.5f)
                            .crossFade()
                            .placeholder(R.mipmap.app_icon)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(viewHolder.profileImage);
                }
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    facebookFriendsActivity.clickFacebookFriends(facebookFriendsResultList.get(position).getUserId());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return facebookFriendsResultList.size();
    }
    class FacebookFriendViewHolder extends RecyclerView.ViewHolder{

        public FacebookFriendViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            facebookUserName = (TextView) itemView.findViewById(R.id.facebookUserName);
            profileImage = (ImageView) itemView.findViewById(R.id.profileImage);

        }
        View itemView;
        TextView facebookUserName;
        ImageView profileImage;

    }
}
