package hello;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        //ApplicationContext ctx = SpringApplication.run(Application.class, args);
        String rootfile = System.getProperty("user.dir");
        File file = new File(rootfile + "/src/main/java/server/models/newFile.txt");
        System.out.println("CAN CREATE A FILE!");
        System.out.println(file.createNewFile());
    }
}