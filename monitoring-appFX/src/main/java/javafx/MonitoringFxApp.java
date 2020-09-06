package javafx;

import com.google.common.flogger.FluentLogger;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class MonitoringFxApp extends Application {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private transient ConfigurableApplicationContext applicationContext;

  @Override
  public void init() throws IOException {
    String javaVersion = System.getProperty("java.version");
    String javafxVersion = System.getProperty("javafx.version");
    logger.atFine().log("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

    ApplicationContextInitializer<GenericApplicationContext> initializer = ac -> {
      ac.registerBean(Application.class, () -> MonitoringFxApp.this);
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
    private static final long serialVersionUID = -3484158214116705816L;

    public Stage getStage() {
      return Stage.class.cast(getSource());
    }

    public StageReadyEvent(Stage source) {
      super(source);
      source.setMaximized(true);
    }
  }
}
