package in.farhanali.androidmvp.module.task.presenter;

import java.util.List;

import in.farhanali.androidmvp.data.model.Task;
import in.farhanali.androidmvp.module.base.ViewInteractor;

/**
 * @author Farhan Ali
 */
public interface TaskListViewInteractor extends ViewInteractor {

    void onTaskCreated(Task task);

    void onTaskUpdated(Task task);

    void onTaskDeleted(Task task);

    void onTasksLoaded(List <Task> tasks);

    void onTasksCleared();

    void onLoggedOut();

    void onError(String message, Throwable e);

    void showProgress();

    void hideProgress();

}
