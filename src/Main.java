import Manager.Managers;
import Manager.TaskManager;
import Status.Status;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.List;

        public class Main {

            public static void main(String[] args) {
                TaskManager taskManager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());

                System.out.println(" Test History ");
                System.out.println(" Create ");
                taskManager.createTask(new Task("Task1", "Описание1", Status.NEW));
                taskManager.createTask(new Task("Task2", "Описание2", Status.NEW));
                taskManager.createEpic(new Epic("Epic1", "Описание1", Status.NEW));
                taskManager.createEpic(new Epic("Epic2", "Описание2", Status.NEW));
                taskManager.createSubtask(new Subtask("Subtask1", "Описание1", Status.NEW, 3));
                taskManager.createSubtask(new Subtask("Subtask2", "Описание2", Status.NEW, 3));
                taskManager.createSubtask(new Subtask("Subtask3", "Описание3", Status.NEW, 3));

                System.out.println(" Get By Id ");
                taskManager.getTaskById(1);
                taskManager.getEpicById(3);
                taskManager.getEpicById(3);
                taskManager.getEpicById(3);
                taskManager.getTaskById(1);
                taskManager.getEpicById(4);
                taskManager.getSubtaskById(5);
                taskManager.getSubtaskById(5);
                taskManager.getSubtaskById(6);

                System.out.println(" Get History ");
                List<Task> history = taskManager.getHistory();
                System.out.println(history);

                System.out.println(" Remove from history ");
                taskManager.remove(1);
                taskManager.deleteEpicById(3);

                List<Task> historyAfterRemove = taskManager.getHistory();
                System.out.println(historyAfterRemove);

            }
        }