package com.example.tourist_guide;

import android.os.Parcel;
import android.os.Parcelable;

public class Hotel implements Parcelable{

	String famousPlace;
	String hotelDescription;
	
	public Hotel() {
		super();
	}

	public Hotel(String famousPlace, String hotelDescription) {
		super();
		this.famousPlace = famousPlace;
		this.hotelDescription = hotelDescription;
		
	}


	@Override
	public String toString() {
		return "Hotel [famousPlace=" + famousPlace + ", hotelDescription="
				+ hotelDescription + "]";
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
		parcel.writeString(hotelDescription);
	}
	
	public static Parcelable.Creator<Hotel> CREATER = new Parcelable.Creator<Hotel>() {

		@Override
		public Hotel createFromParcel(Parcel parcel) {
			// TODO Auto-generated method stub
			Hotel hotel = new Hotel();
			hotel.famousPlace= parcel.readString();
			hotel.hotelDescription = parcel.readString();
			return hotel;
		}

		@Override
		public Hotel[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
}
