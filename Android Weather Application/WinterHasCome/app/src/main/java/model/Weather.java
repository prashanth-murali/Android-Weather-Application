package model;

/**
 * Copyright © 2016 by Prashanth Murali. Permission is granted to use, modify, and distribute this document.
 */
public class Weather {
    public Place place;
    public String iconData;
    public  Conditions currentCondition=new Conditions();
    public Temperature temperature=new Temperature();
    public Wind wind=new Wind();
    public Snow snow=new Snow();
    public Clouds clouds=new Clouds();

}
