package it.interno.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public interface ConversionUtils {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    static Timestamp getCurrentTimestamp(){
        ZoneId fusoOrario = ZoneId.of("Europe/Rome");
        return Timestamp.valueOf(LocalDateTime.now(fusoOrario));
    }

    static LocalDate timestampToLocalDate(Timestamp timestamp){
        return timestamp.toLocalDateTime().toLocalDate();
    }

    static Timestamp getTimeStamp(String date) throws ParseException {

        java.util.Date parsedDate = dateFormat.parse(date);

        // Convert java.util.Date to java.sql.Timestamp
        return  new Timestamp(parsedDate.getTime());




    }


}
