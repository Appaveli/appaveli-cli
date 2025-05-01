package com.example;

import java.sql.*;
import java.util.*;
import tech.appavelitech.domain.User;
import tech.appavelitech.db.DatabaseConnection;

public class UserDaoImpl implements UserDao {

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO {{TABLE_NAME}} ({{FIELDS}}) VALUES ({{PLACEHOLDERS}})";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            {{SET_PREPARED_STATEMENT_VALUES}}

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM {{TABLE_NAME}} WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User entity = new User();
                {{SET_ENTITY_FROM_RESULTSET}}
                return entity;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM {{TABLE_NAME}}";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User entity = new User();
                {{SET_ENTITY_FROM_RESULTSET}}
                list.add(entity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE {{TABLE_NAME}} SET {{UPDATE_FIELDS}} WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            {{SET_PREPARED_STATEMENT_VALUES_FOR_UPDATE}}

            stmt.setInt({{UPDATE_PARAM_INDEX}}, entity.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM {{TABLE_NAME}} WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}