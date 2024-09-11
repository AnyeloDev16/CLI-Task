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
    public void updateDescription(String id, String description) {
        try {
            repository.updateDescription(Long.parseLong(id), description);
            System.out.println(" ==> Updated Task");
        } catch (IdNotFoundException infe) {
            System.out.println(infe.getMessage());
        }
    }

    @Override
    public void markInProgress(String id) {
        try {
            repository.updateStatus(Long.parseLong(id), TaskStatus.IN_PROGRESS);
            System.out.println(" ==> Task mark in Progress");
        } catch (IdNotFoundException infe) {
            System.out.println(infe.getMessage());
        }
    }

    @Override
    public void markDone(String id) {
        try {
            repository.updateStatus(Long.parseLong(id), TaskStatus.DONE);
            System.out.println(" ==> Task mark in Done");
        } catch (IdNotFoundException infe) {
            System.out.println(infe.getMessage());
        }
    }

    @Override
    public void deleteTask(String id) {
        try {
            repository.deleteTask(Long.parseLong(id));
            System.out.println(" ==> Deleted Task");
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

    public void saveChange(){
        repository.saveChange();
    }

}
