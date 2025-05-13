package org.mentor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mentor.exception.ReadFileException;
import org.mentor.exception.WriteFileException;
import org.mentor.model.OrderReport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileOrderServiceTest {

    private FileOrderService fileOrderService = new FileOrderService();

    @TempDir
    Path tempDir;

    @Test
    void readShouldReturnsLinesIfFileExists() throws IOException, URISyntaxException {
        URL resource = getClass().getResource("/testFile.txt");
        Path testFile = Paths.get(resource.toURI());
        List<String> expectedLines = List.of("Line1", "Line2");
        Files.write(testFile, expectedLines);
        List<String> actualLines = fileOrderService.read(testFile.toString());
        assertEquals(expectedLines.size(), actualLines.size());
        assertIterableEquals(expectedLines, actualLines);
    }

    @Test
    void readShouldThrowsReadFileException() {
        assertThrows(ReadFileException.class,
                () -> fileOrderService.read("non_existent_file.txt"));
    }

    @Test
    void writeShouldCreateAndWriteFile() throws IOException {
        List<OrderReport> orderReports = List.of(
                new OrderReport("CompanyA", 111),
                new OrderReport("CompanyB", 222)
        );

        Path outputFile = tempDir.resolve("output.txt");
        try {
            fileOrderService.write(outputFile.toString(), orderReports);
            assertTrue(Files.exists(outputFile));
            List<String> actualLines = Files.readAllLines(outputFile);
            assertEquals(orderReports.size(), actualLines.size());
            for (int i = 0; i < orderReports.size(); i++) {
                assertEquals(orderReports.get(i).toString(), actualLines.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void writeShouldThrowsWriteFileException() {
        List<OrderReport> orderReports = List.of(new OrderReport("CompanyA", 111));
        assertThrows(WriteFileException.class,
                () -> fileOrderService.write("/non_writable_directory/output.txt", orderReports));
    }

    @Test
    void writeShouldThrowExceptionWhenNullPath() {
        List<OrderReport> orderReports = List.of(
                new OrderReport("CompanyA", 111),
                new OrderReport("CompanyB", 222)
        );
        String nullPath = null;
        assertThrows(NullPointerException.class,
                () -> fileOrderService.write(nullPath, orderReports));
    }

    @Test
    void writeShouldThrowExceptionWhenEmptyPath() {
        List<OrderReport> orderReports = List.of(
                new OrderReport("CompanyA", 111),
                new OrderReport("CompanyB", 222)
        );
        String emptyPath = "";
        assertThrows(WriteFileException.class,
                () -> fileOrderService.write(emptyPath, orderReports));
    }

    @Test
    void writeShouldThrowExceptionWhenNonExistentPath() {
        List<OrderReport> orderReports = List.of(
                new OrderReport("CompanyA", 111),
                new OrderReport("CompanyB", 222)
        );
        String nonExistentPath = "/non/existent/path/output.txt";
        assertThrows(WriteFileException.class,
                () -> fileOrderService.write(nonExistentPath, orderReports));
    }

    @Test
    void writeShouldThrowExceptionWhenNullOrderReports() {
        List<OrderReport> orderReports = null;
        String path = "output.txt";
        assertThrows(NullPointerException.class,
                () -> fileOrderService.write(path, orderReports));
    }

    @Test
    void writeShouldCreateFileAndWriteWithoutExceptionWhenEmptyOrderReports() {
        List<OrderReport> orderReports = List.of();
        String path = "output_emptyOrdersList.txt";
        fileOrderService.write(path, orderReports);
        Path filePath = Path.of(path);
        assertTrue(Files.exists(filePath));
    }
}