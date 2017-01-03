package com.tindfire.model.LikeResponce;

/**
 * Created by vcareall on 29/12/16.
 */

public class LikeResponceExample {
    Object match;
    int likesRemaining;
     boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public Object match() {
        return match;
    }

    @Override
    public String toString() {
        return "LikeResponceExample{" +
                "likesRemaining=" + likesRemaining +
                ", match=" + match.getClass().getSimpleName() +
                '}';
    }
}
