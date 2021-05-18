package fifth.lab.dao;

import fifth.lab.connection.CustomConnection;
import fifth.lab.dto.MovieDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    public static MovieDTO findById(long id) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "SELECT * FROM movie WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            MovieDTO movie = null;
            if (resultSet.next()) {
                movie = new MovieDTO();
                movie.setMovieId(resultSet.getLong(1));
                movie.setName(resultSet.getString(2));
                movie.setReleaseYear(resultSet.getInt(3));
                movie.setGenreId(resultSet.getLong(4));
            }
            preparedStatement.close();
            return movie;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MovieDTO findByName(String name) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "SELECT * FROM movie WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            MovieDTO movie = null;
            if (resultSet.next()) {
                movie = new MovieDTO();
                movie.setMovieId(resultSet.getLong(1));
                movie.setName(resultSet.getString(2));
                movie.setReleaseYear(resultSet.getInt(3));
                movie.setGenreId(resultSet.getLong(4));
            }
            preparedStatement.close();
            return movie;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(MovieDTO movie) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "UPDATE movie SET name = ?, release_year = ?, genre_id = ? WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setInt(2, movie.getReleaseYear());
            preparedStatement.setLong(3, movie.getGenreId());
            preparedStatement.setLong(4, movie.getMovieId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(MovieDTO movie) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "INSERT INTO movie (name, release_year, genre_id) VALUES (?,?,?) RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setInt(2, movie.getReleaseYear());
            preparedStatement.setLong(3, movie.getGenreId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                movie.setMovieId(resultSet.getLong(1));
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(MovieDTO movie) {
        try (Connection connection = CustomConnection.getConnection();) {
            String sql = "DELETE FROM movie WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, movie.getMovieId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<MovieDTO> findAll() {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM movie";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MovieDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                MovieDTO movie = new MovieDTO();
                movie.setMovieId(resultSet.getLong(1));
                movie.setName(resultSet.getString(2));
                movie.setReleaseYear(resultSet.getInt(3));
                movie.setGenreId(resultSet.getLong(4));
                list.add(movie);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<MovieDTO> findByGenreId(Long id) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM movie WHERE genre_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MovieDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                MovieDTO movie = new MovieDTO();
                movie.setMovieId(resultSet.getLong(1));
                movie.setName(resultSet.getString(2));
                movie.setReleaseYear(resultSet.getInt(3));
                movie.setGenreId(resultSet.getLong(4));
                list.add(movie);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
