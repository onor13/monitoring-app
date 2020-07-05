import config.RabbitConfiguration;
import logProducer.LogProducer;
import logProducer.QueueLogProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import task.TaskResult;

public class Runner  {

  public Runner() { }

  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new AnnotationConfigApplicationContext( RabbitConfiguration.class);
    LogProducer lp = context.getBean( QueueLogProducer.class );
    TaskResultGenerator generator = new TaskResultGenerator( "Doctor123" );
    for( int i = 0; i < 3; i++ ){
      TaskResult tr = generator.generateInstance();
      System.out.println( "Sending task: " + tr.getTaskName()  );
      lp.sendTaskResult( tr );
      Thread.sleep( 2000 );
    }
  }
}
