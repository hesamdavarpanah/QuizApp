package repository;

import models.Answer;
import models.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnswerRepo implements CrudRepository<Answer, Integer> {
    @Override
    public List<Answer> findAll() {
        List<Answer> answerList = new ArrayList<>();
        String SQL = "SELECT a.id, answer_text, is_true, question_id, question_text, point FROM public.\"question\" AS q INNER JOIN public.\"answer\" AS a ON q.id = a.question_id;";

        try (Connection conn = ConnectionManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setAnswerText(rs.getString("answer_text"));
                answer.setTrue(rs.getBoolean("is_true"));
                Question question = new Question(rs.getInt("question_id"), rs.getString("question_text"),
                        rs.getInt("point"), null);
                answer.setQuestion(question);
                answerList.add(answer);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return answerList;
    }

    @Override
    public Optional<Answer> findById(Integer id) {
        Answer answer = null;
        String SQL = "SELECT a.id, answer_text, is_true, question_id, question_text, point FROM public.\"question\" AS q INNER JOIN public.\"answer\" AS a ON q.id = a.question_id WHERE a.id = ?;";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Question question = new Question();
                question.setId(resultSet.getInt("question_id"));
                question.setQuestionText(resultSet.getString("question_text"));
                question.setPoint(resultSet.getInt("point"));

                answer = new Answer();
                answer.setId(resultSet.getInt("id"));
                answer.setAnswerText(resultSet.getString("answer_text"));
                answer.setTrue(resultSet.getBoolean("is_true"));
                answer.setQuestion(question);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return Optional.ofNullable(answer);
    }

    public List<Answer> findByQuestionId(Integer questionId) {
        List<Answer> answerList = new ArrayList<>();
        String SQL = "SELECT a.id, answer_text, is_true, question_id, question_text, point FROM public.\"question\" AS q INNER JOIN public.\"answer\" AS a ON q.id = a.question_id WHERE a.question_id = ?;";

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, questionId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setAnswerText(rs.getString("answer_text"));
                answer.setTrue(rs.getBoolean("is_true"));
                Question question = new Question(rs.getInt("question_id"), rs.getString("question_text"),
                        rs.getInt("point"), null);
                answer.setQuestion(question);
                answerList.add(answer);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return answerList;
    }

    @Override
    public Integer create(Answer obj) {
        String SQL = "INSERT INTO public.\"answer\"(answer_text, is_true, question_id) VALUES (?, ?, ?)";
        int id = 0;

        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getAnswerText());
            preparedStatement.setBoolean(2, obj.isTrue());
            preparedStatement.setInt(3, obj.getQuestion().getId());

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
    public void update(Integer id, Answer obj) {
        String SQL = "UPDATE public.\"answer\" SET answer_text = ?, is_true = ?, question_id = ? WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, obj.getAnswerText());
            preparedStatement.setBoolean(2, obj.isTrue());
            preparedStatement.setInt(3, obj.getQuestion().getId());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM public.\"answer\" WHERE id = ?";
        try (Connection connection = ConnectionManager.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }
}
