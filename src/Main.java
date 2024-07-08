import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    static int maxStud = 100;
    static Student[] studDetails = new Student[maxStud];
    static int registeredStud;
    static Scanner input = new Scanner(System.in);
    static boolean studDetailsLoaded = false;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        menu(input);
    }

    // Display the menu and handle the user input.
    public static void menu(Scanner input) {
        int option = 99;
        while (option != 0) {
            System.out.println();
            System.out.println("+-------------------------------+");
            System.out.println("| Welcome to the Students Registration Portal |");
            System.out.println("| Menu |");
            System.out.println("+-------------------------------+");
            System.out.println("1. Check available seats");
            System.out.println("2. Register student (with ID)");
            System.out.println("3. Delete student");
            System.out.println("4. Find student (with student ID)");
            System.out.println("5. Store student details into a file");
            System.out.println("6. Load student details from the file to the system");
            System.out.println("7. View the list of students based on their names");
            System.out.println("8. Manage students details");
            System.out.println("0. Exit program");
            System.out.print("Enter your option: ");
            try {
                option = input.nextInt();
                input.nextLine(); // for consume new line.
                switch (option) {
                    case 1:
                        CheckAvailableSeats();
                        break;
                    case 2:
                        RegisterStudents();
                        break;
                    case 3:
                        DeleteStudents();
                        break;
                    case 4:
                        FindStudents();
                        break;
                    case 5:
                        StoreDetailsInFile();
                        break;
                    case 6:
                        LoadDetailsFromFile();
                        break;
                    case 7:
                        ViewTheStudList();
                        break;
                    case 8:
                        manageStudentDetails();
                        break;
                    case 0:
                        System.out.print("Exit...");
                        break;
                    default:
                        System.out.print("Invalid option, Try again!");
                }
            } catch (InputMismatchException e) {
                System.out.println(" Invalid input. Enter the number.");
                input.nextLine();
            }
        }
    }

    // Check available seats
    public static void CheckAvailableSeats() {
        System.out.print("Available seats: " + (maxStud - registeredStud) + " seats.");
    }

    // Register a new student into array.
    public static void RegisterStudents() {
// Check if registered students count cross the limit of students count.
        if (registeredStud >= maxStud) {
            System.out.println("Maximum 100 students can be registered, So seats are not available.");
            return;
        }
// Ask user to until enter the valid name.
        String studName, studID;
        while (true) {
            System.out.print("Enter the student Name : ");
            studName = input.nextLine();
// Check if student name has alphabets letters or alphabets letter with full stop or space.
            if (studName.matches("[a-zA-Z .]+")) {
                System.out.println("Student Name: " + studName);
// Exit the while loop if the input is valid.
                break;
            } else {
                System.out.println("Invalid student name, Please enter only alphabet letters.");
            }
        }
// Ask user to until enter the valid student ID.
        while (true) {
            System.out.print("Enter the student ID: ");
            studID = input.nextLine().toLowerCase();
// Check if student ID starting with w or W followed by 7 digits.
            if (studID.matches("[wW]\\d{7}")) {
// Check if student ID already exist.
                for (int i = 0; i < registeredStud; i++) {
                    if (studDetails[i].getStudID().equals(studID)) {
                        System.out.println("This student ID is already registered.");
                        return;
                    }
                }
// Add students details in array and increment registered students count
                studDetails[registeredStud++] = new Student(studID, studName);
// Exit the while loop if the input is valid.
                break;
            } else {
                System.out.println("Invalid student ID, Please enter 8 characters starting with 'w'.");
            }
        }
        System.out.println("Student details registered successfully.");
    }

    // Delete a student from array.
    public static void DeleteStudents() {
// Check if any students details are available in array.
        if (registeredStud == 0) {
            System.out.print("No students registered yet.");
            return;
        }
// Ask user to enter the student id user need to delete.
        System.out.print("Enter the Student ID you want to delete: ");
        String studId = input.nextLine().toLowerCase();
// Find the student ID from array which user entered, then delete.
        for (int i = 0; i < registeredStud; i++) {
            if (studDetails[i].getStudID().equals(studId)) {
                studDetails[i] = studDetails[--registeredStud];
                studDetails[registeredStud] = null;
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student ID not found.");
    }

    // Find a student by using ID.
    public static void FindStudents() {
// Check if any students details are available in array.
        if (registeredStud == 0) {
            System.out.print("No students registered yet.");
            return;
        }
// Ask user to enter the student id user need to find.
        System.out.print("Enter the Student ID you want to find: ");
        String studId = input.nextLine().toLowerCase();
// Using enhanced loop for find student ID which user entered, then print it.
        for (Student student : studDetails) {
            if (student != null && student.getStudID().equals(studId)) {
                System.out.println("Student Name: " + student.getStudName() + "\n" +
                        "Student ID: " + student.getStudID());
                return;
            }
        }
        System.out.println("Student ID not found.");
    }

    // Store details to file.
    public static void StoreDetailsInFile() {
        FileWriter file = null;
// Handle the error and catch it, when write the student details in text file.
        try {
// Create a file writer object.
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String filename = "StudDetail_" + timestamp + ".txt";
            System.out.println(filename);

            // Create a file writer object.
            file = new FileWriter(filename);
// Iterate over each registered student and write each details into the text file.
            for (int i = 0; i < registeredStud; i++) {
                if (studDetails[i] != null) {
                    Student stud = studDetails[i];
                    file.write(stud.getStudID() + " , ");
                    file.write(stud.getStudName() + " , ");
                    file.write(stud.getModules()[0].getModuleMarks() + " , ");
                    file.write(stud.getModules()[1].getModuleMarks() + " , ");
                    file.write(stud.getModules()[2].getModuleMarks() + "\n");
                }
            }
            file.close();

            System.out.println("Student details are successfully stored in file.");
        } catch (IOException e) {
            System.out.print("An error occurred.");
        }
    }

    public static void LoadDetailsFromFile() {
// Check if details from text file has been already loaded.
        if (studDetailsLoaded) {
            System.out.println("Details have already been loaded.");
            return;
        }
// Hand the file not found error and catch it, when load students details from file.
        try {
            File file = new File("StudDetail.txt");
            Scanner fileReader = new Scanner(file);
// Read the file line by line
            while (fileReader.hasNextLine()) {
                String text = fileReader.nextLine();
                String[] details = text.split(" , ");
// Check the line has the correct number of fields and extract the students details from file.
                if (details.length == 5) {
                    String studID = details[0];
                    String studName = details[1];
// Convert the module marks from strings to integers.
                    int module1Marks = Integer.parseInt(details[2]);
                    int module2Marks = Integer.parseInt(details[3]);
                    int module3Marks = Integer.parseInt(details[4]);
// Set the module marks from file then get in array.
                    Student stud = new Student(studID, studName);
                    stud.getModules()[0].setModuleMarks(module1Marks);
                    stud.getModules()[1].setModuleMarks(module2Marks);
                    stud.getModules()[2].setModuleMarks(module3Marks);
// Add students details in array and increment registered students count.
                    studDetails[registeredStud] = stud;
                    registeredStud++;
                }
            }
            fileReader.close();
// Set it to true after loading details.
            studDetailsLoaded = true;
            System.out.println("Successfully loaded file.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static void ViewTheStudList() {
// Bubble sort students by name's first letter in alphabetical order.
        for (int i = 0; i < registeredStud - 1; i++) {
            for (int j = 0; j < registeredStud - 1 - i; j++) {
                if (studDetails[j].getStudName().compareToIgnoreCase(studDetails[j +
                        1].getStudName()) > 0) {
                    // Swap students[j] and students[j + 1].
                    Student temp = studDetails[j];
                    studDetails[j] = studDetails[j + 1];
                    studDetails[j + 1] = temp;
                }
            }
        }
// Print sorted list of students.
        System.out.println("List of students with ID (sorted by name in alphabetical order):");
        System.out.printf("+--------------------------------------+%n");
        System.out.printf("| %-20s | %-12s |%n", "Student Name", "Student ID");
        System.out.printf("+--------------------------------------+%n");
        for (int i = 0; i < registeredStud; i++) {
            System.out.printf("| %-20s | %-12s |%n", studDetails[i].getStudName(),
                    studDetails[i].getStudID());
            System.out.printf("----------------------------------------%n");
        }
    }

    public static void manageStudentDetails() {
        char subOption = ' ';
// Loop until the user decides to return the main menu.
        while (subOption != '0') {
            System.out.println();
            System.out.println("+--------------------------+");
            System.out.println("|--Manage Student Details--|");
            System.out.println("+--------------------------+");
            System.out.println("a. Update student name");
            System.out.println("b. Module marks 1, 2 and 3");
            System.out.println("c. Generate summary of the system");
            System.out.println("d. Generate complete report");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your option: ");
            String entry = input.nextLine();
// Check if the input is exactly one character and if it's a valid option.
            if (entry.length() == 1) {
                subOption = entry.charAt(0);
                switch (subOption) {
                    case 'a':
                        updateStudentName();
                        break;
                    case 'b':
                        addModuleMarks();
                        break;
                    case 'c':
                        generateSummaryReport();
                        break;
                    case 'd':
                        generateCompleteReport();
                        break;
                    case '0':
                        System.out.println("Return to the main menu....");
                        break;
                    default:
                        System.out.println("Invalid option, Please try again.");
                }
            } else {
                System.out.println("Invalid sub-option, Please try again.");
            }
        }
    }

    public static void generateSummaryReport() {
        int passedModule1 = 0;
        int passedModule2 = 0;
        int passedModule3 = 0;
// By using enhanced loop, calculate the number of students who passed every module.
        for (Student student : studDetails) {
            if (student != null) {
                Module[] modules = student.getModules();
                if (modules[0].getModuleMarks() > 40) passedModule1++;
                if (modules[1].getModuleMarks() > 40) passedModule2++;
                if (modules[2].getModuleMarks() > 40) passedModule3++;
            }
        }
        System.out.println("+---------------------+");
        System.out.println("|--Summary of Report--|");
        System.out.println("+---------------------+");
// Print the registered students count.
        System.out.println("Total number of student registrations: " + registeredStud +
                " students.");
        System.out.println("Total number of students who scored more than 40 marks in Module 1: " + passedModule1 + " students");
        System.out.println("Total number of students who scored more than 40 marks in Module 2: " + passedModule2 + " students");
        System.out.println("Total number of students who scored more than 40 marks in Module 3: " + passedModule3 + " students");
    }

    ;

    public static void generateCompleteReport() {
// Check if no students are registered.
        if (registeredStud == 0) {
            System.out.println("There are no students have been registered yet.");
        } else {
// Sort students by average marks using bubble sort.
            for (int i = 0; i < registeredStud - 1; i++) {
                for (int j = 0; j < registeredStud - i - 1; j++) {
                    if (studDetails[j + 1] != null && (studDetails[j] == null ||
                            studDetails[j].getAverageMarks() < studDetails[j + 1].getAverageMarks())) {
                        Student temp = studDetails[j];
                        studDetails[j] = studDetails[j + 1];
                        studDetails[j + 1] = temp;
                    }
                }
            }
            System.out.printf("+-------------------------------------------------------------------------------------------------------+%n");
            System.out.printf(" Complete Report with students details %n");
            System.out.printf("+-------------------------------------------------------------------------------------------------------+%n");
            System.out.printf("| %-8s | %-25s | %-7s | %-7s | %-7s | %-6s | %-7s | %-12s |%n", "ID", "Name", "Module1", "Module2", "Module3", "Total", "Average", "Grade");
            System.out.printf("+-------------------------------------------------------------------------------------------------------+%n");
// List the students details with module marks by using for loop.
            for (int i = 0; i < registeredStud; i++) {
                Student student = studDetails[i];
                if (student != null) {
                    int total = student.getTotalMarks();
                    int average = student.getAverageMarks();
                    String moduleGrade = student.getModuleGrade();
                    System.out.printf("| %-8s | %-25s | %-7s | %-7s | %-7s | %-6s | %-7s | %-12s |%n",
                            student.getStudID(), student.getStudName(),
                            student.getModules()[0].getModuleMarks(),
                            student.getModules()[1].getModuleMarks(), student.getModules()[2].getModuleMarks(),
                            total, average + "%", moduleGrade);
                    System.out.printf("---------------------------------------------------------------------------------------------------------%n");
                }
            }
        }
    }


    public static void updateStudentName() {
        String studName, studID;
// Ask user to until valid student ID input.
        while (true) {
            System.out.print("Enter student ID to manage: ");
            studID = input.nextLine().toLowerCase();
            if (studID.matches("[wW]\\d{7}")) {
// Assign a student variable.
                Student student = null;
// Find the student with the given ID.
                for (Student stud : studDetails) {
                    if (stud != null && stud.getStudID().equals(studID)) {
                        student = stud;
                        break;
                    }
                }
// Check if the student not found.
                if (student == null) {
                    System.out.println("Student ID not found.");
                    return;
                }
// Ask user to until valid name input.
                while (true) {
                    System.out.print("Enter the update student Name : ");
                    studName = input.nextLine();
// Check if student name has alphabets letters or alphabets letter with full stop or space.
                    if (studName.matches("[a-zA-Z .]+")) {
                        System.out.println("Student Name updated as: " + studName);
// Exit the loop if the input is valid.
                        break;
                    } else {
                        System.out.println("Invalid student name, Please used alphabet letters.");
                    }
                }
// Set the updated student name.
                student.setStudName(studName);
                System.out.println("Student name updated successfully.");
                return;
            } else {
                System.out.println("Invalid Student ID, Please enter 8 characters starting with 'w'.");
            }
        }
    }

    // Add 3 modules marks for student.
    public static void addModuleMarks() {
// Ask user to until valid student ID entered.
        while (true) {
            System.out.print("Enter student ID to manage: ");
            String studID = input.nextLine().toLowerCase();
// Find the student with the given ID.
            if (studID.matches("[wW]\\d{7}")) {
                Student student = null;
                for (Student stud : studDetails) {
                    if (stud != null && stud.getStudID().equals(studID)) {
                        student = stud;
// Exit the while loop if the input is valid.
                        break;
                    }
                }
// Check if the student not found.
                if (student == null) {
                    System.out.println("Student ID not found.");
                    return;
                }
// Ask user to input the module marks using for loop.
                for (int i = 0; i < 3; i++) {
// Ask user to until valid marks entered.
                    while (true) {
                        System.out.print("Enter mark for module" + (i + 1) + " (>=0 & <= 100):");
// Checks if the next token in the input is an integer.
                        if (input.hasNextInt()) {
                            int mark = input.nextInt();
//Check if the marks range 0 to 100.
                            if (mark >= 0 && mark <= 100) {
                                student.getModules()[i].setModuleMarks(mark);
// Exit the while loop if the input is valid.
                                break;
                            } else {
                                System.out.println("The mark must be greater than or equal to 0 and less than or equal to 100.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a numeric value.");
// Clear the invalid input.
                            input.next();
                        }
                    }
                }
// Consume newline.
                input.nextLine();
                System.out.println("Module marks updated successfully.");
                return;
            } else {
                System.out.println("Invalid Student ID, Please enter 8 characters starting with 'w'.");
            }
        }
    }}