package com.tudv8.helper;

import com.tudv8.entities.Course;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERS = {"course_id", "course_name",
                                "start_date", "end_date"};

    public static boolean isCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

     public static List<Course> convertCsvToCourseList(InputStream inputStream) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                                                 CSVFormat.DEFAULT.
                                                         withFirstRecordAsHeader().
                                                         withIgnoreHeaderCase().
                                                         withTrim());)
        {
            List<Course> courses = new ArrayList<Course>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Course course = new Course(Long.parseLong(csvRecord.get(HEADERS[0])),
                                            csvRecord.get(HEADERS[1]),
                                            Timestamp.valueOf(csvRecord.get(HEADERS[2])),
                                            Timestamp.valueOf(csvRecord.get(HEADERS[3])));
                courses.add(course);
            }
            return courses;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to parse CSV file: " + ex.getMessage());
        }
     }
}
