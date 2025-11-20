package core.basesyntax;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalaryInfo {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public String getSalaryInfo(String[] employeeNames, String[] workLogData,
                                String dateFrom, String dateTo) {
        LocalDate startDate = LocalDate.parse(dateFrom.trim(), DATE_FORMATTER);
        LocalDate endDate = LocalDate.parse(dateTo.trim(), DATE_FORMATTER);
        int[] salaries = new int[employeeNames.length];

        for (String logEntry : workLogData) {
            String[] parts = logEntry.trim().split("\\s+");
            if (parts.length != 4) {
                continue;
            }
            LocalDate workDate;
            try {
                workDate = LocalDate.parse(parts[0].trim(), DATE_FORMATTER);
            } catch (Exception e) {
                continue;
            }
            if (!workDate.isBefore(startDate) && !workDate.isAfter(endDate)) {
                String employeeName = parts[1].trim();
                int hoursWorked;
                int hourlyRate;
                try {
                    hoursWorked = Integer.parseInt(parts[2].trim());
                    hourlyRate = Integer.parseInt(parts[3].trim());
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
                .append(dateTo.trim());

        for (int i = 0; i < employeeNames.length; i++) {
            reportBuilder.append(LINE_SEPARATOR)
                    .append(employeeNames[i].trim())
                    .append(" - ")
                    .append(salaries[i]);
        }

        return reportBuilder.toString();
    }
}

