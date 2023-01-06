import Manager.Manager;
import Tasks.Epic;
import Tasks.Task;
import Tasks.Subtask;

import java.util.ArrayList;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();

        Task task1 = new Task("Убраться", "В квартире", "DONE");
        Task task2 = new Task("Приготовить", "Блюдо для ужина", "NEW");

        ArrayList<Subtask> SubtasksEpic1 = new ArrayList<>();
        Subtask Subtask1Epic1 = new Subtask("Помыть полы", "Использовать тряпку для пола",
                "NEW");

        Subtask Subtask2Epic1 = new Subtask("Протереть полки", "Использовать тряпку для пыли",
                "DONE");

        SubtasksEpic1.add(Subtask1Epic1);
        SubtasksEpic1.add(Subtask2Epic1);

        Epic Epic1 = new Epic("Выбрать комнату", "начать с зала", SubtasksEpic1);

        Subtask Subtask1Epic2 = new Subtask("Почистить","картошку","NEW");
        ArrayList<Subtask> SubtasksEpic2 = new ArrayList<>();

        SubtasksEpic2.add(Subtask1Epic2);

        Epic Epic2 = new Epic("Приготовить овощи", "Для нарезания", SubtasksEpic2);

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(Subtask1Epic1);
        manager.createTask(Subtask2Epic1);
        manager.createTask(Epic1);
        manager.createTask(Subtask1Epic2);
        manager.createTask(Epic2);

        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getTaskStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getEpicStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getSubtaskStorage()).toArray()));

        manager.deleteAllTasksOfAnyType(manager.getEpicStorage());

        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getTaskStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getEpicStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getSubtaskStorage()).toArray()));

        for (int i = 0; i <= 8; i++) {
            System.out.println(manager.getTaskOfAnyTypeById(i));
        }

        System.out.println(manager.createCopyOfTaskOfAnyType(task1));
        System.out.println(manager.createCopyOfTaskOfAnyType(Epic1));
        System.out.println(manager.createCopyOfTaskOfAnyType(Subtask1Epic1));

        manager.updateTaskOfAnyType(5, Epic1);
        manager.updateTaskOfAnyType(7, Epic2);

        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getEpicStorage()).toArray()));

        manager.removeTaskOfAnyTypeById(1);
        manager.removeTaskOfAnyTypeById(2);

        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getTaskStorage()).toArray()));


        System.out.println(manager.getCompleteListOfSubtaskByEpic(Epic1));
        System.out.println(manager.getCompleteListOfSubtaskByEpic(Epic2));
    }
}