package com.example.tourist_guide;

import android.os.Parcel;
import android.os.Parcelable;

public class Festival implements Parcelable{

	String famousPlace;
	String festivalDescription;
	String festivalImage;
	
	public Festival() {
		super();
	}

	
	public Festival(String famousPlace, String festivalDescription,
			String festivalImage) {
		super();
		this.famousPlace = famousPlace;
		this.festivalDescription = festivalDescription;
		this.festivalImage = festivalImage;
	}


	@Override
	public String toString() {
		return "Festival [famousPlace=" + famousPlace
				+ ", festivalDescription=" + festivalDescription
				+ ", festivalImage=" + festivalImage + "]";
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg1) {
		// TODO Auto-generated method stub
		parcel.writeString(famousPlace);
		parcel.writeString(festivalDescription);
		parcel.writeString(festivalImage);
		
	}
	
	public static Parcelable.Creator<Festival> CREATER = new Parcelable.Creator<Festival>() {

		@Override
		public Festival createFromParcel(Parcel parcel) {
			// TODO Auto-generated method stub
			Festival festival = new Festival();
			festival.famousPlace= parcel.readString();
			festival.festivalDescription = parcel.readString();
			festival.festivalImage = parcel.readString();
			return festival;
		}

		@Override
		public Festival[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
}