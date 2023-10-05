package com.asrii.quiz;

public class Player {
    private Integer id;
    private String name;
    private Integer points;
    

    public Player(Integer id, String name, Integer score) {
        this.id = id;
        this.name = name;
        this.points = score;
    }

    public void resetScore(){
        this.points=0;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}