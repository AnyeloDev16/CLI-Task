import service.ITaskService;
import service.TaskServiceImpl;

public class TaskCLIApp {
    public static void main(String[] args) throws Exception {
        
        ITaskService service = new TaskServiceImpl();

        if(args.length < 1){
            System.out.println(" ==> Error, Sintaxis: <Command> [Argument]");
            return ;
        }

        String command = args[0];

        switch(command){
            case "add" -> {
                if(args.length < 2){
                    System.out.println(" ==> Error, Sintaxis: add <Task description>");
                    return ;
                }
                long id = service.saveTask(args[1]);
                System.out.println(" ==> Created new Task - id : " + id);
            }
            case "update" -> {
                if(args.length < 3){
                    System.out.println(" ==> Error, Sintaxis: update <id> <New task description>");
                    return ;
                }
                service.updateDescription(args[1], args[2]);
            }
            case "mark-p" -> {
                if(args.length < 2){
                    System.out.println(" ==> Error, Sintaxis: mark-p <id>");
                    return ;
                }
                service.markInProgress(args[1]);
            }
            case "mark-d" -> {
                if(args.length < 2){
                    System.out.println(" ==> Error, Sintaxis: mark-d <id>");
                    return ;
                }
                service.markDone(args[1]);
            }
            case "delete" -> {
                if(args.length < 2){
                    System.out.println(" ==> Error, Sintaxis: delete <id>");
                    return ;
                }
                service.deleteTask(args[1]);
            }
            default ->{
                System.out.println(" ==> Conmmad invalid");
                return ;
            }
        }

        service.saveChange();

    }
}
