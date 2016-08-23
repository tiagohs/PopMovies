package br.com.tiagohs.popmovies.model;

public class Cast {
    private int cast_id;
    private String character;
    private int id;
    private String name;
    private int order;
    private String profile_path;

    public Cast(int cast_id, String character, int id, String name, int order, String profile_path) {
        this.cast_id = cast_id;
        this.character = character;
        this.id = id;
        this.name = name;
        this.order = order;
        this.profile_path = profile_path;
    }

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
