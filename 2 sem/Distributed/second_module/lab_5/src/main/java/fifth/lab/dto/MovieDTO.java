package fifth.lab.dto;

import java.io.Serializable;

public class MovieDTO implements Serializable {
    private Long movieId;
    private Long genreId;
    private String name;
    private Integer releaseYear;

    public MovieDTO(Long movieId, String name, Integer releaseYear, Long genreId) {
        this.genreId = genreId;
        this.movieId = movieId;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public MovieDTO() { }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
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
}

