package org.springframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystemResource implements Resource {
    private String path;

    public FileSystemResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            Path p = new File(this.path).toPath();
            return Files.newInputStream(p);
        } catch (IOException ex) {
            throw new FileNotFoundException(ex.getMessage());
        }
    }
}
