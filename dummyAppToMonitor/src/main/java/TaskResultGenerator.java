import task.JsonTaskResult;
import task.TaskResult;
import task.TaskResultType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskResultGenerator {

  AtomicInteger counter = new AtomicInteger();
  final String applicationId;
  final String group = "Medical";

  public enum Categories{
    Fracture,
    Flu,
    Cancer,
    Physical,
    Psychological,
    Unknown;
    public static Categories fromInteger(int nb) {
      switch( nb ) {
        case 0:
          return Fracture;
        case 1:
          return Flu;
        case 2:
          return Cancer;
        case 3:
          return Physical;
        case 4:
          return Psychological;
      }
      return Unknown;
    }
  }

  public TaskResultGenerator( String applicationId ){
    this.applicationId = applicationId;
  }

  public TaskResult generateInstance(){
    JsonTaskResult tr = new JsonTaskResult();
    tr.setApplicationId( applicationId );
    tr.setTaskName( Categories.fromInteger( getRandomBetweenRange( 0, Categories.values().length ) ).name() + counter.incrementAndGet() );
    tr.setTaskGroup( group );
    tr.setTaskResultType( getResultTypeFromInt( getRandomBetweenRange( 0, 10 ) ) );
    tr.setStartTime( LocalDateTime.now() );
    tr.setExecutionDuration( Duration.ofMinutes( getRandomBetweenRange( 1, 60 ) ) );
    return tr;
  }

  public static int getRandomBetweenRange(int min, int max){
    return (int) ( ( Math.random() * ( ( max-min ) + 1 ) ) + min);
  }

  public TaskResultType getResultTypeFromInt( int nb ){
    switch( nb ) {
      case 0:
        return TaskResultType.ERROR;
      case 1:
        return TaskResultType.WARNING;
      default:
        return TaskResultType.SUCCESS;
    }
  }
}
