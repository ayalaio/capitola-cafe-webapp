import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

  @RequestMapping("/")
  String home() {
    return "<h1>Hello and welcome!</h1>";
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Example.class, args);
  }

}
