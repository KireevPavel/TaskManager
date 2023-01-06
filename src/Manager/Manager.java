package Manager;

import Tasks.Epic;
import Tasks.Task;
import Tasks.Subtask;

import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<Integer, Task> getTaskStorage() {
        return taskStorage;
    }

    public HashMap<Integer, Epic> getEpicStorage() {
        return EpicStorage;
    }

    public HashMap<Integer, Subtask> getSubtaskStorage() {
        return SubtaskStorage;
    }

    public void createTask(Object object) {
        switch (object.getClass().toString()) {
            case "class Tasks.Task": {
                taskStorage.put(((Task) object).getId(), (Task) object);
                break;
            }
            case "class Tasks.Epic": {
                EpicStorage.put(((Epic) object).getId(), (Epic) object);
                break;
            }
            case "class Tasks.Epic$Subtask": {
                SubtaskStorage.put(((Subtask) object).getId(), (Subtask) object);
                break;
            }
        }
    }

    public ArrayList<Object> getCompleteListOfAnyTasks(HashMap<Integer, ? extends Task> HashMap) {
        ArrayList<Object> completeListOfAnyTasks = new ArrayList<>();

            for (Integer key : HashMap.keySet()) {
                completeListOfAnyTasks.add(HashMap.get(key));
            }
        return completeListOfAnyTasks;
    }

    public void deleteAllTasksOfAnyType(HashMap<Integer, ? extends Task> HashMap) {

        HashMap.clear();
    }

    public Object getTaskOfAnyTypeById(int id) {
        Object taskOfAnyKind = null;

        if (taskStorage.get(id) != null) {
            taskOfAnyKind = taskStorage.get(id);
        } else if (EpicStorage.get(id) != null) {
            taskOfAnyKind = EpicStorage.get(id);
        } else if (SubtaskStorage.get(id) != null) {
            taskOfAnyKind = SubtaskStorage.get(id);
        }
        return taskOfAnyKind;
    }

    public Object createCopyOfTaskOfAnyType(Object object) {
        switch (object.getClass().toString()) {
            case "class Tasks.Task": {
                return new Task((Task) object);
            }
            case "class Tasks.Epic$Subtask": {
                return new Subtask((Subtask) object);
            }
            case "class Tasks.Epic": {
                return new Epic((Epic) object);
            }
            default:
                return null;
        }
    }

    public void updateTaskOfAnyType(int id, Object object) {
        switch (object.getClass().toString()) {
            case "class Tasks.Task": {
                taskStorage.put(id, (Task) object);
                break;
            }
            case "class Tasks.Epic": {
                EpicStorage.put(id, (Epic) object);
                break;
            }
            case "class Tasks.Epic$Subtask": {
                SubtaskStorage.put(id, (Subtask) object);
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