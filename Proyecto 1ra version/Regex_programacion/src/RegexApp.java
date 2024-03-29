import java.util.regex.Matcher;
import java.util.regex.Pattern;

//	 Equipo 6

public class RegexApp {

    private int conteo;
    public RegexApp() {
        conteo = 0;
        System.out.printf("|%14s|%14s|%14s|%14s|%n", "No.", "No. Linea", "Cadena", "Tipo");
        iniciarApp();
    }

    String texto = "Estos datos positivos también tienen su correlación con la salud. La tasa de mortalidad\r\n" + //
            "entre los recién nacidos era de 648 por cada 1000 en 1990. Pero en 2016, la realidad\r\n" + //
            "era bien diferente, ya que solo 30,5 fallecían por cada 1.000 (en 26 años se ha reducido un\r\n" + //
            "53%). Igual de positivo es la caída de la mortalidad entre los menores de 5A; en 26 años se\r\n" + //
            "ha pasado de una tasa de 93,4 fallecidos por 1.0E2 a un 40,8 (una reducción del 56%).\r\n" + //
            "De igual manera, el número de mujeres que fallece durante el parto también ha decrecido\r\n" + //
            "(en 1990 el número de muertes fue de 532.000 y en 2015 de 303.00, una disminución del\r\n" + //
            "43%).";

    public void iniciarApp() {
        // Números naturales "\\b(?<![.,%])\\d+\\b(?![.,%])"
        String regexNatural = "\\s\\d+[,.]?\\s";
        imprimirResultados(texto, regexNatural, "Natural");

        // Números reales
        String regexReal = "\\b(?<!\\.)\\d+[.,]\\d+([eE][+-]?\\d+)?\\b(?!\\.|\\d)";
        imprimirResultados(texto, regexReal, "Real");

        // Números porcentuales
        String regexPorcentajes = "[+-]?\\d+(\\.\\d+)?[%)]";
        imprimirResultados(texto, regexPorcentajes, "Porcentaje");

        // Números exponenciales
        String regexExponencial = "[+-]?\\d+(\\.\\d+)?([eE][+-]?\\d+)";
        imprimirResultados(texto, regexExponencial, "Exponencial");

        // Números no válidos
        String regexNoValidos = "\\b\\d+[.,]\\d+[B]\\b|\\b\\d+[.,]\\d+[.][0]\\b";
        imprimirResultados(texto, regexNoValidos, "No validos");

    }

    public void imprimirResultados(String texto, String regex, String tipo) {
        Matcher matcher = Pattern.compile(regex).matcher(texto);

        while (matcher.find()) {
            conteo++;
            System.out.printf("|%14s|%14s|%14s|%14s|%n", conteo, encontrarLinea(matcher), matcher.group(), tipo);
        }

    }

    public int encontrarLinea(Matcher matcher){
        Matcher line = Pattern.compile("\r\n").matcher(texto);
        int lineaAterior = 0;
        int linea = 0;
        
        while (line.find()) {
            linea++;
            if (((matcher.start() > lineaAterior) && (matcher.start() < line.start())) || (line.start() == ultimaLinea())) {
                linea = (matcher.start() > ultimaLinea()) ? linea + 1 : linea;
                break;
            }
            lineaAterior = line.start();
        }
        
        return linea;
    }

    public int ultimaLinea(){
        Matcher line = Pattern.compile("\r\n").matcher(texto);
        int ultLinea = -1;

        while (line.find()) {
            ultLinea = line.start();
        }
        return ultLinea;
    }
}

    
