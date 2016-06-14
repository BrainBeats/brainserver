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
public class BrainController {

    @RequestMapping("/")
    public String index() {
        return "BrainBeatsServer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/models/hard")
    public String uploadHard(@RequestParam("file") MultipartFile file) {
        try {
            write(file, Weka.HARD + ".csv");
        } catch (IOException e) {
            return e.getMessage();
        }
        JSONObject json = createResponse(file, Weka.HARD);
        return json.toJSONString();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/models/calm")
    public String uploadCalm(@RequestParam("file") MultipartFile file) {
        try {
            write(file, Weka.CALM + ".csv");
        } catch (IOException e) {
            return e.getMessage();
        }
        JSONObject json = createResponse(file, Weka.CALM);
        return json.toJSONString();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/models/build")
    public String buildmodels() {
        if (modelMissing()) {
            return "Model missing";
        }
        return Weka.buildModels();
    }

    /**
     *Compares test csv to models.
     *
     * @param file that contains test data
     */
    @RequestMapping(method = RequestMethod.POST, value = "/models/testmodel")
    public String testModel(@RequestParam("file") MultipartFile file) {
        if (modelMissing()) {
            return "Model(s) missing";
        }
        try {
            write(file, Weka.TEST_MODEL + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Weka.compareToModel();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/models")
    public String getFilenames() {
        if (modelMissing()) {
            return "No models on server";
        } else {
            JSONObject json = new JSONObject();
            json.put("calm", Weka.CALM);
            json.put("hard", Weka.HARD);
            return json.toJSONString();
        }
    }

    private boolean modelMissing() {
        return !new File(Weka.MODEL_DIR_PATH + Weka.HARD + Weka.FILETYPE).exists() ||
                !new File(Weka.MODEL_DIR_PATH + Weka.CALM + Weka.FILETYPE).exists();
    }

    private JSONObject createResponse(@RequestParam("file") MultipartFile file, String hard) {
        JSONObject json = new JSONObject();
        json.put("filename", file.getOriginalFilename());
        json.put("size", file.getSize());
        json.put("", hard);
        return json;
    }

    public File write(MultipartFile file, String filename) throws IOException {
        File convFile = new File(Weka.MODEL_DIR_PATH + filename);
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}