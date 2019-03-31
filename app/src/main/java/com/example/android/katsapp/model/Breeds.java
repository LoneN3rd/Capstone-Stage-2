package com.example.android.katsapp.model;

public class Breeds {

    private String id;
    private String origin;
    private String country_code;
    private int hypoallergenic;
    private String name;
    private String description;
    private String temperament;
    private int affection_level;
    private int adaptability;
    private int child_friendly;
    private int dog_friendly;
    private int energy_level;
    private int grooming;
    private int health_issues;
    private int intelligence;
    private int shedding_level;
    private int social_needs;
    private int stranger_friendly;
    private String wikipedia_url;

    public Breeds() {}

    public String getId(){ return id;}

    public void setId(String breed_id){ this.id = breed_id;}

    public String getOrigin(){ return origin;}

    public void setOrigin(String origin){ this.origin = origin;}

    public String getCountryCode(){
        return country_code;
    }

    public void setCountryCode(String country_code){
        this.country_code = country_code;
    }

    public int getHypoallergenic(){ return hypoallergenic;}

    public void setHypoallergenic(int hypoallergenic){ this.hypoallergenic = hypoallergenic;}

    public String getName(){ return name;}

    public void setName(String name){ this.name = name;}

    public String getDescription(){ return description;}

    public void setDescription(String description){ this.description = description;}

    public String getTemperament(){ return temperament;}

    public void setTemperament(String temperament){ this.temperament = temperament;}

    public int getAffection_level(){ return affection_level;}

    public void setAffection_level(int affection_level){ this.affection_level = affection_level;}

    public int getAdaptability(){ return adaptability;}

    public void setAdaptability(int adaptability){ this.adaptability = adaptability;}

    public int getChild_friendly(){ return child_friendly;}

    public void setChild_friendly(int child_friendly){ this.child_friendly = child_friendly;}

    public int getDog_friendly(){ return dog_friendly;}

    public void setDog_friendly(int dog_friendly){ this.dog_friendly = dog_friendly;}

    public int getGrooming(){ return grooming;}

    public void setGrooming(int grooming){ this.grooming = grooming;}

    public int getEnergy_level(){ return energy_level;}

    public void setEnergy_level(int energy_level){ this.energy_level = energy_level;}

    public int getHealth_issues(){ return health_issues;}

    public void setHealth_issues(int health_issues){ this.health_issues = health_issues;}

    public int getIntelligence(){ return intelligence;}

    public void setIntelligence(int intelligence){ this.intelligence = intelligence;}

    public int getShedding_level(){ return shedding_level;}

    public void setShedding_level(int shedding_level){ this.shedding_level = shedding_level;}

    public int getSocial_needs(){ return social_needs;}

    public void setSocial_needs(int social_needs){ this.social_needs = social_needs;}

    public int getStranger_friendly(){ return stranger_friendly;}

    public void setStranger_friendly(int stranger_friendly){ this.stranger_friendly = stranger_friendly;}

    public String getWikipedia_url(){ return wikipedia_url;}

    public void setWikipedia_url(String wikipedia_url){ this.wikipedia_url = wikipedia_url;}
}
