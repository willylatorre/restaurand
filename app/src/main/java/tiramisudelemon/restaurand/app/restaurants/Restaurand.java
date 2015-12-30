package tiramisudelemon.restaurand.app.restaurants;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Restaurand implements Parcelable{

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

    @DatabaseField
    private String type;




    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Restaurand setName(String name) {
        this.name = name;
        return this;
    }

    public String getPic() {
        return pic;
    }

    public Restaurand setPic(String pic) {
        this.pic = pic;
        return this;

    }

    public String getAddress() {
        return address;
    }

    public Restaurand setAddress(String address) {
        this.address = address;
        return this;

    }

    public String getWebsite() {
        return website;
    }

    public Restaurand setWebsite(String website) {
        this.website = website;
        return this;

    }

    public String getGeoposition() {
        return geoposition;
    }

    public Restaurand setGeoposition(String geoposition) {
        this.geoposition = geoposition;
        return this;

    }

    public double getRandom_factor() {
        return random_factor;
    }

    public Restaurand setRandom_factor(double random_factor) {
        this.random_factor = random_factor;
        return this;

    }

    public Date getLastSelected() {
        return lastSelected;
    }

    public Restaurand setLastSelected(Date lastSelected) {
        this.lastSelected = lastSelected;
        return this;

    }

    public int getViewed() {
        return viewed;
    }

    public Restaurand setViewed(int viewed) {
        this.viewed = viewed;
        return this;

    }

    public String getPhone() {
        return phone;
    }

    public Restaurand setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public Restaurand setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public RRType getType() {
        return RRType.forValue(type);
    }

    public Restaurand setType(RRType type) {
        this.type = type.getValue();
        return this;
    }

    public Restaurand(){
        this.type = RRType.DEFAULT.getValue();
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
        dest.writeDouble(this.rating);
        dest.writeString(this.type);
    }

    protected Restaurand(Parcel in) {
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
        this.rating = in.readDouble();
        this.type = in.readString();
    }

    public static final Creator<Restaurand> CREATOR = new Creator<Restaurand>() {
        public Restaurand createFromParcel(Parcel source) {
            return new Restaurand(source);
        }

        public Restaurand[] newArray(int size) {
            return new Restaurand[size];
        }
    };
}
