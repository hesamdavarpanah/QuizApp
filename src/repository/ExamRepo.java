package repository;

import models.Category;
import models.Exam;
import models.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamRepo implements CrudRepository<Exam, Integer> {
    @Override
    public List<Exam> findAll() {
        List<Exam> examList = new ArrayList<>();
        String SQL = "SELECT e.id, e.name, category_id, c.name AS category_name, difficulty FROM public.\"exam\" AS e INNER JOIN public.\"category\" AS c ON e.category_id = c.id;";

        try (Connection conn = ConnectionManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(SQL)) {

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                Exam exam = new Exam(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("difficulty"), category);
                examList.add(exam);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return examList;
    }

    @Override
    public Optional<Exam> findById(Integer id) {
        Exam exam = null;
        String SQL = "SELECT e.id, e.name, category_id, c.name AS category_name, difficulty FROM public.\"exam\" AS e INNER JOIN public.\"category\" AS c ON e.category_id = c.id WHERE e.id = ?";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));

                exam = new Exam(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("difficulty"),
                        category);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return Optional.ofNullable(exam);
    }

    public List<Exam> findByCategoryId(Integer categoryId) {
        List<Exam> examList = new ArrayList<>();
        String SQL = "SELECT e.id, e.name, category_id, c.name AS category_name, difficulty FROM public.\"exam\" AS e INNER JOIN public.\"category\" AS c ON e.category_id = c.id WHERE e.category_id = ?";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                Exam exam = new Exam(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("difficulty"), category);
                examList.add(exam);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return examList;
    }

    @Override
    public Integer create(Exam obj) {
        String SQL = "INSERT INTO public.\"exam\"(name, category_id, difficulty) VALUES (?, ?, ?)";
        int id = 0;

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setInt(2, obj.getCategory().getId());
            preparedStatement.setInt(3, obj.getDifficulty());

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
    public void update(Integer id, Exam obj) {
        String SQL = "UPDATE public.\"exam\" SET name = ?, category_id = ?, difficulty = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, obj.getName());
            preparedStatement.setInt(2, obj.getCategory().getId());
            preparedStatement.setInt(3, obj.getDifficulty());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM public.\"exam\" WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }
}
