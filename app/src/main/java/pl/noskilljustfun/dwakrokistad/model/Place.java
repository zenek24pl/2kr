package pl.noskilljustfun.dwakrokistad.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bartosz on 29.04.2016.
 */
public class Place {

    @SerializedName("id")
    protected int id;
    @SerializedName("name")
    protected String name;
    @SerializedName("description")
    protected String description;
    @SerializedName("lat")
    protected  float positionX;
    @SerializedName("lng")
    protected  float positionY;


    public Place() {

    }

    public Place(int id, String name, String description, float positionX, float positionY) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}
