package com.example.tracktelematicsthesis.service;

import com.example.tracktelematicsthesis.model.ObdData;
import com.example.tracktelematicsthesis.model.User;
import com.example.tracktelematicsthesis.repository.ObdDataRepository;
import com.example.tracktelematicsthesis.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class TTTService {

    @Autowired
    ObdDataRepository dataRepository;

    @Autowired
    UserRepository userRepository;

    public static String TYPE = "text/csv";
    static String[] HEADERS = { "GPS Time", "Device Time", "Longitude", "Latitude", "GPS Speed (Meters/second)",
            "Horizontal Dilution of Precision", "Altitude", "Bearing", "G(x)", "G(y)", "G(z)", "G(calibrated)",
            "Acceleration Sensor(Total)(g)", "Acceleration Sensor(Z axis)(g)", "Speed (OBD)(km/h)",
            "Acceleration Sensor(X axis)(g)", "Acceleration Sensor(Y axis)(g)", "Average trip speed(whilst stopped or moving)(km/h)"};


    public void save(MultipartFile file) {
        try {
            List<ObdData> tutorials = csvToObdData(file.getInputStream());
            dataRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<ObdData> getAllTutorials() {
        return dataRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    private List<ObdData> csvToObdData(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<ObdData> obdDataList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                ObdData obdData = new ObdData();
                obdData.setUserId(10);
                obdData.setDeviceTime(csvRecord.get("Device Time"));
                obdData.setGx(Double.parseDouble(csvRecord.get("G(x)").replace("-", "0")));
                obdData.setGy(Double.parseDouble(csvRecord.get("G(y)").replace("-", "0")));
                obdData.setGz(Double.parseDouble(csvRecord.get("G(z)").replace("-", "0")));
                obdData.setG_calibrated(Double.parseDouble(csvRecord.get("G(calibrated)").replace("-", "0")));
                obdData.setAccelerationSensorTotalG(Double.parseDouble(csvRecord.get("Acceleration Sensor(Total)(g)").replace("-", "0")));
                obdData.setAccelerationSensorZ_axisG(Double.parseDouble(csvRecord.get("Acceleration Sensor(Z axis)(g)").replace("-", "0")));
                obdData.setSpeed(Double.parseDouble(csvRecord.get("Speed (OBD)(km/h)").replace("-", "0")));
                obdData.setAccelerationSensorX_axisG(Double.parseDouble(csvRecord.get("Acceleration Sensor(X axis)(g)").replace("-", "0")));
                obdData.setAccelerationSensorY_axisG(Double.parseDouble(csvRecord.get("Acceleration Sensor(Y axis)(g)").replace("-", "0")));
                obdData.setAverageTripSpeed(Double.parseDouble(csvRecord.get("Average trip speed(whilst stopped or moving)(km/h)").replace("-", "0")));

                obdDataList.add(obdData);

                calculateScore(obdData);
            }
            return obdDataList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private double calculateScore(ObdData data) {
        double score = Math.random();
        long userId = data.getUserId();

        if (!userRepository.existsById(userId)) {
            User newUser = new User();
            newUser.setId(userId);
            userRepository.save(newUser);
        }

        User userToUpdate = userRepository.getById(userId);
        long numberOfData = userToUpdate.getNumberOfData();

        userToUpdate.setScore(score);
        userToUpdate.setNumberOfData(numberOfData+1);

        userRepository.save(userToUpdate);

        return score;
    }
}
