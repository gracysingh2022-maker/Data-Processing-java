import java.io.*;
import java.util.*;

// ======================= PART A: Sum Using Autoboxing & Unboxing =======================
class SumUsingAutoboxing {
    public static void run() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> numbers = new ArrayList<>();

        System.out.println("\n=== Part A: Sum of Integers Using Autoboxing & Unboxing ===");
        System.out.println("Enter integers separated by space:");
        String input = sc.nextLine();
        String[] tokens = input.split(" ");

        // Autoboxing: int → Integer
        for (String token : tokens) {
            Integer num = Integer.parseInt(token); // parse int → autobox to Integer
            numbers.add(num);
        }

        int sum = 0;
        // Unboxing: Integer → int
        for (Integer num : numbers) {
            sum += num;
        }

        System.out.println("Sum of integers: " + sum);
    }
}

// ======================= PART B: Student Serialization =======================
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int studentID;
    private String name;
    private double grade;

    public Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }

    public void display() {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Grade: " + grade);
    }
}

class StudentSerializationDemo {
    public static void run() {
        String filename = "student.ser";

        System.out.println("\n=== Part B: Serialization & Deserialization of Student ===");

        // Serialize Student object
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            Student s1 = new Student(101, "Alice", 9.1);
            oos.writeObject(s1);
            System.out.println("Student object serialized and saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialize Student object
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Student s2 = (Student) ois.readObject();
            System.out.println("\nDeserialized Student Data:");
            s2.display();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// ======================= PART C: Employee Management System =======================
class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String designation;
    private double salary;

    public Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee ID: " + id + ", Name: " + name +
               ", Designation: " + designation + ", Salary: " + salary;
    }
}

class AppendableObjectOutputStream extends ObjectOutputStream {
    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    @Override
    protected void writeStreamHeader() throws IOException {
        reset(); // Avoids writing header when appending
    }
}

class EmployeeManagementSystem {
    private static final String FILE_NAME = "employees.dat";

    public static void run() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addEmployee(sc);
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    System.out.println("Exiting Employee Management System...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 3);
    }

    private static void addEmployee(Scanner sc) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Designation: ");
            String designation = sc.nextLine();

            System.out.print("Enter Salary: ");
            double salary = sc.nextDouble();

            Employee emp = new Employee(id, name, designation, salary);

            File file = new File(FILE_NAME);
            ObjectOutputStream oos;
            if (file.exists() && file.length() > 0) {
                oos = new AppendableObjectOutputStream(new FileOutputStream(file, true));
            } else {
                oos = new ObjectOutputStream(new FileOutputStream(file));
            }

            oos.writeObject(emp);
            oos.close();
            System.out.println("Employee added successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("\n===== Employee Records =====");
            while (true) {
                Employee emp = (Employee) ois.readObject();
                System.out.println(emp);
            }
        } catch (EOFException e) {
            // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No employees found or error reading file.");
        }
    }
}

// ======================= MAIN CLASS =======================
public class MainProgram {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int mainChoice;
        do {
            System.out.println("\n======= Main Menu =======");
            System.out.println("1. Part A: Sum Using Autoboxing & Unboxing");
            System.out.println("2. Part B: Student Serialization Demo");
            System.out.println("3. Part C: Employee Management System");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = sc.nextInt();
            sc.nextLine();

            switch (mainChoice) {
                case 1:
                    SumUsingAutoboxing.run();
                    break;
                case 2:
                    StudentSerializationDemo.run();
                    break;
                case 3:
                    EmployeeManagementSystem.run();
                    break;
                case 4:
                    System.out.println("Exiting Main Program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (mainChoice != 4);

        sc.close();
    }
}
