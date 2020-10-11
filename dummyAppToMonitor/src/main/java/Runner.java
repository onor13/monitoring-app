import com.google.common.flogger.FluentLogger;
import config.RabbitConfiguration;
import java.util.Random;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import producers.LogProducer;
import producers.QueueLogProducer;
import task.Application;
import task.JsonTaskResult;

public class Runner  {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final ApplicationGenerator appGen = new ApplicationGenerator();
  private static final TaskResultGenerator generator1 = new TaskResultGenerator(appGen.generateInstance());
  private static final TaskResultGenerator generator2 = new TaskResultGenerator(appGen.generateInstance());

  /***
   * <p>Project entry point. generates and sends tasksResults</p>
   * @param args command line args
   * @throws InterruptedException not clear when
   */
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfiguration.class);
    LogProducer lp = context.getBean(QueueLogProducer.class);

    Application app2 = appGen.generateInstance();
    TaskResultGenerator generator2 = new TaskResultGenerator(app2);
    int taskCounter = 1;
    while (true) {
      JsonTaskResult tr = generateTask();
      logger.atFine().log(String.format("Sending task %d: %s", taskCounter, tr.getTaskName()));
      lp.sendTaskResult(tr);
      Thread.sleep(1000);
      ++taskCounter;
    }
  }

  public static JsonTaskResult generateTask(){
     int random = getRandomNumberUsingNextInt(0, 10);
     return random > 5 ? generator1.generateInstance() : generator2.generateInstance();
  }

  private static int getRandomNumberUsingNextInt(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min) + min;
  }
}
