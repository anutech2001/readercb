package hello;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class BookService {
	private static Logger log = LoggerFactory.getLogger(BookService.class);
	
  private final RestTemplate restTemplate;

  public BookService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  @HystrixCommand(fallbackMethod = "reliable")
  public String readingList() {
	log.info("Searching for recommended books");
    URI uri = URI.create("http://bookstore:8011/recommended");
    String result = this.restTemplate.getForObject(uri, String.class);
    log.info("Return a recommended book list");
    return result; 
  }

  public String reliable() {
    log.info("Can not connect Bookstore service, uses a default recommended book list");
    return "Cloud Native Java (O'Reilly)";
  }

}