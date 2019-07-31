package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.*;
// import org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    public String hasd(String value) {
        if (value == "" || value == " " || value == null) {
            return "Value is empty";
        } else {
            String result = DigestUtils.sha256Hex(value);
            return result;
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    @Consumes(MediaType.APPLICATION_JSON)
    // @URL http://localhost:8080/test?value=abcd&num=2
    // @desc: value - you can insert a value to hash it. num 1 = hash, num 2 = test case
    public Response createJSON(@RequestParam String value, String num) {
        String result;

        if (num.equals("2")) {
            String testCase[] = new String[4];
            testCase[0] = "abc";
            testCase[1] = "abc123";
            testCase[2] = "a23!@";
            testCase[3] = " ";

            System.out.println("--------- Start Test Case ---------------");
            JSONObject obj = new JSONObject();
            JSONArray list = new JSONArray();

            for (int i = 0; i < testCase.length; i++) {
                result = hasd(testCase[i]);
                if (result != "Value is empty") {
                    System.out.println("----- " + i + " - test case success --------");
                    obj.put("output: ", result);
                    list.add("Success " + i + " - " + result);
                    System.out.println(obj);
                } else {
                    System.out.println("----- " + i + " - test case fail --------");
                    obj.put("messages: ", result);
                    list.add("Fail: " + i + " - " + result);
                    System.out.println(obj);
                }
            }
            System.out.println("--------- End Test Case ---------------");
            return Response.status(200).entity(list).build();

        } else if (num == " " || num == "" || num == null) {
            return Response.status(400).entity("Num is empty").build();
        } else if (num.equals("1")){
            result = hasd(value);
            if (result != "Value is empty") {
                return Response.status(200).entity(result).build();
            } else {
                return Response.status(400).entity(result).build();
            }
        }else{
            return Response.status(400).entity("Num format error").build();
        }

    }
}
