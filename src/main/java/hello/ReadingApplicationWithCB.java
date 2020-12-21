package hello;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableCircuitBreaker
@RestController
@EnableHystrixDashboard
@SpringBootApplication
public class ReadingApplicationWithCB {
  private static Logger log = LoggerFactory.getLogger(ReadingApplicationWithCB.class);
  @Autowired
  private BookService bookService;

  @Bean
  public RestTemplate rest(RestTemplateBuilder builder) {
    return builder.build();
  }

  @RequestMapping(value = "/to-read", method = RequestMethod.GET)
  public String toRead(@RequestHeader Map<String, String> headers) {
    log.info("get request for recommended books.");
    headers.forEach((key, value) -> {
      log.info(String.format("Header '%s' = %s", key, value));
    });
    return bookService.readingList();
  }

  public static void main(String[] args) {
    SpringApplication.run(ReadingApplicationWithCB.class, args);
  }
}