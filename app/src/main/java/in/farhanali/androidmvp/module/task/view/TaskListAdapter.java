package in.farhanali.androidmvp.module.task.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.farhanali.androidmvp.R;
import in.farhanali.androidmvp.data.model.Task;

/**
 * @author Farhan Ali
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private Context context;
    private List<Task> tasks;
    private ItemClickListener itemClickListener;

    public TaskListAdapter(
            Context context, List<Task> tasks,
            ItemClickListener itemClickListener) {
        this.context = context;
        this.tasks = tasks;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.task_list_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.chk_active) CheckBox chkActive;
        @Bind(R.id.txt_title) TextView txtTitle;
        @Bind(R.id.btn_delete) Button btnDelete;

        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(int position) {
            this.position = position;
            Task item = tasks.get(position);
            chkActive.setChecked(!item.isActive());
            txtTitle.setText(item.getName());
            int titlePaintFlags = item.isActive()
                    ? txtTitle.getPaintFlags() & ~ Paint.STRIKE_THRU_TEXT_FLAG
                    : txtTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG;
            txtTitle.setPaintFlags(titlePaintFlags);
        }

        @OnClick(R.id.txt_title)
        void onTitleTextClick() {
            itemClickListener.onTaskItemClick(tasks.get(position));
        }

        @OnClick(R.id.chk_active)
        void onStatusCheckboxClick() {
            itemClickListener.onTaskStatusClick(tasks.get(position));
        }

        @OnClick(R.id.btn_delete)
        void onDeleteButtonClick() {
            itemClickListener.onTaskDeleteClick(tasks.get(position));
        }
    }

    public static interface ItemClickListener {

        void onTaskItemClick(Task task);

        void onTaskStatusClick(Task task);

        void onTaskDeleteClick(Task task);

    }

}
