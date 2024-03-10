import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Escaner {
	
	public Escaner(){
		iniciarApp();
	}
	
	//LOS EJEMPLOS SON MUESTRAS CORRECTAS DE LO QUE DEBERIA SER CAPAZ DE ANALIZAR COMO CORRECTO
	String ejemploUno = "SELECT *\r\n"+
						"FROM PROFESORES\r\n"+
						"WHERE EDAD >45 AND GRADO='MAE' OR GRADO='DOC'";
	
	String ejemploDos = "SELECT SELECT SELECT , , ,";
	
	public void iniciarApp() {
		analizarCadena(dividirTexto());
	}
	
	// SE LE PASA UNA CADENA YA DIVIDIDA Y LA ANALIZA SEGUN EL ORDEN
	public void analizarCadena(String[] arreglo) {
		String regexReservadas = "\\b(SELECT|INSERT|UPDATE|DELETE|CREATE|DROP|ALTER|FROM|JOIN|WHERE|GROUP BY|ORDER BY|HAVING|AND|OR|NOT|AS|INTO|VALUES|"
									+"SET|TABLE|DATABASE|INDEX|FOREIGN KEY|PRIMARY KEY|UNIQUE|CHECK|DEFAULT|NULL|IS|LIKE|IN|BETWEEN|EXISTS|ALL|ANY|CASE|"
									+"WHEN|THEN|ELSE|END|LIMIT)\\b";
		
		for (String palabra : arreglo) {
			Matcher matcher = Pattern.compile(regexReservadas).matcher(palabra);
			if (matcher.find()) {
				System.out.println("Palabra reservada: "+palabra);
			}
		}
		
	}
	
	// DIVIDE EL TEXTO SEGUN LOS ESPACIOS EN BLANCO Y LO DEVUELVE COMO UN ARREGLO
	public String[] dividirTexto() {
		String[] texto = ejemploUno.split(" ");
		
		return texto;
	}
	
}
