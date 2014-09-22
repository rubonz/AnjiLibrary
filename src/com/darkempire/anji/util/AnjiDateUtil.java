package com.darkempire.anji.util;

import com.darkempire.anji.annotation.AnjiUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Домоміжний Util клас, який допомогє впоратися з різними задачами та типами дат у Java
 *
 * @author siredvin
 * @see com.darkempire.anji.annotation.AnjiUtil
 * @since Anji 0.1
 */
@AnjiUtil
public final class AnjiDateUtil {

    private AnjiDateUtil() {
    }

    /**
     * Метод-конвертор.
     * Переводить формат Date в формат LocalDate
     *
     * @param date дата, вказана у старому форматі
     * @return дата, вказана у новому форматі
     * @see java.time.LocalDate
     * @see java.util.Date
     */
    public static LocalDate convertDateToLocalDate(Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Метод конвертор.
     * Переводить формат LocalDate у формат Date
     *
     * @param date дата, вказана в новому форматі
     * @return дата, вказана в старому форматі
     * @see java.time.LocalDate
     * @see java.util.Date
     */
    public static java.sql.Date convertLocalDateToSQLDate(LocalDate date) {
        return new java.sql.Date(java.sql.Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()).getTime());
    }
}
