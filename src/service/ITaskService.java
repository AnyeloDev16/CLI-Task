package service;

import java.util.List;

import model.Task;

public interface ITaskService {

    Long saveTask(String description);
    void updateDescription(String id, String description);
    void markInProgress(String id);
    void markDone(String id);
    void deleteTask(String id);

    List<Task> findAllTask();
    List<Task> findAllTaskTodo();
    List<Task> findAllTaskInProgress();
    List<Task> findAllTaskDone();

    void saveChange();

}
