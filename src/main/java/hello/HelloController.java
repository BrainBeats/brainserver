package hello;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "BrainBeatsServer";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/models")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        JSONObject json = new JSONObject();
        json.put("filename", file.getName());
        json.put("size", file.getSize());
        return json.toJSONString();
    }

}