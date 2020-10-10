package config;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.TaskResultType;
import task.dao.ApplicationDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

@Service
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class TestDatabaseInitializer {

  @Autowired
  ApplicationDao applicationDao;

  public static final String firstDoctorAppTaskName = "prostateExam";
  public static final LocalDateTime firstTaskStartTime = LocalDateTime.of( 2020, 07, 12, 10, 00, 00 );
  public static final String doctorAppName = "yourDoctorApp";
  public static final String billAppName = "billApp";
  public static final int totalNumberOfTasks = 4;

  @PostConstruct
  public void initDB(){
    ApplicationEntity app1 = createDoctorApp();
    applicationDao.save( app1 );

    ApplicationEntity app2 = createBillApp();
    applicationDao.save( app2 );
  }

  private ApplicationEntity createDoctorApp(){
    ApplicationEntity app = new ApplicationEntity();
    app.setName(doctorAppName);
    app.setStartTime( LocalDateTime.now().minus( 10, ChronoUnit.MINUTES ) );

    {
      TaskResultEntity trt = new TaskResultEntity();
      trt.setApplication( app );
      trt.setTaskExecutionDuration( Duration.ofMinutes( 13 ) );
      trt.setTaskGroup( "medical" );
      trt.setTaskName(firstDoctorAppTaskName);
      trt.setTaskResultType( TaskResultType.WARNING );
      trt.setTaskStartTime( firstTaskStartTime );
      app.addTaskResult( trt );
    }
    {
      TaskResultEntity trt2 = new TaskResultEntity();
      trt2.setApplication( app );
      trt2.setTaskExecutionDuration( Duration.ofMinutes( 13 ) );
      trt2.setTaskGroup( "medical" );
      trt2.setTaskName( "vaccination" );
      trt2.setTaskResultType( TaskResultType.SUCCESS );
      trt2.setTaskStartTime( LocalDateTime.now() );
      app.addTaskResult( trt2 );
    }
    return app;
  }

  private ApplicationEntity createBillApp(){
    ApplicationEntity app = new ApplicationEntity();
    app.setName(billAppName);
    app.setStartTime( LocalDateTime.now().minus( 10, ChronoUnit.MINUTES ) );

    {
      TaskResultEntity trt = new TaskResultEntity();
      trt.setApplication( app );
      trt.setTaskExecutionDuration( Duration.ofMinutes( 220 ) );
      trt.setTaskGroup( "surgery" );
      trt.setTaskName("brain surgery");
      trt.setTaskResultType( TaskResultType.WARNING );
      trt.setTaskStartTime( firstTaskStartTime );
      app.addTaskResult( trt );
    }
    {
      TaskResultEntity trt2 = new TaskResultEntity();
      trt2.setApplication( app );
      trt2.setTaskExecutionDuration( Duration.ofMinutes( 50 ) );
      trt2.setTaskGroup( "consultation" );
      trt2.setTaskName( "vaccination" );
      trt2.setTaskResultType( TaskResultType.SUCCESS );
      trt2.setTaskStartTime( LocalDateTime.now() );
      app.addTaskResult( trt2 );
    }
    return app;
  }
}