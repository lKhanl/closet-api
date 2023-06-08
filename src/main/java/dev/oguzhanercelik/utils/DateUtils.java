package dev.oguzhanercelik.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(TimeZone.getTimeZone("Turkey").toZoneId()).toInstant());
    }

}