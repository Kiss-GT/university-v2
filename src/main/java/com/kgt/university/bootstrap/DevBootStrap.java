package com.kgt.university.bootstrap;

import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.domain.Student;
import com.kgt.university.repositories.CourseRepo;
import com.kgt.university.repositories.ProfRepo;
import com.kgt.university.repositories.StudRepo;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DevBootStrap implements ApplicationListener<ContextRefreshedEvent> {


    private CourseRepo courseRepo;
    private ProfRepo profRepo;
    private StudRepo studRepo;


    public DevBootStrap(CourseRepo courseRepo, ProfRepo profRepo, StudRepo studRepo) {
        this.courseRepo = courseRepo;
        this.profRepo = profRepo;
        this.studRepo = studRepo;

    }

    private List<Course> initData() {
        List<Course> courses=new ArrayList<>();

        Professor prof1 = new Professor();
        Professor prof2 = new Professor();

        Course cartography = new Course("Cartography", "Learn how maps are made", prof1);
        Course geomorphology = new Course("Geomorphology", "Learn how nature shapes the land", prof2);
        Course gis = new Course("Gis", "Learn how to use ArcGis", prof2);


        Student student1 = new Student();
        Student student2 = new Student();

         student1.setFirstName("Jimmie");
        student1.setLastName("Young");
        student2.setFirstName("Robyn");
        student2.setLastName("Lockwood");


        prof1.setFirstName("Brennan");
        prof1.setLastName("Eliot");
        prof1.setBio("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

        prof2.setFirstName("Orval");
        prof2.setLastName("Ryland");
        prof2.setBio("It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).");

        cartography.getStudents().add(student1);
        cartography.getStudents().add(student2);
        geomorphology.getStudents().add(student2);
        gis.getStudents().add(student1);

        profRepo.save(prof1);
        profRepo.save(prof2);
        courseRepo.save(geomorphology);
        courseRepo.save(cartography);
        courseRepo.save(gis);
        studRepo.save(student2);
        studRepo.save(student1);
        ;
        return courses;
    }
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {courseRepo.saveAll(initData());
        //initData();
    }
}
