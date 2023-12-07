package impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLToPlantUMLConverter {

    private static final Pattern CREATE_TABLE_PATTERN = Pattern.compile(
        "CREATE TABLE `(\\w+)` \\(([^;]+);", Pattern.CASE_INSENSITIVE);
    private static final Pattern COLUMN_PATTERN = Pattern.compile(
        "`(\\w+)` (\\w+\\(?\\d*\\)?) \\(\\s*\\d+(?:\\s*,\\s*\\d+)?\\s*\\) (DEFAULT NULL|NOT NULL|DEFAULT ''|DEFAULT '"
            + "([^']+)' )? ?(?:COMMENT '([^']+)')?,?",
        Pattern.CASE_INSENSITIVE);


    public static String convertToPlantUML(String sqlContent) {
        StringBuilder plantUML = new StringBuilder();
        plantUML.append("@startuml\n");
        Matcher tableMatcher = CREATE_TABLE_PATTERN.matcher(sqlContent);

        while (tableMatcher.find()) {
            String tableName = tableMatcher.group(1);
            String columnsPart = tableMatcher.group(2);

            plantUML.append("entity ")
                .append(tableName)
                .append(" {\n");
            Matcher columnMatcher = COLUMN_PATTERN.matcher(columnsPart);
            while (columnMatcher.find()) {
                System.out.println(columnMatcher.group());
                String columnName = columnMatcher.group(1);
                String columnType = columnMatcher.group(2);
                boolean isNotNull = columnMatcher.group(3) != null && "NOT NULL".equalsIgnoreCase(
                    columnMatcher.group(3));
                String columnComment = columnMatcher.group(4) != null ? columnMatcher.group(4) : (columnMatcher.group(5)
                    != null ? columnMatcher.group(5) : "");

                plantUML.append("    ");
                if (isNotNull) {
                    plantUML.append("*");
                }
                plantUML.append(columnName)
                    .append(" : ")
                    .append(columnType)
                    .append(" ")
                    .append(columnComment)
                    .append("\n");
            }
            plantUML.append("}\n\n");
        }

        plantUML.append("@enduml\n");
        return plantUML.toString();
    }
}
