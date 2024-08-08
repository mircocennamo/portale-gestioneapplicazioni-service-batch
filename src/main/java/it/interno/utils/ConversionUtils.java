package it.interno.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public interface ConversionUtils {



    static Timestamp getCurrentTimestamp(){
        ZoneId fusoOrario = ZoneId.of("Europe/Rome");
        return Timestamp.valueOf(LocalDateTime.now(fusoOrario));
    }

    static LocalDate timestampToLocalDate(Timestamp timestamp){
        return timestamp.toLocalDateTime().toLocalDate();
    }


}
