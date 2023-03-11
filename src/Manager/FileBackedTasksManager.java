package Manager;

import TaskType.TaskType;
import Status.Status;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;
    private static final String HEADER_CSV_FILE = "id,type,name,status,description,epic\n";

    public FileBackedTasksManager(HistoryManager historyManager) {
        super(historyManager);
    }

    public FileBackedTasksManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }
    public static void main(String[] args){

        //Менеджер для проверки загрузки из файла
        Path path = Path.of("save_tasks.txt");

        File file = new File(String.valueOf(path));


        FileBackedTasksManager manager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);


        Task firstTask = new Task("Описание-1", "Task-1", Status.NEW);
        manager.createTask(firstTask);
        Task secondTask = new Task("Описание-2", "Task-2", Status.NEW);
        manager.createTask(secondTask);

        Epic firstEpic = new Epic("Описание-1", "Epic-1", Status.NEW);
        manager.createEpic(firstEpic);
        Epic secondEpic =new Epic("Описание-2", "Epic-2", Status.NEW);
        manager.createEpic(secondEpic);

        Subtask firstSubtask = new Subtask("Описание-1", "Subtask-1", Status.NEW, 3);
        manager.createSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask("Описание-2", "Subtask-2", Status.NEW, 3);
        manager.createSubtask(secondSubtask);

        //Запрос некоторых задач, чтобы заполнилась история просмотра
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getEpicById(3);
        manager.getEpicById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(6);


        //Просмотр истории обращения к задачам
        List<Task> history = manager.getHistory();
        System.out.println(history);

        System.out.println("\n----------Создание второго менеджера на основе файла первого экземпляра.");

        //Создание нового FileBackedTasksManager менеджера из этого же файла.
        FileBackedTasksManager manager1 = loadFromFile(file);

        //Вывод списка задач
        List<Task> history1 = manager1.getHistory();
        System.out.println(history1);
    }

    public static FileBackedTasksManager loadFromFile(File file){
        FileBackedTasksManager fm = new FileBackedTasksManager(Managers.getDefaultHistory(),file);
        List<String> line = List.of(fm.readFile(file).split("\n"));

        for (int i = 1; i < line.size();i++){
                    if (!line.get(i).isEmpty()) {
                        fm.fromString(line.get(i));

                    } else {
                        historyFromString(line.get(line.size() - 1));
                    }
                }
        return fm;
    }

    private String readFile(File file){
        try{
            return Files.readString(file.toPath());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось найти файл для записи данных");
        }

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(HEADER_CSV_FILE);

            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }

            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }

            writer.write("\n");
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл", e);
        }
    }

    private String getParentEpicId(Task task) {
        if (task instanceof Subtask) {
            return Integer.toString(((Subtask) task).getEpicId());
        }
        return "";
    }

    private TaskType getType(Task task) {
        if (task instanceof Epic) {
            return TaskType.EPIC;
        } else if (task instanceof Subtask) {
            return TaskType.SUBTASK;
        }
        return TaskType.TASK;
    }

    // Метод сохранения задачи в строку
    private String toString(Task task) {
        String[] toJoin = {Integer.toString(task.getId()), getType(task).toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), getParentEpicId(task)};
        return String.join(",", toJoin);
    }

    // Метод создания задачи из строки
    private void fromString(String line) {
        if (line.contains("TASK") || line.contains("EPIC") || line.contains("SUBTASK")) {

            String[] params = line.split(",");
            TaskType type = TaskType.valueOf(params[1]);
            switch (type) {
                case TASK: {
                    Task task = new Task(params[0], params[4], Status.valueOf(params[3].toUpperCase()));
                    task.setId(Integer.parseInt(params[0]));
                    task.setStatus(Status.valueOf(params[3].toUpperCase()));
                    tasks.put(task.getId(), task);

                }
                case EPIC: {
                    Epic epic = new Epic(params[2], params[4], Status.valueOf(params[3].toUpperCase()));
                    epic.setId(Integer.parseInt(params[0]));
                    epic.setStatus(Status.valueOf(params[3].toUpperCase()));
                    epics.put(epic.getId(), epic);

                }
                case SUBTASK: {
                    Subtask subtask = new Subtask(params[2], params[4], Status.valueOf(params[3].toUpperCase()), Integer.parseInt(params[0]));
                    subtask.setId(Integer.parseInt(params[0]));
                    subtask.setStatus(Status.valueOf(params[3].toUpperCase()));
                    subtasks.put (subtask.getId(), subtask);

                }
            }

        }
    }

    @Override
    public int createTask(Task task) {
        int newTaskId = super.createTask(task);
        save();
        return newTaskId;
    }

    @Override
    public int createEpic(Epic epic) {
        int newEpicId = super.createEpic(epic);
        save();
        return newEpicId;
    }

    @Override
    public int createSubtask(Subtask subtask) {
        int newSubtaskId = super.createSubtask(subtask);
        save();
        return newSubtaskId;
    }

    public int addTask(Task task) {
        return super.createTask(task);
    }

    public int addEpic(Epic epic) {
        return super.createEpic(epic);
    }

    public int addSubtask(Subtask subtask) {
        return super.createSubtask(subtask);
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    // Метод для сохранения истории в CSV
    static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder str = new StringBuilder();

        if (history.isEmpty()) {
            return "";
        }

        for (Task task : history) {
            str.append(task.getId()).append(",");
        }

        if (str.length() != 0) {
            str.deleteCharAt(str.length() - 1);
        }

        return str.toString();
    }

    // Метод восстановления менеджера истории из CSV
    static List<Integer> historyFromString(String value) {
        List<Integer> toReturn = new ArrayList<>();
        if (value != null) {
            String[] id = value.split(",");

            for (String number : id) {
                toReturn.add(Integer.parseInt(number));
            }

            return toReturn;
        }
        return toReturn;
    }

}
