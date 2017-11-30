package com.example.tourist_guide;

import android.os.Parcel;
import android.os.Parcelable;

public class Country  implements Parcelable{

	String countryId;
	String countries;
	String language;
	String capital;
	String currency;
	String flagName;
	
	// Default Constructor
	public  Country() {
		super();
	}
	
	// Parameterized Constructor
	public Country(String countryId,String countries,String language,String capital,String currency,String flagName){
		
		super();
		this.countryId= countryId;
		this.countries = countries;
		this.language =  language;
		this.capital = capital;
		this.currency =currency;
		this.flagName= flagName;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countries=" + countries
				+ ", language=" + language + ", capital=" + capital
				+ ", currency=" + currency + ", flagName=" + flagName + "]";
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int arg) {
		// TODO Auto-generated method stub
		parcel.writeString(countryId);
		parcel.writeString(countries);
		parcel.writeString(language);
		parcel.writeString(capital);
		parcel.writeString(currency);
		parcel.writeString(flagName);
		
	}
	public static Parcelable.Creator<Country> CREATER = new Parcelable.Creator<Country>() {

		@Override
		public Country createFromParcel(Parcel parcel) {
			// TODO Auto-generated method stub
			Country country = new Country();
			country.countryId = parcel.readString();
			country.countries = parcel.readString();
			country.language = parcel.readString();
			country.capital = parcel.readString();
			country.currency = parcel.readString();
			country.flagName = parcel.readString();
			return country;
		}

		@Override
		public Country[] newArray(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
}
