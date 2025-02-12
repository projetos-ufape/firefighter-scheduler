package project_2;

import java.util.HashMap;
import java.util.Map;

public class FireFighter {
    private final String name;
    private final Map<Task, Integer> tasks;

    FireFighter(String name, int fire, int sos, int phone) {
        this.name = name;
        tasks = new HashMap<>();
        tasks.put(Task.FIRE, fire);
        tasks.put(Task.SOS, sos);
        tasks.put(Task.PHONE, phone);
    }

    public String getName() {
        return this.name;
    }

    public boolean canBeAllocated(Task task) {
        return this.tasks.get(task) > 0;
    }

    public void allocate(Task task) {
        int value = this.tasks.get(task);
        this.tasks.put(task, --value);
    }

    public void deallocate(Task task) {
        int value = this.tasks.get(task);
        this.tasks.put(task, ++value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(this.name).append(" | ");

        for (Map.Entry<Task, Integer> entry : tasks.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
        }

        return sb.toString();
    }
}
