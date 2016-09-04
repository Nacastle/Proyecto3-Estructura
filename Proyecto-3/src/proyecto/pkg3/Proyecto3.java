package proyecto.pkg3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 *
 * @author Alexis
 */
public class Proyecto3 {

    public static void main(String[] args) {
        Graph graph = new DefaultGraph("Amigos");
        String aux = "";
        String texto = "";
        ArrayList<String> nodosCreados = new ArrayList();
        String list[] = new String[2];
        try {
            JFileChooser file = new JFileChooser();
            file.showOpenDialog(file);

            File abrir = file.getSelectedFile();
            if (abrir != null) {
                FileReader archivo = new FileReader(abrir);
                BufferedReader read = new BufferedReader(archivo);
                while ((aux = read.readLine()) != null) {
                    //graph.setAutoCreate(true);
                    list = nombres(aux);
                    if (!nodosCreados.contains(list[0])) {
                        nodosCreados.add(list[0]);
                        graph.addNode(list[0]);
                    }
                    if (!nodosCreados.contains(list[1])) {
                        nodosCreados.add(list[1]);
                        graph.addNode(list[1]);
                    }
                    //nombres(aux);
                    graph.addEdge(aux, list[0], list[1]).addAttribute("length", 15);

                    texto += aux + "\n";
                }
                read.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + ""
                    + "\nNo se ha encontrado el archivo",
                    "ADVERTENCIA!!!", JOptionPane.WARNING_MESSAGE);
        }

        for (Node n : graph) {
            n.addAttribute("label", n.getId());
        }
        graph.display(true);
        int op;
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog(null, "1.Ver si son amigos\n2.Camino mas corto a un amigo\nCualquier otro para salir\nIngrese una opcion:"));

            switch (op) {
                case 1:
                    String nombres1 = JOptionPane.showInputDialog(null, "Ingrese el nombre de la primera persona");
                    String nombres2 = JOptionPane.showInputDialog(null, "Ingrese el nombre de la segunda persona para ver si son amigos.");
                    Dijkstra dijkstras = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
                    dijkstras.init(graph);
                    dijkstras.setSource(graph.getNode(nombres1));
                    dijkstras.compute();
                    List<Node> list1 = new ArrayList<Node>();
                    for (Node node : dijkstras.getPathNodes(graph.getNode(nombres2))) {
                        list1.add(0, node);
                    }
                    if (list1.size() == 2) {
                        JOptionPane.showMessageDialog(null, "Son amigos");
                    } else {
                        JOptionPane.showMessageDialog(null, "No son amigos :(");
                    }
                    break;
                case 2:
                    String nombre1 = JOptionPane.showInputDialog(null, "Ingrese de donde quiere comenzar");
                    String nombre2 = JOptionPane.showInputDialog(null, "Ingrese hasta donde quiere llegar, para mostrar el camino de esas personas.");
                    Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
                    dijkstra.init(graph);
                    dijkstra.setSource(graph.getNode(nombre1));
                    dijkstra.compute();
                    JOptionPane.showMessageDialog(null, dijkstra.getPath(graph.getNode(nombre2)));
                    dijkstra.clear();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Gracias por utilizar este programa");
                    System.exit(0);
                    break;
            }
        } while (op == 1 || op == 2);
    }

    public static String[] nombres(String nombres) {
        String lista[] = new String[2];
        int pos1 = nombres.indexOf(',');
        lista[0] = nombres.substring(0, pos1);
        lista[1] = nombres.substring(pos1 + 1);

        return lista;
    }

}
