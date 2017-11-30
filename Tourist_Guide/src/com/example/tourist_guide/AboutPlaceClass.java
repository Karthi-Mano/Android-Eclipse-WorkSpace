package com.example.tourist_guide;
import android.os.Parcel;
import android.os.Parcelable;
    
    public class AboutPlaceClass  implements Parcelable{

    	
    	String famousPlace;
    	String placeDescription;
        String placeImage;
    	
    	// Default Constructor
    	public  AboutPlaceClass() {
    		super();
    	}
    	
    	// Parameterized Constructor
    	
    	
    	public AboutPlaceClass( String famousPlace, String placeDescription,
				String placeImage) {
			super();
			this.famousPlace = famousPlace;
			this.placeDescription = placeDescription;
			this.placeImage = placeImage;
		}
		@Override
		public String toString() {
			return "PlaceDescriptionClass [famousPlace=" + famousPlace
					+ ", placeDescription=" + placeDescription
					+ ", placeImage=" + placeImage + "]";
		}

		@Override
    	public int describeContents() {
    		// TODO Auto-generated method stub
    		return 0;
    	}

    	@Override
    	public void writeToParcel(Parcel parcel, int arg) {
    		// TODO Auto-generated method stub
    		parcel.writeString(famousPlace);
    		parcel.writeString(placeDescription);
    		//parcel.writeString(placeImage);
    		
    	}
    	public static Parcelable.Creator<AboutPlaceClass> CREATER = new Parcelable.Creator<AboutPlaceClass>() {

    		@Override
    		public AboutPlaceClass createFromParcel(Parcel parcel) {
    			// TODO Auto-generated method stub
    			AboutPlaceClass placeDescriptionObj1 = new AboutPlaceClass();
    			placeDescriptionObj1.famousPlace = parcel.readString();
    			placeDescriptionObj1.placeDescription= parcel.readString();
    	//		placeDescriptionObj1.placeImage = parcel.readString();
    			return placeDescriptionObj1;
    		}

    		@Override
    		public AboutPlaceClass[] newArray(int arg0) {
    			// TODO Auto-generated method stub
    			return null;
    		}
    	};
    	
    }