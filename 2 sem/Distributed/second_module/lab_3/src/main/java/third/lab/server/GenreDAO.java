package third.lab.server;

import third.lab.connection.CustomConnection;
import third.lab.dto.GenreDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    public static GenreDTO findById(long id) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql =
                    "SELECT * FROM genre WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            GenreDTO genre = null;
            if (resultSet.next()) {
                genre = new GenreDTO();
                genre.setId(resultSet.getLong(1));
                genre.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return genre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GenreDTO findByName(String name) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM genre WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            GenreDTO genre = null;
            if (resultSet.next()) {
                genre = new GenreDTO();
                genre.setId(resultSet.getLong(1));
                genre.setName(resultSet.getString(2));
            }
            preparedStatement.close();
            return genre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean update(GenreDTO genre) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "UPDATE genre SET name = ? WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setLong(2, genre.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean insert(GenreDTO genre) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "INSERT INTO genre (name) VALUES (?) RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, genre.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                genre.setId(resultSet.getLong(1));
            } else
                return false;
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean delete(GenreDTO genre) {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "DELETE FROM genre WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, genre.getId());
            int result = preparedStatement.executeUpdate();
            preparedStatement.close();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<GenreDTO> findAll() {
        try (Connection connection = CustomConnection.getConnection()) {
            String sql = "SELECT * FROM genre";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<GenreDTO> list = new ArrayList<>();
            while (resultSet.next()) {
                GenreDTO genre = new GenreDTO();
                genre.setId(resultSet.getLong(1));
                genre.setName(resultSet.getString(2));
                list.add(genre);
            }
            preparedStatement.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

