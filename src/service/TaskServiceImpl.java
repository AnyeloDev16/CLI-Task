package service;

import java.util.List;

import exception.IdNotFoundException;
import model.Task;
import model.TaskStatus;
import repository.ITaskRepository;
import repository.TaskRepositoryImpl;

public class TaskServiceImpl implements ITaskService{

    private static final ITaskRepository repository = new TaskRepositoryImpl();

    @Override
    public Long saveTask(String description) {
        return repository.saveTask(new Task(description));
    }

    @Override
    public void updateDescription(Long id, String description) {
        try {
            repository.updateDescription(id, description);
        } catch (IdNotFoundException infe) {
            System.out.println(infe.getMessage());
        }
    }

    @Override
    public void markInProgress(Long id) {
        try {
            repository.updateStatus(id, TaskStatus.IN_PROGRESS);
        } catch (IdNotFoundException infe) {
            System.out.println(infe.getMessage());
        }
    }

    @Override
    public void markDone(Long id) {
        try {
            repository.updateStatus(id, TaskStatus.DONE);
        } catch (IdNotFoundException infe) {
            System.out.println(infe.getMessage());
        }
    }

    @Override
    public void deleteTask(Long id) {
        try {
            repository.deleteTask(id);
        } catch (IdNotFoundException infe) {
            System.out.println(infe.getMessage());
        }
    }

    @Override
    public List<Task> findAllTask() {
        return repository.findAllTask();
    }

    @Override
    public List<Task> findAllTaskTodo() {
        return repository.findAllTaskFilter(TaskStatus.TODO);
    }

    @Override
    public List<Task> findAllTaskInProgress() {
        return repository.findAllTaskFilter(TaskStatus.IN_PROGRESS);
    }

    @Override
    public List<Task> findAllTaskDone() {
        return repository.findAllTaskFilter(TaskStatus.DONE);
    }

}
