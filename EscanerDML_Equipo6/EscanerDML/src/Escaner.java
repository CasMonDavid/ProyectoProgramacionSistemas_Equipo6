import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Escaner {

    private static final Escaner instance = new Escaner();
    private final Pattern regexReservadas;
    private final Pattern regexOperadores;
    private final Pattern regexDelimitadores;
    private final Pattern regexIdentificadores;
    private final Pattern regexConstantes;
    private final Pattern regexRelaciones;

	// Definir códigos únicos para cada tipo de token
    private final Map<String, Integer> codigosReservadas = new HashMap<>();
    private final Map<String, Integer> codigosDelimitadores = new HashMap<>();
    private final Map<String, Integer> codigosOperadores = new HashMap<>();
    private final Map<String, Integer> codigosConstantes = new HashMap<>();
    private final Map<String, Integer> codigosRelacionales = new HashMap<>();

    public Escaner() {
		// Expresiones Regulares
		regexReservadas = Pattern.compile("\\b(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|FROM|JOIN|WHERE|GROUP BY|ORDER BY|HAVING|AND|OR|NOT|AS|INTO|VALUES|"
				+ "SET|TABLE|DATABASE|INDEX|FOREIGN KEY|PRIMARY KEY|UNIQUE|CHECK|DEFAULT|NULL|IS|LIKE|IN|BETWEEN|EXISTS|ALL|ANY|CASE|"
				+ "WHEN|THEN|ELSE|END|LIMIT)\\b", Pattern.CASE_INSENSITIVE);
		regexOperadores = Pattern.compile("[+\\-*/]");
		regexDelimitadores = Pattern.compile("[,.'()]");
		regexIdentificadores = Pattern.compile("[a-zA-Z_][a-zA-Z_0-9]*");
		regexConstantes = Pattern.compile("'[^']*'|\\b\\w+\\b|[0-9]+");
		regexRelaciones = Pattern.compile("(>=?|<=?|==|!=)");

		 // Asignar códigos únicos a las palabras reservadas
        codigosReservadas.put("SELECT", 10);
        codigosReservadas.put("FROM", 11);
        codigosReservadas.put("WHERE", 12);
        codigosReservadas.put("IN", 13);
        codigosReservadas.put("AND", 14);
        codigosReservadas.put("OR", 15);
        codigosReservadas.put("CREATE", 16);
        codigosReservadas.put("TABLE", 17);
        codigosReservadas.put("CHAR", 18);
        codigosReservadas.put("NUMERIC", 19);
        codigosReservadas.put("NOT", 20);
        codigosReservadas.put("NULL", 21);
        codigosReservadas.put("CONSTRAINT", 22);
        codigosReservadas.put("KEY", 23);
        codigosReservadas.put("PRIMARY", 24);
        codigosReservadas.put("FOREIGN", 25);
        codigosReservadas.put("REFERENCES", 26);
        codigosReservadas.put("INSERT", 27);
        codigosReservadas.put("INTO", 28);
        codigosReservadas.put("VALUES", 29);

        // Asignar códigos únicos a los delimitadores
        codigosDelimitadores.put(",", 50);
        codigosDelimitadores.put(".", 51);
        codigosDelimitadores.put("(", 52);
        codigosDelimitadores.put(")", 53);
        codigosDelimitadores.put("'", 54);

        // Asignar códigos únicos a los operadores
        codigosOperadores.put("+", 70);
        codigosOperadores.put("-", 71);
        codigosOperadores.put("*", 72);
        codigosOperadores.put("/", 73);

        // Asignar códigos únicos a las constantes
        codigosConstantes.put("numericos", 61);
        codigosConstantes.put("alfanumericos", 62);

        // Asignar códigos únicos a los relacionales
        codigosRelacionales.put(">", 81);
        codigosRelacionales.put("<", 82);
        codigosRelacionales.put("=", 83);
        codigosRelacionales.put(">=", 84);
        codigosRelacionales.put("<=", 85);
	}
	

    public static Escaner getInstance() {
        return instance;
    }

    public List<PropiedadesCadena> analizarCadena(String entradaUsuario) {
		List<PropiedadesCadena> arrayResultados = new ArrayList<>();
		String[] lineas = entradaUsuario.split("\\r?\\n");
		int numLinea = 1;
	
		for (String linea : lineas) {
			// Utilizamos una expresión regular para dividir la línea en palabras considerando los espacios alrededor de operadores y delimitadores
			String[] palabras = linea.split("(?<=[^\\w'])|(?=[^\\w'])");
			for (String palabra : palabras) {
				PropiedadesCadena propiedadesCadena = new PropiedadesCadena();
				propiedadesCadena.setNumLinea(numLinea);

				// Identificar el tipo de token y asignar el código correspondiente
				int codigo = asignarCodigo(palabra);
				propiedadesCadena.setCodigo(codigo); // Establecer el código del token en la instancia de PropiedadesCadena
	
				Matcher matcherReservadas = regexReservadas.matcher(palabra);
				Matcher matcherConstantes = regexConstantes.matcher(palabra);
				Matcher matcherIdentificadores = regexIdentificadores.matcher(palabra);
				Matcher matcherRelacionales = regexRelaciones.matcher(palabra);
	
				if (matcherReservadas.matches()) {
					propiedadesCadena.setTipoPalabra(1, palabra.length(), palabra);
					arrayResultados.add(propiedadesCadena);
				} else if (matcherIdentificadores.matches()) {
					PropiedadesCadena identificador = new PropiedadesCadena();
					identificador.setTipoPalabra(4, palabra.length(), palabra);
					identificador.setNumLinea(numLinea);
					arrayResultados.add(identificador);
				} else if (matcherConstantes.matches()) {
					PropiedadesCadena constante = new PropiedadesCadena();
					constante.setTipoPalabra(5, palabra.length(), palabra);
					constante.setNumLinea(numLinea);
					arrayResultados.add(constante);
				} else if (matcherRelacionales.matches()) {
					// Verificar si el operador relacional es parte de un operador compuesto
					String operadorRelacional = matcherRelacionales.group();
					if (palabra.length() > operadorRelacional.length()) {
						// Operador relacional compuesto encontrado
						PropiedadesCadena relacion = new PropiedadesCadena();
						relacion.setTipoPalabra(6, operadorRelacional.length(), operadorRelacional);
						relacion.setNumLinea(numLinea);
						arrayResultados.add(relacion);
	
						// Agregar el resto de la palabra como otro token
						String restoPalabra = palabra.substring(operadorRelacional.length());
						if (!restoPalabra.isEmpty()) {
							PropiedadesCadena resto = new PropiedadesCadena();
							resto.setTipoPalabra(4, restoPalabra.length(), restoPalabra);
							resto.setNumLinea(numLinea);
							arrayResultados.add(resto);
						}
					} else {
						// Operador relacional individual encontrado
						propiedadesCadena.setTipoPalabra(6, palabra.length(), palabra);
						arrayResultados.add(propiedadesCadena);
					}
				} else {
					// Identificar operadores y delimitadores
					for (char caracter : palabra.toCharArray()) {
						String caracterString = String.valueOf(caracter);
						if (regexOperadores.matcher(caracterString).matches()) {
							propiedadesCadena.setTipoPalabra(2, palabra.length(), palabra);
							arrayResultados.add(propiedadesCadena);
						} else if (regexDelimitadores.matcher(caracterString).matches()) {
							propiedadesCadena.setTipoPalabra(3, palabra.length(), palabra);
							arrayResultados.add(propiedadesCadena);
						}
					}
				}
	
				if (arrayResultados.isEmpty()) {
					System.out.println("Error de sintaxis: " + palabra);
				}
			}
			numLinea++;
		}
		return arrayResultados;
	}
	// Método para asignar códigos únicos a cada tipo de token
    private int asignarCodigo(String token) {
        if (codigosReservadas.containsKey(token)) {
            return codigosReservadas.get(token);
        } else if (codigosDelimitadores.containsKey(token)) {
            return codigosDelimitadores.get(token);
        } else if (codigosOperadores.containsKey(token)) {
            return codigosOperadores.get(token);
        } else if (codigosConstantes.containsKey(token)) {
            return codigosConstantes.get(token);
        } else if (codigosRelacionales.containsKey(token)) {
            return codigosRelacionales.get(token);
        } else {
            // En caso de que el token no tenga un código asignado específicamente
            return 0; // O el valor que prefieras
        }
    }
}
