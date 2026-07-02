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
        assertEquals(SecureFileUploadFactory.MAX_PART_HEADER_SIZE_BYTES, upload.getPartHeaderSizeMax());
        assertEquals(SecureFileUploadFactory.MAX_FILE_COUNT, upload.getFileCountMax());
        assertEquals(SecureFileUploadFactory.MAX_FILE_SIZE_BYTES, upload.getFileSizeMax());
        assertEquals(SecureFileUploadFactory.MAX_REQUEST_SIZE_BYTES, upload.getSizeMax());
    }
}
