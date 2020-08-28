package javafx;

import config.RabbitConfiguration;
import distributors.TaskDataDistributor;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(
    basePackages = {"javafx", "config", "queue", "task"},
    basePackageClasses = {TaskDataDistributor.class})
@SpringBootApplication
@Import(RabbitConfiguration.class)
@EnableScheduling
public class MonitoringApplication  {

  public static void main(String[] args) {
    Application.launch(MonitoringFxApp.class, args);
  }

}
