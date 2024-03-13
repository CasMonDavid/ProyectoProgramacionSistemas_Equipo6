import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class Escaner {
	
	PropiedadesCadena propiedadesCadena;
	String entradaU;

	public List<PropiedadesCadena> iniciarApp(String entradaUsuario) {
		propiedadesCadena = new PropiedadesCadena();
		entradaU = entradaUsuario;
		return analizarCadena(dividirTexto(entradaUsuario));
	}
	
	public List<PropiedadesCadena> analizarCadena(String[] arreglo) {
		List<PropiedadesCadena> arrayResultados = new ArrayList<>();
		
		//REGEX PARA PALABRAS RESERVADAS
		String regexReservadas = "\\b(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|FROM|JOIN|WHERE|GROUP BY|ORDER BY|HAVING|AND|OR|NOT|AS|INTO|VALUES|"
									+"SET|TABLE|DATABASE|INDEX|FOREIGN KEY|PRIMARY KEY|UNIQUE|CHECK|DEFAULT|NULL|IS|LIKE|IN|BETWEEN|EXISTS|ALL|ANY|CASE|"
									+"WHEN|THEN|ELSE|END|LIMIT)\\b";
		//REGEX PARA OPERADORES
		String regexOperadores = "[+\\-*/]";
		//REGEX PARA DELIMITADORES
		String regexDelimitadores = "[,.'()]";
		//REGEX PARA DELIMITADORES
		String regexIdentificadores = "[a-zA-Z_][a-zA-Z_0-9]+";
		//REGEX PARA CONSTANTES
		String regexConstantes = "(['][a-zA-Z-_0-9]+[']|[0-9]+)";
		

		// SE LE PASA UNA CADENA YA DIVIDIDA Y LA ANALIZA SEGUN EL ORDEN
		for (String palabra : arreglo) {
			Matcher matcherReservadas = Pattern.compile(regexReservadas, Pattern.CASE_INSENSITIVE).matcher(palabra);
			Matcher matcherConstantes = Pattern.compile(regexConstantes).matcher(palabra);
			Matcher matcherIdentificadores = Pattern.compile(regexIdentificadores).matcher(palabra);
			
			if (matcherReservadas.find()) {
				propiedadesCadena.setResultados(1, palabra.length(), palabra); // TIPO DE PALABRA
				propiedadesCadena.setNumLinea(encontrarLinea(matcherReservadas));
				arrayResultados.add(propiedadesCadena);
				//reservada
			}else if (matcherIdentificadores.find()) {
				System.out.println("Identificador: "+matcherIdentificadores.group());
			}
			if(matcherConstantes.find()) {
				System.out.println("Constante: " + matcherConstantes.group());
				propiedadesCadena.setNumLinea(encontrarLinea(matcherConstantes));
			}
			
			for (char caracter : palabra.toCharArray()) {
				Matcher matcherOperadores = Pattern.compile(regexOperadores).matcher(String.valueOf(caracter));
				Matcher matcherDelimitadores = Pattern.compile(regexDelimitadores).matcher(String.valueOf(caracter));
				if (matcherOperadores.find()) {
					propiedadesCadena.setResultados(2, palabra.length(), palabra); // TIPO DE PALABRA
					propiedadesCadena.setNumLinea(encontrarLinea(matcherOperadores));
					arrayResultados.add(propiedadesCadena);
					//operador matematico
				} else if (matcherDelimitadores.find()) {
					propiedadesCadena.setResultados(3, palabra.length(), palabra); // TIPO DE PALABRA
					propiedadesCadena.setNumLinea(encontrarLinea(matcherDelimitadores));
					arrayResultados.add(propiedadesCadena);
					//delimitador
				}
			}
			
		}
		return arrayResultados;
		
	}
	
	public int encontrarLinea(Matcher matcher){
		Matcher line = Pattern.compile("\r\n").matcher(entradaU);
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
		Matcher line = Pattern.compile("\r\n").matcher(entradaU);
		int ultLinea = -1;

		while (line.find()) {
			ultLinea = line.start();
		}
		return ultLinea;
	}
	
	public String[] dividirTexto(String entradaUsuario) {
		String[] texto = entradaUsuario.split(" ");
		return texto;
	}
	
}

