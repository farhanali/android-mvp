package in.farhanali.androidmvp.data.api;

import java.util.List;

import in.farhanali.androidmvp.data.model.Task;
import in.farhanali.androidmvp.data.model.User;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Farhan Ali
 */
public interface TodoService {

    @POST("login")
    Observable<Response<User>> login(@Body User user);

    @POST("tasks")
    Observable<Response<Task>> createTask(
            @Header("Authorization") String authorization, @Body Task task);

    @GET("tasks")
    Observable<Response<List<Task>>> readTasks(@Header("Authorization") String authorization);

    @GET("tasks")
    Observable<Response<List<Task>>> filterTasks(
            @Header("Authorization") String authorization, @Query("active") boolean isActive);

    @DELETE("tasks")
    Observable<Response<Void>> clearTasks(@Header("Authorization") String authorization);

    @DELETE("tasks?active=false")
    Observable<Response<Void>> clearCompletedTasks(@Header("Authorization") String authorization);

    @GET("tasks/{id}")
    Observable<Response<Task>> getTask(@Header("Authorization") String authorization, @Path("id") long taskId);

    @PUT("tasks/{id}")
    Observable<Response<Task>> updateTask(
            @Header("Authorization") String authorization, @Path("id") long id, @Body Task task);

    @DELETE("tasks/{id}")
    Observable<Response<Void>> deleteTask(@Header("Authorization") String authorization, @Path("id") long taskId);

}
