import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.io.Serializable;
import java.util.*;

public class ClubFairSetupPlanner implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for (Project project : projectList) {
            int[] schedule = project.getEarliestSchedule();
            project.printSchedule(schedule);
        }
    }

    /**
     * Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    //God i love xml parsing
    public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();

        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList projectNodes = doc.getElementsByTagName("Project");

            for (int i = 0; i < projectNodes.getLength(); i++) {
                Node projectNode = projectNodes.item(i);
                if (projectNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element projectElement = (Element) projectNode;

                    String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();

                    List<Task> taskList = new ArrayList<>();
                    NodeList taskNodes = projectElement.getElementsByTagName("Task");

                    for (int j = 0; j < taskNodes.getLength(); j++) {
                        Node taskNode = taskNodes.item(j);
                        if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element taskElement = (Element) taskNode;

                            int taskId = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
                            String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();
                            int duration = Integer.parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());

                            List<Integer> dependencies = new ArrayList<>();
                            NodeList dependencyNodes = ((Element) taskElement.getElementsByTagName("Dependencies").item(0))
                                    .getElementsByTagName("DependsOnTaskID");

                            for (int k = 0; k < dependencyNodes.getLength(); k++) {
                                dependencies.add(Integer.parseInt(dependencyNodes.item(k).getTextContent()));
                            }

                            Task task = new Task(taskId, description, duration, dependencies);
                            taskList.add(task);
                        }
                    }

                    Project project = new Project(projectName, taskList);
                    projectList.add(project);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return projectList;
    }


}
