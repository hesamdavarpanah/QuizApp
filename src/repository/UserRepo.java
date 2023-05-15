package repository;

import models.Category;
import models.User;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepo implements UserCrudRepository<User, Integer> {

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String SQL = "SELECT id, first_name, last_name, email FROM public.\"user\"";

        try (Connection conn = ConnectionManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("email"),
                        null, null, null);
                userList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userList;
    }

    @Override
    public Optional<User> findById(Integer id) {
        User user = null;
        String SQL = "SELECT id, first_name, last_name, email FROM public.\"user\" WHERE id = ?";

        try (Connection conn = ConnectionManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("email"),
                        null, null, null);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> login(String username, String password) {
        User user = null;
        String SQL = "SELECT id, first_name, last_name, email FROM public.\"user\" WHERE email = ? AND password = ?";

        try (Connection conn = ConnectionManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("email"),
                        null, null, null);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Integer create(User obj) {
        String SQL = "INSERT INTO public.\"user\"(first_name, last_name, email, password, created_date, modified_date) VALUES(?, ?, ?, ?, ?, ?)";

        int id = 0;

        try (Connection conn = ConnectionManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            LocalDate localDate = LocalDate.now();

            pstmt.setString(1, obj.getFirstName());
            pstmt.setString(2, obj.getLastName());
            pstmt.setString(3, obj.getEmail());
            pstmt.setString(4, obj.getPassword());
            pstmt.setObject(5, localDate);
            pstmt.setObject(6, localDate);
//            pstmt.setString(4, "2022-08-12");
//            pstmt.setString(5, "2022-08-12");

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(6);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    @Override
    public void update(Integer id, User obj) {
        String SQL = "UPDATE public.\"user\" SET first_name = ?, last_name = ?, email = ?, modified_date = ? WHERE id = ?";

        try (Connection conn = ConnectionManager.connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            LocalDate localDate = LocalDate.now();

            pstmt.setString(1, obj.getFirstName());
            pstmt.setString(2, obj.getLastName());
            pstmt.setString(3, obj.getEmail());
            pstmt.setObject(4, localDate);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM public.\"user\" WHERE id = ?";

        try (Connection conn = ConnectionManager.connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
