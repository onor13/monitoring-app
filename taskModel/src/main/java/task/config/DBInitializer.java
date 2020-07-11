package task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.TaskResultType;
import task.dao.ApplicationDao;
import task.entities.ApplicationTable;
import task.entities.TaskResultTable;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

@Service
public class DBInitializer {

  @Autowired
  ApplicationDao applicationDao;

  @PostConstruct
  public void initDB(){

    ApplicationTable app = new ApplicationTable();
    app.setName( "yourDoctorApp" );
    app.setStartTime( LocalDateTime.now().minus( 10, ChronoUnit.MINUTES ) );

    {
      TaskResultTable trt = new TaskResultTable();
      trt.setApplication( app );
      trt.setTaskExecutionDuration( Duration.ofMinutes( 13 ) );
      trt.setTaskGroup( "medical" );
      trt.setTaskName( "prostateExam" );
      trt.setTaskResultType( TaskResultType.WARNING );
      trt.setTaskStartTime( LocalDateTime.now() );
      app.addTaskResult( trt );
    }
    {
      TaskResultTable trt2 = new TaskResultTable();
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
