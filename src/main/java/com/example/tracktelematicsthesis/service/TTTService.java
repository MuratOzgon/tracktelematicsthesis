package com.example.tracktelematicsthesis.service;

import com.example.tracktelematicsthesis.controller.pojos.CarDataPojo;
import com.example.tracktelematicsthesis.controller.pojos.UserDataPojo;
import com.example.tracktelematicsthesis.model.AnalysisData;
import com.example.tracktelematicsthesis.model.CarData;
import com.example.tracktelematicsthesis.model.ObdData;
import com.example.tracktelematicsthesis.model.User;
import com.example.tracktelematicsthesis.repository.AnalysisRepository;
import com.example.tracktelematicsthesis.repository.CarDataRepository;
import com.example.tracktelematicsthesis.repository.ObdDataRepository;
import com.example.tracktelematicsthesis.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
    CarDataRepository carDataRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnalysisRepository analysisRepository;

    public static String TYPE = "text/csv";


    public void save(MultipartFile file) {
        try {
            List<ObdData> tutorials = csvToObdData(file.getInputStream());
            dataRepository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public void saveFromJson(UserDataPojo userDataPojo) {

        if(!userRepository.existsByUserId(userDataPojo.getUserId())) {
            userRepository.save(new User(userDataPojo.getUserId()));
        }
        User usr = userRepository.findUserByUserId(userDataPojo.getUserId());

        List<AnalysisData> analysisDataList = new ArrayList<>();

        userDataPojo.getAnalysis().forEach(analysisPojo -> {
            AnalysisData analysisData = new AnalysisData(usr);
            analysisData.setDateTime(analysisPojo.getDateTime());
            analysisData.setPoints(analysisPojo.getPoints());
            analysisData.setHeavyAccel(analysisPojo.isHeavyAccel());
            analysisData.setHeavyDecel(analysisPojo.isHeavyDecel());
            analysisData.setHighSpeed(analysisPojo.getHighSpeed());
            analysisData.setZigzagging(analysisPojo.isZigzagging());
            analysisRepository.save(analysisData);
        });




        User usr1 = userRepository.findUserByUserId(userDataPojo.getUserId());

        analysisDataList = analysisRepository.findAll();

        List<User> users = userRepository.findAll();

        /*try {
            List<CarData> carDataList = new ArrayList<>();
            carDataPojoList.forEach(carDataPojo -> {
                CarData carData = new CarData();
                carData.setUserId("10");
                carData.setDateTimeStr(carDataPojo.getDateTime());
                carData.setPoints(carDataPojo.getPoints());
                carData.setHeavyAccel(carDataPojo.isHeavyAccel());
                carData.setHeavyDecel(carDataPojo.isHeavyDecel());
                carData.setZigzagging(carDataPojo.isZigzagging());
                carData.setHighSpeed(carDataPojo.getHighSpeed());
                carDataList.add(carData);
            });
            carDataRepository.saveAll(carDataList);
        } catch (Exception e) {
            throw new RuntimeException("fail to store json data: " + e.getMessage());
        }*/
    }

    public List<ObdData> getAllTutorials() {
        return dataRepository.findAll();
    }

    public List<CarData> getAllCarData() {
        return carDataRepository.findAll();
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

                //calculateScore(obdData);
            }
            return obdDataList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private List<CarData> csvToCarData(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<CarData> carDataList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                CarData carData = new CarData();
                carData.setUserId("10");
                carData.setDateTimeStr(csvRecord.get("Device Time"));
                carData.setPoints(Double.parseDouble(csvRecord.get("Points")));
                carData.setHeavyAccel("TRUE".equalsIgnoreCase(csvRecord.get("heavyAccel")));
                carData.setHeavyDecel("TRUE".equalsIgnoreCase(csvRecord.get("heavyDecel")));
                carData.setZigzagging("TRUE".equalsIgnoreCase(csvRecord.get("zigzagging")));

                carDataList.add(carData);

                //calculateScore(carData);
            }
            return carDataList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
/*
    private double calculateScore(ObdData data) {
        double score = Math.random();
        long userId = data.getUserId();

        if (!userRepository.existsById(userId)) {
            User newUser = new User();
            newUser.setUserId(userId);
            userRepository.save(newUser);
        }

        User userToUpdate = userRepository.getById(userId);
        long numberOfData = userToUpdate.getNumberOfData();

        userToUpdate.setScore(score);
        userToUpdate.setNumberOfData(numberOfData+1);

        userRepository.save(userToUpdate);

        return score;
    }*/
}
