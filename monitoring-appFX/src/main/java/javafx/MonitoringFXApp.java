package javafx;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

public class MonitoringFXApp
    extends Application {

  static Logger log = LoggerFactory.getLogger(MonitoringFXApp.class);

  private ConfigurableApplicationContext applicationContext;

  @Override
  public void init() throws IOException {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    log.debug("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
      ac.registerBean(Application.class, () -> MonitoringFXApp.this);
      ac.registerBean(Parameters.class, () -> getParameters());
      ac.registerBean(HostServices.class, () -> getHostServices());
    };

    SpringApplicationBuilder applicationBuilder =
        new SpringApplicationBuilder(MonitoringApplication.class).initializers(initializer);
    String[] args = getParameters().getRaw().stream().toArray(String[]::new);

    this.applicationContext = applicationBuilder.run(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.applicationContext.publishEvent(new StageReadyEvent(primaryStage));
  }

  @Override
  public void stop() {
    applicationContext.close();
    Platform.exit();
  }


  class StageReadyEvent extends ApplicationEvent {
    public Stage getStage() {
      return Stage.class.cast(getSource());
    }

    public StageReadyEvent(Stage source) {
      super(source);
    }
  }
}
