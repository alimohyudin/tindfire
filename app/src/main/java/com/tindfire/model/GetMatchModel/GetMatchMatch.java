package com.tindfire.model.GetMatchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vcareall on 30/12/16.
 */

public class GetMatchMatch implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("closed")
    @Expose
    private Boolean closed;
    @SerializedName("common_friend_count")
    @Expose
    private Integer commonFriendCount;
    @SerializedName("common_like_count")
    @Expose
    private Integer commonLikeCount;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("dead")
    @Expose
    private Boolean dead;
    @SerializedName("last_activity_date")
    @Expose
    private String lastActivityDate;
    @SerializedName("message_count")
    @Expose
    private Integer messageCount;
    @SerializedName("messages")
    @Expose
    private List<Object> messages = null;
    @SerializedName("muted")
    @Expose
    private Boolean muted;
    @SerializedName("participants")
    @Expose
    private List<String> participants = null;
    @SerializedName("pending")
    @Expose
    private Boolean pending;
    @SerializedName("is_super_like")
    @Expose
    private Boolean isSuperLike;
    @SerializedName("is_boost_match")
    @Expose
    private Boolean isBoostMatch;
    @SerializedName("person")
    @Expose
    private GetMatchPerson person;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Integer getCommonFriendCount() {
        return commonFriendCount;
    }

    public void setCommonFriendCount(Integer commonFriendCount) {
        this.commonFriendCount = commonFriendCount;
    }

    public Integer getCommonLikeCount() {
        return commonLikeCount;
    }

    public void setCommonLikeCount(Integer commonLikeCount) {
        this.commonLikeCount = commonLikeCount;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getDead() {
        return dead;
    }

    public void setDead(Boolean dead) {
        this.dead = dead;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public List<Object> getMessages() {
        return messages;
    }

    public void setMessages(List<Object> messages) {
        this.messages = messages;
    }

    public Boolean getMuted() {
        return muted;
    }

    public void setMuted(Boolean muted) {
        this.muted = muted;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public Boolean getPending() {
        return pending;
    }

    public void setPending(Boolean pending) {
        this.pending = pending;
    }



    public Boolean getIsSuperLike() {
        return isSuperLike;
    }

    public void setIsSuperLike(Boolean isSuperLike) {
        this.isSuperLike = isSuperLike;
    }

    public Boolean getIsBoostMatch() {
        return isBoostMatch;
    }

    public void setIsBoostMatch(Boolean isBoostMatch) {
        this.isBoostMatch = isBoostMatch;
    }

    public GetMatchPerson getPerson() {
        return person;
    }

    public void setPerson(GetMatchPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "GetMatchMatch{" +
                "closed=" + closed +
                ", id='" + id + '\'' +
                ", commonFriendCount=" + commonFriendCount +
                ", commonLikeCount=" + commonLikeCount +
                ", createdDate='" + createdDate + '\'' +
                ", dead=" + dead +
                ", lastActivityDate='" + lastActivityDate + '\'' +
                ", messageCount=" + messageCount +
                ", messages=" + messages +
                ", muted=" + muted +
                ", participants=" + participants +
                ", pending=" + pending +
                ", isSuperLike=" + isSuperLike +
                ", isBoostMatch=" + isBoostMatch +
                ", person=" + person +
                '}';
    }
}
