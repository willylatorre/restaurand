package tiramisudelemon.restaurand.app.restaurants;

/**
 * Created by Past on 15/07/2014.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Restaurant implements Parcelable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String pic;
    @DatabaseField
    private String address;
    @DatabaseField
    private String website;
    @DatabaseField
    private String geoposition;
    @DatabaseField(columnName = "RND_FACTOR")
    private double random_factor;
    @DatabaseField(columnName = "LAST_SELECTED")
    private Date lastSelected;
    @DatabaseField(columnName = "VIEWED")
    private int viewed;
    @DatabaseField
    private String phone;
    @DatabaseField
    private double rating;




    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Restaurant setPic(String pic) {
        this.pic = pic;
        return this;

    }

    public String getAddress() {
        return address;
    }

    public Restaurant setAddress(String address) {
        this.address = address;
        return this;

    }

    public String getWebsite() {
        return website;
    }

    public Restaurant setWebsite(String website) {
        this.website = website;
        return this;

    }

    public String getGeoposition() {
        return geoposition;
    }

    public Restaurant setGeoposition(String geoposition) {
        this.geoposition = geoposition;
        return this;

    }

    public double getRandom_factor() {
        return random_factor;
    }

    public Restaurant setRandom_factor(double random_factor) {
        this.random_factor = random_factor;
        return this;

    }

    public Date getLastSelected() {
        return lastSelected;
    }

    public Restaurant setLastSelected(Date lastSelected) {
        this.lastSelected = lastSelected;
        return this;

    }

    public int getViewed() {
        return viewed;
    }

    public Restaurant setViewed(int viewed) {
        this.viewed = viewed;
        return this;

    }

    public String getPhone() {
        return phone;
    }

    public Restaurant setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public Restaurant setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public Restaurant(){

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.pic);
        dest.writeString(this.address);
        dest.writeString(this.website);
        dest.writeString(this.geoposition);
        dest.writeDouble(this.random_factor);
        dest.writeLong(lastSelected != null ? lastSelected.getTime() : -1);
        dest.writeInt(this.viewed);
        dest.writeString(this.phone);
    }

    protected Restaurant(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.pic = in.readString();
        this.address = in.readString();
        this.website = in.readString();
        this.geoposition = in.readString();
        this.random_factor = in.readDouble();
        long tmpLastSelected = in.readLong();
        this.lastSelected = tmpLastSelected == -1 ? null : new Date(tmpLastSelected);
        this.viewed = in.readInt();
        this.phone = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}
