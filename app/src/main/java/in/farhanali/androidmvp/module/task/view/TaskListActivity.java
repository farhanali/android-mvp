package in.farhanali.androidmvp.module.task.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindString;
import in.farhanali.androidmvp.R;
import in.farhanali.androidmvp.data.model.Task;
import in.farhanali.androidmvp.injection.Injector;
import in.farhanali.androidmvp.module.base.BaseActivity;
import in.farhanali.androidmvp.module.common.util.Bakery;
import in.farhanali.androidmvp.module.login.view.LoginActivity;
import in.farhanali.androidmvp.module.task.presenter.TaskListPresenter;
import in.farhanali.androidmvp.module.task.presenter.TaskListViewInteractor;

/**
 * @author Farhan Ali
 */

public class TaskListActivity extends BaseActivity implements TaskListViewInteractor,
        TaskListAdapter.ItemClickListener {

    @Inject TaskListPresenter taskListPresenter;
    @Inject Bakery bakery;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.txt_toolbar_title) TextView txtToolbarTitle;
    @Bind(R.id.progress) ProgressBar progress;
    @Bind(R.id.edt_new_task) EditText edtNewTask;
    @Bind(R.id.recycler_tasks) RecyclerView recyclerTasks;
    @Bind(R.id.view_no_tasks) View viewNoTasks;

    @BindString(R.string.app_name) String appName;

    private TaskListAdapter taskListAdapter;
    private List<Task> tasks = new ArrayList<>();

    // activity lifecycle methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // the implementation in BaseActivity will do the binding using butterknife
        setContentView(R.layout.task_list_activity);

        // to inject dependencies by dagger 2
        Injector.component().inject(this);

        // attach view interactor for the presenter
        taskListPresenter.attachViewInteractor(this);

        // mock tasks
        setupToolbar();
        setupTasksRecyclerView();
        setupNewTaskEditText();

        taskListPresenter.loadAllTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_activity_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear_completed:
                taskListPresenter.clearCompletedTasks();
                break;
            case R.id.menu_clear_all:
                taskListPresenter.clearAllTask();
                break;
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_logout:
                taskListPresenter.doLogout();
                break;
        }
        return true;
    }

    // task list adapter click listener method implementations

    @Override
    public void onTaskItemClick(Task task) {
        bakery.toastShort(task.getName());
    }

    @Override
    public void onTaskStatusClick(Task task) {
        task.toggleActive();
        taskListPresenter.updateTask(task);
    }

    @Override
    public void onTaskDeleteClick(Task task) {
        taskListPresenter.deleteTask(task);
    }

    // view interactor method implementations

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onTaskCreated(Task task) {
        // loading all task again to make it simple - not a good practice
        taskListPresenter.loadAllTasks();
    }

    @Override
    public void onTaskUpdated(Task task) {
        // loading all task again to make it simple - not a good practice
        taskListPresenter.loadAllTasks();
    }

    @Override
    public void onTaskDeleted(Task task) {
        // loading all task again to make it simple - not a good practice
        taskListPresenter.loadAllTasks();
    }

    @Override
    public void onTasksLoaded(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        taskListAdapter.notifyDataSetChanged();
        refreshNoTasksVisibility();
    }

    @Override
    public void onTasksCleared() {
        taskListPresenter.loadAllTasks();
    }

    @Override
    public void onLoggedOut() {
        startActivity(LoginActivity.class, null);
        finish();
    }

    @Override
    public void onError(String message, Throwable e) {
        bakery.toastShort(message);
    }

    // activity private methods

    private void setupToolbar() {
        //txtToolbarTitle.setText(appName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(appName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    private void setupTasksRecyclerView() {
        taskListAdapter = new TaskListAdapter(this, tasks, this);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerTasks.setAdapter(taskListAdapter);
    }

    private void setupNewTaskEditText() {
        edtNewTask.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() != KeyEvent.ACTION_DOWN) ||
                        (keyEvent.getKeyCode() != KeyEvent.KEYCODE_ENTER)) {
                    return false;
                }

                String inputText = edtNewTask.getText().toString();
                if (inputText.length() > 0) {
                    taskListPresenter.createTask(inputText);
                }

                edtNewTask.setText("");
                return true;
            }
        });
    }

    private void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(this, findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.task_list_filter_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.active:
                        taskListPresenter.loadActiveTasks();
                        break;
                    case R.id.completed:
                        taskListPresenter.loadCompletedTasks();
                        break;
                    default:
                        taskListPresenter.loadAllTasks();
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    private void refreshNoTasksVisibility() {
        int visibility = tasks.size() > 0 ? View.GONE : View.VISIBLE;
        viewNoTasks.setVisibility(visibility);
    }

}
