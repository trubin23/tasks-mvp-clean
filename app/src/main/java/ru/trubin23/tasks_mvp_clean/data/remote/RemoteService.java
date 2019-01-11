package ru.trubin23.tasks_mvp_clean.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface RemoteService {

    @GET("/api_clean/tasks")
    Call<List<NetworkTask>> getTasks();

    @GET("/api_clean/tasks/{id}")
    Call<NetworkTask> getTask(@Path("id") String id);

    @POST("/api_clean/tasks")
    Call<NetworkTask> addTask(@Body NetworkTask task);

    @PUT("/api_clean/tasks/{id}")
    Call<NetworkTask> updateTask(@Path("id") String id, @Body NetworkTask task);

    @PUT("/api_clean/tasks/{id}")
    Call<NetworkTask> completeTask(@Path("id") String id, @Body StatusOfTask task);

    @DELETE("/api_clean/tasks/{id}")
    Call<NetworkTask> deleteTask(@Path("id") String id);

    @DELETE("/api_clean/tasks/completed")
    Call<Integer> deleteCompletedTasks();
}
