import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    HashMap<String, ArrayList<Lugar> > lugaresAll = new HashMap<String, ArrayList<Lugar> >();
    float costoTotal;

    Comparator<Lugar > comparator = new Comparator<Lugar>() {
        @Override
        public int compare(Lugar P1, Lugar P2) {
            return P1.getDistRect() < P2.getDistRect() ? -1:1;
        }
    };

    public Main(HashMap<String, ArrayList<Lugar> > lugaresAll) {
        this.lugaresAll = lugaresAll;
    }
    
    public Main(){ }


    public void addLugares(Lugar lugar){
        ArrayList<Lugar> lugares = new ArrayList<>();
        lugares.add(lugar);
        lugaresAll.put(lugar.getNombre(), lugares);
    }

   public String getOrigen(String origen){
        return origen.split("-")[0];
    }
  
    public String getDestino(String destino){ return destino.split("-")[1]; }
   
    public void Conexion(String origen, String destino, int transicionCosto){
        Lugar primerLugar =
                new Lugar (lugaresAll.get(destino).get(0).getNombre(),transicionCosto,  lugaresAll.get(destino).get(0).distRect );
        primerLugar.setCosto(transicionCosto);
        lugaresAll.get(origen).add(   primerLugar   );

        Lugar segundoLugar =
                new Lugar (lugaresAll.get(origen).get(0).getNombre(),transicionCosto,  lugaresAll.get(origen).get(0).distRect );
        segundoLugar.setCosto(transicionCosto);
        lugaresAll.get(destino).add(  segundoLugar  );
    }

    public boolean AEstrella(Lugar origen, Lugar objetivo){
        HashMap<String, Boolean> visitados = new HashMap<>();
        String currentNodo= "Inicio";
        String nodoAnterior="";
        LinkedList<Lugar> queue = new LinkedList<Lugar>();
        Lugar aux= new Lugar();
        float costoTotal=0;
        lugaresAll.forEach((nombreLugar,lugaresDisponibles)->{
            visitados.put(nombreLugar,false);
        });
        visitados.replace(origen.getNombre(),true);
        queue.push(origen); 
        while (queue.size() >0 && !currentNodo.equals(objetivo.getNombre()) ){

            nodoAnterior = currentNodo;
            aux =  (queue.poll() );
            currentNodo= aux.getNombre();
            costoTotal+=aux.costo;
            System.out.println(nodoAnterior+"->"+currentNodo+"  (Costo: "+aux.costo+" , dist. a : "+aux.distRect+")" );
       
            visitados.put(currentNodo,true);

            LinkedList<Lugar> auxQueue = new LinkedList<>();

            lugaresAll.get(currentNodo).forEach( (ciudad)->{
                if(!visitados.get(ciudad.nombre) ){

                    auxQueue.push(ciudad);
                }
            });

            auxQueue.sort(comparator);
            queue.addAll(0,auxQueue);

        }
        printSumario(currentNodo,costoTotal);
        return currentNodo.equals(objetivo.getNombre());
    }





    public void printSumario(String destinoFinal, float distTotal){
        System.out.println("Distancia Total: "+distTotal);
        System.out.println("Destino Final: "+destinoFinal);
    }



    public static void main(String[] args) {
      Lugar acapulco = new Lugar("Acapulco",0,400);
      Lugar aguascalientes = new Lugar("Aguascalientes",0,400);
      Lugar chihuahua = new Lugar("Chihuahua",0,1500);
      Lugar cuernavaca = new Lugar("Cuernavaca",0,300);
      Lugar guadalajara = new Lugar("Guadalajara",0,450);
      Lugar guanajuato = new Lugar("Guanajuato",0,430);
      Lugar  hermosillo = new Lugar("Hermosillo",0,2000);
      Lugar  morelia = new Lugar("Morelia",0,500);
      Lugar  pachuca = new Lugar("Pachuca",0,380);
      Lugar  puebla = new Lugar("Puebla",0,100);
      Lugar  reynosa = new Lugar("Reynosa",0,1300);
      Lugar sanluispotosi = new Lugar("San Luis Potosi",0,750);
      Lugar tepic = new Lugar("Tepic",0,600);
      Lugar veracruz = new Lugar("Veracruz",0,0);
      Lugar zacatecas = new Lugar("Zacatecas",0,800);

        Main grafo = new Main();
       
/* Registro de los grafos */ 
    
      grafo.addLugares(acapulco);
      grafo.addLugares(aguascalientes);
      grafo.addLugares(chihuahua);
      grafo.addLugares(cuernavaca);
      grafo.addLugares(guadalajara);
      grafo.addLugares(guanajuato);
      grafo.addLugares(hermosillo);
      grafo.addLugares(morelia);
      grafo.addLugares(pachuca);
      grafo.addLugares(puebla);
      grafo.addLugares(reynosa);
      grafo.addLugares(sanluispotosi);
      grafo.addLugares(tepic);
      grafo.addLugares(veracruz);
      grafo.addLugares(zacatecas);

/* Conexiones de los grafos */
      grafo.Conexion(acapulco.nombre,cuernavaca.nombre,290);
      grafo.Conexion(acapulco.nombre,puebla.nombre,425);
      
      grafo.Conexion(aguascalientes.nombre,chihuahua.nombre,946);
      grafo.Conexion(aguascalientes.nombre,pachuca.nombre,510);
      grafo.Conexion(aguascalientes.nombre,guadalajara.nombre,220);
      grafo.Conexion(aguascalientes.nombre,hermosillo.nombre,1533);
      grafo.Conexion(aguascalientes.nombre,zacatecas.nombre,543);
      
      grafo.Conexion(chihuahua.nombre,tepic.nombre,1147);
      grafo.Conexion(chihuahua.nombre,reynosa.nombre,1025);
      grafo.Conexion(chihuahua.nombre,hermosillo.nombre,690);
      
      grafo.Conexion(cuernavaca.nombre,puebla.nombre,158);
      grafo.Conexion(cuernavaca.nombre,veracruz.nombre,435);
      grafo.Conexion(cuernavaca.nombre,pachuca.nombre,180);
      
      grafo.Conexion(guadalajara.nombre,tepic.nombre,204);
      
      grafo.Conexion(guanajuato.nombre,pachuca.nombre,373);
      grafo.Conexion(guanajuato.nombre,reynosa.nombre,841);
      grafo.Conexion(guanajuato.nombre,zacatecas.nombre,300);
      
      grafo.Conexion(hermosillo.nombre,zacatecas.nombre,1420);
      
      grafo.Conexion(morelia.nombre,puebla.nombre,427);
      
      grafo.Conexion(pachuca.nombre,sanluispotosi.nombre,424);
      grafo.Conexion(pachuca.nombre,veracruz.nombre,382);
      grafo.Conexion(pachuca.nombre,reynosa.nombre,869);
      
      grafo.Conexion(tepic.nombre,zacatecas.nombre,543);
      
      grafo.Conexion(veracruz.nombre,puebla.nombre,274);
        

        System.out.println("\n");
        //System.out.println("Algoritmo Voraz\n");
        //grafo.AEstrella(hermosillo,veracruz);
       // System.out.println("\n");
        System.out.println("Algoritmo A * \n");
        grafo.AEstrella(hermosillo,veracruz);
    }
}
