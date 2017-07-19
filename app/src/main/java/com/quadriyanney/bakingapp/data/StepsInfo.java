package com.quadriyanney.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quadriy on 6/23/17.
 */

public class StepsInfo implements Parcelable{

    private String shortDescription, description, videoUrl, thumbnailUrl;

    public StepsInfo(String shortDescription, String description, String videoUrl, String thumbnailUrl){
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    private StepsInfo(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<StepsInfo> CREATOR = new Creator<StepsInfo>() {
        @Override
        public StepsInfo createFromParcel(Parcel in) {
            return new StepsInfo(in);
        }

        @Override
        public StepsInfo[] newArray(int size) {
            return new StepsInfo[size];
        }
    };

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
    }
}
