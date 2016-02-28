/*
 * PROGRAMA QUE MODELA UNA ESFERA 
 * LA ESFERA ESTA DELIMITADA POR LOS PUNTOS DE UN POLIGONO DE N LADOS
 * CADA POLIGONO ESTA UNIDO POR M ARISTAS VERTICALES
 * 
 * LOS VALORES N,M SON RECIBIDOS COMO PARAMETOS
 *
 * Karen Abarca Garcia A01323627
 * Fernando Arey Duran A00397411
 */

import javax.swing.JFrame;
import javax.swing.JPanel;

class Loader{
      public static void main(String[] args) {

            WireframeJApplet applet = new WireframeJApplet();
            
            //Parametros
            int cv = Integer.parseInt(args[0]);
            int g = Integer.parseInt(args[1]);

            if ((cv % 2 != 0) && (cv >= 3) && (g >= 3)){
                  applet.init(cv, g);  
                  final JFrame frame = new JFrame("ESFERA");
                  frame.setContentPane(applet.getContentPane());
                  frame.setJMenuBar(applet.getJMenuBar());
                  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                  frame.setSize(800, 600);
                  frame.setLocation(100, 100);
                  frame.setVisible(true);
                  applet.start();    
            }//Fin if de valores validos

            else {
                  System.out.println("Error. Valores invalidos");
            }
                  
      }//Fin del Main

}//Fin de la clase