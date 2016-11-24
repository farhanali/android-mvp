package in.farhanali.androidmvp.module.task.presenter;

import java.util.List;

import javax.inject.Inject;

import in.farhanali.androidmvp.data.api.ApiObserver;
import in.farhanali.androidmvp.data.api.TodoService;
import in.farhanali.androidmvp.data.model.Task;
import in.farhanali.androidmvp.data.model.User;
import in.farhanali.androidmvp.injection.Injector;
import in.farhanali.androidmvp.module.base.BaseNetworkPresenter;
import in.farhanali.androidmvp.module.common.util.UserPreference;
import retrofit2.Response;
import rx.Observable;

/**
 * @author Farhan Ali
 */
public class TaskListPresenterImpl extends BaseNetworkPresenter<TaskListViewInteractor>
        implements TaskListPresenter {

    @Inject TodoService todoService;
    @Inject UserPreference userPreference;

    private User user;

    public TaskListPresenterImpl() {
        Injector.component().inject(this);

        user = userPreference.read();
    }

    @Override
    public void createTask(String name) {
        Task task = new Task(user.getId(), name, true);
        Observable<Response<Task>> createTaskObservable
                = todoService.createTask(user.getAuthorization(), task);

        getViewInteractor().showProgress();
        subscribeForNetwork(createTaskObservable, new ApiObserver<Response<Task>>() {
            @Override
            public void onResponse(Response<Task> response) {
                getViewInteractor().hideProgress();

                if (response.code() == 201) {
                    getViewInteractor().onTaskCreated(response.body());
                    return;
                }

                handleResponseError(response);
            }

            @Override
            public void onError(Throwable e) {
               handleServerError(e);
            }
        });
    }

    @Override
    public void updateTask(Task task) {
        Observable<Response<Task>> updateTaskObservable
                = todoService.updateTask(user.getAuthorization(), task.getId(), task);

        getViewInteractor().showProgress();
        subscribeForNetwork(updateTaskObservable, new ApiObserver<Response<Task>>() {
            @Override
            public void onResponse(Response<Task> response) {
                getViewInteractor().hideProgress();

                if (response.code() == 200) {
                    getViewInteractor().onTaskUpdated(response.body());
                    return;
                }

                handleResponseError(response);
            }

            @Override
            public void onError(Throwable e) {
                handleServerError(e);
            }
        });
    }

    @Override
    public void deleteTask(final Task task) {
        Observable<Response<Void>> deleteTaskObservable
                = todoService.deleteTask(user.getAuthorization(), task.getId());

        getViewInteractor().showProgress();
        subscribeForNetwork(deleteTaskObservable, new ApiObserver<Response<Void>>() {
            @Override
            public void onResponse(Response<Void> response) {
                getViewInteractor().hideProgress();

                if (response.code() == 200) {
                    getViewInteractor().onTaskDeleted(task);
                    return;
                }

                handleResponseError(response);
            }

            @Override
            public void onError(Throwable e) {
                handleServerError(e);
            }
        });
    }

    @Override
    public void loadAllTasks() {
        Observable<Response<List<Task>>> loadAllTasksObservable
                = todoService.readTasks(user.getAuthorization());
        loadTasks(loadAllTasksObservable);
    }

    @Override
    public void loadCompletedTasks() {
        Observable<Response<List<Task>>> loadCompletedTasksObservable
                = todoService.filterTasks(user.getAuthorization(), false);
        loadTasks(loadCompletedTasksObservable);
    }

    @Override
    public void loadActiveTasks() {
        Observable<Response<List<Task>>> loadActiveTasksObservable
                = todoService.filterTasks(user.getAuthorization(), true);
        loadTasks(loadActiveTasksObservable);
    }

    @Override
    public void clearAllTask() {
        Observable<Response<Void>> clearAllObservable
                = todoService.clearTasks(user.getAuthorization());
        clearTasks(clearAllObservable);
    }

    @Override
    public void clearCompletedTasks() {
        Observable<Response<Void>> clearCompletedObservable
                = todoService.clearCompletedTasks(user.getAuthorization());
        clearTasks(clearCompletedObservable);
    }

    @Override
    public void doLogout() {
        userPreference.clear();
        getViewInteractor().onLogoutDone();
    }

    private void loadTasks(Observable observable) {
        getViewInteractor().showProgress();
        subscribeForNetwork(observable, new ApiObserver<Response<List<Task>>>() {
            @Override
            public void onResponse(Response<List<Task>> response) {
                getViewInteractor().hideProgress();

                if (response.code() == 200) {
                    getViewInteractor().onTasksLoaded(response.body());
                    return;
                }

                handleResponseError(response);
            }

            @Override
            public void onError(Throwable e) {
                handleServerError(e);
            }
        });
    }

    private void clearTasks(Observable observable) {
        getViewInteractor().showProgress();
        subscribeForNetwork(observable, new ApiObserver<Response<Void>>() {
            @Override
            public void onResponse(Response<Void> response) {
                getViewInteractor().hideProgress();

                if (response.code() == 200) {
                    getViewInteractor().onTasksCleared();
                    return;
                }

                handleResponseError(response);
            }

            @Override
            public void onError(Throwable e) {
                handleServerError(e);
            }
        });
    }

    private void handleResponseError(Response response) {
        String errorMessage = response.code() == 401
                ? "Authentication Failed"
                : "Error: " + response.code() + " " + response.message();
        getViewInteractor().onError(errorMessage, null);
    }

    private void handleServerError(Throwable e) {
        getViewInteractor().hideProgress();
        getViewInteractor().onError("Server or network failed", e);
    }

}
