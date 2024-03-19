public class PropiedadesCadena {
    private int[] tipoPalabra;
    private String cadena;
    private int numLinea;
    private int codigo; // Nuevo campo para almacenar el código del token

    public PropiedadesCadena() {
        tipoPalabra = new int[6];
        tipoPalabra[0] = 0;
        tipoPalabra[1] = 0;
        tipoPalabra[2] = 0;
        cadena = "";
        numLinea = 0;
        codigo = 0; // Inicializar el código en 0
    }

    public int[] getTipoPalabra() {
        return tipoPalabra;
    }

    public void setTipoPalabra(int resultado1, int resultado2, String cadena) {
        this.tipoPalabra[0] = resultado1;
        this.tipoPalabra[1] = resultado2;
        this.cadena = cadena;
    }

    public String getCadena() {
        return cadena;
    }

    public int getNumLinea() {
        return numLinea;
    }

    public void setNumLinea(int numLinea) {
        this.numLinea = numLinea;
    }

    public int getCodigo() { // Getter para el código del token
        return codigo;
    }

    public void setCodigo(int codigo) { // Setter para el código del token
        this.codigo = codigo;
    }
}

