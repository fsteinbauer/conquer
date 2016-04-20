package at.acid.conquer.model;

/**
 * Created by florian on 09.03.2016.
 */
public class Area extends Shape{

    public Area(Route route){
        this.points = route.points;
    }
}
