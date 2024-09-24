package com.sparta.spartdelivery.common.service;


import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTImeService {

    public LocalTime getCurrentDateTime(String dateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        return LocalTime.parse(dateTime, formatter);

    }

}
