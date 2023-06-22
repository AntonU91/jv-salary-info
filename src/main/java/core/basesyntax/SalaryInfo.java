package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalaryInfo {
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getSalaryInfo(String[] names, String[] data, String dateFrom, String dateTo) {
        LocalDate from = LocalDate.parse(dateFrom, DATE_TIME_FORMATTER);
        LocalDate to = LocalDate.parse(dateTo, DATE_TIME_FORMATTER);
        List<String> dataList = new ArrayList<>(new ArrayList<>(Arrays.asList(data)));
        int[] countedSalary = countSalary(names, from, to, dataList);
        StringBuilder stringBuilder = new StringBuilder();
        appendReportHeader(dateFrom, dateTo, stringBuilder);
        return appendSalaryInfo(names, stringBuilder, countedSalary);
    }

    private void appendReportHeader(String dateFrom, String dateTo, StringBuilder stringBuilder) {
        stringBuilder.append(String.format("Report for period %s - %s", dateFrom, dateTo))
                .append(System.lineSeparator());
    }

    private int[] countSalary(String[] names, LocalDate from, LocalDate to, List<String> list) {
        int[] countedSalary = new int[names.length];
        LocalDate dateToCompare;
        String[] dataEntry;
        for (String temp : list) {
            dataEntry = temp.split("\\s");
            dateToCompare = LocalDate.parse(dataEntry[0], DATE_TIME_FORMATTER);
            if (isDateInRange(from, to, dateToCompare)) {
                for (int j = 0; j < names.length; j++) {
                    if (names[j].contains(dataEntry[1])) {
                        countedSalary[j] += multiplyHoursOnRate(dataEntry);
                    }
                }
            }
        }
        return countedSalary;
    }

    private int multiplyHoursOnRate(String[] strings) {
        int hours = Integer.parseInt(strings[2]);
        int rate = Integer.parseInt(strings[3]);
        return hours * rate;
    }

    private boolean isDateInRange(LocalDate from, LocalDate to, LocalDate dateToCompare) {
        return dateToCompare.compareTo(from) >= 0 && dateToCompare.compareTo(to) <= 0;
    }

    private String appendSalaryInfo(String[] names, StringBuilder stringBuilder,
                                    int[] countedSalary) {
        for (int i = 0; i < names.length; i++) {
            stringBuilder.append(names[i]).append(" - ")
                    .append(countedSalary[i]).append(System.lineSeparator());
        }
        return stringBuilder.toString().trim();
    }
}

