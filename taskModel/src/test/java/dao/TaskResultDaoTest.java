package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import config.TestDBConfig;
import config.TestDatabaseInitializer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import task.TaskResult;
import task.TaskResultType;
import task.dao.ApplicationDao;
import task.dao.TaskResultDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
public class TaskResultDaoTest {
  private GenericApplicationContext ctx;
  private TaskResultDao  taskResultDao;
  private ApplicationDao appDao;

  @BeforeEach
  public void setUp(){
    ctx = new AnnotationConfigApplicationContext( TestDBConfig.class );
    taskResultDao = ctx.getBean(TaskResultDao.class);
    appDao = ctx.getBean(ApplicationDao.class);
    assertNotNull(taskResultDao, "taskResultDao bean");
  }

  @Test
  public void testFindAll(){
    List<TaskResultEntity> taskResultEntities = taskResultDao.findAll();
    assertEquals( taskResultEntities.size(), TestDatabaseInitializer.totalNumberOfTasks,
        "all tasks results should be present");
    for( TaskResultEntity tre : taskResultEntities ){
      //make sure that a join with Application table was applied
      assertNotNull(tre.getApplicationStartTime(),  "applicationStartTime != null");
    }
  }

  @Test
  public void testFindByID(){
    TaskResult tr = taskResultDao.findById(2L);
    assertNotNull(tr, "taskResult find result");
  }

  @Test
  public void testFindByAppIdTaskNameTaskStartTime(){
    String taskName = TestDatabaseInitializer.firstDoctorAppTaskName;
    LocalDateTime taskStartTime = TestDatabaseInitializer.firstTaskStartTime;
    TaskResultEntity tre = taskResultDao.find( TestDatabaseInitializer.doctorAppName, taskName, taskStartTime );
    assertNotNull(tre, "taskResult find result");
  }

  @Test
  public void testInsert(){
    ApplicationEntity app = appDao.findByName(TestDatabaseInitializer.doctorAppName);
    TaskResultEntity trt = new TaskResultEntity();
    String expectedTaskName = "testTask";
    trt.setTaskName( expectedTaskName );
    trt.setTaskGroup( "medical" );
    trt.setTaskResultType( TaskResultType.SUCCESS );
    LocalDateTime expectedTime = LocalDateTime.now();
    trt.setTaskStartTime( expectedTime );
    trt.setTaskExecutionDuration( Duration.ofMinutes( 6 ) );
    trt.setApplication( app );
    app.addTaskResult( trt );
    taskResultDao.save( trt );

    List<TaskResultEntity> trEntities = taskResultDao.findAll();
    assertFalse(trEntities.isEmpty(), "taskResultEntity");
    Optional<TaskResultEntity> dbTaskResultEntities = trEntities.stream().filter(
        tr -> tr.getTaskName().equals(expectedTaskName)).findFirst();
    assertTrue(dbTaskResultEntities.isPresent(), "insert of TaskResultEntity");
    assertNotNull(dbTaskResultEntities.get().getApplicationStartTime(),
        "application of the insert TaskResultEntity");
  }

  @Test
  public void testUpdate(){
    TaskResultEntity tre = taskResultDao.findById(2L);
    assertNotNull(tre, "taskResultEntity");
    String oldTaskName = tre.getTaskName();

    String newTaskName = "new" + oldTaskName;
    tre.setTaskName(newTaskName);

    taskResultDao.save( tre );
    TaskResultEntity updatedTaskResultEntity = taskResultDao.findById(2L);
    assertEquals(newTaskName, updatedTaskResultEntity.getTaskName(), "update taskTame");
  }
}
