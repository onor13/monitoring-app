package db;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import task.Application;
import task.TaskResultType;
import task.config.DBConfig;
import task.dao.ApplicationDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ApplicationDaoTest {

  private GenericApplicationContext ctx;
  private ApplicationDao            appDao;

  @Before
  public void setUp(){
    ctx = new AnnotationConfigApplicationContext( TestDBConfig.class );
    appDao = ctx.getBean(ApplicationDao.class);
    assertNotNull( appDao );
  }

  @Test
  public void testFindAll(){
    List<ApplicationEntity> appEntities = appDao.findAll();
    assertTrue(appEntities.size() > 0);
  }

  @Test
  public void testFindAllWithTasksResults(){
    List<ApplicationEntity> apps = appDao.findAllWithTasksResults();
    assertTrue(apps.size() > 0);
    for( ApplicationEntity app : apps ){
      if ( app.getTasksResults() == null ){
        fail( "no tasks results found" );
      }
      else{
        assertTrue( app.getTasksResults().size() > 0 );
      }
    }
  }

  @Test
  public void testFindByID(){
    Application app = appDao.findById(1L);
    assertNotNull(app);
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
    assertNotNull( app.getId() );

    List<ApplicationEntity> apps = appDao.findAllWithTasksResults();
    assertTrue(apps.size() > 0 );
    //LocalDateTime will not be exactly the same due to approximation when stored in the DB
    Optional<ApplicationEntity> dbApp = apps.stream().filter( myApp -> myApp.getName().equals( expectedAppName ) && Duration.between( myApp.getStartTime(), expectedTime).getSeconds() < 1 ).findFirst();
    assertTrue( dbApp.isPresent() );
    assertEquals( dbApp.get().getTasksResults().size(), 1 );
  }

  @Test
  public void testUpdate(){
    ApplicationEntity app = appDao.findById(1L);
    assertNotNull(app);
    String oldAppName = app.getName();

    String newAppName = "new" + oldAppName;
    app.setName( newAppName );

    appDao.save( app );
    ApplicationEntity updatedApp = appDao.findById(1L);
    assertEquals( newAppName, updatedApp.getName() );
  }
}
