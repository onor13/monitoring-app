import com.google.common.flogger.FluentLogger;
import config.RabbitConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import producers.LogProducer;
import producers.QueueLogProducer;
import task.Application;
import task.JsonTaskResult;

public class Runner  {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  /***
   * <p>Project entry point. generates and sends tasksResults</p>
   * @param args command line args
   * @throws InterruptedException not clear when
   */
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
    LogProducer lp = context.getBean(QueueLogProducer.class);
    ApplicationGenerator appGen = new ApplicationGenerator();
    Application app = appGen.generateInstance();
    TaskResultGenerator generator = new TaskResultGenerator(app);
    int taskCounter = 1;
    while (true) {
      JsonTaskResult tr = generator.generateInstance();
      logger.atFine().log(String.format("Sending task %d: %s", taskCounter, tr.getTaskName()));
      lp.sendTaskResult(tr);
      Thread.sleep(1000);
      ++taskCounter;
    }
  }
}
