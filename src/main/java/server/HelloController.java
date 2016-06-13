package server;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class HelloController {

    private File modelHard;
    private File modelCalm;

    @RequestMapping("/")
    public String index() {
        return "BrainBeatsServer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/models/hard")
    public String uploadHard(@RequestParam("file") MultipartFile file) {
        try {
            modelHard = convert(file);
        } catch (IOException e) {
            return e.getMessage();
        }
        JSONObject json = createResponse(file, "hard");
        return json.toJSONString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/models/calm")
    public String uploadCalm(@RequestParam("file") MultipartFile file) {
        try {
            modelCalm = convert(file);
        } catch (IOException e) {
            return e.getMessage();
        }
        JSONObject json = createResponse(file, "calm");
        return json.toJSONString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/models")
    public String getFilenames() {
        if (modelCalm == null || modelHard == null) {
            return "No models on server";
        } else {
            JSONObject json = new JSONObject();
            json.put("calm", modelCalm.getName());
            json.put("hard", modelHard.getName());
            return json.toJSONString();
        }
    }

    private JSONObject createResponse(@RequestParam("file") MultipartFile file, String hard) {
        JSONObject json = new JSONObject();
        json.put("filename", file.getOriginalFilename());
        json.put("size", file.getSize());
        json.put("", hard);
        return json;
    }

    public File convert(MultipartFile file) throws IOException {
        String pathToModelDir = "src/main/java/server/models/";
        File convFile = new File(pathToModelDir + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}