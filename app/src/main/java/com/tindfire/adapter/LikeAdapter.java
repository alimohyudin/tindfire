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
import com.tindfire.firebase.FirebaseLike;
import com.tindfire.fragment.LikedFragment;

import java.util.List;

/**
 * Created by vcareall on 7/12/16.
 */
public class LikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LikedFragment likedFragment;
    private final Context context;
    private final List<FirebaseLike> firebaseLikeList;


    public LikeAdapter(LikedFragment likedFragment, Context context, List<FirebaseLike>firebaseLikeList) {
        this.likedFragment=likedFragment;
        this.context=context;
        this.firebaseLikeList=firebaseLikeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activity_match_item,null);
        RecyclerView.ViewHolder holder = new AlreadyLikeAdapterHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AlreadyLikeAdapterHolder viewHolder = (AlreadyLikeAdapterHolder) holder;

        try{
                String userImage=firebaseLikeList.get(position).getImageUrl();
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

            if(firebaseLikeList.get(position).getUserName().length()>0){
                viewHolder.userName.setText(firebaseLikeList.get(position).getUserName());
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likedFragment.setlikeProfile(firebaseLikeList.get(position).getUserID());
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return firebaseLikeList.size();
    }
    class AlreadyLikeAdapterHolder extends RecyclerView.ViewHolder{

        public AlreadyLikeAdapterHolder(View itemView) {
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
