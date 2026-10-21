
package coursegrademanagement;

import java.util.List;

public class ViewStudentsTest {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        List<Student> students = dao.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {

            System.out.println("STUDENT DETAILS");
            System.out.println("------------------------------");

            for (Student student : students) {
                System.out.println("Student ID: " + student.getStudentId());
                System.out.println("Name: " + student.getName());
                System.out.println("Department: " + student.getDepartment());
                System.out.println("Semester: " + student.getSemester());
                System.out.println("Email: " + student.getEmail());
                System.out.println("------------------------------");
            }
        }
    }
}