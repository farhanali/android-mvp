package in.farhanali.androidmvp.module.task.presenter;

import in.farhanali.androidmvp.data.model.Task;
import in.farhanali.androidmvp.module.base.Presenter;

/**
 * @author Farhan Ali
 */
public interface TaskListPresenter extends Presenter<TaskListViewInteractor> {

    void createTask(String name);

    void updateTask(Task task);

    void deleteTask(Task task);

    void loadAllTasks();

    void loadCompletedTasks();

    void loadActiveTasks();

    void clearAllTask();

    void clearCompletedTasks();

    void doLogout();

}
