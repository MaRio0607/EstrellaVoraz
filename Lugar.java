import java.util.Objects;

public class Lugar {
    String nombre;
    float costo;
    float distRect;

    public Lugar(String nombre, float costo, float distRect) {
        this.nombre = nombre;
        this.costo = costo;
        this.distRect = distRect;
    }

    public Lugar(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getDistRect() {
        return distRect;
    }

    public void setDistanciaRecta(int distRect) {
        this.distRect = distRect;
    }


      @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getCosto(), getDistRect());
    }
  
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lugar lugar = (Lugar) o;
        return getCosto() == lugar.getCosto() &&
                getDistRect() == lugar.getDistRect() &&
                Objects.equals(getNombre(), lugar.getNombre());
    }


}
