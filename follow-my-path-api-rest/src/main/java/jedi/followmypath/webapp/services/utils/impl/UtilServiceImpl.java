package jedi.followmypath.webapp.services.utils.impl;

import jedi.followmypath.webapp.services.utils.UtilService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UtilServiceImpl implements UtilService {

    private static final String CERO = "0";
    @Override
    public boolean isToday(LocalDateTime dateCreationCar) {
        String[] dateNow = LocalDate.now().getChronology().dateNow().toString().split("-");

        return  dateNow[0].equals(completeMonthAndDay(dateCreationCar.getYear())) &&
                dateNow[1].equals(completeMonthAndDay(dateCreationCar.getMonthValue())) &&
                dateNow[2].equals(completeMonthAndDay(dateCreationCar.getDayOfMonth()));
    }

    private String completeMonthAndDay(int value){
        if(value < 10){
            return CERO.concat(String.valueOf(value));
        }
        return String.valueOf(value);
    }

}
