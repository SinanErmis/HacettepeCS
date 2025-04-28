import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class AlienFlora {
    private File xmlFile;
    private List<GenomeCluster> clusters = new ArrayList<>();

    public AlienFlora(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void readGenomes() {
        // DONE:
        // - Parse XML
        // - Read genomes and links
        // - Create clusters
        // - Print number of clusters and their genome IDs
        System.out.println("##Start Reading Flora Genomes##");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList genomeList = doc.getElementsByTagName("genome");
            Map<String, Genome> allGenomes = new HashMap<>();

            for (int i = 0; i < genomeList.getLength(); i++) {
                Element genomeElement = (Element) genomeList.item(i);
                String id = genomeElement.getElementsByTagName("id").item(0).getTextContent();
                int evolutionFactor = Integer.parseInt(genomeElement.getElementsByTagName("evolutionFactor").item(0).getTextContent());
                Genome genome = new Genome(id, evolutionFactor);

                NodeList links = genomeElement.getElementsByTagName("link");
                for (int j = 0; j < links.getLength(); j++) {
                    Element linkElement = (Element) links.item(j);
                    String target = linkElement.getElementsByTagName("target").item(0).getTextContent();
                    int adaptationFactor = Integer.parseInt(linkElement.getElementsByTagName("adaptationFactor").item(0).getTextContent());
                    genome.addLink(target, adaptationFactor);
                }
                allGenomes.put(id, genome);
            }

            // Build undirected graph
            Map<String, List<String>> adjacencyList = new HashMap<>();
            for (String id : allGenomes.keySet()) {
                adjacencyList.put(id, new ArrayList<>());
            }

            for (Genome genome : allGenomes.values()) {
                for (Genome.Link link : genome.links) {
                    adjacencyList.get(genome.id).add(link.target);
                    adjacencyList.get(link.target).add(genome.id); // !!! reverse connection
                }
            }

            // Find clusters using BFS
            Set<String> visited = new HashSet<>();
            for (String genomeId : allGenomes.keySet()) {
                if (!visited.contains(genomeId)) {
                    GenomeCluster cluster = new GenomeCluster();
                    Queue<String> queue = new LinkedList<>();
                    queue.add(genomeId);
                    visited.add(genomeId);
                    while (!queue.isEmpty()) {
                        String currentId = queue.poll();
                        cluster.addGenome(allGenomes.get(currentId));
                        for (String neighbor : adjacencyList.get(currentId)) {
                            if (!visited.contains(neighbor)) {
                                visited.add(neighbor);
                                queue.add(neighbor);
                            }
                        }
                    }
                    clusters.add(cluster);
                }
            }

            System.out.println("Number of Genome Clusters: " + clusters.size());
            List<List<String>> clusterIds = new ArrayList<>();
            for (GenomeCluster cluster : clusters) {
                clusterIds.add(new ArrayList<>(cluster.genomeMap.keySet()));
            }
            System.out.println("For the Genomes: " + clusterIds);
            System.out.println("##Reading Flora Genomes Completed##");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluateEvolutions() {
        // DONE:
        // - Parse and process possibleEvolutionPairs
        // - Find min evolution genome in each cluster
        // - Calculate and print evolution factors

        System.out.println("##Start Evaluating Possible Evolutions##");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList pairList = ((Element)doc.getElementsByTagName("possibleEvolutionPairs").item(0)).getElementsByTagName("pair");
            List<Double> results = new ArrayList<>();
            int certifiedCount = 0;

            for (int i = 0; i < pairList.getLength(); i++) {
                Element pair = (Element) pairList.item(i);
                String firstId = pair.getElementsByTagName("firstId").item(0).getTextContent();
                String secondId = pair.getElementsByTagName("secondId").item(0).getTextContent();

                GenomeCluster cluster1 = null;
                GenomeCluster cluster2 = null;

                for (GenomeCluster cluster : clusters) {
                    if (cluster.contains(firstId)) cluster1 = cluster;
                    if (cluster.contains(secondId)) cluster2 = cluster;
                }

                if (cluster1 != cluster2) {
                    assert cluster1 != null;
                    Genome min1 = cluster1.getMinEvolutionGenome();
                    assert cluster2 != null;
                    Genome min2 = cluster2.getMinEvolutionGenome();
                    double average = (min1.evolutionFactor + min2.evolutionFactor) / 2.0;
                    results.add(average);
                    certifiedCount++;
                } else {
                    results.add(-1.0);
                }
            }

            System.out.println("Number of Possible Evolutions: " + results.size());
            System.out.println("Number of Certified Evolution: " + certifiedCount);
            System.out.println("Evolution Factor for Each Evolution Pair: " + results);
            System.out.println("##Evaluated Possible Evolutions##");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluateAdaptations() {
        // DONE:
        // - Parse and process possibleAdaptationPairs
        // - If genomes in same cluster, use Dijkstra to calculate min path
        // - Print adaptation factors

        System.out.println("##Start Evaluating Possible Adaptations##");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList pairList = ((Element)doc.getElementsByTagName("possibleAdaptationPairs").item(0)).getElementsByTagName("pair");
            List<Integer> results = new ArrayList<>();
            int certifiedCount = 0;

            for (int i = 0; i < pairList.getLength(); i++) {
                Element pair = (Element) pairList.item(i);
                String firstId = pair.getElementsByTagName("firstId").item(0).getTextContent();
                String secondId = pair.getElementsByTagName("secondId").item(0).getTextContent();

                GenomeCluster cluster1 = null;
                GenomeCluster cluster2 = null;

                for (GenomeCluster cluster : clusters) {
                    if (cluster.contains(firstId)) cluster1 = cluster;
                    if (cluster.contains(secondId)) cluster2 = cluster;
                }

                if (cluster1 != null && cluster1 == cluster2) {
                    int minAdaptation = cluster1.dijkstra(firstId, secondId);
                    results.add(minAdaptation);
                    certifiedCount++;
                } else {
                    results.add(-1);
                }
            }

            System.out.println("Number of Possible Adaptations: " + results.size());
            System.out.println("Number of Certified Adaptations: " + certifiedCount);
            System.out.println("Adaptation Factor for Each Adaptation Pair: " + results);
            System.out.println("##Evaluated Possible Adaptations##");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
