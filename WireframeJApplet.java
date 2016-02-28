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


//Importando librerias
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/*Clase Point3D que define un tipo de valor formado por 3 valores double
  Cada valor representa una coordenada en x,y,z
*/
class Point3D {
   public double x, y, z;
   public Point3D( double X, double Y, double Z ) {
      x = X;  y = Y;  z = Z;
   }
}//Fin de la clase Point3D



/*Clase Edge que define un tipo de valor formado por 2 enteros
  Cada valor representa un punto que sera unido por la arista
*/
class Edge {
   public int a, b;
   public Edge( int A, int B ) {
      a = A;  b = B;
   }
}//Fin de la clase Edge




/*****CLASE PRINCIPAL*****/
public class WireframeJApplet extends JApplet implements KeyListener, FocusListener, MouseListener {
                      
   //CANVAS
   int width, height;           //Ancho y alto del canvas
   boolean focussed = false;   //True when this applet has input focus.
   DisplayPanel canvas;  

   //ESFERA
   int azimuth = 35, elevation = 30; //Elevacion y rotacion de la figura
   Point3D[] vertices;               //Arreglo de puntos
   Edge[] edges;                     //Arreglo de aristas

   

   /****METODO QUE INICIALIZA LOS RECURSOS****/
   /*Parametros: numero de cortes verticales y horizontales*/
   public void init(int cortesHorizontales, int cortesVerticales) {

      /**VALORES FIJOS DE LA ESFERA**/
      double radio = 2; //Radio
      int mitadCortes = cortesHorizontales / 2;

      //Angulos
      double alfaHorizontal = 0.0;
      double alfaVertical = 0.0;
      double alfaVertical2 = 0.0;
      double incrementoAlfa = 360.0 / cortesVerticales;
      double incrementoAlfaV = 90.0 / (mitadCortes + 1);

      //Declarando vertices
      int numVertices = (cortesHorizontales * cortesVerticales) + 2;//N puntos por nivel más los puntos extremos
      vertices = new Point3D[numVertices];

      //Declarando aristas
      int numAristas = (cortesHorizontales * cortesVerticales) + 
                       (cortesVerticales * (cortesHorizontales + 1)); //N aristas por nivel más las uniones
      edges = new Edge[numAristas];



      /**VALORES DEPENDIENTES DE LAS CIRCUNFERENCIAS**/
      double radioCircunferencia = radio;      



      /**DEFINIENDO PUNTOS Y ARISTAS**/

      //Contadores
      int contadorVertices = 0;
      int contadorAristas = 0;

      //Puntos extremos
      vertices[numVertices-2] = new Point3D(0.0, radio, 0.0);
      vertices[numVertices-1] = new Point3D(0.0, -radio, 0.0);


      //FOR QUE RECORRE LOS CORTES HORIZONTALES (PLANO X,Y)
      for(int i = 0; i < cortesHorizontales; i++){ 

         alfaHorizontal = 0.0;

         //Inicializando Coordenadas
         double x = 0.0;
         double y = 0.0;
         double z = 0.0;
         
         
         //DETERMINANDO QUE MITAD DE LA ESFERA SE VA A MANIPULAR
         
         //Centro de la esfera
         if(i == 0); 

         //Mitad superior
         else if (i <= mitadCortes){ 
            alfaVertical += incrementoAlfaV;                                        //Se aumenta angulo
            radioCircunferencia = radio * Math.cos(Math.toRadians(alfaVertical));  //Se calcula el radio
            y = radio* Math.sin(Math.toRadians(alfaVertical));                    //Se calcula la coordenada 'y'
         }
            
         //Mitad inferior
         else{ 
            alfaVertical2 -= incrementoAlfaV;                                        //Se disminuye angulo      
            radioCircunferencia = radio * Math.cos(Math.toRadians(alfaVertical2));  //Se calcula el radio
            y = radio * Math.sin(Math.toRadians(alfaVertical2));                    //Se calcula la coordenada 'y'
         }
         
         
         //Puntos de union
         int vertice1 = cortesVerticales * i;
         int vertice2 = 0;

         

         /*CREANDO LOS CIRCULOS HORIZONTALES*/
         //FOR QUE RECORRE LOS CORTES VERTICALES (PLANO X,Z)
         for(int j = 0; j < cortesVerticales; j++){ 

            //Calculando coordenadas
            x = (Math.cos(Math.toRadians(alfaHorizontal)) * radioCircunferencia);
            z = (Math.sin(Math.toRadians(alfaHorizontal)) * radioCircunferencia);

            //Declarando vertice y arista
            vertices[contadorVertices] = new Point3D(x,y,z);
            edges[contadorAristas] = new Edge(contadorVertices, (contadorVertices + 1) % cortesVerticales + (i * cortesVerticales));

            //Cambiando angulo
            alfaHorizontal = (alfaHorizontal + incrementoAlfa) % 360;

            contadorAristas++;
            contadorVertices++;


            //CREANDO ARISTAS VERTICALES QUE UNEN LOS PUNTOS EXTREMOS
            //Circulo superior
            if (i == mitadCortes){ 
               vertice2 = numVertices - 2; //Se une con el punto extremo superior
               edges[contadorAristas] = new Edge(vertice1++, vertice2);
               contadorAristas++;
            }

            //Circulo inferior
            else if ((cortesHorizontales - i) == 1){ 
               vertice2 = numVertices - 1; //Se une con el punto extremo inferior
               edges[contadorAristas] = new Edge(vertice1++, vertice2);
               contadorAristas++;
            }

         }//Fin for que recorre los cortes verticales

      }//Fin for que recorre los cortes horizontales


      

      /*CREANDO LAS ARISTAS VERTICALES*/
      //FOR QUE RECORRE LOS NIVELES HORIZONTALES
      for (int i = 1; i < cortesHorizontales; i++) {
         
         //Declarando puntos de union
         int vertice1 = cortesVerticales * i;
         int vertice2 = 0;

         //FOR QUE RECORRE LOS CORTES VERTICALES
         for (int k = 0; k < cortesVerticales; k++) {
            
            //Circulos superiores e inferiores
            if( (i <=  mitadCortes) || ( i >  (mitadCortes + 1)) ){
               vertice2 = vertice1 - (cortesVerticales);
               edges[contadorAristas] = new Edge(vertice1++, vertice2++);
               contadorAristas++;
            }

            //Circulo que esta inmediatamente abajo del centro
            else if( i ==  (mitadCortes + 1)){
               vertice2 = vertice1 - (cortesVerticales * i);
               edges[contadorAristas] = new Edge(vertice1++, vertice2++);
               contadorAristas++;
            }

         }//Fin for que recorre los cortes verticales

      }//Fin for que recorre los cortes horizontales
      


      // Create drawing surface and install it as the applet's content pane.
      canvas = new DisplayPanel();  
      setContentPane(canvas);       
   
      // Set up the applet to listen for events from the canvas.
      canvas.addFocusListener(this);   
      canvas.addKeyListener(this);      
      canvas.addMouseListener(this);
      
   } // end init();


   


   /*CLASE QUE CREA EL FRAME PARA REALIZAR LOS TRAZOS DE LA FIGURA*/
   class DisplayPanel extends JPanel {

      /*METODO QUE TRAZA LO NECESARIO PARA DESPLEGAR LA FIGURA*/
      public void paintComponent(Graphics g) {
         
         //Constructor
         super.paintComponent(g);  


         //Verificando enfoque
         if (focussed) 
            g.setColor(Color.cyan);
         else
            g.setColor(Color.lightGray);


         int width = getSize().width;  // Width of the applet.
         int height = getSize().height; // Height of the applet.


         //Dibujando borde
         g.drawRect(0,0,width-1,height-1);
         g.drawRect(1,1,width-3,height-3);
         g.drawRect(2,2,width-5,height-5);


         //Acciones si el frame no esta activo
         if (!focussed) {
            g.setColor(Color.magenta);
            g.drawString("Click to activate",100,120);
            g.drawString("Use arrow keys to change azimuth and elevation",100,150);

         }


         //Acciones cuando el frame se activa
         else {

            //Calculando orientacion y perspectiva
            double theta = Math.PI * azimuth / 180.0;
            double phi = Math.PI * elevation / 180.0;
            float cosT = (float)Math.cos( theta ), sinT = (float)Math.sin( theta );
            float cosP = (float)Math.cos( phi ), sinP = (float)Math.sin( phi );
            float cosTcosP = cosT*cosP, cosTsinP = cosT*sinP,
                  sinTcosP = sinT*cosP, sinTsinP = sinT*sinP;


            //Project vertices onto the 2D viewport
            Point[] points = new Point[ vertices.length ];
            int scaleFactor = width/8;
            float near = 10;         // distance from eye to near plane
            float nearToObj = 10f;  // distance from near plane to center of object


            //Recorriendo arreglo de vertices
            for (int j = 0; j < vertices.length; ++j ) {

               //Obteniendo coordenadas y almacenandolas en un arreglo
               double x0 = vertices[j].x;
               double y0 = vertices[j].y;
               double z0 = vertices[j].z;

               //Compute an orthographic projection
               double x1 = cosT*x0 + sinT*z0;
               double y1 = -sinTsinP*x0 + cosP*y0 + cosTsinP*z0;

               //Now adjust things to get a perspective projection
               double z1 = cosTcosP*z0 - sinTcosP*x0 - sinP*y0;
               x1 = x1*near/(z1+near+nearToObj);
               y1 = y1*near/(z1+near+nearToObj);

               //the 0.5 is to round off when converting to int
               points[j] = new Point(
                  (int)(width/2 + scaleFactor*x1 + 0.5),
                  (int)(height/2 - scaleFactor*y1 + 0.5)
               );

            }//Fin del for



            //Dibujando fondo del wireframe
            g.setColor( Color.black );
            g.fillRect( 0, 0, width, height );

            //Trazando Esfera
            for (int j = 0; j < edges.length; j++ ) { //Se recorren las aristas
               g.setColor(Color.white);
               g.drawLine(
                  points[ edges[j].a ].x, points[ edges[j].a ].y,
                  points[ edges[j].b ].x, points[ edges[j].b ].y
               );

            }//Fin del for que recorre las aristas para trazar la esfera
           
         }//Fin else del frame activo

      } //End paintComponent()    

    } //End nested class DisplayPanel 



   /**EVENT HANDLING METHODS**/
   
   //Metodos para saber si el frame esta activo o no
   public void focusGained(FocusEvent evt) {
      focussed = true;
      canvas.repaint();
   }
   public void focusLost(FocusEvent evt) {
      focussed = false;
      canvas.repaint(); 
   }
      

   //Metodos para detectar la seleccion de una tecla
   public void keyPressed(KeyEvent evt) { 
       
      int key = evt.getKeyCode(); // keyboard code for the key that was pressed
      
      if (key == KeyEvent.VK_LEFT) {
         azimuth += 5;
         canvas.repaint();         
      }
      else if (key == KeyEvent.VK_RIGHT) {
         azimuth -= 5;
         canvas.repaint();         
      }
      else if (key == KeyEvent.VK_UP) {
         elevation -= 5;
         canvas.repaint();         
      }
      else if (key == KeyEvent.VK_DOWN) {
         elevation += 5;         
         canvas.repaint();
      }

   }  // end keyPressed()



   //Metodos vacios requeridos por la interfaz KeyListener
   public void keyTyped(KeyEvent evt) {  
   }  
   public void keyReleased(KeyEvent evt) { 
   }
   


   /*METODOS REQUERIDOS POR LA INTERFAZ MOUSELISTENER*/

   /*Metodo que detecta cuando el mouse es presionado y activa el foco en el canvas*/
   public void mousePressed(MouseEvent evt) {      
      canvas.requestFocus();
   }      

   public void mouseEntered(MouseEvent evt) { }  
   public void mouseExited(MouseEvent evt) { }   
   public void mouseReleased(MouseEvent evt) { } 
   public void mouseClicked(MouseEvent evt) { }
   public void mouseDragged( MouseEvent e ) { }

} // end class 
