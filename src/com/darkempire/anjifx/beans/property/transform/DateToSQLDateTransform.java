package com.darkempire.anjifx.beans.property.transform;

import com.darkempire.anji.util.AnjiDateUtil;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Create in 23:57
 * Created by siredvin on 14.04.14.
 */
public class DateToSQLDateTransform implements ITransform<LocalDate, Date> {
    @Override
    public LocalDate value(Date value) {
        //return LocalDate.of(value.getYear(),value.getMonth() + 1,value.getDay());
        return AnjiDateUtil.convertDateToLocalDate(value);
    }

    @Override
    public Date key(LocalDate value) {
        //return new Date(value.getYear(),value.getMonthValue() - 1,value.getDayOfMonth());
        return AnjiDateUtil.convertLocalDateToSQLDate(value);
        /*Одиниця віднімається тому, що у першій даті все рахнується від 0 до 11, а у другій від 1 до 12*/
    }
}
