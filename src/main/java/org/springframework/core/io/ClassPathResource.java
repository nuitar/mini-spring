package org.springframework.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ClassPathResource implements Resource {
    private final String classPath;

    public ClassPathResource(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(this.classPath);
        if(is == null){
            throw new FileNotFoundException(this.classPath + " file not found");
        }
        return is;
    }
}
