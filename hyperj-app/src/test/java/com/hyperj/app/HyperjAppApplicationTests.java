package com.hyperj.app;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


@SpringBootTest
class HyperjAppApplicationTests {

    @Test
    public void inputStream() throws IOException {
        File file = new File("src/main/resources/file/晖哥语录.txt");
        InputStream input = new FileInputStream(file);
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/file/晖哥语录.txt"));
        System.out.println(bytes.length);



        ClassPathResource classPathResource = new ClassPathResource("file/晖哥语录.txt");
        InputStream inputStream = classPathResource.getInputStream();
    }

}
