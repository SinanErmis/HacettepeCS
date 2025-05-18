import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;

        int[] startTimes = getEarliestSchedule();

        for (Task task : tasks) {
            int endTime = startTimes[task.getTaskID()] + task.getDuration();
            projectDuration = Math.max(projectDuration, endTime);
        }

        return projectDuration;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        int n = tasks.size();
        int[] earliestStart = new int[n];
        int[] inDegree = new int[n];
        int[] endTimes = new int[n];

        // Map taskID to Task for quick access
        Map<Integer, Task> taskMap = new HashMap<>();
        for (Task task : tasks) {
            taskMap.put(task.getTaskID(), task);
        }

        // Build dependency graph and compute in-degrees
        Map<Integer, List<Integer>> adj = new HashMap<>();
        for (Task task : tasks) {
            for (int dep : task.getDependencies()) {
                adj.computeIfAbsent(dep, k -> new ArrayList<>()).add(task.getTaskID());
                inDegree[task.getTaskID()]++;
            }
        }

        // Topological sort
        Queue<Integer> queue = new LinkedList<>();
        for (Task task : tasks) {
            if (inDegree[task.getTaskID()] == 0) {
                queue.offer(task.getTaskID());
            }
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();
            Task currentTask = taskMap.get(current);
            int currentStart = earliestStart[current];
            int currentEnd = currentStart + currentTask.getDuration();
            endTimes[current] = currentEnd;

            // Update dependent tasks
            if (adj.containsKey(current)) {
                for (int neighbor : adj.get(current)) {
                    earliestStart[neighbor] = Math.max(earliestStart[neighbor], currentEnd);
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return earliestStart;
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY! 
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-3d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
