public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = manager.createTask("Приготовить", "Блюдо для ужина");
        Task task2 = manager.createTask("Убраться", "В квартире");
        Epic epic1 = manager.createEpic("Приготовить овощи", "Для нарезания");
        Subtask subtask = manager.createSubtask(epic1.getId(), "Почистить", "картошку");
        Epic epic2 = manager.createEpic("Выбрать комнату", "начать с зала");
        Subtask subtask1 = manager.createSubtask(epic2.getId(), "Помыть полы", "Использовать тряпку для пола");
        Subtask subtask2 = manager.createSubtask(epic2.getId(), "Протереть полки", "Использовать тряпку для пыли");

        System.out.println(task1.toString());
        System.out.println(task2.toString());
        System.out.println(epic1.toString());
        System.out.println(subtask.toString());
        System.out.println(epic2.toString());
        System.out.println(subtask1.toString());
        System.out.println(subtask2.toString());

        task1.setStatus(Task.IN_PROGRESS_STATUS);
        manager.updateTask(task1);
        System.out.println(task1.toString());

        task2.setStatus(Task.DONE_STATUS);
        manager.updateTask(task2);
        System.out.println(task2.toString());

        manager.changeSubtaskStatus(epic1.getId(), subtask.getId(), Task.IN_PROGRESS_STATUS);
        System.out.println(epic1.toString());

        manager.changeSubtaskStatus(epic2.getId(), subtask1.getId(), Task.DONE_STATUS);
        manager.changeSubtaskStatus(epic2.getId(), subtask2.getId(), Task.IN_PROGRESS_STATUS);
        System.out.println(epic2.toString());

        manager.removeIdEpic(epic1.getId());
        manager.removeIdTask(task1.getId());

        System.out.println(manager.getEpics());
        System.out.println(manager.getTasks());
    }
}
