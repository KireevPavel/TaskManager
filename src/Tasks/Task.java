package Tasks;
import Manager.Manager;
public class Task {

    private final int id;
    private final String name;
    private final String description;
    private String status;

    public Task(String name, String description, String status) {
        this.id = Manager.getId() + 1;
        Manager.setId(this.id);
        this.name = name;
        this.description = description;
        this.status = status;
    }

    Task(String name, String description) {
        this.id = Manager.getId() + 1;
        Manager.setId(this.id);
        this.name = name;
        this.description = description;
    }

    public Task(Task task) {
        this(task.name, task.description, task.status);
    }

    public int getId() {
        return id;
    }
    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
