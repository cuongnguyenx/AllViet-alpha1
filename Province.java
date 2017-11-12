package com.example.ckmj.interactivemap;

/**
 * Created by CUONG on 4/28/2017.
 */
import java.util.*; //represents national-level
public class Province extends subProvince {
    private String name;
    private double area;
    private String ID;
    private String capital;
    private int population;
    private String description;
    private int popDensity;
    private int teleCode;
    private int[] vehicleCode;
    private double PCI;
    private boolean isCity;
    private String region;
    private ArrayList<subProvince> district;
    private ArrayList<subProvince> city;
    private ArrayList<subProvince> town;

    public Province(String ID,String name, double area, String capital, String region, int population, double PCI, String description, int teleCode, int[] vehicleCode,int popDensity, ArrayList<subProvince> district, ArrayList<subProvince> city, ArrayList<subProvince>town)
    {
        super(name,area,population,description);
        this.ID =ID;
        this.capital= capital;
        this.region= region;
        this.teleCode= teleCode;
        this.PCI=PCI;
        this.vehicleCode= vehicleCode;
        this.district= district;
        this.city= city;
        this.town= town;
        this.popDensity=popDensity;
        this.isCity=false;
    }

    public Province(String ID, String name, double area, String capital, String region, int population, double PCI, String description, int teleCode, int[] vehicleCode,int popDensity, boolean isCity, ArrayList<subProvince> district, ArrayList<subProvince> city, ArrayList<subProvince>town)
    {
        super(name,area,population,description);
        this.ID= ID;
        this.capital= capital;
        this.region= region;
        this.PCI= PCI;
        this.teleCode= teleCode;
        this.vehicleCode= vehicleCode;
        this.district= district;
        this.city= city;
        this.town= town;
        this.isCity=isCity;
        this.popDensity=popDensity;
    }




    public String getCapital()
    {

        return capital;
    }

    public String getID()
    {
        return ID;
    }

    public String getRegion()
    {
        return region;
    }

    public double getPCI()
    {
        return PCI;
    }

    public int getPopDensity()
    {
        return popDensity;
    }

    public boolean getIsCity()
    {
        return this.isCity;
    }

    public int getTeleCode()
    {
        return teleCode;
    }

    public int[] getVehicleCode()
    {
        return vehicleCode;
    }

    public ArrayList<subProvince> getDistrict()
    {
        return district;
    }

    public ArrayList<subProvince> getCity()
    {
        return city;
    }

    public ArrayList<subProvince> getTown()
    {
        return town;
    }

    private void addDistrict(subProvince a)
    {
        district.add(a);
    }

    private void addCity(subProvince a)
    {
        city.add(a);
    }

    private void addTown(subProvince a)
    {
        town.add(a);
    }

    public String toString()
    {
       StringBuilder s= new StringBuilder();
        s.append(this.ID+"\n");
        s.append(this.capital+"\n");
        s.append(this.population+"\n");
        s.append(this.popDensity+"\n");
        s.append(this.area+"\n");
        s.append(this.teleCode+"\n");
        s.append(this.isCity+"\n");
        return s.toString();
    }

}
