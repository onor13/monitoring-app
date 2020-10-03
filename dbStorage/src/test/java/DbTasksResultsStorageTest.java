import config.TestDBConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import storage.DbTasksResultsStorage;
import storage.TasksResultsStorage;
import task.dao.ApplicationDao;
import task.dao.TaskResultDao;

@ContextConfiguration
public class DbTasksResultsStorageTest extends TasksResultsStorageTest {

  final transient DbTasksResultsStorage storage = new DbTasksResultsStorage();

  @BeforeEach
  public void setUp(){
    ApplicationContext ctx = new AnnotationConfigApplicationContext(TestDBConfig.class);
    TaskResultDao taskResultDao = ctx.getBean(TaskResultDao.class);
    ApplicationDao appDao = ctx.getBean(ApplicationDao.class);
    storage.setAppDao(appDao);
    storage.setTaskResultDao(taskResultDao);
  }

  @Override
  protected TasksResultsStorage createStorage() {
    return storage;
  }
}
