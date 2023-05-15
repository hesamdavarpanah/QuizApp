package repository;

import models.Category;
import models.Exam;
import models.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionRepo implements CrudRepository<Question, Integer> {
    @Override
    public List<Question> findAll() {
        List<Question> questionList = new ArrayList<>();
        String SQL = "SELECT q.id, question_text, point, exam_id, e.name FROM public.\"question\" AS q INNER JOIN public.\"exam\" AS e ON q.exam_id = e.id;";

        try (Connection conn = ConnectionManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Exam exam = new Exam();
                exam.setId(rs.getInt("exam_id"));
                exam.setName(rs.getString("name"));
                Question question = new Question(rs.getInt("id"), rs.getString("question_text"),
                        rs.getInt("point"), exam);
                questionList.add(question);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return questionList;
    }

    @Override
    public Optional<Question> findById(Integer id) {
        Question question = null;
        String SQL = "SELECT q.id, question_text, point, exam_id, e.name FROM public.\"question\" AS q INNER JOIN public.\"exam\" AS e ON q.exam_id = e.id WHERE q.id = ?";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Exam exam = new Exam();
                exam.setId(resultSet.getInt("exam_id"));
                exam.setName(resultSet.getString("name"));

                question = new Question(resultSet.getInt("id"),
                        resultSet.getString("question_text"),
                        resultSet.getInt("point"),
                        exam);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return Optional.ofNullable(question);
    }

    public List<Question> findByExamId(Integer examId) {
        List<Question> questionList = new ArrayList<>();
        String SQL = "SELECT q.id, question_text, point, exam_id, e.name FROM public.\"question\" AS q INNER JOIN public.\"exam\" AS e ON q.exam_id = e.id WHERE q.exam_id = ?";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, examId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Exam exam = new Exam();
                exam.setId(resultSet.getInt("exam_id"));
                exam.setName(resultSet.getString("name"));
                Question question = new Question(resultSet.getInt("id"), resultSet.getString("question_text"),
                        resultSet.getInt("point"), exam);
                questionList.add(question);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return questionList;
    }

    @Override
    public Integer create(Question obj) {
        String SQL = "INSERT INTO public.\"question\"(question_text, point, exam_id) VALUES (?, ?, ?)";
        int id = 0;

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getQuestionText());
            preparedStatement.setInt(2, obj.getPoint());
            preparedStatement.setInt(3, obj.getExam().getId());

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
    public void update(Integer id, Question obj) {
        String SQL = "UPDATE public.\"question\" SET question_text = ?, point = ?, exam_id = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, obj.getQuestionText());
            preparedStatement.setInt(2, obj.getPoint());
            preparedStatement.setInt(3, obj.getExam().getId());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM public.\"question\" WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

    }
}
