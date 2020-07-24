package javafx;

import com.google.common.flogger.FluentLogger;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StageListener implements ApplicationListener<MonitoringFXApp.StageReadyEvent> {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private final String applicationTitle;
  private final Resource           fxml;
  private final ApplicationContext applicationContext;

  public StageListener(
      @Value("${spring.application.ui.title}") String applicationTitle,
      @Value("classpath:/fxml/main.fxml") Resource resource,
      ApplicationContext ac) {
    this.applicationTitle = applicationTitle;
    this.fxml = resource;
    this.applicationContext = ac;
  }

  @PostConstruct
  private void postConstruct() {
    logger.atFine().log("PostConstruct of " + this.getClass().getSimpleName());
  }

  @Override
  public void onApplicationEvent(MonitoringFXApp.StageReadyEvent event) {
    try {
      Stage stage = event.getStage();
      URL url = this.fxml.getURL();
      FXMLLoader fxmlLoader = new FXMLLoader(url);
      fxmlLoader.setControllerFactory(applicationContext::getBean);
      Parent root = fxmlLoader.load();
      Scene scene = new Scene(root, 800, 500);
      stage.setScene(scene);
      stage.setTitle(this.applicationTitle);
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
      new RuntimeException("failed to initalize" + e.toString());
    }
  }

}
