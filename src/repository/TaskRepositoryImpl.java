package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import exception.IdNotFoundException;
import model.Task;
import model.TaskStatus;

public class TaskRepositoryImpl implements ITaskRepository{

    private static final Path JSON_PATH = Path.of("tasks.json");
    private static List<Task> listTasks;

    public TaskRepositoryImpl(){
        TaskRepositoryImpl.listTasks = loadTasks();
    }

    private List<Task> loadTasks(){
        
        List<Task> auxListTasks = new ArrayList<>();

        try{

            if(Files.notExists(JSON_PATH)){
                Files.createFile(JSON_PATH);
                return auxListTasks;
            }
    
            String jsonData = Files.readString(JSON_PATH);

            if(jsonData.isBlank()){
                return auxListTasks;
            }

            String[] jsonTasks = jsonData.replace("[\n", "")
                                            .replace("\n]" , "")                                           
                                            .replace("},\n{", "}/{")
                                            .split("/"); 

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

        throw new IdNotFoundException();

    }

    @Override
    public void updateStatus(Long id, TaskStatus taskStatus) throws IdNotFoundException{
        
        for (Task task : listTasks) {
            if(task.getId() == id){
                task.updateStatus(taskStatus);
                return ;
            }
        }

        throw new IdNotFoundException();

    }

    @Override
    public void deleteTask(Long id) throws IdNotFoundException{
        
        for(int i=0; i < listTasks.size(); i++){
            if(listTasks.get(i).getId() == id){
                listTasks.remove(i);
                return ;
            }
        }

        throw new IdNotFoundException();

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

    @Override
    public void saveChange(){

        StringBuilder sb = new StringBuilder();

        if(!listTasks.isEmpty()){
            sb.append("[\n");


            for(int i = 0; i<listTasks.size(); i++){           
                if(i > 0){
                    sb.append(",\n");
                }
                sb.append(Task.convertToJson(listTasks.get(i)));
            }


            sb.append("\n]");
        }

        try {
            Files.write(JSON_PATH, sb.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
