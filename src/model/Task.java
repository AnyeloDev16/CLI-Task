package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Task {

    private static Long lastId = 0L;
    // ISO 8601
    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private Long id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Task(String description) {
        this.id = ++Task.lastId;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(Long id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        ++Task.lastId;
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateDescription(String description){
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(TaskStatus taskStatus){
        this.status = taskStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public static String convertToJson(Task task){
        return "{\"id\":\"" + task.getId() + "\", \"description\":\"" + task.getDescription() + "\", \"status\":\"" + task.getStatus().name() + "\", \"createdAt\":\"" + task.getCreatedAt().format(formatter) + "\", \"updatedAt\":\"" + task.getUpdatedAt().format(formatter) + "\"}";
    }

    public static Task convertToTask(String jsonTask){
        
        String[] dataJson = jsonTask.replace("{", "")
                                        .replace("}", "")
                                        .replace("\"", "")
                                        .split(",");

        String idString = dataJson[0].split(":")[1].strip();
        String description = dataJson[1].split(":")[1].strip();
        String statusString = dataJson[2].split(":")[1].strip();
        String createdAtString = dataJson[3].split("[a-z]:")[1].strip();
        String updatedAtString = dataJson[4].split("[a-z]:")[1].strip();

        Long id = Long.parseLong(idString);

        TaskStatus status = TaskStatus.valueOf(statusString.toUpperCase().replace(" ", "_"));

        LocalDateTime createdAt = LocalDateTime.parse(createdAtString, formatter);
        LocalDateTime updatedAt = LocalDateTime.parse(updatedAtString, formatter);

        Task task = new Task(id, description, status, createdAt, updatedAt);

        return task;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
