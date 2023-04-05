package jedi.followmypath.webapp.services.utils.impl;

import jedi.followmypath.webapp.services.utils.UtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UtilServiceImplTest {

    @Autowired
    UtilService utilService;

    private static final List<String> months1 = List.of("0","1","3","5","7","8","10","12");
    private static final List<String> months2 = List.of("4","6","9","11");

    @Test
    void whe_the_path_traveled_have_the_same_date_return_true(){

        assertThat(utilService.isToday(LocalDateTime.now())).isTrue();
    }

    @Test
    void whe_the_path_traveled_is_yesterday_date_return_false(){

        LocalDateTime dateToTest = LocalDateTime.now();

        int[] newDate = getDatAndMonthBefore(dateToTest.getYear(),dateToTest.getMonthValue(),dateToTest.getDayOfMonth());

        assertThat(utilService.isToday(LocalDateTime.of(newDate[2],newDate[1],newDate[0],0,0))).isFalse();
    }

    private int[] getDatAndMonthBefore(int valueOfYear,int valueOfMonth,int valueOfDay){
        boolean dayChangedMoreOfOneDay = false;
        if(valueOfDay == 1){
            if(months1.contains(String.valueOf(valueOfMonth-1))){
                valueOfDay = 31;
            }else if (months2.contains(String.valueOf(valueOfMonth-1))){
                valueOfDay = 30;
            }else {
                valueOfDay = 28;
            }
            dayChangedMoreOfOneDay = true;
        }else {
            valueOfDay = valueOfDay - 1;
        }

        if(dayChangedMoreOfOneDay){
            if(valueOfMonth == 1){
                valueOfMonth = 12;
                valueOfYear = valueOfYear - 1;
            }
            valueOfMonth = valueOfMonth - 1;
        }

        int[] newDate = {valueOfDay,valueOfMonth,valueOfYear};
        return newDate;
    }

}