package sg.edu.rp.c346.id20002694.oursingapore;

import java.io.Serializable;

public class Song implements Serializable {

    private 	int id;
    private 	String title;
    private String singers;
    private int year;
    private int stars;

    public Song( String title, String singers, int year,  int stars) {
        this.singers = singers;
        this.title = title;
        this.year = year;
        this.id = stars;
    }
    public Song(  int id, String title, String singers, int year,  int stars) {
        this.singers = singers;
        this.title = title;
        this.year = year;
        this.id = id;
        this.stars=stars;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSingers() {
        return singers;
    }

    public int getYear() {
        return year;
    }

    public int getStars() {
        return stars;
    }

    public void setSongContent(String title, String singers, int year,  int stars) {
        this.singers = singers;
        this.title = title;
        this.year = year;
        this.id = id;
        this.stars = stars;
    }


    @Override
    public String toString() {
        String starsString="";
        if(stars==1){
            starsString="*";
        }
        else if(stars==2){
            starsString="* *";
        }
        else if(stars==3){
            starsString="* * *";
        }
        else if(stars==4){
            starsString="* * * *";
        }
        else if(stars==5){
            starsString="* * * * *";
        }
        return starsString;  }



}
