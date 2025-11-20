package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public String getSalaryInfo(String[] employeeNames, String[] workLogData,
                                String dateFrom, String dateTo) {
        LocalDate startDate = LocalDate.parse(dateFrom, DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(dateTo, DATE_FORMATTER);
        int[] salaries = new int[employeeNames.length];

        for (String logEntry : workLogData) {
            String[] parts = logEntry.split(" ");
            LocalDate workDate = LocalDate.parse(parts[0], DATE_FORMATTER);

            if (!workDate.isBefore(startDate) && !workDate.isAfter(endDate)) {
                String employeeName = parts[1];
                int hoursWorked = Integer.parseInt(parts[2]);
                int hourlyRate = Integer.parseInt(parts[3]);

                for (int i = 0; i < employeeNames.length; i++) {
                    if (employeeNames[i].equals(employeeName)) {
                        salaries[i] += hoursWorked * hourlyRate;
                        break;
                    }
                }
            }
        }

        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("Report for period ")
                .append(dateFrom)
                .append(" - ")
                .append(dateTo);

        for (int i = 0; i < employeeNames.length; i++) {
            reportBuilder.append(LINE_SEPARATOR)
                    .append(employeeNames[i])
                    .append(" - ")
                    .append(salaries[i]);
        }

        return reportBuilder.toString();
    }
}

