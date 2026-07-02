package org.example;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;

public final class SecureFileUploadFactory {
    static final String PART_HEADER_SIZE_PROPERTY = "fuzzyservice.upload.maxPartHeaderSizeBytes";
    static final String FILE_SIZE_PROPERTY = "fuzzyservice.upload.maxFileSizeBytes";
    static final String REQUEST_SIZE_PROPERTY = "fuzzyservice.upload.maxRequestSizeBytes";
    static final String FILE_COUNT_PROPERTY = "fuzzyservice.upload.maxFileCount";

    static final int DEFAULT_PART_HEADER_SIZE_BYTES = 512;
    static final long DEFAULT_MAX_FILE_SIZE_BYTES = 10L * 1024 * 1024;
    static final long DEFAULT_MAX_REQUEST_SIZE_BYTES = 20L * 1024 * 1024;
    static final long DEFAULT_MAX_FILE_COUNT = 10L;

    private SecureFileUploadFactory() {
    }

    public static ServletFileUpload newServletFileUpload(File repository) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(repository);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setPartHeaderSizeMax(Integer.getInteger(PART_HEADER_SIZE_PROPERTY, DEFAULT_PART_HEADER_SIZE_BYTES));
        upload.setFileCountMax(Long.getLong(FILE_COUNT_PROPERTY, DEFAULT_MAX_FILE_COUNT));
        upload.setFileSizeMax(Long.getLong(FILE_SIZE_PROPERTY, DEFAULT_MAX_FILE_SIZE_BYTES));
        upload.setSizeMax(Long.getLong(REQUEST_SIZE_PROPERTY, DEFAULT_MAX_REQUEST_SIZE_BYTES));
        return upload;
    }
}
