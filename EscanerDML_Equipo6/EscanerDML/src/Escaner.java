import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Escaner {
	
	public void iniciarApp(String entradaUsuario) {
		analizarCadena(dividirTexto(entradaUsuario));
	}
	
	public void analizarCadena(String[] arreglo) {
		//REGEX PARA PALABRAS RESERVADAS
		String regexReservadas = "\\b(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|FROM|JOIN|WHERE|GROUP BY|ORDER BY|HAVING|AND|OR|NOT|AS|INTO|VALUES|"
									+"SET|TABLE|DATABASE|INDEX|FOREIGN KEY|PRIMARY KEY|UNIQUE|CHECK|DEFAULT|NULL|IS|LIKE|IN|BETWEEN|EXISTS|ALL|ANY|CASE|"
									+"WHEN|THEN|ELSE|END|LIMIT)\\b";
		//REGEX PARA OPERADORES
		String regexOperadores = "[+\\-*/]";
		//REGEX PARA DELIMITADORES
		String regexDelimitadores = "[,.'()]";
		

		// SE LE PASA UNA CADENA YA DIVIDIDA Y LA ANALIZA SEGUN EL ORDEN
		for (String palabra : arreglo) {
			Matcher matcherReservadas = Pattern.compile(regexReservadas, Pattern.CASE_INSENSITIVE).matcher(palabra);
			
			if (matcherReservadas.find()) {
				System.out.println("Reservada: "+palabra);
			}
			
			for (char caracter : palabra.toCharArray()) {
				Matcher matcherOperadores = Pattern.compile(regexOperadores).matcher(String.valueOf(caracter));
				Matcher matcherDelimitadores = Pattern.compile(regexDelimitadores).matcher(String.valueOf(caracter));
				if (matcherOperadores.find()) {
					System.out.println("Operador matemático: "+caracter);
				} else if (matcherDelimitadores.find()) {
					System.out.println("Delimitador: "+caracter);
				}
			}
			
		}
		
	}
	
	public String[] dividirTexto(String entradaUsuario) {
		String[] texto = entradaUsuario.split(" ");
		return texto;
	}
	
}

