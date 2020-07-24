
import task.Application;
import task.JsonTaskResult;
import task.TaskResultType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskResultGenerator {

  final AtomicInteger counter = new AtomicInteger();
  final Application app;
  final String group = "Medical";

  public enum Categories {
    Fracture,
    Flu,
    Cancer,
    Physical,
    Psychological,
    Unknown;

    public static Categories fromInteger(int nb) {
      switch (nb) {
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

  public TaskResultGenerator(Application application) {
    this.app = application;
  }

  public JsonTaskResult generateInstance() {
    JsonTaskResult tr = new JsonTaskResult(app);
    String taskName = Categories.fromInteger(getRandomBetweenRange(0, Categories.values().length)).name()
        + counter.incrementAndGet();
    tr.setTaskName(taskName);
    tr.setTaskGroup(group);
    tr.setTaskResultType(getResultTypeFromInt(getRandomBetweenRange(0, 10)));
    tr.setTaskStartTime(LocalDateTime.now());
    tr.setTaskExecutionDuration(Duration.ofMinutes(getRandomBetweenRange(1, 60)));
    return tr;
  }

  public static int getRandomBetweenRange(int min, int max) {
    return (int) ((Math.random() * ((max - min) + 1)) + min);
  }

  public TaskResultType getResultTypeFromInt(int nb) {
    switch (nb) {
      case 0:
        return TaskResultType.ERROR;
      case 1:
        return TaskResultType.WARNING;
      default:
        return TaskResultType.SUCCESS;
    }
  }
}
