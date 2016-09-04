package proyecto.pkg3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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

        int op = Integer.parseInt(JOptionPane.showInputDialog(null, "1.Ver si son amigos\n2.Camino mas corto a un amigo\nCualquier otro para salir\nIngrese una opcion:"));

        switch (op) {
            case 1:
                
                break;
            case 2:
                String nombre1 = JOptionPane.showInputDialog(null, "Ingrese de donde quiere comenzar");
                String nombre2 = JOptionPane.showInputDialog(null, "Ingrese hasta donde quiere llegar, para mostrar el camino de esas personas.");
                Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
                dijkstra.init(graph);
                dijkstra.setSource(graph.getNode(nombre1));
                dijkstra.compute();
                JOptionPane.showMessageDialog(null, dijkstra.getPath(graph.getNode(nombre2)));
                //System.out.println(dijkstra.getPath(graph.getNode("rafael")));
                break;
            default:
                JOptionPane.showMessageDialog(null, "Gracias por utilizar este programa");
                System.exit(0);
                break;
        }
    }

    public static String[] nombres(String nombres) {
        String lista[] = new String[2];
        int pos1 = nombres.indexOf(',');
        lista[0] = nombres.substring(0, pos1);
        lista[1] = nombres.substring(pos1 + 1);

        return lista;
    }

}
