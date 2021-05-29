package second.module.test.models;

import java.io.Serializable;
import java.util.Date;

/*
    Serializable for 3rd task ->
    for transferring classes to RMI server
*/
public class Director implements Serializable {
    private Long id;
    private Long movieId;
    private String name;
    private String surname;
    private Date birthDate;

    public Director() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
