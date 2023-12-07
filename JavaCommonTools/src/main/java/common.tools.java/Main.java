package common.tools.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static impl.SQLToPlantUMLConverter.convertToPlantUML;

/**
 * @author zhangsongqi
 * @date 2023/12/07
 */

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "/Users/zhangsongqi/Downloads/sql.sql";
        //sql语句转换为plantUml语句
        sqlToPlantUML(inputFilePath);

    }


    private static void sqlToPlantUML(String inputFilePath){
        try {
            String sqlContent = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String plantUMLContent = convertToPlantUML(sqlContent);
            System.out.println(plantUMLContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}