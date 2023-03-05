import Manager.FileBackedTasksManager;
import Manager.Managers;
import Status.Status;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.io.File;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        Path path = Path.of("data.csv");
        File file = new File(String.valueOf(path));
        FileBackedTasksManager manager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);

        Task firstTask = new Task("Сделать задание", "Уроки", Status.NEW);
        manager.createTask(firstTask);
        Task secondTask = new Task("Приготовить ужин", "Ужин", Status.NEW);
        manager.createTask(secondTask);

        Epic firstEpic = new Epic("Поздравить друзей", "Праздник", Status.NEW);
        manager.createEpic(firstEpic);

        Subtask firstSubtask = new Subtask("Купить продукты", "Магазин", Status.NEW, firstEpic.getId());
        manager.createSubtask(firstSubtask);

        manager.getTaskById(firstTask.getId());
        manager.getTaskById(secondTask.getId());
        System.out.println();


    }
}
