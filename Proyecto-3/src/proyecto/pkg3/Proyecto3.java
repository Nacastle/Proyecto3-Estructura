package proyecto.pkg3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
                while((aux = read.readLine()) != null){
                    //graph.setAutoCreate(true);
                    list = nombres(aux);
                    if (!nodosCreados.contains(list[0])) {
                        nodosCreados.add(list[0]);
                        graph.addNode(list[0]);
                    }if (!nodosCreados.contains(list[1])){
                        nodosCreados.add(list[1]);
                        graph.addNode(list[1]);
                    }
                    //nombres(aux);
                    graph.addEdge(aux, list[0], list[1]);
                    texto += aux + "\n";
                }
                read.close();
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,e+"" +
           "\nNo se ha encontrado el archivo",
                 "ADVERTENCIA!!!",JOptionPane.WARNING_MESSAGE);
        }
        
        graph.display(true);
    }
    
    public static String[] nombres(String nombres){
        String lista[] = new String[2];
        int pos1 = nombres.indexOf(',');
        lista[0] = nombres.substring(0,pos1);
        lista[1] = nombres.substring(pos1+1);

        return lista;
    }

}
