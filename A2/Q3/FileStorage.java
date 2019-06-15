import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileStorage implements IStudentFileStorage {


    @Override
    public void save(Student student) {
        try
        {
            PrintWriter writer = new PrintWriter("student.txt", "UTF-8");
            writer.println(student.getBannerID());
            writer.println(student.getFirstName());
            writer.println(student.getLastName());
            writer.println(student.getEmail());
            writer.close();

        }
        catch (Exception e)
        {
            System.out.println("I am a bad programmer that hid an exception.");
        }
    }

    @Override
    public Student load()
    {
        Student student = new Student();
        try
        {
            Scanner in = new Scanner(new FileReader("student.txt"));

            student. bannerID = in.next();
            student. firstName = in.next();
            student. lastName = in.next();
            student. email = in.next();

        }
        catch (Exception e)
        {
            System.out.println("I am a bad programmer that hid an exception.");
        }
        return  student;
    }
}
