package com.springfulldemo.api.utils.enums;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public enum EnumDateFormat {
    DDMM(newThreadLocalSimpleDateFormat("dd/MM")),
    DD_MM(newThreadLocalSimpleDateFormat("MM-dd")),
    DDMMYY(newThreadLocalSimpleDateFormat("dd/MM/yy")),
    DDMMYYYY(newThreadLocalSimpleDateFormat("dd/MM/yyyy")),
    DDMMHHMM(newThreadLocalSimpleDateFormat("dd/MM HH:mm")),
    DDMMYYHHMM(newThreadLocalSimpleDateFormat("dd/MM/yy HH:mm")),
    DDMMYYYYHHMM(newThreadLocalSimpleDateFormat("dd/MM/yyyy HH:mm")),
    DDMMYYYYHHMMSS(newThreadLocalSimpleDateFormat("dd/MM/yyyy HH:mm:ss")),
    YYYYMMDD(newThreadLocalSimpleDateFormat("yyyy-MM-dd")),
    YYYYMMDDHHMMSS(newThreadLocalSimpleDateFormat("yyyy-MM-dd HH:mm:ss")),
    YYYYMMDDTHHMMSS(newThreadLocalSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")),
    MMYY(newThreadLocalSimpleDateFormat("MM/yy")),
    MMYYYY(newThreadLocalSimpleDateFormat("MM/yyyy")),
    DDMMMM(newThreadLocalSimpleDateFormat("dd 'de' MMMMM")),
    EXTENSO(newThreadLocalSimpleDateFormat("dd 'de' MMMMM 'de' yyyy")),
    FULLEXTENSO(newThreadLocalSimpleDateFormat("EEEE',' dd 'de' MMMMM 'de' yyyy 'as' HH:mm:ss")),
    HHMM(newThreadLocalSimpleDateFormat("HH:mm")),
    HHMMSS(newThreadLocalSimpleDateFormat("HHmmss"));

    private final ThreadLocal<SimpleDateFormat> formatter;

    private EnumDateFormat(final ThreadLocal formatter) {
        this.formatter = formatter;
    }

    private static ThreadLocal<SimpleDateFormat> newThreadLocalSimpleDateFormat(final String frmtString) {
        return ThreadLocal.withInitial(() -> {
            return new SimpleDateFormat(frmtString);
        });
    }

    public String format(final Date date) {
        return ((SimpleDateFormat)this.formatter.get()).format(date);
    }

    public SimpleDateFormat getFormat() {
        return (SimpleDateFormat)this.formatter.get();
    }

    public Date parse(final String source) throws ParseException {
        return ((SimpleDateFormat)this.formatter.get()).parse(source);
    }

    public Date parse(final String date, TimeZone timeZone) throws ParseException {
        SimpleDateFormat sdf = (SimpleDateFormat)this.formatter.get();
        sdf.setTimeZone(timeZone);
        return sdf.parse(date);
    }

    public String toPattern() {
        return ((SimpleDateFormat)this.formatter.get()).toPattern();
    }
}