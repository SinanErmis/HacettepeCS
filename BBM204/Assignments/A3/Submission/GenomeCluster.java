import java.util.*;

public class GenomeCluster {
    public Map<String, Genome> genomeMap = new HashMap<>();

    public void addGenome(Genome genome) {
        // DONE: Add genome to the cluster
        genomeMap.put(genome.id, genome);
    }

    public boolean contains(String genomeId) {
        // DONE: Return true if the genome is in the cluster
        return genomeMap.containsKey(genomeId);
    }

    public Genome getMinEvolutionGenome() {
        // DONE: Return the genome with minimum evolutionFactor
        Genome minGenome = null;
        for (Genome genome : genomeMap.values()) {
            if (minGenome == null || genome.evolutionFactor < minGenome.evolutionFactor) {
                minGenome = genome;
            }
        }
        return minGenome;
    }

    public int dijkstra(String startId, String endId) {
        // DONE: Implement Dijkstra's algorithm to return shortest path
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (String id : genomeMap.keySet()) {
            distances.put(id, Integer.MAX_VALUE);
        }

        distances.put(startId, 0);
        pq.add(startId);

        while (!pq.isEmpty()) {
            String currentId = pq.poll();
            Genome currentGenome = genomeMap.get(currentId);

            for (Genome.Link link : currentGenome.links) {
                if (!genomeMap.containsKey(link.target)) continue;
                int newDist = distances.get(currentId) + link.adaptationFactor;
                if (newDist < distances.get(link.target)) {
                    distances.put(link.target, newDist);
                    pq.add(link.target);
                }
            }
        }

        return distances.get(endId) == Integer.MAX_VALUE ? -1 : distances.get(endId);
    }
}
