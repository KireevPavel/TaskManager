package Tasks;
import Manager.Manager;
import java.util.ArrayList;


public class Epic extends Task {
    private final ArrayList<Subtask> subtasks;

    public Epic(String name, String description, ArrayList<Subtask> subtasks) {
        super(name, description);
        this.setStatus(Manager.getEpicStatus(subtasks));
        this.subtasks = subtasks;
    }

    public Epic(Epic epic) {
        this(epic.getName(), epic.getDescription(), epic.subtasks);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }
    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                ", subtasks=" + subtasks +
                '}';
    }

}
