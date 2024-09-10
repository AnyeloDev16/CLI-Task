package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import exception.IdNotFoundException;
import model.Task;
import model.TaskStatus;

public class TaskRepositoryImpl implements ITaskRepository{

    private static final Path JSON_PATH = Path.of("src/tasks.json");
    private static List<Task> listTasks;

    public TaskRepositoryImpl(){
        TaskRepositoryImpl.listTasks = loadTasks();
    }

    private List<Task> loadTasks(){
        
        List<Task> auxListTasks = new ArrayList<>();

        try{

            if(Files.notExists(JSON_PATH)){
                Files.createFile(JSON_PATH);
                return new ArrayList<>();
            }
    
            String jsonData = Files.readString(JSON_PATH);
    
            String[] jsonTasks = jsonData.replace("[", "")
                                            .replace("]", "")
                                            .replace("},{", "}|{")
                                            .split("|"); 
    
            Arrays.asList(jsonTasks)
                        .forEach(task -> auxListTasks.add(Task.convertToTask(task)));
    
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        return auxListTasks;

    }

    @Override
    public Long saveTask(Task task) {
        TaskRepositoryImpl.listTasks.add(task);
        return task.getId();
    }

    @Override
    public void updateDescription(Long id, String description) throws IdNotFoundException{

        for (Task task : listTasks) {
            if(task.getId() == id){
                task.updateDescription(description);
                return ;
            }
        }

        throw new IdNotFoundException("ID not found");

    }

    @Override
    public void updateStatus(Long id, TaskStatus taskStatus) throws IdNotFoundException{
        
        for (Task task : listTasks) {
            if(task.getId() == id){
                task.updateStatus(taskStatus);
                return ;
            }
        }

        throw new IdNotFoundException("ID not found");

    }

    @Override
    public void deleteTask(Long id) throws IdNotFoundException{
        
        for(int i=0; i < listTasks.size(); i++){
            if(listTasks.get(i).getId() == id){
                listTasks.remove(i);
                return ;
            }
        }

        throw new IdNotFoundException("ID not found");

    }

    @Override
    public List<Task> findAllTask() {
        return listTasks;
    }
    
    @Override
    public List<Task> findAllTaskFilter(TaskStatus taskStatus) {
        return listTasks.stream()
                            .filter(task -> task.getStatus().ordinal() == taskStatus.ordinal())
                            .collect(Collectors.toList());
    }

}
