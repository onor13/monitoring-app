package task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.TaskResultType;
import task.dao.ApplicationDao;
import task.entities.ApplicationEntity;
import task.entities.TaskResultEntity;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class DBInitializer {

  @Autowired
  ApplicationDao applicationDao;

  public static final String firstTaskName = "prostateExam";
  public static final LocalDateTime firstTaskStartTime = LocalDateTime.of( 2020, 07, 12, 10, 00, 00 );

  @PostConstruct
  public void initDB(){

    ApplicationEntity app = new ApplicationEntity();
    app.setName( "yourDoctorApp" );
    app.setStartTime( LocalDateTime.now().minus( 10, ChronoUnit.MINUTES ) );

    {
      TaskResultEntity trt = new TaskResultEntity();
      trt.setApplication( app );
      trt.setTaskExecutionDuration( Duration.ofMinutes( 13 ) );
      trt.setTaskGroup( "medical" );
      trt.setTaskName( firstTaskName );
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

    applicationDao.save( app );
  }
}
