package http;

import manager.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPTaskManagerTest<T extends TaskManagerTest<HTTPTaskManager>> {
    private KVServer server;
    private TaskManager manager;
    public static final Path path = Path.of("http.test.csv");
    File file = new File(String.valueOf(path));

    @BeforeEach
    public void createManager() {
        try {
            server = new KVServer();
            server.start();
            manager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        } catch (IOException e) {
            System.out.println("Ошибка при создании менеджера");
        }
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void shouldCorrectlySaveAndLoad() {
        Task task = new Task("Description", "Title", Status.NEW, Instant.now(), 1);
        manager.createTask(task);
        Epic epic = new Epic("Description", "Title", Status.NEW, Instant.now(), 2);
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Description", "Title", Status.NEW, epic.getId()
                , Instant.now(), 3);
        manager.createSubtask(subtask);

        FileBackedTasksManager fileManager = new FileBackedTasksManager(Managers.getDefaultHistory(), file);
        fileManager.loadFromFile();
        assertEquals(fileManager.getAllSubtasks(), manager.getAllSubtasks());
        assertEquals(fileManager.getAllEpics(), manager.getAllEpics());
        assertEquals(fileManager.getPrioritizedTasks(), manager.getPrioritizedTasks());
        assertEquals(fileManager.getHistory(), manager.getHistory());
        assertEquals(fileManager.getTaskById(task.getId()), manager.getTaskById(task.getId()));
        assertEquals(fileManager.getEpicById(epic.getId()), manager.getEpicById(epic.getId()));
    }
}