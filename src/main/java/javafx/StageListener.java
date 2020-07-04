package javafx;

import javafx.MonitoringFXApp;
import javafx.controllers.TaskResultsTableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import task.TaskResult;
import task.TaskResultType;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StageListener implements ApplicationListener<MonitoringFXApp.StageReadyEvent> {

  static Logger log = LoggerFactory.getLogger( MonitoringFXApp.class );

  private final String applicationTitle;
  private final Resource           fxml;
  private final ApplicationContext applicationContext;

  public StageListener(
      @Value("${spring.application.ui.title}") String applicationTitle,
      @Value( "classpath:/fxml/taskResultsTable.fxml" ) Resource resource,
      ApplicationContext ac ){
    this.applicationTitle = applicationTitle;
    this.fxml = resource;
    this.applicationContext = ac;
  }

  @PostConstruct
  private void postConstruct() {
    log.debug( "PostConstruct of " + this.getClass().getSimpleName() );
  }

  @Override
  public void onApplicationEvent( MonitoringFXApp.StageReadyEvent event ) {
    try {
      Stage stage = event.getStage();
      URL url = this.fxml.getURL();
      FXMLLoader fxmlLoader = new FXMLLoader( url );
      fxmlLoader.setControllerFactory( applicationContext::getBean );
      Parent root = fxmlLoader.load();
      Scene scene = new Scene( root, 800, 500 );
      stage.setScene( scene );
      stage.setTitle( this.applicationTitle );
      stage.show();
      populateWithDummyData();
    }
    catch ( IOException e ) {
      e.printStackTrace();
      new RuntimeException( "failed to initalize"  + e.toString() );
    }
  }

  protected void populateWithDummyData(){
     TaskResultsTableController table = applicationContext.getBean( TaskResultsTableController.class );
    table.addTaskResult( new TaskResult() {
      @Override public String getApplicationId() {
        return "Id122444";
      }

      @Override public String getTaskName() {
        return "cancer check";
      }

      @Override public String getTaskGroup() {
        return "medical";
      }

      @Override public TaskResultType getTaskResultType() {
        return TaskResultType.SUCCESS;
      }

      @Override public Instant getStartTime() {
        return Instant.now();
      }

      @Override public Duration getExecutionDuration() {
        return Duration.ofMinutes( 65 );
      }
    } );
    table.addTaskResult( new TaskResult() {
      @Override public String getApplicationId() {
        return "Id1254844";
      }

      @Override public String getTaskName() {
        return "fractures check";
      }

      @Override public String getTaskGroup() {
        return "medical";
      }

      @Override public TaskResultType getTaskResultType() {
        return TaskResultType.ERROR;
      }

      @Override public Instant getStartTime() {
        return Instant.now();
      }

      @Override public Duration getExecutionDuration() {
        return Duration.ofMinutes( 15 );
      }
    } );
  }
}
