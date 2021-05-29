package second.module.test.dao;

import second.module.test.connection.DBConnection;
import second.module.test.models.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    public static Movie findById(Long id) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM movie WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            Movie movie = null;
            if (rs.next()) {
                movie = new Movie();
                movie.setId(rs.getLong(1));
                movie.setName(rs.getString(2));
                movie.setCountry(rs.getString(3));
                movie.setReleaseYear(rs.getInt(4));
            }
            st.close();
            return movie;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Movie findByName(String name) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM movie WHERE name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            Movie movie = null;
            if (rs.next()) {
                movie = new Movie();
                movie.setId(rs.getLong(1));
                movie.setName(rs.getString(2));
                movie.setCountry(rs.getString(3));
                movie.setReleaseYear(rs.getInt(4));
            }
            st.close();
            return movie;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean updateMovieByName(Movie movie) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE movie SET name = ? WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, movie.getName());
            st.setLong(2, movie.getId());
            int result = st.executeUpdate();
            st.close();
            if(result > 0) {
                return true;
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean insert(Movie movie) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO movie (name, country_name, release_year) "
                            + "VALUES (?,?,?) "
                            + "RETURNING id";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, movie.getName());
            st.setString(2, movie.getCountry());
            st.setLong(3, movie.getReleaseYear());
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                movie.setId(rs.getLong(1));
            } else
                return false;
            st.close();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Movie movie) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM movie WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, movie.getId());
            int result = st.executeUpdate();
            st.close();
            if(result>0)
                return true;
            else
                return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static List<Movie> findAll(){
        try(Connection connection = DBConnection.getConnection();) {
            String sql = "SELECT * FROM movie";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Movie> list = new ArrayList<>();
            while(rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong(1));
                movie.setName(rs.getString(2));
                movie.setCountry(rs.getString(3));
                movie.setReleaseYear(rs.getInt(4));
                list.add(movie);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static List<Movie> findAllByCurrentAndLastYear(){
        try(Connection connection = DBConnection.getConnection();) {
            String sql = "SELECT * FROM movie WHERE release_year=2021 OR release_year=2020";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Movie> list = new ArrayList<>();
            while(rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getLong(1));
                movie.setName(rs.getString(2));
                movie.setCountry(rs.getString(3));
                movie.setReleaseYear(rs.getInt(4));
                list.add(movie);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static boolean deleteByReleaseYearLessThan(Long number) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM movie WHERE release_year < 2021 - ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, number);
            int result = st.executeUpdate();
            st.close();
            if(result>0)
                return true;
            else
                return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}
