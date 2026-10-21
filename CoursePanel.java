import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class CoursePanel extends JPanel {
    private final CourseDAO courseDAO = new CourseDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    private final JTextField codeField = new JTextField(10);
    private final JTextField nameField = new JTextField(15);
    private final JTextField creditsField = new JTextField(5);
    private final DefaultTableModel tableModel =
            new DefaultTableModel(new String[]{"Code", "Name", "Credits"}, 0);
    private final JComboBox<Student> studentBox = new JComboBox<>();
    private final JComboBox<Course> courseBox = new JComboBox<>();

    public CoursePanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Code:"));    form.add(codeField);
        form.add(new JLabel("Name:"));    form.add(nameField);
        form.add(new JLabel("Credits:")); form.add(creditsField);
        JButton addBtn = new JButton("Add Course");
        form.add(addBtn);

        JPanel enroll = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enroll.add(new JLabel("Student:")); enroll.add(studentBox);
        enroll.add(new JLabel("Course:"));  enroll.add(courseBox);
        JButton enrollBtn = new JButton("Enroll");
        enroll.add(enrollBtn);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
        add(enroll, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> handleAddCourse());
        enrollBtn.addActionListener(e -> handleEnroll());

        refresh();
    }

    private void handleAddCourse() {
        String code = codeField.getText().trim();
        String name = nameField.getText().trim();
        if (code.isEmpty() || name.isEmpty() || creditsField.getText().trim().isEmpty()) {
            showError("All fields are required.");
            return;
        }
        try {
            int credits = Integer.parseInt(creditsField.getText().trim());
            if (credits <= 0) { showError("Credits must be positive."); return; }
            courseDAO.addCourse(new TheoryCourse(code, name, credits));
            codeField.setText(""); nameField.setText(""); creditsField.setText("");
            refresh();
        } catch (NumberFormatException ex) {
            showError("Credits must be a number.");
        } catch (SQLException ex) {
            showError("Could not add course (duplicate code?): " + ex.getMessage());
        }
    }

    private void handleEnroll() {
        Student s = (Student) studentBox.getSelectedItem();
        Course c = (Course) courseBox.getSelectedItem();
        if (s == null || c == null) { showError("Select a student and a course."); return; }
        try {
            courseDAO.enrollStudent(s.getStudentId(), c.getCourseId());
            JOptionPane.showMessageDialog(this, "Enrolled successfully.");
        } catch (SQLException ex) {
            showError(ex.getMessage());
        }
    }

    public void refresh() {
        try {
            tableModel.setRowCount(0);
            courseBox.removeAllItems();
            studentBox.removeAllItems();
            for (Course c : courseDAO.getAllCourses()) {
                tableModel.addRow(new Object[]{c.getCourseId(), c.getCourseName(), c.getCredits()});
                courseBox.addItem(c);
            }
            for (Student s : studentDAO.getAllStudents()) {
                studentBox.addItem(s);
            }
        } catch (SQLException ex) {
            showError("Failed to load data: " + ex.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
