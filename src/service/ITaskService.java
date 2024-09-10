package service;

import java.util.List;

import model.Task;

public interface ITaskService {

    Long saveTask(String description);
    void updateDescription(Long id, String description);
    void markInProgress(Long id);
    void markDone(Long id);
    void deleteTask(Long id);

    List<Task> findAllTask();
    List<Task> findAllTaskTodo();
    List<Task> findAllTaskInProgress();
    List<Task> findAllTaskDone();

}
