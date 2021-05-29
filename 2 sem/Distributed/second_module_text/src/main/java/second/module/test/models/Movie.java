package second.module.test.models;

import java.io.Serializable;

/*
    Serializable for 3rd task ->
    for transferring classes to RMI server
*/
public class Movie implements Serializable {
    private Long id;
    private String name;
    private Integer releaseYear;
    private String country;

    public Movie() { }

    public Movie(Long id, String name, Integer releaseYear, String country) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
