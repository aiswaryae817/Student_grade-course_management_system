
package coursegrademanagement;

public class UpdateStudentTest {

    public static void main(String[] args) {

        Student student = new Student(
            101,
            "Anu Krishna",
            "CSE",
            4,
            "anu@example.com"
        );

        StudentDAO dao = new StudentDAO();

        if (dao.updateStudent(student)) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student update failed.");
        }
    }
}
