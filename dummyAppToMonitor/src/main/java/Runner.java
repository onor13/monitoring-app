import config.RabbitConfiguration;
import logProducer.LogProducer;
import logProducer.QueueLogProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import task.Application;
import task.JsonTaskResult;
import task.TaskResult;

public class Runner  {

  public Runner() { }

  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new AnnotationConfigApplicationContext( RabbitConfiguration.class);
    LogProducer lp = context.getBean( QueueLogProducer.class );
    ApplicationGenerator appGen = new ApplicationGenerator();
    Application app = appGen.generateInstance();
    TaskResultGenerator generator = new TaskResultGenerator( app );
    for( int i = 0; i < 5; i++ ){
      JsonTaskResult tr = generator.generateInstance();
      System.out.println( "Sending task: " + tr.getTaskName()  );
      lp.sendTaskResult( tr );
      Thread.sleep( 2000 );
    }
  }
}
