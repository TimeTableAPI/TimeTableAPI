package pt.iscte.asd.projectn3.group11.services.loaders;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jetbrains.annotations.NotNull;
import pt.iscte.asd.projectn3.group11.models.ClassCourse;
import pt.iscte.asd.projectn3.group11.models.Classroom;
import pt.iscte.asd.projectn3.group11.models.util.Date;
import pt.iscte.asd.projectn3.group11.models.util.TimeShift;
import pt.iscte.asd.projectn3.group11.services.LogService;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public final class ClassCourseLoaderService extends LoaderService<ClassCourse> {

    private static ClassCourseLoaderService INSTANCE = null;

    private ClassCourseLoaderService(){}
    public static synchronized ClassCourseLoaderService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ClassCourseLoaderService();
        }
        return INSTANCE;
    }

    @Override
    protected void extract(@NotNull CSVReader csvReader, @NotNull List<ClassCourse> classCourses) throws IOException, CsvValidationException {
        csvReader.readNext();
        String[] nextRecord;

        while ((nextRecord = csvReader.readNext()) != null) {

            final String course = nextRecord[0];
            final String unit = nextRecord[1];
            final String shift = nextRecord[2];
            final String classOfCourse = nextRecord[3];
            final String studentNum = nextRecord[4];
            final String shiftsWithFreeSpots = nextRecord[5];
            final String shiftsWithMoreThanTheCapacity = nextRecord[6];
            final String weekday = nextRecord[7];
            final String beginningHourString = nextRecord[8];
            final String endHourString = nextRecord[9];
            final String dateString = nextRecord[10];
            final String askedCharacteristics = nextRecord[11];
            final String classroom = nextRecord[12];
            final String capacityString = nextRecord[13];
            final String realCharacteristics = nextRecord[14];

            final int capacity = capacityString.isEmpty() ? -1 : Integer.parseInt(capacityString);
            final TimeShift beginningHour = TimeShift.stringToClassTime(beginningHourString);
            final TimeShift endHour = TimeShift.stringToClassTime(endHourString);
            final Date date = new Date(dateString);

            for( int i =beginningHour.getId() ; i< endHour.getId();i++){
                TimeShift begginingTime = TimeShift.getById(i);
                TimeShift endTime = TimeShift.getById(i+1);

                final List<String> realCharacteristicsList = Arrays.asList((realCharacteristics.split(", ")));
                LinkedList<Boolean> classroomCharacteristics = new LinkedList<Boolean>();
                for(String s : Classroom.CHARACTERISTICS_LIST){
                    classroomCharacteristics.add(realCharacteristicsList.contains(s));
                }

               final String[] classroomStringSplit = classroom.split(",");
               Classroom classroomBuild;
               if(classroomStringSplit.length>1) {
                   classroomBuild = new Classroom.Builder()
                           .building(classroomStringSplit[0])
                           .classroomName(classroomStringSplit[1])
                           .normalCapacity(capacity)
                           .examCapacity(capacity)
                           .numberCharacteristics(realCharacteristicsList.size())
                           .characteristics(classroomCharacteristics)
                           .build();
               }else{
                   classroomBuild = new Classroom.Builder()
                           .classroomName(classroomStringSplit[0])
                           .normalCapacity(capacity)
                           .examCapacity(capacity)
                           .numberCharacteristics(realCharacteristicsList.size())
                           .characteristics(classroomCharacteristics)
                           .build();
               }

                ClassCourse aclassCourse = new ClassCourse.Builder().
                        courses(Arrays.asList(course.split(", "))).
                        units(Arrays.asList(unit.split(", "))).
                        shift(shift).
                        classesOfCourse(Arrays.asList(classOfCourse.split(", "))).
                        numberOfStudentsInClass(Integer.parseInt(studentNum)).
                        shiftsWithFreeSpots(Integer.parseInt(shiftsWithFreeSpots)).
                        shiftsWithMoreThanTheCapacity(Integer.parseInt(shiftsWithMoreThanTheCapacity)).
                        weekday(weekday).
                        beginningHour(begginingTime).
                        endHour(endTime).
                        date(date).
                        askedCharacteristics(Arrays.asList((askedCharacteristics).split(","))).
                        capacity(capacity).
                        realCharacteristics(realCharacteristicsList).
                        classroom(
                                classroomBuild
                        ).
                        build();
                LogService.getInstance().info(aclassCourse);
                classCourses.add(aclassCourse);
            }
        }
    }

    @NotNull
    public static File export(List<ClassCourse> classCourses) throws IOException {
        try {
            Path temp = Files.createTempFile("tempExportedClasses", ".csv");
            File myObj = new File(temp.toUri());
            try (FileWriter writer = new FileWriter(myObj)) {
                writer.write(String.join(",", ClassCourse.HEADER) + "\n");
                for (ClassCourse classCourse : classCourses) {
                    writer.write(classCourse.toCSVEntry());
                    writer.write("\n");
                }

            }
            LogService.getInstance().info("File created in:" + myObj.getAbsolutePath());
            return myObj;
        } catch (IOException e) {
            LogService.getInstance().error("An error occurred.");
            e.printStackTrace();
            throw e;
        }
    }

}
