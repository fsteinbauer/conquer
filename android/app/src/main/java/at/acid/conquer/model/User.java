package at.acid.conquer.model;

/**
 * Created by florian on 10.05.2016.
 */
public class User {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void persist(){
        // TODO: Create Network persist function
    }
}