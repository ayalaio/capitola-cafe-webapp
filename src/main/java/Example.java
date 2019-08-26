
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class Example {

  @RequestMapping("/")
  String home() {
    String a = "<h1 style='font-size: 5em; text-align: center'>" + Message.message + "</h1>";
    a = a
        + "<img style='margin:auto; width:200px;display:block' src='https://www.kahlua.com/globalassets/images/cocktails/2018/opt/kahluadrinks_wide_coffee1.png'>";
    return a;
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Example.class, args);
  }

}
