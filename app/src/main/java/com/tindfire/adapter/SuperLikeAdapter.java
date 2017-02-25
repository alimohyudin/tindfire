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
import com.tindfire.fragment.SuperLikedFragment;

import java.util.List;

/**
 * Created by vcareall on 7/12/16.
 */
public class SuperLikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final SuperLikedFragment superLikedFragment;
    private final Context context;
    private final List<FirebaseLike> firebaseLikeList;


    public SuperLikeAdapter(SuperLikedFragment superLikedFragment, Context context, List<FirebaseLike>firebaseLikeList) {
        this.superLikedFragment=superLikedFragment;
        this.context=context;
        this.firebaseLikeList=firebaseLikeList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_activity_match_item,null);
        RecyclerView.ViewHolder holder = new AlreadySuperLikeHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AlreadySuperLikeHolder viewHolder = (AlreadySuperLikeHolder) holder;

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
                    superLikedFragment.setSuperLikeMe(firebaseLikeList.get(position).getUserID());
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
    class AlreadySuperLikeHolder extends RecyclerView.ViewHolder{

        public AlreadySuperLikeHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            userName = (TextView) itemView.findViewById(R.id.userName);
            userBt = (ImageView) itemView.findViewById(R.id.likeIV);
            userImage = (ImageView) itemView.findViewById(R.id.userNmage);
            userBt.setImageResource(R.mipmap.superlike);
        }
        View itemView;
        TextView userName;
        ImageView userBt,userImage;
    }


}
