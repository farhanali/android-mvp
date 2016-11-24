package in.farhanali.androidmvp.injection.module;

import dagger.Module;
import dagger.Provides;
import in.farhanali.androidmvp.module.task.presenter.TaskListPresenter;
import in.farhanali.androidmvp.module.task.presenter.TaskListPresenterImpl;

/**
 * Provides all task module dependencies.
 *
 * @author Farhan Ali
 */
@Module
public class TaskModule {

    @Provides
    public TaskListPresenter provideTaskListPresenter() {
        return new TaskListPresenterImpl();
    }

}
