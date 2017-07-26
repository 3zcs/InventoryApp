package me.a3zcs.habittracker.inventoryapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 25/07/17.
 */

public class Product implements Parcelable{
    private String id;
    private String name;
    private float price;
    private int quantity;
    private String image;
    private String supplier;


    public Product(String name, float price, int quantity, String image, String supplier) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.supplier = supplier;
    }

    public Product(String id, String name, float price, int quantity, String image, String supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.supplier = supplier;
    }

    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readFloat();
        quantity = in.readInt();
        image = in.readString();
        supplier = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeFloat(price);
        parcel.writeInt(quantity);
        parcel.writeString(image);
        parcel.writeString(supplier);
    }
}
