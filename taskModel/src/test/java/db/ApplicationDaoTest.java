package db;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import task.TaskResultType;
import task.dao.ApplicationDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
public class ApplicationDaoTest {

  private GenericApplicationContext ctx;
  private ApplicationDao            appDao;

  @Before
  public void setUp(){
    ctx = new AnnotationConfigApplicationContext( TestDBConfig.class );
    appDao = ctx.getBean(ApplicationDao.class);
    assertNotNull( "bean should be initialized", appDao );
  }

  @Test
  public void testFindAll(){
    List<ApplicationEntity> appEntities = appDao.findAll();
    assertFalse("should not be empty", appEntities.isEmpty());
  }

  @Test
  public void testFindAllWithTasksResults(){
    List<ApplicationEntity> apps = appDao.findAllWithTasksResults();
    assertFalse("ApplicationEntity", apps.isEmpty());
    for( ApplicationEntity app : apps ){
      if ( app.getTasksResults() == null ){
        fail( "no tasks results found" );
      }
      else{
        assertFalse("should be at least one task", app.getTasksResults().isEmpty() );
      }
    }
  }

  @Test
  public void testInsert(){
    ApplicationEntity app = new ApplicationEntity();
    LocalDateTime expectedTime = LocalDateTime.now();
    String expectedAppName = "testApp";
    app.setName( expectedAppName );
    app.setStartTime( expectedTime );
    String expectedTask = "testTask";

    TaskResultEntity trt = new TaskResultEntity();
    trt.setTaskName( expectedTask );
    trt.setTaskGroup( "medical" );
    trt.setTaskResultType( TaskResultType.SUCCESS );
    trt.setTaskStartTime( expectedTime );
    trt.setTaskExecutionDuration( Duration.ofMinutes( 6 ) );
    trt.setApplication( app );

    app.addTaskResult( trt );
    appDao.save(app);

    List<ApplicationEntity> apps = appDao.findAllWithTasksResults();
    assertFalse("ApplicationEntity", apps.isEmpty() );
    //LocalDateTime will not be exactly the same due to approximation when stored in the DB
    Optional<ApplicationEntity> dbApp = apps.stream().filter( myApp -> myApp.getName().equals( expectedAppName ) && Duration.between( myApp.getStartTime(), expectedTime).getSeconds() < 1 ).findFirst();
    assertTrue( "applicationEntity", dbApp.isPresent() );
    assertEquals( "number of tasks",dbApp.get().getTasksResults().size(), 1 );
  }
}
