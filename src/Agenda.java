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
            Map<String, FireFighter[]> dayMap = new LinkedHashMap<>();
            for (String day : DAYS) {
                dayMap.put(day, new FireFighter[2]);
            }
            agenda.put(task, dayMap);
        }
    }

    /**
     * Inicia a alocação, percorrendo as tarefas (tasks) e, para cada tarefa,
     * aloca os bombeiros para todos os dias.
     *
     * @return true se a alocação for completa, false caso contrário.
     */
    public boolean allocateFirefighters() {
        return allocateTasks(0, 0);
    }

    /**
     * Método recursivo que percorre as tarefas e os dias.
     *
     * @param taskIdx índice da tarefa atual.
     * @param dayIdx  índice do dia atual para a tarefa.
     * @return true se conseguir alocar para todas as tarefas e todos os dias; false, caso contrário.
     */
    private boolean allocateTasks(int taskIdx, int dayIdx) {
        // Se todas as tarefas foram processadas, a alocação está completa.
        if (taskIdx == TASKS.length) {
            return true;
        }
        // Se concluímos todos os dias para a tarefa atual, avançamos para a próxima tarefa.
        if (dayIdx == DAYS.length) {
            return allocateTasks(taskIdx + 1, 0);
        }

        Task currentTask = TASKS[taskIdx];
        String currentDay = DAYS[dayIdx];


        for (FireFighter morningCandidate : fireFighters) {
            if (!morningCandidate.canBeAllocated(currentTask)) {
                continue;
            }

            if (isAssignedInDayShift(morningCandidate, currentDay, 0)) {
                continue;
            }

            agenda.get(currentTask).get(currentDay)[0] = morningCandidate;
            morningCandidate.allocate(currentTask);


            for (FireFighter afternoonCandidate : fireFighters) {
                if (!afternoonCandidate.canBeAllocated(currentTask)) {
                    continue;
                }

                if (afternoonCandidate.getName().equals(morningCandidate.getName())) {
                    continue;
                }

                if (isAssignedInDayShift(afternoonCandidate, currentDay, 1)) {
                    continue;
                }


                agenda.get(currentTask).get(currentDay)[1] = afternoonCandidate;
                afternoonCandidate.allocate(currentTask);


                if (allocateTasks(taskIdx, dayIdx + 1)) {
                    return true;
                }

                agenda.get(currentTask).get(currentDay)[1] = null;
                afternoonCandidate.deallocate(currentTask);
            }

            agenda.get(currentTask).get(currentDay)[0] = null;
            morningCandidate.deallocate(currentTask);
        }
        return false;
    }

    /**
     * Verifica se um bombeiro já foi alocado em algum task no mesmo dia e turno.
     *
     * @param candidate O bombeiro candidato.
     * @param day       O dia em questão.
     * @param shift     O turno (0 para manhã, 1 para tarde).
     * @return true se o candidato já foi escalado no turno para o dia; false caso contrário.
     */
    private boolean isAssignedInDayShift(FireFighter candidate, String day, int shift) {
        for (Task task : TASKS) {
            FireFighter assigned = agenda.get(task).get(day)[shift];
            if (assigned != null && assigned.getName().equals(candidate.getName())) {
                return true;
            }
        }
        return false;
    }

    public Map<Task, Map<String, FireFighter[]>> getAgenda() {
        return agenda;
    }
}