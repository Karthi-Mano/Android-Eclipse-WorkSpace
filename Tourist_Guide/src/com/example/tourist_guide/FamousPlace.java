package com.example.tourist_guide;

import android.os.Parcel;
import android.os.Parcelable;
    
    public class FamousPlace  implements Parcelable{

    	int id;
    	String famousPlace;
    	String location;
        String placeImage;
    	
    	// Default Constructor
    	public  FamousPlace() {
    		super();
    	}
    	
    	// Parameterized Constructor
    	
    	
    	public FamousPlace(int id, String famousPlace, String location,
				String placeImage) {
			super();
			this.id = id;
			this.famousPlace = famousPlace;
			this.location = location;
			this.placeImage = placeImage;
		}

		@Override
		public String toString() {
			return "FamousPlace [id=" + id + ", famousPlace=" + famousPlace
					+ ", location=" + location + ", placeImage=" + placeImage
					+ "]";
		}

		@Override
    	public int describeContents() {
    		// TODO Auto-generated method stub
    		return 0;
    	}

    	@Override
    	public void writeToParcel(Parcel parcel, int arg) {
    		// TODO Auto-generated method stub
    		parcel.writeInt(id);
    		parcel.writeString(famousPlace);
    		parcel.writeString(location);
    		parcel.writeString(placeImage);
    		
    	}
    	public static Parcelable.Creator<FamousPlace> CREATER = new Parcelable.Creator<FamousPlace>() {

    		@Override
    		public FamousPlace createFromParcel(Parcel parcel) {
    			// TODO Auto-generated method stub
    			FamousPlace famousPlace = new FamousPlace();
    			famousPlace.id = parcel.readInt();
    			famousPlace.famousPlace = parcel.readString();
    			famousPlace.location= parcel.readString();
    			famousPlace.placeImage = parcel.readString();
    			return famousPlace;
    		}

    		@Override
    		public FamousPlace[] newArray(int arg0) {
    			// TODO Auto-generated method stub
    			return null;
    		}
    	};
    	
    }
