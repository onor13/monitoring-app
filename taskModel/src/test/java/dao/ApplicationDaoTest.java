package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import config.TestDBConfig;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import task.TaskResultType;
import task.dao.ApplicationDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.JUnitTestContainsTooManyAsserts"})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=TestDBConfig.class, loader=AnnotationConfigContextLoader.class)
public class ApplicationDaoTest {

  @Autowired
  private ApplicationDao            appDao;

  @Test
  public void testFindAll(){
    List<ApplicationEntity> appEntities = appDao.findAll();
    assertFalse(appEntities.isEmpty(), "should be at least one existing application");
  }

  @Test
  public void testFindAllWithTasksResults(){
    List<ApplicationEntity> apps = appDao.findAllWithTasksResults();
    assertFalse(apps.isEmpty(), "applicationEntity");
    for(ApplicationEntity app : apps) {
      if (app.getTasksResults() == null) {
        fail("no tasks results found");
      }
      else {
        assertFalse(app.getTasksResults().isEmpty(), "should be at least one taskResult");
      }
    }
  }

  @Test
  @DisplayName("Test insert task")
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
    assertFalse(apps.isEmpty(), "applicationEntity");
    //LocalDateTime will not be exactly the same due to approximation when stored in the DB
    Optional<ApplicationEntity> dbApp = apps.stream().filter( myApp -> myApp.getName().equals( expectedAppName ) && Duration.between( myApp.getStartTime(), expectedTime).getSeconds() < 1 ).findFirst();
    assertTrue(dbApp.isPresent(), "find applicationEntity");
    assertEquals(dbApp.get().getTasksResults().size(), 1, "taskResult count");
  }
}
