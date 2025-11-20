package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final int DATE_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int HOURS_INDEX = 2;
    private static final int RATE_INDEX = 3;
    private static final int EXPECTED_DATA_PARTS_COUNT = 4;

    public String getSalaryInfo(String[] employeeNames, String[] workLogData,
                                String dateFrom, String dateTo) {
        LocalDate startDate = LocalDate.parse(dateFrom.trim(), DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(dateTo.trim(), DATE_FORMATTER);
        int[] salaries = new int[employeeNames.length];

        for (String logEntry : workLogData) {
            if (logEntry == null || logEntry.trim().isEmpty()) {
                continue;
            }
            String[] parts = logEntry.trim().split("\\s+");
            if (parts.length != EXPECTED_DATA_PARTS_COUNT) {
                continue;
            }
            LocalDate workDate;
            try {
                workDate = LocalDate.parse(parts[DATE_INDEX].trim(), DATE_FORMATTER);
            } catch (Exception e) {
                continue;
            }
            if (!workDate.isBefore(startDate) && !workDate.isAfter(endDate)) {
                String employeeName = parts[NAME_INDEX].trim();
                int hoursWorked;
                int hourlyRate;
                try {
                    hoursWorked = Integer.parseInt(parts[HOURS_INDEX].trim());
                    hourlyRate = Integer.parseInt(parts[RATE_INDEX].trim());
                } catch (NumberFormatException e) {
                    continue;
                }
                for (int i = 0; i < employeeNames.length; i++) {
                    if (employeeNames[i].trim().equals(employeeName)) {
                        salaries[i] += hoursWorked * hourlyRate;
                        break;
                    }
                }
            }
        }

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Report for period ")
                .append(dateFrom.trim())
                .append(" - ")
                .append(dateTo.trim())
                .append(LINE_SEPARATOR);

        for (int i = 0; i < employeeNames.length; i++) {
            reportBuilder.append(employeeNames[i].trim())
                    .append(" - ")
                    .append(salaries[i]);
            if (i < employeeNames.length - 1) {
                reportBuilder.append(LINE_SEPARATOR);
            }
        }

        return reportBuilder.toString();
    }
}
