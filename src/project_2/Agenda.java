package project_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Agenda {
    public static final String[] DAYS = {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SAB"};
    public static final Task[] TASKS = {Task.FIRE, Task.SOS, Task.PHONE};

    private final Map<Task, Map<String, FireFighter[]>> agenda;
    private final ArrayList<FireFighter> fireFighters;

    public Agenda(ArrayList<FireFighter> fireFighters) {
        this.fireFighters = fireFighters;

        this.agenda = new HashMap<>();
        for (Task task : TASKS) {
            agenda.put(task, new LinkedHashMap<>());

            for (String day : DAYS) {
                agenda.get(task).put(day, new ArrayList<>().toArray(new FireFighter[2]));
            }
        }
    }

    public boolean allocateFirefighters(int dayIdx) {
        if (dayIdx == DAYS.length) {
            return true; // All allocated days
        }

        String currentDay = DAYS[dayIdx];
        FireFighter[] a = new FireFighter[TASKS.length*2];
        for (int i = 0; i < TASKS.length*2; i++) {
            a[i] = (null);
        }

        return allocateToDay(dayIdx, currentDay, 0, a, 0);
    }

    private boolean allocateToDay(int dayIdx, String day, int taskIdx, FireFighter[] fireFightersDay, int taskFireFighterIdx) {
        if (taskIdx == TASKS.length) {
            return this.allocateFirefighters(dayIdx + 1); // All allocated tasks
        }

        Task currentTask = TASKS[taskIdx];

        for (FireFighter f1 : fireFighters) {
            if (this.canAllocateMorning(fireFightersDay, f1, currentTask)) {
                this.agenda.get(currentTask).get(day)[0] = f1;
                f1.allocate(currentTask);
                fireFightersDay[taskFireFighterIdx++] = f1;

                for (FireFighter f2 : fireFighters) {
                    if (this.canAllocateAfternoon(fireFightersDay, f2, currentTask)) {

                        this.agenda.get(currentTask).get(day)[1] = f2;
                        f2.allocate(currentTask);
                        fireFightersDay[taskFireFighterIdx++] = f2;

                        if (this.allocateToDay(dayIdx, day, taskIdx + 1, fireFightersDay, taskFireFighterIdx)) {
                            return true;
                        }

                        this.agenda.get(currentTask).get(day)[1] = null;
                        f2.deallocate(currentTask);
                        fireFightersDay[--taskFireFighterIdx] = null;
                    }

                }
                this.agenda.get(currentTask).get(day)[0] = null;
                f1.deallocate(currentTask);
                fireFightersDay[--taskFireFighterIdx] = null;

            }

        }

        return false; // couldn't allocate
    }

    private boolean canAllocateMorning(FireFighter[] fireFightersDay, FireFighter f, Task currentTask) {
        return f.canBeAllocated(currentTask)
                && (fireFightersDay[0] == null || !fireFightersDay[0].getName().equals(f.getName()))
                && (fireFightersDay[2] == null || !fireFightersDay[2].getName().equals(f.getName()))
                && (fireFightersDay[4] == null || !fireFightersDay[4].getName().equals(f.getName()));
    }

    private boolean canAllocateAfternoon(FireFighter[] fireFightersDay, FireFighter f, Task currentTask) {
        return f.canBeAllocated(currentTask)
                && (fireFightersDay[1] == null || !fireFightersDay[1].getName().equals(f.getName()))
                && (fireFightersDay[3] == null || !fireFightersDay[3].getName().equals(f.getName()))
                && (fireFightersDay[5] == null || !fireFightersDay[5].getName().equals(f.getName()));
    }

    public Map<Task, Map<String, FireFighter[]>> getAgenda() {
        return this.agenda;
    }

}
