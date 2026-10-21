public class Enrollment {
    private int enrollmentId;
    private String studentId;
    private String courseId;

    public Enrollment(int enrollmentId, String studentId, String courseId) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
}
