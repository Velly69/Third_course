package second.module.first.models;

import java.util.HashMap;
import java.util.Map;

public class VideoShop {
    private Map<String, Genre> genres = new HashMap<>();
    private Map<String, String> genreNames = new HashMap<>();
    private Map<String, Movie> movies = new HashMap<>();
    private Map<String, String> movieNames = new HashMap<>();

    public VideoShop() { }

    public void createId(Genre genre) {
        int id = genres.size();
        String idToString = "genre" + id;
        while(genres.get(idToString) != null) {
            id++;
            idToString = "genre" + id;
        }
        genre.setGenreId(idToString);
    }

    public void createId(Movie movie) {
        int id = movies.size();
        String idToString = "id" + id;
        while(genres.get(idToString) != null) {
            id++;
            idToString = "id" + id;
        }
        movie.setMovieId(idToString);
    }

    public void addGenre(Genre genre) {
        boolean isChanged = false;
        if(genres.get(genre.getGenreId()) != null) {
            isChanged = true;
            createId(genre);
        }
        if(isChanged) {
            for(Movie m: genre.getMovies()) {
                m.setGenreId(genre.getGenreId());
            }
        }
        genres.put(genre.getGenreId(), genre);
        genreNames.put(genre.getName(), genre.getGenreId());
    }

    public void removeGenre(Genre genre) {
        genres.remove(genre.getGenreId());
        genreNames.remove(genre.getName());
        for(Movie m: genre.getMovies()) {
            movies.remove(m.getMovieId());
        }
    }

    public void addMovie(Movie movie) {
        if(movies.get(movie.getMovieId()) != null) {
            createId(movie);
        }
        movies.put(movie.getMovieId(), movie);
        movieNames.put(movie.getName(), movie.getMovieId());
        genres.get(movie.getGenreId()).getMovies().add(movie);
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie.getMovieId());
        movieNames.remove(movie.getName());
        genres.get(movie.getGenreId()).getMovies().remove(movie);
    }

    public void changeMoviePlace(Movie movie, Genre genre) {
        Genre old = genres.get(movie.getGenreId());
        if(old != null) {
            old.getMovies().remove(movie);
        }
        movie.setGenreId(genre.getGenreId());
        genre.getMovies().add(movie);
    }

    public void renameMovie(Movie movie, String newName) {
        movieNames.remove(movie.getName());
        movie.setName(newName);
        movieNames.put(movie.getName(), movie.getMovieId());
    }

    public void renameGenre(Genre genre, String newName) {
        genreNames.remove(genre.getName());
        genre.setName(newName);
        genreNames.put(genre.getName(), genre.getGenreId());
    }

    public Movie getMovie(String name) {
        String id = movieNames.get(name);
        if(id != null) {
            return movies.get(id);
        }
        return null;
    }

    public Genre getGenre(String name) {
        String id = genreNames.get(name);
        if(id != null) {
            return genres.get(id);
        }
        return null;
    }

    public Map<String, Genre> getGenres() {
        return genres;
    }

    public Map<String, String> getGenreNames() {
        return genreNames;
    }

    public Map<String, Movie> getMovies() {
        return movies;
    }

    public Map<String, String> getMovieNames() {
        return movieNames;
    }

    public void setGenres(Map<String, Genre> genres) {
        this.genres = genres;
    }

    public void setGenreNames(Map<String, String> genreNames) {
        this.genreNames = genreNames;
    }

    public void setMovies(Map<String, Movie> movies) {
        this.movies = movies;
    }

    public void setMovieNames(Map<String, String> movieNames) {
        this.movieNames = movieNames;
    }
}
