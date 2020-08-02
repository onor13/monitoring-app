import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import task.Application;
import task.JsonTaskResult;
import task.TaskResultType;

public class TaskResultGenerator {

  static final AtomicInteger counter = new AtomicInteger();
  final transient Application app;

  public enum Group {
    Fracture,
    Flu,
    Cancer,
    Physical,
    Psychological,
    Unknown;

    /***
     * <p>Creates category from integer.</p>
     * @param nb integer used for choosing a category
     * @return one of the existing Categories
     */
    public static Group fromInteger(int nb) {
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
        default:
          return Unknown;
      }
    }
  }

  public TaskResultGenerator(Application application) {
    this.app = application;
  }

  public JsonTaskResult generateInstance() {
    JsonTaskResult tr = new JsonTaskResult(app);
    String group = Group.fromInteger(getRandomBetweenRange(0, Group.values().length)).name();
    tr.setTaskName("give medicine for " + group);
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
