package com.example.android.katsapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "breeds") //define the table name here
public class FavoriteBreeds {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "breed_id")
    private String breed_id;

    @ColumnInfo(name = "breed_name")
    private String breedName;

    @ColumnInfo(name = "origin")
    private String origin;

    @ColumnInfo(name = "country_code")
    private String countryCode;

    @ColumnInfo(name = "hypoallergenic")
    private int hypoallergenic;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "temperament")
    private String temperament;

    @ColumnInfo(name = "affection")
    private int affection;

    @ColumnInfo(name = "adaptability")
    private int adaptability;

    @ColumnInfo(name = "child_friendly")
    private int childFriendly;

    @ColumnInfo(name = "dog_friendly")
    private int dogFriendly;

    @ColumnInfo(name = "energy_level")
    private int energyLevel;

    @ColumnInfo(name = "grooming")
    private int grooming;

    @ColumnInfo(name = "health_issues")
    private int healthIssues;

    @ColumnInfo(name = "intelligence")
    private int intelligence;

    @ColumnInfo(name = "shedding_level")
    private int sheddingLevel;

    @ColumnInfo(name = "social_needs")
    private int socialNeeds;

    @ColumnInfo(name = "stranger_friendly")
    private int strangerFriendly;

    @ColumnInfo(name = "wikipedia_url")
    private String wikipediaUrl;

    @Ignore
    public FavoriteBreeds(@NonNull String breed_id){
        this.breed_id = breed_id;
    }

    public FavoriteBreeds(@NonNull String breed_id, String breedName, String origin, String countryCode,
                          int hypoallergenic, String description, String temperament,
                          int affection, int adaptability, int childFriendly,
                          int dogFriendly, int energyLevel, int grooming, int healthIssues,
                          int intelligence, int sheddingLevel, int socialNeeds,
                          int strangerFriendly, String wikipediaUrl){

        this.breed_id = breed_id;
        this.breedName = breedName;
        this.origin = origin;
        this.countryCode = countryCode;
        this.hypoallergenic = hypoallergenic;
        this.description = description;
        this.temperament = temperament;
        this.affection = affection;
        this.adaptability = adaptability;
        this.childFriendly = childFriendly;
        this.dogFriendly = dogFriendly;
        this.energyLevel = energyLevel;
        this.grooming = grooming;
        this.healthIssues = healthIssues;
        this.intelligence = intelligence;
        this.sheddingLevel = sheddingLevel;
        this.socialNeeds = socialNeeds;
        this.strangerFriendly = strangerFriendly;
        this.wikipediaUrl = wikipediaUrl;

    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(String breed_id) {
        this.breed_id = breed_id;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getHypoallergenic() {
        return hypoallergenic;
    }

    public void setHypoallergenic(int hypoallergenic) {
        this.hypoallergenic = hypoallergenic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public int getAffection() {
        return affection;
    }

    public void setAffection(int affection) {
        this.affection = affection;
    }

    public int getAdaptability() {
        return adaptability;
    }

    public void setAdaptability(int adaptability) {
        this.adaptability = adaptability;
    }

    public int getChildFriendly() {
        return childFriendly;
    }

    public void setChildFriendly(int childFriendly) {
        this.childFriendly = childFriendly;
    }

    public int getDogFriendly() {
        return dogFriendly;
    }

    public void setDogFriendly(int dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public int getGrooming() {
        return grooming;
    }

    public void setGrooming(int grooming) {
        this.grooming = grooming;
    }

    public int getHealthIssues() {
        return healthIssues;
    }

    public void setHealthIssues(int healthIssues) {
        this.healthIssues = healthIssues;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSheddingLevel() {
        return sheddingLevel;
    }

    public void setSheddingLevel(int sheddingLevel) {
        this.sheddingLevel = sheddingLevel;
    }

    public int getSocialNeeds() {
        return socialNeeds;
    }

    public void setSocialNeeds(int socialNeeds) {
        this.socialNeeds = socialNeeds;
    }

    public int getStrangerFriendly() {
        return strangerFriendly;
    }

    public void setStrangerFriendly(int strangerFriendly) {
        this.strangerFriendly = strangerFriendly;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }
}
