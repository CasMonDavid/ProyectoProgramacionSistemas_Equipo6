import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Escaner {

    private static final Escaner instance = new Escaner();
    private final Pattern regexReservadas;
    private final Pattern regexOperadores;
    private final Pattern regexDelimitadores;
    private final Pattern regexIdentificadores;
    private final Pattern regexConstantes;
    private final Pattern regexRelaciones;
    private List<PropiedadesCadena> sintaxisResultado = new ArrayList<>();

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
			//String[] palabras = linea.split("(?<=[^\\w'])|(?=[^\\w'])");
			
			// MODIFICACION: Ahora la forma de buscar coincidencia es por un regex Universal que lo que hace es que cuando encuentra cualquier compatibilidad con sentido en automatico lo
			// separa y lo manda analizar, pero solo analiza la linea para que siga detectando el donde se encuentra
			String regexUniversalSQL = "SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|FROM|JOIN|WHERE|GROUP BY|ORDER BY|HAVING|AND|OR|NOT|AS|INTO|VALUES|"
					+ "SET|TABLE|DATABASE|INDEX|FOREIGN KEY|PRIMARY KEY|UNIQUE|CHECK|DEFAULT|NULL|IS|LIKE|\\s+IN\\s+|BETWEEN|EXISTS|ALL|ANY|CASE|"
					+ "WHEN|THEN|ELSE|END|LIMIT|<=|>=|>|<|=|\\d+|\\w+[#]?|'[^']*'|\\*|\\(|\\)|[,.'()]|\\s?(?=.+[^a-zA-Z0-9])[a-zA-Z0-9!'#\\$%&\\/]{3,}\\s?";
			Pattern pattern = Pattern.compile(regexUniversalSQL, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(linea);
	        // FIN MODIFICACION //
			
			// MODIFICACION DAVID: ahora es un ciclo while que buscara coincidencias
			while (matcher.find()) {
				PropiedadesCadena propiedadesCadena = new PropiedadesCadena();
				propiedadesCadena.setNumLinea(numLinea);
				
				String token = matcher.group(); // MODIFICACION DAVID: ahora segun el regex universal agarra una coincidencia para su revision
				
				// Identificar el tipo de token y asignar el código correspondiente
				int codigo = asignarCodigo(token);
				propiedadesCadena.setCodigo(codigo); // Establecer el código del token en la instancia de PropiedadesCadena
				
				// Manda a llamar una funcion para que le regrese el tipo de token que esta ejecutandose y segun sea el caso registra los datos
				switch (identificarTipoToken(token)) {
				case "Reservada":
					propiedadesCadena.setTipoPalabra(1, token.length(), token);
					arrayResultados.add(propiedadesCadena);
					break;
				case "Identificador":
					PropiedadesCadena identificador = new PropiedadesCadena();
					identificador.setTipoPalabra(4, token.length(), token);
					identificador.setNumLinea(numLinea);
					arrayResultados.add(identificador);
					break;
				case "Constante":
					PropiedadesCadena constante = new PropiedadesCadena();
					constante.setTipoPalabra(5, token.length(), token);
					constante.setNumLinea(numLinea);
					arrayResultados.add(constante);
					break;
				case "Operador":
					// PropiedadesCadena relacion = new PropiedadesCadena();
					propiedadesCadena.setTipoPalabra(2, token.length(), token);
					propiedadesCadena.setNumLinea(numLinea);
					arrayResultados.add(propiedadesCadena);
					break;
				case "Delimitador":
					// PropiedadesCadena Delimitador = new PropiedadesCadena();
					propiedadesCadena.setTipoPalabra(3, token.length(), token);
					propiedadesCadena.setNumLinea(numLinea);
					arrayResultados.add(propiedadesCadena);
					break;
				case "Desconocido":
					JOptionPane.showMessageDialog(null, "Error de sintaxis en la linea " + numLinea + ": " + token, "Error de sintaxis", JOptionPane.ERROR_MESSAGE);
					System.out.println("Error de sintaxis en la linea " + numLinea + ": " + token);
					break;
				default:break;
				}
				//System.out.println("Token: "+token);
			}
			numLinea++;
		}
		setSintaxisResultado(arrayResultados);
		return arrayResultados;
	}
    
    // IDENTIFICA QUE TIPO DE TOKEN ES Y REGRESA EL TIPO EN CADENA
    public String identificarTipoToken(String token) {
        if (token.matches("\\s?(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|FROM|JOIN|WHERE|GROUP BY|ORDER BY|HAVING|AND|OR|NOT|AS|INTO|VALUES|\"\r\n"
        		+ "SET|TABLE|DATABASE|INDEX|FOREIGN KEY|PRIMARY KEY|UNIQUE|CHECK|DEFAULT|NULL|IS|LIKE|IN|BETWEEN|EXISTS|ALL|ANY|CASE|\"\r\n"
        		+ "WHEN|THEN|ELSE|END|LIMIT)\\s?")) {
            return "Reservada";
        } else if (token.matches("\\s?[a-zA-Z_][a-zA-Z_0-9-_#]*\\s?")) {
            return "Identificador";
        } else if (token.matches("\\s?'[^']*'|\\b\\w+\\b|[0-9]+\\s?")) {
            return "Constante";
        } else if (token.matches(">|<|=|<=|>=|\\*")) {
            return "Operador";
        } else if (token.matches("[,.'()]")) {
            return "Delimitador";
        } else {
            return "Desconocido";
        }
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

    // OBTENER LA LISTA DE SINTAXIS
	public List<PropiedadesCadena> getSintaxisResultado() {
		return sintaxisResultado;
	}

	public void setSintaxisResultado(List<PropiedadesCadena> sintaxisResultado) {
		this.sintaxisResultado = sintaxisResultado;
	}
}

    
