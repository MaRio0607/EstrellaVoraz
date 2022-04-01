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

    Comparator<Lugar> compAEstrella = new Comparator<Lugar>() {
        @Override
        public int compare(Lugar P1, Lugar P2) {
            return (P1.getDistRect()+P2.getCosto()) < ( P2.getDistRect()+P2.getCosto() ) ? -1:1 ;
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

    public boolean Voraz(Lugar origen, Lugar objetivo){
        HashMap<String, Boolean> visitados = new HashMap<>();
        String currentNodo= "Se comienza en: ";
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
            System.out.println(nodoAnterior+"->"+currentNodo+"  (Costo: "+aux.costo+" , dist. a Puebla: "+aux.distRect+")" );
       
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

    public boolean AEstrella(Lugar origen, Lugar objetivo){
        HashMap<String, Boolean> visitados = new HashMap<>();
        String nodoActual= "Inicio del viaje";
        LinkedList<Lugar> queue = new LinkedList<Lugar>();
        Lugar aux= new Lugar();
        float costoTotal=0;
        lugaresAll.forEach((nombreLugar,lugaresDisponibles)->{
            visitados.put(nombreLugar,false);
        });

        visitados.replace(origen.getNombre(),true);
        queue.push(new Lugar("Inicio-"+origen.getNombre(),origen.getCosto(),origen.getDistRect()) ); //insertar nodo inicial;
        //Iterar
        while (queue.size() >0 && !getOrigen(nodoActual).equals(objetivo.getNombre()) ){
            //Crear historial y push
            aux =  (queue.poll() );
            nodoActual= aux.getNombre();
            if(getOrigen(nodoActual).equals(objetivo.getNombre())) break;
            costoTotal+=aux.costo;
            System.out.println( nodoActual+"  (f(n) = "+ (aux.getCosto()+aux.getDistRect()) +")" );

            visitados.put( getDestino(nodoActual) ,true);

            String finalCurrentNodo = nodoActual;
            LinkedList<Lugar> auxQueue = new LinkedList<>();

            lugaresAll.get( getDestino(finalCurrentNodo) ).forEach( (ciudad)->{
                if(!visitados.get(ciudad.nombre) ){

                    auxQueue.push( new Lugar(getDestino(finalCurrentNodo)+"-"+ciudad.getNombre(),ciudad.getCosto(),ciudad.distRect) );
                }
            });
            auxQueue.sort(compAEstrella);
            queue.addAll(auxQueue);
        }
        printSumario(nodoActual,costoTotal);
        return getOrigen(nodoActual).equals(objetivo.getNombre());
    }



    public void printSumario(String destinoFinal, float distTotal){
        System.out.println("Distancia Total: "+distTotal);
        System.out.println("Destino Final: "+destinoFinal);
    }




    public static void main(String[] args) {
        Lugar hermosillo = new Lugar("Hermosillo",0,1708);
      Lugar chihuahua = new Lugar("Chihuahua",0,1333);
        Lugar guanajuato = new Lugar("Guanajuato",0,387);
        Lugar tepic = new Lugar("Tepic",0,750);
        Lugar  guadalajara = new Lugar("Guadalajara",0,569);
        Lugar  reynosa= new Lugar("Reynosa",0,783);
        Lugar  pachuca= new Lugar("Pachuca",0,132);
        Lugar  cuernavaca= new Lugar("Cuernavaca",0,109);
        Lugar  acapulco = new Lugar("Acapulco",0,303);
        Lugar zacatecas = new Lugar("Zacatecas",0,615);
        Lugar aguascalientes = new Lugar("Aguascalientes",0,530);
        Lugar puebla = new Lugar("Puebla",0,0);
        Lugar veracruz= new Lugar("Veracruz",0,217);

        Main grafo = new Main();
        grafo.addLugares(hermosillo);
        grafo.addLugares(zacatecas);
        grafo.addLugares(guanajuato);
        grafo.addLugares(tepic);
        grafo.addLugares(guadalajara);
        grafo.addLugares(reynosa);
        grafo.addLugares(pachuca);
        grafo.addLugares(cuernavaca);
        grafo.addLugares(acapulco);
        grafo.addLugares(aguascalientes);
        grafo.addLugares(chihuahua);
        grafo.addLugares(puebla);
        grafo.addLugares(veracruz);

        grafo.Conexion(hermosillo.nombre,chihuahua.nombre,690);
        grafo.Conexion(hermosillo.nombre,aguascalientes.nombre,1533);
        grafo.Conexion(aguascalientes.nombre,chihuahua.nombre,946);
        grafo.Conexion(aguascalientes.nombre,pachuca.nombre,510);
        grafo.Conexion(guanajuato.nombre,pachuca.nombre,373);
        grafo.Conexion(puebla.nombre,acapulco.nombre,425);
        grafo.Conexion(acapulco.nombre,cuernavaca.nombre,290);
        grafo.Conexion(pachuca.nombre,cuernavaca.nombre,180);
        grafo.Conexion(guadalajara.nombre,tepic.nombre,204);
        grafo.Conexion(reynosa.nombre,chihuahua.nombre,1025);
        grafo.Conexion(reynosa.nombre,guanajuato.nombre,841);
        grafo.Conexion(reynosa.nombre,pachuca.nombre,869);
        grafo.Conexion(hermosillo.nombre,zacatecas.nombre,1420);
        grafo.Conexion(zacatecas.nombre,aguascalientes.nombre,118);
        grafo.Conexion(aguascalientes.nombre,guadalajara.nombre,220);
        grafo.Conexion(cuernavaca.nombre,puebla.nombre,158);
        grafo.Conexion(cuernavaca.nombre,veracruz.nombre,435);
        grafo.Conexion(veracruz.nombre,puebla.nombre,274);
        grafo.Conexion(zacatecas.nombre,guanajuato.nombre,300);
        grafo.Conexion(tepic.nombre,zacatecas.nombre,543);
        grafo.Conexion(pachuca.nombre,veracruz.nombre,382);
     
        System.out.println("********Algoritmo A estrella********");
        grafo.AEstrella(hermosillo,veracruz);
        System.out.println("********Algoritmo Voraz********");
        grafo.Voraz(hermosillo,veracruz);
    }
}
