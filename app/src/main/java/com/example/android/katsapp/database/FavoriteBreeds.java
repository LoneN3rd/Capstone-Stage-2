package com.example.android.katsapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "breeds") //define the table name here
public class FavoriteBreeds {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    private String breed_id;

    @ColumnInfo(name = "breed_name")
    private String breedName;

    @ColumnInfo(name = "origin")
    private String origin;

    @ColumnInfo(name = "country_code")
    private String countryCode;

    @ColumnInfo(name = "hypoallergenic")
    private String hypoallergenic;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "temperament")
    private String temperament;

    @ColumnInfo(name = "affection")
    private String affection;

    @ColumnInfo(name = "adaptability")
    private String adaptability;

    @ColumnInfo(name = "child_friendly")
    private String childFriendly;

    @ColumnInfo(name = "dog_friendly")
    private String dogFriendly;

    @ColumnInfo(name = "energy_level")
    private String energyLevel;

    @ColumnInfo(name = "grooming")
    private String grooming;

    @ColumnInfo(name = "health_issues")
    private String healthIssues;

    @ColumnInfo(name = "intelligence")
    private String intelligence;

    @ColumnInfo(name = "shedding_level")
    private String sheddingLevel;

    @ColumnInfo(name = "social_needs")
    private String socialNeeds;

    @ColumnInfo(name = "stranger_friendly")
    private String strangerFriendly;

    @ColumnInfo(name = "wikipedia_url")
    private String wikipediaUrl;

    @Ignore
    public FavoriteBreeds(@NonNull String breed_id){
        this.breed_id = breed_id;
    }

    public FavoriteBreeds(@NonNull String breed_id, String breedName, String origin, String countryCode,
                          String hypoallergenic, String description, String temperament,
                          String affection, String adaptability, String childFriendly,
                          String dogFriendly, String energyLevel, String grooming, String healthIssues,
                          String intelligence, String sheddingLevel, String socialNeeds,
                          String strangerFriendly, String wikipediaUrl){

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

    @NonNull
    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(@NonNull String breed_id) {
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

    public String getHypoallergenic() {
        return hypoallergenic;
    }

    public void setHypoallergenic(String hypoallergenic) {
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

    public String getAffection() {
        return affection;
    }

    public void setAffection(String affection) {
        this.affection = affection;
    }

    public String getAdaptability() {
        return adaptability;
    }

    public void setAdaptability(String adaptability) {
        this.adaptability = adaptability;
    }

    public String getChildFriendly() {
        return childFriendly;
    }

    public void setChildFriendly(String childFriendly) {
        this.childFriendly = childFriendly;
    }

    public String getDogFriendly() {
        return dogFriendly;
    }

    public void setDogFriendly(String dogFriendly) {
        this.dogFriendly = dogFriendly;
    }

    public String getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(String energyLevel) {
        this.energyLevel = energyLevel;
    }

    public String getGrooming() {
        return grooming;
    }

    public void setGrooming(String grooming) {
        this.grooming = grooming;
    }

    public String getHealthIssues() {
        return healthIssues;
    }

    public void setHealthIssues(String healthIssues) {
        this.healthIssues = healthIssues;
    }

    public String getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(String intelligence) {
        this.intelligence = intelligence;
    }

    public String getSheddingLevel() {
        return sheddingLevel;
    }

    public void setSheddingLevel(String sheddingLevel) {
        this.sheddingLevel = sheddingLevel;
    }

    public String getSocialNeeds() {
        return socialNeeds;
    }

    public void setSocialNeeds(String socialNeeds) {
        this.socialNeeds = socialNeeds;
    }

    public String getStrangerFriendly() {
        return strangerFriendly;
    }

    public void setStrangerFriendly(String strangerFriendly) {
        this.strangerFriendly = strangerFriendly;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }
}
