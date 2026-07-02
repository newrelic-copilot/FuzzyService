package org.example;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;

public final class SecureFileUploadFactory {
    static final int MAX_PART_HEADER_SIZE_BYTES = 512;
    static final long MAX_FILE_SIZE_BYTES = 10L * 1024 * 1024;
    static final long MAX_REQUEST_SIZE_BYTES = 20L * 1024 * 1024;
    static final long MAX_FILE_COUNT = 10L;

    private SecureFileUploadFactory() {
    }

    public static ServletFileUpload newServletFileUpload(File repository) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(repository);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setPartHeaderSizeMax(MAX_PART_HEADER_SIZE_BYTES);
        upload.setFileCountMax(MAX_FILE_COUNT);
        upload.setFileSizeMax(MAX_FILE_SIZE_BYTES);
        upload.setSizeMax(MAX_REQUEST_SIZE_BYTES);
        return upload;
    }
}
