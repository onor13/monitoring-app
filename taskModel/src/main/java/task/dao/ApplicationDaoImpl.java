package task.dao;

import com.google.common.flogger.FluentLogger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task.entities.ApplicationEntity;

import javax.annotation.Resource;
import java.util.List;

@SuppressWarnings("unchecked")
@Transactional
@Repository("applicationDao")
public class ApplicationDaoImpl implements ApplicationDao {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private SessionFactory sessionFactory;

  @Override
  @Transactional(readOnly = true)
  public List<ApplicationEntity> findAll() {
    logger.atFine().log("find all application's");
    return sessionFactory.getCurrentSession().createQuery("from Application app").list();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ApplicationEntity> findAllWithTasksResults() {
    logger.atFine().log("find all application's with tasks results");
    return sessionFactory.getCurrentSession().getNamedQuery(ApplicationEntity.FIND_ALL_WITH_TASKS_RESULTS).list();
  }

  @Override
  @Transactional(readOnly = true)
  public ApplicationEntity findById(Long id) {
    logger.atFine().log("find application by %d", id);
    return (ApplicationEntity) sessionFactory.getCurrentSession().
        getNamedQuery(ApplicationEntity.FIND_APPLICATION_BY_ID).
        setParameter("id", id).uniqueResult();
  }

  @Override
  @Transactional(readOnly = true)
  public ApplicationEntity findByName(String name) {
    logger.atFine().log("find application by name %s", name);
    return (ApplicationEntity) sessionFactory.getCurrentSession().
        getNamedQuery(ApplicationEntity.FIND_APPLICATION_BY_NAME).
        setParameter("name", name).uniqueResult();
  }

  @Override
  public ApplicationEntity save(ApplicationEntity app) {
    sessionFactory.getCurrentSession().saveOrUpdate(app);
    logger.atFine().log("Application saved with id %s", app.getId());
    return app;
  }

  @Override
  public void delete(ApplicationEntity app) {
    sessionFactory.getCurrentSession().delete(app);
    logger.atFine().log("Application deleted with id %s", app.getId());
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
