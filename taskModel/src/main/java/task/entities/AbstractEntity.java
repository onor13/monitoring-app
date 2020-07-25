package task.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@SuppressWarnings({"PMD.UnusedLocalVariable", "PMD.UnusedPrivateField"})
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

  private static final long serialVersionUID = -2795710048195775073L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(updatable = false)
  protected Long id;
  @Version
  @Column(name = "VERSION")
  private int version;

  /**
   * Returns the entity identifier. This identifier is unique per entity.
   * It is used by persistence frameworks used in a project,
   * and although is public, it should not be used by application code.
   * This identifier is mapped by ORM (Object Relational Mapper)
   * to the database primary key of the Person record to which
   * the entity instance is mapped.
   *
   * @return the unique entity identifier
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the entity identifier. This identifier is unique per entity.  Is is used by persistence frameworks
   * and although is public, it should never be set by application code.
   *
   * @param id the unique entity identifier
   */
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractEntity that = (AbstractEntity) o;
    if (!Objects.equals(id, that.id)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
