package org.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultResourceLoader implements ResourceLoader {
    private static final String CLASSPATH_URL_PREFIX = "classpath:";

    @Override
    public Resource getResource(String path) {
        if (path.startsWith(CLASSPATH_URL_PREFIX))
            return new ClassPathResource(path.substring(CLASSPATH_URL_PREFIX.length()));
        else {

            try {
                // 先尝试当成URL处理
                URL url = new URL(path);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                return new FileSystemResource(path);
            }
        }

    }
}
