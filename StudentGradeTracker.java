import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Integer> marks;
    double average;

    Student(String name) {
        this.name = name;
        marks = new ArrayList<>();
    }

    // Calculate average
    void calculateAverage() {
        int sum = 0;

        for (int mark : marks) {
            sum += mark;
        }

        average = (double) sum / marks.size();
    }

    // Calculate grade
    String getGrade() {
        if (average >= 90)
            return "A";
        else if (average >= 80)
            return "B";
        else if (average >= 70)
            return "C";
        else if (average >= 60)
            return "D";
        else
            return "F";
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        // Input student details
        for (int i = 0; i < n; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            Student student = new Student(name);

            System.out.print("Enter number of subjects: ");
            int subjects = sc.nextInt();

            // Input marks
            for (int j = 0; j < subjects; j++) {

                System.out.print("Enter marks for Subject "
                        + (j + 1) + ": ");

                int mark = sc.nextInt();

                while (mark < 0 || mark > 100) {
                    System.out.println("Marks must be between 0 and 100.");
                    System.out.print("Enter marks again: ");
                    mark = sc.nextInt();
                }

                student.marks.add(mark);
            }

            student.calculateAverage();
            students.add(student);

            sc.nextLine();
        }

        // Find highest and lowest
        double highest = students.get(0).average;
        double lowest = students.get(0).average;

        for (Student student : students) {

            if (student.average > highest) {
                highest = student.average;
            }

            if (student.average < lowest) {
                lowest = student.average;
            }
        }

        // Display report
        System.out.println("\n======================================");
        System.out.println("       STUDENT GRADE REPORT");
        System.out.println("======================================");

        System.out.printf("%-20s %-10s %-10s%n",
                "Name", "Average", "Grade");

        System.out.println("--------------------------------------");

        for (Student student : students) {

            System.out.printf("%-20s %-10.2f %-10s%n",
                    student.name,
                    student.average,
                    student.getGrade());
        }

        System.out.println("--------------------------------------");

        System.out.printf("Highest Average : %.2f%n", highest);
        System.out.printf("Lowest Average  : %.2f%n", lowest);

        System.out.println("======================================");

        sc.close();
    }
}
