package second.module.test.dao;

import second.module.test.connection.DBConnection;
import second.module.test.models.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorsDAO {
    public static Actor findById(Long id) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM actor WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            Actor actor = null;
            if (rs.next()) {
                actor = new Actor();
                actor.setId(rs.getLong(1));
                actor.setName(rs.getString(2));
                actor.setSurname(rs.getString(3));
                actor.setBirthDate(rs.getDate(4));
                actor.setMovieId(rs.getLong(5));
            }
            st.close();
            return actor;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Actor findByName(String name) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM actor WHERE name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            Actor actor = null;
            if (rs.next()) {
                actor = new Actor();
                actor.setId(rs.getLong(1));
                actor.setName(rs.getString(2));
                actor.setSurname(rs.getString(3));
                actor.setBirthDate(rs.getDate(4));
                actor.setMovieId(rs.getLong(5));
            }
            st.close();
            return actor;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean updateActorByName(Actor actor) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "UPDATE actor SET name = ? WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, actor.getName());
            st.setLong(2, actor.getId());
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

    public static boolean insert(Actor actor) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO actor (name, surname, birth_date) "
                            + "VALUES (?,?,?) "
                            + "RETURNING id";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, actor.getName());
            st.setString(2, actor.getSurname());
            st.setDate(3, (Date) actor.getBirthDate());
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                actor.setId(rs.getLong(1));
            } else
                return false;
            st.close();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Actor actor) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM actor WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, actor.getId());
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

    public static List<Actor> findAll(){
        try(Connection connection = DBConnection.getConnection();) {
            String sql = "SELECT * FROM actor";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Actor> list = new ArrayList<>();
            while(rs.next()) {
                Actor actor = new Actor();
                actor.setId(rs.getLong(1));
                actor.setName(rs.getString(2));
                actor.setSurname(rs.getString(3));
                actor.setBirthDate(rs.getDate(4));
                actor.setMovieId(rs.getLong(5));
                list.add(actor);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static List<Actor> finaAllByMovieId(Long id) {
        try(Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM actor WHERE movie_id = ?";
            PreparedStatement pr = connection.prepareStatement(sql);
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            List<Actor> actors = new ArrayList<>();
            while(rs.next()) {
                Actor actor = new Actor();
                actor.setId(rs.getLong(1));
                actor.setName(rs.getString(2));
                actor.setSurname(rs.getString(3));
                actor.setBirthDate(rs.getDate(4));
                actor.setMovieId(rs.getLong(5));
                actors.add(actor);
            }
            pr.close();
            return actors;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


}
