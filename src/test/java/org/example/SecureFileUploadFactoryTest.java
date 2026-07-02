package org.example;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecureFileUploadFactoryTest {
    @Test
    void createsUploadWithSecureLimits() {
        File repository = new File(System.getProperty("java.io.tmpdir"));

        ServletFileUpload upload = SecureFileUploadFactory.newServletFileUpload(repository);

        assertTrue(upload.getFileItemFactory() instanceof DiskFileItemFactory);
        DiskFileItemFactory factory = (DiskFileItemFactory) upload.getFileItemFactory();
        assertEquals(repository, factory.getRepository());
        assertEquals(SecureFileUploadFactory.DEFAULT_PART_HEADER_SIZE_BYTES, upload.getPartHeaderSizeMax());
        assertEquals(SecureFileUploadFactory.DEFAULT_MAX_FILE_COUNT, upload.getFileCountMax());
        assertEquals(SecureFileUploadFactory.DEFAULT_MAX_FILE_SIZE_BYTES, upload.getFileSizeMax());
        assertEquals(SecureFileUploadFactory.DEFAULT_MAX_REQUEST_SIZE_BYTES, upload.getSizeMax());
    }

    @Test
    void allowsConfiguredUploadLimits() {
        File repository = new File(System.getProperty("java.io.tmpdir"));

        System.setProperty(SecureFileUploadFactory.PART_HEADER_SIZE_PROPERTY, "768");
        System.setProperty(SecureFileUploadFactory.FILE_COUNT_PROPERTY, "4");
        System.setProperty(SecureFileUploadFactory.FILE_SIZE_PROPERTY, "2048");
        System.setProperty(SecureFileUploadFactory.REQUEST_SIZE_PROPERTY, "4096");

        try {
            ServletFileUpload upload = SecureFileUploadFactory.newServletFileUpload(repository);

            assertEquals(768, upload.getPartHeaderSizeMax());
            assertEquals(4L, upload.getFileCountMax());
            assertEquals(2048L, upload.getFileSizeMax());
            assertEquals(4096L, upload.getSizeMax());
        } finally {
            System.clearProperty(SecureFileUploadFactory.PART_HEADER_SIZE_PROPERTY);
            System.clearProperty(SecureFileUploadFactory.FILE_COUNT_PROPERTY);
            System.clearProperty(SecureFileUploadFactory.FILE_SIZE_PROPERTY);
            System.clearProperty(SecureFileUploadFactory.REQUEST_SIZE_PROPERTY);
        }
    }
}
