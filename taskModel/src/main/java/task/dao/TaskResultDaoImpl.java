package task.dao;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.LazyArgs;
import converters.LocalDateTimeConverter;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task.criteria.FilterCriteria;
import task.criteria.FilterCriteriaType;
import task.entities.ApplicationEntity;
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
  public List<TaskResultEntity> findByCriteria(Collection<FilterCriteria> criteria) {
    logger.atFine().log("find taskResult by critiria: %s",
        LazyArgs.lazy(() -> criteria.stream().map(cr ->
            String.format("criteriaType: %s, value: %s", cr.getType().toString(), cr.getCriteriaValue().toString()))
            .collect(Collectors.joining(","))));
    if (criteria.isEmpty()) {
      return findAll();
    }

    CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
    CriteriaQuery<TaskResultEntity> criteriaQuery = criteriaBuilder.createQuery(TaskResultEntity.class);
    Root<TaskResultEntity> root = criteriaQuery.from(TaskResultEntity.class);
    Predicate[] predicates = createPredicates(criteria, root, criteriaBuilder);
    criteriaQuery.select(root).where(predicates);
    return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getResultList();
  }

  private Predicate[] createPredicates(Collection<FilterCriteria> criteria,
                                       Root<TaskResultEntity> root, CriteriaBuilder criteriaBuilder) {
    Predicate[] predicates = new Predicate[criteria.size()];
    Iterator<FilterCriteria> criteriaIterator = criteria.iterator();
    int predicateIndex = 0;
    while (criteriaIterator.hasNext()) {
      predicates[predicateIndex] = predicateFromCriteria(criteriaIterator.next(), root, criteriaBuilder);
      ++predicateIndex;
    }
    return predicates;
  }

  private Predicate predicateFromCriteria(FilterCriteria criteria,
                                          Root<TaskResultEntity> root, CriteriaBuilder criteriaBuilder) {
    if (criteria.getType() == FilterCriteriaType.ApplicationName) {
      return criteriaBuilder.equal(root.get(TaskResultEntity.CRITERIA_APPLICATION)
          .get(ApplicationEntity.CRITERIA_NAME), criteria.getCriteriaValue());
    } else if (criteria.getType() == FilterCriteriaType.ResultType) {
      return criteriaBuilder.equal(root.get(TaskResultEntity.CRITERIA_TASK_RESULT_TYPE), criteria.getCriteriaValue());
    } else if (criteria.getType() == FilterCriteriaType.TaskGroup) {
      return criteriaBuilder.equal(root.get(TaskResultEntity.CRITERIA_TASK_GROUP), criteria.getCriteriaValue());
    }
    throw new NotYetImplementedException("not supported criteria type " + criteria.getType().name());
  }

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
  public TaskResultEntity find(String applicationName, String taskName, LocalDateTime startTaskTime) {
    logger.atFine().log("find taskResult by appName %s, taskName %s, taskStartTime %s",
        applicationName,
        taskName,
        LazyArgs.lazy(() -> ldcFormatter.format(startTaskTime)));
    return (TaskResultEntity) sessionFactory.getCurrentSession()
        .getNamedQuery(TaskResultEntity.FIND_TASK_RESULT_BY_APP_NAME_TASK_NAME_TASK_START_TIME)
        .setParameter(TaskResultEntity.PARAM_APP_NAME, applicationName)
        .setParameter(TaskResultEntity.PARAM_TASK_NAME, taskName)
        .setParameter(TaskResultEntity.PARAM_TASK_START_TIME, startTaskTime).uniqueResult();
  }

  @Override
  public TaskResultEntity save(TaskResultEntity tr) {
    //TODO find better way to avoid find check
    TaskResultEntity tre = find(tr.getApplicationName(), tr.getTaskName(), tr.getTaskStartTime());
    if (tre != null) {
      return tre;
    }
    sessionFactory.getCurrentSession().saveOrUpdate(tr);
    logger.atFine().log("TaskResult saved with id %s", tr.getId());
    return tr;
  }

  @Override
  public void delete(TaskResultEntity tr) {
    sessionFactory.getCurrentSession().delete(tr);
    logger.atFine().log("TaskResult deleted with id %s", tr.getId());
  }

  @Override
  public void deleteOlderThan(LocalDateTime taskStartTime) {
    sessionFactory.getCurrentSession().getNamedQuery(TaskResultEntity.DELETE_TASK_RESULT_OLDER_THAN)
        .setParameter(TaskResultEntity.PARAM_TASK_START_TIME, taskStartTime).executeUpdate();
    logger.atFine().log("Delete tasksResults older than %s",
        LazyArgs.lazy(() -> ldcFormatter.format(taskStartTime)));
  }

  @Override
  public void deleteAll() {
    sessionFactory.getCurrentSession().createQuery(
        "delete from " + TaskResultEntity.TABLE_NAME).executeUpdate();
    logger.atFine().log("delete all TaskResult ");
  }

  @Override
  public long size() {
    long rowCount = (long) sessionFactory.getCurrentSession().createQuery(
        "SELECT count(*) from " + TaskResultEntity.TABLE_NAME).getSingleResult();
    logger.atFine().log("row count of TaskResult table: " + rowCount);
    return rowCount;
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
