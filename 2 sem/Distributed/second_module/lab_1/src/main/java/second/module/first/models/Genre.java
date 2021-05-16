package second.module.first.models;

import java.util.ArrayList;
import java.util.List;

public class Genre {
    private String genreId;
    private String name;
    private List<Movie> movies = new ArrayList<>();

    public Genre() { }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
