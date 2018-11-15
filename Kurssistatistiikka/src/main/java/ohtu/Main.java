package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";
        String urlCourse = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String urlOhtu = "https://studies.cs.helsinki.fi/courses/ohtu2018/stats";
        String urlRails = "https://studies.cs.helsinki.fi/courses/rails2018/stats";
        
        //Haetaan tiedot
        String bodyText = Request.Get(url).execute().returnContent().asString();
        String courseBodyText = Request.Get(urlCourse).execute().returnContent().asString();
        String ohtuStats = Request.Get(urlOhtu).execute().returnContent().asString();
        String railsStats = Request.Get(urlRails).execute().returnContent().asString();
        

        //System.out.println("json-muotoinen tehtävädata:");
        //System.out.println( bodyText );
        
        //System.out.println("json-muotoinen kurssidata:");
        //System.out.println(courseBodyText);
        


        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        Course[] courses = mapper.fromJson(courseBodyText, Course[].class);
        
        JsonParser parser = new JsonParser();
        JsonObject ohtuData = parser.parse(ohtuStats).getAsJsonObject();
        JsonObject railsData = parser.parse(railsStats).getAsJsonObject();
        
        //Stats for ohtu
        Object[] weeksOhtu =  ohtuData.keySet().toArray();
        Integer studentsOhtu = 0;
        Integer exerciseTotalOhtu = 0;
        Double hoursTotalOhtu = 0.0;
        for (Object week : weeksOhtu) {
            JsonObject weeklyStats = ohtuData.getAsJsonObject((String) week);
            studentsOhtu += Integer.parseInt(weeklyStats.get("students").toString());
            exerciseTotalOhtu += Integer.parseInt(weeklyStats.get("exercise_total").toString());
            hoursTotalOhtu += Double.parseDouble(weeklyStats.get("hour_total").toString());
        }
        
        //Stats for rails
        Object[] weeksRails =  railsData.keySet().toArray();
        Integer studentsRails = 0;
        Integer exerciseTotalRails = 0;
        Double hoursTotalRails = 0.0;
        for (Object week : weeksRails) {
            JsonObject weeklyStats = railsData.getAsJsonObject((String) week);
            studentsRails += Integer.parseInt(weeklyStats.get("students").toString());
            exerciseTotalRails += Integer.parseInt(weeklyStats.get("exercise_total").toString());
            hoursTotalRails += Double.parseDouble(weeklyStats.get("hour_total").toString());
        }

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
                System.out.println(course + "\n");
                
                if (course.getName().equals("ohtu2018")) {
                    System.out.println("kurssilla yhteensä " + studentsOhtu + " palautusta, palautettuja tehtäviä " +
                            exerciseTotalOhtu + " kpl, aikaa käytetty yhteensä " + hoursTotalOhtu + " tuntia\n");
                } else if (course.getName().equals("rails2018")) {
                    System.out.println("kurssilla yhteensä " + studentsRails + " palautusta, palautettuja tehtäviä " +
                            exerciseTotalRails + " kpl, aikaa käytetty yhteensä " + hoursTotalRails + " tuntia\n");
                } else {
                    System.out.println("\n");
                }
            }
        }
        
        System.out.println("\n yhteensä: " + exerciseCount + " tehtävää, " + hours + " tuntia");

    }
}