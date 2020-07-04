package javafx;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "javafx")
@SpringBootApplication
public class MonitoringApplication  {

  public static void main(String[] args) {
    Application.launch( MonitoringFXApp.class, args);
  }
}
