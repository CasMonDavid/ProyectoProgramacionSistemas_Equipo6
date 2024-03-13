public class PropiedadesCadena {
    public int[] resultados;
    public String cadena;
    public int numLinea;
    public PropiedadesCadena(){
        resultados = new int[3];
        resultados[0] = 0;
        resultados[1] = 0;
        resultados[2] = 0;
        cadena = "";
        numLinea = 0;
    }

    public int[] getResultados() {
        return resultados;
    }

    public void setResultados(int resultado1, int resultado2, String cadena) {
        this.resultados[0] = resultado1;
        this.resultados[1] = resultado2;
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
}
