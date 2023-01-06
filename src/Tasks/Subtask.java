package Tasks;

public class Subtask extends Task {



        public Subtask(String name, String description, String status) {
            super(name, description, status);

        }

        public Subtask(Subtask Subtask) {
            this(Subtask.getName(), Subtask.getDescription(), Subtask.getStatus());
        }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}

