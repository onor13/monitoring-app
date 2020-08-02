package db;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import task.TaskResult;
import task.TaskResultType;
import task.dao.ApplicationDao;
import task.dao.TaskResultDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
public class TaskResultDaoTest {
  private GenericApplicationContext ctx;
  private TaskResultDao  taskResultDao;
  private ApplicationDao appDao;

  @Before
  public void setUp(){
    ctx = new AnnotationConfigApplicationContext( TestDBConfig.class );
    taskResultDao = ctx.getBean(TaskResultDao.class);
    appDao = ctx.getBean(ApplicationDao.class);
    assertNotNull( "ApplicationDao", taskResultDao );
  }

  @Test
  public void testFindAll(){
    List<TaskResultEntity> taskResultEntities = taskResultDao.findAll();
    assertFalse("TaskResultEntities size", taskResultEntities.isEmpty());
    for( TaskResultEntity tre : taskResultEntities ){
      //make sure that a join with Application table was applied
      assertNotNull( "TaskResultEntity->applicationStartTime", tre.getApplicationStartTime() );
    }
  }

  @Test
  public void testFindByID(){
    TaskResult tr = taskResultDao.findById(2L);
    assertNotNull("TaskResult", tr);
  }

  @Test
  public void testFindByAppIdTaskNameTaskStartTime(){
    String taskName = DBInitializer.firstTaskName;
    LocalDateTime taskStartTime = DBInitializer.firstTaskStartTime;
    TaskResultEntity tre = taskResultDao.find( DBInitializer.appName, taskName, taskStartTime );
    assertNotNull("TaskResultEntity", tre);
  }

  @Test
  public void testInsert(){
    ApplicationEntity app = appDao.findByName(DBInitializer.appName);
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
    assertFalse("TaskResultEntity size", trEntities.isEmpty() );
    Optional<TaskResultEntity> dbTaskResultEntities = trEntities.stream().filter( tr -> tr.getTaskName().equals( expectedTaskName ) ).findFirst();
    assertTrue( "TasksResultsEntities are saved too", dbTaskResultEntities.isPresent() );
    assertNotNull( "TaskResultEntity->applicationStartTime", dbTaskResultEntities.get().getApplicationStartTime() );
  }

  @Test
  public void testUpdate(){
    TaskResultEntity tre = taskResultDao.findById(2L);
    assertNotNull("TaskResultEntity", tre);
    String oldTaskName = tre.getTaskName();

    String newTaskName = "new" + oldTaskName;
    tre.setTaskName( newTaskName );

    taskResultDao.save( tre );
    TaskResultEntity updatedTaskResultEntity = taskResultDao.findById(2L);
    assertEquals( "TaskResultEntity taskName change", newTaskName, updatedTaskResultEntity.getTaskName() );
  }
}
