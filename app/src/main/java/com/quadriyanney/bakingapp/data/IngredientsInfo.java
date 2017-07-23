package com.quadriyanney.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by quadriy on 6/23/17.
 */

public class IngredientsInfo implements Parcelable{

    private double quantity;
    private String measurement, ingredient_name;

    public IngredientsInfo(int quantity, String measurement, String ingredient_name){
        this.quantity = quantity;
        this.measurement = measurement;
        this.ingredient_name = ingredient_name;
    }

    private IngredientsInfo(Parcel in) {
        quantity = in.readInt();
        measurement = in.readString();
        ingredient_name = in.readString();
    }

    public static final Creator<IngredientsInfo> CREATOR = new Creator<IngredientsInfo>() {
        @Override
        public IngredientsInfo createFromParcel(Parcel in) {
            return new IngredientsInfo(in);
        }

        @Override
        public IngredientsInfo[] newArray(int size) {
            return new IngredientsInfo[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measurement);
        parcel.writeString(ingredient_name);
    }
}
