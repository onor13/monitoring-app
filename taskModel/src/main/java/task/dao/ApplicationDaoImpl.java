package task.dao;

import com.google.common.flogger.FluentLogger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task.entities.ApplicationTable;

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
  public List<ApplicationTable> findAll() {
    return sessionFactory.getCurrentSession().createQuery("from Application app").list();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ApplicationTable> findAllWithTasksResults() {
    return sessionFactory.getCurrentSession().getNamedQuery(ApplicationTable.FIND_ALL_WITH_TASKS_RESULTS).list();
  }

  @Override
  @Transactional(readOnly = true)
  public ApplicationTable findById( Long id ) {
    logger.atFine().log( "find application by %d", id );
    return (ApplicationTable) sessionFactory.getCurrentSession().
        getNamedQuery(ApplicationTable.FIND_APPLICATION_BY_ID).
        setParameter("id", id).uniqueResult();
  }

  @Override
  public ApplicationTable save( ApplicationTable app ) {
    sessionFactory.getCurrentSession().saveOrUpdate(app);
    logger.atFine().log( "Application saved with id %s", app.getId() );
    return app;
  }

  @Override
  public void delete( ApplicationTable app ) {
    sessionFactory.getCurrentSession().delete(app);
    logger.atFine().log( "Application deleted with id %s", app.getId() );
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Resource(name = "sessionFactory")
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
