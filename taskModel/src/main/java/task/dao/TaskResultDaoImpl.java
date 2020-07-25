package task.dao;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.LazyArgs;
import converters.LocalDateTimeConverter;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task.entities.TaskResultEntity;


@SuppressWarnings("unchecked")
@Transactional
@Repository("taskResultDao")
public class TaskResultDaoImpl implements TaskResultDao {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private SessionFactory sessionFactory;
  private final transient LocalDateTimeConverter ldcFormatter = new LocalDateTimeConverter();

  @Override
  @Transactional(readOnly = true)
  public List<TaskResultEntity> findAll() {
    logger.atFine().log("find all taskResult's");
    return sessionFactory.getCurrentSession().createQuery("from TaskResult tr").list();
  }

  @Override
  @Transactional(readOnly = true)
  public TaskResultEntity findById(Long id) {
    logger.atFine().log("find taskResult by %d", id);
    return (TaskResultEntity) sessionFactory.getCurrentSession()
        .getNamedQuery(TaskResultEntity.FIND_TASK_RESULT_BY_ID)
        .setParameter("id", id).uniqueResult();
  }

  @Override
  public TaskResultEntity find(Long appId, String taskName, LocalDateTime startTaskTime) {
    logger.atFine().log("find taskResult by appId %d, taskName %s, taskStartTime %s",
        appId,
        taskName,
        LazyArgs.lazy(() -> ldcFormatter.format(startTaskTime)));
    return (TaskResultEntity) sessionFactory.getCurrentSession()
        .getNamedQuery(TaskResultEntity.FIND_TASK_RESULT_BY_APP_ID_TASK_NAME_TASK_START_TIME)
        .setParameter(TaskResultEntity.PARAM_APP_ID, appId)
        .setParameter(TaskResultEntity.PARAM_TASK_NAME, taskName)
        .setParameter(TaskResultEntity.PARAM_TASK_START_TIME, startTaskTime).uniqueResult();
  }

  @Override
  public TaskResultEntity save(TaskResultEntity tr) {
    sessionFactory.getCurrentSession().saveOrUpdate(tr);
    logger.atFine().log("TaskResult saved with id %s", tr.getId());
    return tr;
  }

  @Override
  public void delete(TaskResultEntity tr) {
    sessionFactory.getCurrentSession().delete(tr);
    logger.atFine().log("TaskResult deleted with id %s", tr.getId());
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Resource(name = "sessionFactory")
  public void setSessionFactory(SessionFactory sessionFactory) {
    logger.atConfig().log("setting SessionFactory");
    this.sessionFactory = sessionFactory;
  }
}
