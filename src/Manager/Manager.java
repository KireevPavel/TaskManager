package Manager;

import Tasks.Epic;
import Tasks.Task;
import Tasks.Subtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    private static int Id = 0;

    private final HashMap<Integer, Task> taskStorage = new HashMap<>();

    private final HashMap<Integer, Epic> EpicStorage = new HashMap<>();

    private final HashMap<Integer, Subtask> SubtaskStorage = new HashMap<>();

    public static int getId() {
        return Id;
    }

    public static void setId(int id) {
        Id = id;
    }

    public List<Task> getTaskStorage() {
        return new ArrayList<>(taskStorage.values());
    }

    public List<Epic> getEpicStorage() {
        return new ArrayList<>(EpicStorage.values());
    }

    public List<Subtask> getSubtaskStorage() {
        return new ArrayList<>(SubtaskStorage.values());
    }

    public void createTask(Task task) {
        switch (task.getClass().toString()) {
            case "class Tasks.Task": {
                taskStorage.put(((Task) task).getId(), (Task) task);
                break;
            }
            case "class Tasks.Epic": {
                EpicStorage.put(((Epic) task).getId(), (Epic) task);
                break;
            }
            case "class Tasks.Epic$Subtask": {
                SubtaskStorage.put(((Subtask) task).getId(), (Subtask) task);
                getEpicStatus((ArrayList<Subtask>) getSubtaskStorage());
                break;
            }
        }
    }

    public ArrayList<Task> getCompleteListOfAnyTasks(List< ? extends Task> tasks) {
        ArrayList<Task> completeListOfAnyTasks = new ArrayList<>();
        for (Task task : tasks) {
            completeListOfAnyTasks.add(task);
            }
        return completeListOfAnyTasks;
    }

    public void deleteAllTasksOfAnyType(List< ? extends Task> task) {

        task.clear();
    }

    public Task getTaskOfAnyTypeById(int id) {
        Task taskOfAnyKind = null;

        if (taskStorage.get(id) != null) {
            taskOfAnyKind = taskStorage.get(id);
        } else if (EpicStorage.get(id) != null) {
            taskOfAnyKind = EpicStorage.get(id);
        } else if (SubtaskStorage.get(id) != null) {
            taskOfAnyKind = SubtaskStorage.get(id);
        }
        return taskOfAnyKind;
    }

    public Task createCopyOfTaskOfAnyType(Task task) {
        switch (task.getClass().toString()) {
            case "class Tasks.Task": {
                return new Task((Task) task);
            }
            case "class Tasks.Epic$Subtask": {
                return new Subtask((Subtask) task);
            }
            case "class Tasks.Epic": {
                return new Epic((Epic) task);
            }
            default:
                return null;
        }
    }

    public void updateTaskOfAnyType(int id, Task task) {
        switch (task.getClass().toString()) {
            case "class Tasks.Task": {
                taskStorage.put(id, (Task) task);
                break;
            }
            case "class Tasks.Epic": {
                EpicStorage.put(id, (Epic) task);
                break;
            }
            case "class Tasks.Epic$Subtask": {
                SubtaskStorage.put(id, (Subtask) task);
                break;
            }
        }
    }

    public void removeTaskOfAnyTypeById(int id) {
        for (Integer task : taskStorage.keySet()) {
            if (id == task) {
                taskStorage.remove(id);
                break;
            }
        }
        for (Integer Epic : EpicStorage.keySet()) {
            if (id == Epic) {
                EpicStorage.remove(id);
                break;
            }
        }
        for (Integer Subtask : SubtaskStorage.keySet()) {
            if (id == Subtask) {
                SubtaskStorage.remove(id);
                break;
            }
        }
    }

    public ArrayList<Subtask> getCompleteListOfSubtaskByEpic(Epic Epic) {

        return Epic.getSubtasks();
    }

    public static String getEpicStatus(ArrayList<Subtask> Subtasks) {
        String statusEpic;
        int countNew = 0;
        int countDone = 0;

        for (Subtask Subtask : Subtasks) {
            if (Subtask.getStatus().equalsIgnoreCase("NEW")) {
                countNew++;
            }
            if (!Subtask.getStatus().equalsIgnoreCase("DONE")) {
                countDone++;
            }
        }

        if ((Subtasks.isEmpty()) || (countNew == Subtasks.size())) {
            statusEpic = "NEW";
        } else if (countDone == Subtasks.size()) {
            statusEpic = "DONE";
        } else {
            statusEpic = "IN_PROGRESS";
        }
        return statusEpic;
    }
}