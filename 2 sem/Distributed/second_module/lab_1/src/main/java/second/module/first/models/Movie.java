package second.module.first.models;

public class Movie {
    private String movieId;
    private String genreId;
    private String name;
    private Integer releaseYear;

    public Movie(String genreId, String movieId, String name, Integer releaseYear) {
        this.genreId = genreId;
        this.movieId = movieId;
        this.name = name;
        this.releaseYear = releaseYear;
    }

    public Movie() { }

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
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
