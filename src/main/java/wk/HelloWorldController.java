package wk;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class HelloWorldController {

    @Autowired
    ServletContext context;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        System.out.println(context.getContextPath());
        return "Hello World Developer!!!";
    }
}