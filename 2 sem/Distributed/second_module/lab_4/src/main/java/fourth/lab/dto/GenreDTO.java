package fourth.lab.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenreDTO implements Serializable {
    private Long genreId;
    private String name;
    private List<MovieDTO> movies = new ArrayList<>();

    public GenreDTO() { }

    public GenreDTO(Long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public Long getId() {
        return genreId;
    }

    public void setId(Long genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}

