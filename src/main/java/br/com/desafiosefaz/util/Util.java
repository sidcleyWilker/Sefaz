package br.com.desafiosefaz.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {

	public static LocalDate converteStringToDate(String data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    return  LocalDate.parse(data,formatter);
	}
}
