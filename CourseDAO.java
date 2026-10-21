import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void addCourse(Course c) throws SQLException {
        String sql = "INSERT INTO courses (course_id, course_name, credits) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCourseId());
            ps.setString(2, c.getCourseName());
            ps.setInt(3, c.getCredits());
            ps.executeUpdate();
        }
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT course_id, course_name, credits FROM courses";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // No type column in the agreed schema, so default to TheoryCourse
                list.add(new TheoryCourse(rs.getString("course_id"),
                        rs.getString("course_name"), rs.getInt("credits")));
            }
        }
        return list;
    }

    public boolean isEnrolled(String studentId, String courseId) throws SQLException {
        String sql = "SELECT 1 FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void enrollStudent(String studentId, String courseId) throws SQLException {
        if (isEnrolled(studentId, courseId)) {
            throw new SQLException("Student is already enrolled in this course.");
        }
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ps.executeUpdate();
        }
    }

    public List<Course> getCoursesForStudent(String studentId) throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT c.course_id, c.course_name, c.credits FROM courses c " +
                     "JOIN enrollments e ON c.course_id = e.course_id WHERE e.student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new TheoryCourse(rs.getString("course_id"),
                            rs.getString("course_name"), rs.getInt("credits")));
                }
            }
        }
        return list;
    }
}
