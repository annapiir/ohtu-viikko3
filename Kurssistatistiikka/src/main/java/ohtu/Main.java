package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";
        String urlCourse = "https://studies.cs.helsinki.fi/courses/courseinfo";

        String bodyText = Request.Get(url).execute().returnContent().asString();
        String courseBodyText = Request.Get(urlCourse).execute().returnContent().asString();
        

        System.out.println("json-muotoinen tehtävädata:");
        System.out.println( bodyText );
        
        System.out.println("json-muotoinen kurssidata:");
        System.out.println(courseBodyText);

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        Course[] courses = mapper.fromJson(courseBodyText, Course[].class);
        
        //Submissions for courses
        for (Course course : courses) {
            ArrayList<Submission> courseSub = new ArrayList<Submission>();
            
            for (Submission sub : subs) {
                if (sub.getCourse().equals(course.getName())) {
                    courseSub.add(sub);
                }
            }
            
            course.setSubmissions(courseSub);
        }
        
        
        int hours = 0;
        int exerciseCount = 0;
        
        for (Submission sub : subs) {
            hours += sub.getHours();
            exerciseCount += sub.getExercises().size();
        }
        
        System.out.println("Opiskelijanumero " + studentNr + "\n");
        for (Course course : courses) {
            if (!course.getSubmissions().isEmpty()) {
                System.out.println(course);
            }
        }
        
        System.out.println("\n yhteensä: " + exerciseCount + " tehtävää, " + hours + " tuntia");

    }
}