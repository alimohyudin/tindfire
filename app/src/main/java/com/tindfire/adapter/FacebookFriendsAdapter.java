package com.tindfire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tindfire.R;
import com.tindfire.activity.FacebookFriendsActivity;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FacebookFriendsAdapter.FacebookFriendViewHolder viewHolder = (FacebookFriendsAdapter.FacebookFriendViewHolder) holder;
        viewHolder.facebookUserName.setText(facebookFriendsResultList.get(position).getName());
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

        }
        View itemView;
        TextView facebookUserName;

    }
}
