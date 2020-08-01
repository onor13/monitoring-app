package javafx;

import config.RabbitConfiguration;
import distributors.TaskDataDistributor;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import storage.DbTasksResultsStorage;
import storage.TasksResultsStorage;
import task.config.DbConfig;

@ComponentScan(
    basePackages = {
      "javafx",
      "queue",
      "task"},
    basePackageClasses = {
      DbConfig.class, TaskDataDistributor.class})
@SpringBootApplication
@Import(RabbitConfiguration.class)
@EnableScheduling
public class MonitoringApplication  {

  @Bean
  TasksResultsStorage tasksResultsStorage() {
    TasksResultsStorage storage = new DbTasksResultsStorage();
    return storage;
  }

  public static void main(String[] args) {
    Application.launch(MonitoringFxApp.class, args);
  }

}
