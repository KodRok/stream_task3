package org.mentor.service;

import org.mentor.exception.OrderAdapterNotFoundException;
import org.mentor.exception.ReadFileException;
import org.mentor.exception.WriteFileException;
import org.mentor.model.OrderReport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOrderService {

    public List<String> read(String inputFilename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw  new ReadFileException("Считать файл не удалось: " + e.getMessage());
        }
        return lines;
    }

    public void write(String outputFilename, List<OrderReport> orderReports) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            for (OrderReport orderReport: orderReports) {
                writer.write(orderReport.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw  new WriteFileException("Записать файл не удалось: " + e.getMessage());
        }
    }
}