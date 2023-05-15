package repository;

import models.Category;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryRepo implements CrudRepository<Category, Integer> {

    @Override
    public List<Category> findAll() {
        List<Category> categoryList = new ArrayList<>();
        String SQL = "SELECT id, name, description FROM category";

        try (Connection conn = ConnectionManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Category category = new Category(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                categoryList.add(category);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return categoryList;
    }

    @Override
    public Optional<Category> findById(Integer id) {
        Category category = null;
        String SQL = "SELECT id, name, description FROM public.\"category\" WHERE id = ?";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                category = new Category(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return Optional.ofNullable(category);
    }

    @Override
    public Integer create(Category obj) {
        String SQL = "INSERT INTO public.\"category\"(name, description) VALUES (?, ?)";
        int id = 0;

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getDescription());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }
                } catch (SQLException sqlException) {
                    System.out.println(sqlException.getMessage());
                }
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return id;
    }

    @Override
    public void update(Integer id, Category obj) {
        String SQL = "UPDATE public.\"category\" SET name = ?, description = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getDescription());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }


    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM public.\"category\" WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

    }
}
