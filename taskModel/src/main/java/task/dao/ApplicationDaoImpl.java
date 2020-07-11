package task.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import task.TaskResult;
import task.entities.ApplicationTable;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import task.entities.TaskResultTable;

import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
@Transactional
@Repository("applicationDao")
public class ApplicationDaoImpl implements ApplicationDao {

  private static final Logger logger = Logger.getLogger( ApplicationDaoImpl.class.getSimpleName() );
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
    return (ApplicationTable) sessionFactory.getCurrentSession().
        getNamedQuery(ApplicationTable.FIND_APPLICATION_BY_ID).
        setParameter("id", id).uniqueResult();
  }

  @Override
  public ApplicationTable save( ApplicationTable app ) {
    sessionFactory.getCurrentSession().saveOrUpdate(app);
    logger.info("Application saved with id: " + app.getId());
    return app;
  }

  @Override
  public void delete( ApplicationTable app ) {
    sessionFactory.getCurrentSession().delete(app);
    logger.info("Application deleted with id: " + app.getId());
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  @Resource(name = "sessionFactory")
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
