package org.mentor.adapter;

import org.mentor.exception.OrderAdapterNotFoundException;

public class OrderAdapterService {


    public OrderAdapter getAdapter(String fileName) {
        String extension = getFileExtension(fileName);
        switch (extension) {
            case "csv":
                return new OrderCsvAdapter();
            case "txt":
                return new OrderCustomAdapter();
            default:
                throw new OrderAdapterNotFoundException("Не поддерживаемый формат файла");
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return fileName.substring(dotIndex + 1);
    }
}
