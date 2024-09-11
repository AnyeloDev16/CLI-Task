package repository;

import java.util.List;

import exception.IdNotFoundException;
import model.Task;
import model.TaskStatus;

public interface ITaskRepository {

    Long saveTask(Task task);
    void updateDescription(Long id, String description) throws IdNotFoundException;
    void updateStatus(Long id, TaskStatus taskStatus) throws IdNotFoundException;
    void deleteTask(Long id) throws IdNotFoundException;

    List<Task> findAllTask();
    List<Task> findAllTaskFilter(TaskStatus taskStatus);
    
    void saveChange();

}
