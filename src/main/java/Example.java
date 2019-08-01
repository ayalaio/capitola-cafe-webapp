import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

  @RequestMapping("/")
  String home() {
    return "<h1 style='font-size: 5em; text-align: center'>" + message() + "</h1>";
  }

  String message() {
    return "Hello and done for k8s!";
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Example.class, args);
  }

}
