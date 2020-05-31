package task.storage;

import task.ITaskResult;
import task.TaskResultType;
import java.util.Set;

public interface ITasksResultsTypeFiltered extends ITasksResultsStorage {
  Set<ITaskResult> getTasksResults( TaskResultType resultType );
}
