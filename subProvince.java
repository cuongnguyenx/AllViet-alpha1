package com.example.ckmj.interactivemap;

/**
 * Created by CUONG on 7/13/2017.
 */

public class subProvince {
    private String name;
    private double area;
    private int population;
    private String description;
    public subProvince(String name, double area, int population, String description)
    {
        this.name=name;
        this.area= area;
        this.population= population;
        this.description= description;
    }

    public String getName()
    {

        return name;
    }

    public double getArea()
    {

        return area;
    }

    public int getPopulation()
    {

        return population;
    }

    public String getDescription()
    {

        return description;
    }
}
