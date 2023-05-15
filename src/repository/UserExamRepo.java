package repository;

import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserExamRepo implements CrudRepository<UserExam, Integer> {
    @Override
    public List<UserExam> findAll() {
        List<UserExam> userExams = new ArrayList<>();
        String SQL = "SELECT ue.id, user_id, first_name, last_name, email, exam_id, name, exam_date, result, description FROM public.\"user\" AS u INNER JOIN public.\"user_exam\" AS ue ON u.id = ue.user_id INNER JOIN public.\"exam\" as e ON ue.exam_id = e.id;";

        try (Connection conn = ConnectionManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                UserExam userExam = new UserExam();
                userExam.setId(rs.getInt("id"));
                userExam.setResult(rs.getInt("result"));
                userExam.setDescription(rs.getString("description"));
                userExam.setExamDate(rs.getDate("exam_date"));

                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));

                Exam exam = new Exam();
                exam.setId(rs.getInt("exam_id"));
                exam.setName(rs.getString("name"));

                userExam.setExam(exam);
                userExam.setUser(user);
                userExams.add(userExam);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userExams;
    }

    @Override
    public Optional<UserExam> findById(Integer id) {
        UserExam userExam = null;
        String SQL = "SELECT ue.id, user_id, first_name, last_name, email, exam_id, name, exam_date, result, description FROM public.\"user\" AS u INNER JOIN public.\"user_exam\" AS ue ON u.id = ue.user_id INNER JOIN public.\"exam\" as e ON ue.exam_id = e.id WHERE ue.id = ?;";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));

                Exam exam = new Exam();
                exam.setId(resultSet.getInt("exam_id"));
                exam.setName(resultSet.getString("name"));

                userExam = new UserExam();
                userExam.setId(resultSet.getInt("id"));
                userExam.setDescription(resultSet.getString("description"));
                userExam.setExamDate(resultSet.getDate("exam_date"));
                userExam.setResult(resultSet.getInt("result"));
                userExam.setUser(user);
                userExam.setExam(exam);


            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return Optional.ofNullable(userExam);
    }

    public List<UserExam> findByUserId(Integer userId) {
        List<UserExam> userExamList = new ArrayList<>();
        String SQL = "SELECT ue.id, ue.exam_date, ue.description, ue.result, ue.user_id, u.first_name, u.last_name, u.email, ue.exam_id, e.name FROM public.user_exam AS ue INNER JOIN public.user AS u ON ue.user_id = u.id INNER JOIN public.exam AS e ON ue.exam_id = e.id WHERE ue.user_id = ?;";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));

                Exam exam = new Exam();
                exam.setId(resultSet.getInt("exam_id"));
                exam.setName(resultSet.getString("name"));

                UserExam userExam = new UserExam();
                userExam.setId(resultSet.getInt("id"));
                userExam.setDescription(resultSet.getString("description"));
                userExam.setExamDate(resultSet.getDate("exam_date"));
                userExam.setResult(resultSet.getInt("result"));
                userExam.setUser(user);
                userExam.setExam(exam);

                userExamList.add(userExam);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return userExamList;
    }

    @Override
    public Integer create(UserExam obj) {
        String SQL = "INSERT INTO public.\"user_exam\"(description, exam_date, result, exam_id, user_id) VALUES (?, ?, ?, ?, ?)";
        int id = 0;

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getDescription());
            preparedStatement.setDate(2, new Date(obj.getExamDate().getTime()));
            preparedStatement.setInt(3, obj.getResult());
            preparedStatement.setInt(4, obj.getExam().getId());
            preparedStatement.setInt(5, obj.getUser().getId());

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
    public void update(Integer id, UserExam obj) {
        String SQL = "UPDATE public.\"user_exam\" SET description = ?, exam_date = ?, result = ?, exam_id = ?, user_id = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, obj.getDescription());
            preparedStatement.setDate(2, new Date(obj.getExamDate().getTime()));
            preparedStatement.setInt(3, obj.getResult());
            preparedStatement.setInt(4, obj.getExam().getId());
            preparedStatement.setInt(5, obj.getUser().getId());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM public.\"user_exam\" WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }
}
