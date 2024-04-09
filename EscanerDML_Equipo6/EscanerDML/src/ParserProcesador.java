import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// ESTE SERA EL MAIN DEL AVANCE DOS, AQUI SE RECEBIRA LOS DATOS Y SE ENVIARA EL RESULTADO

public class ParserProcesador {
	
    public List<PropiedadesCadena> listaLexica;
    
	public ParserProcesador() {
	}
	
	// PROCESADOR CENTRAL DE LA SINTAXIS
	public boolean procesarSintaxis() {
		boolean resultado = false;
		Stack pila = new Stack<>();
		Stack pilaLexica = llenar_Pila_Lexica();
		String varX="", varK="";;
		
		// SIEMPRE INICIARA CON UN SIMBOLO DE DOLAR AL FONDO Y LA UNICA REGLA INICIAL QUE HAREMOS "SELECT" CON EL SIMBOLO "Q"
		pila.push("$");
		pila.push("Q");
		//
		
		// ESTE ES EL CICLO QUE SE VE EN EL VIDEO, EL CICLO SE MANTENDRA HASTA QUE LLEGEMOS AL FINAL DE LA VARIABLE "Pila"
		do {
			varX = (String) pila.pop();
			varK = (String) pilaLexica.pop(); // AQUI DEBE DE IR UNA LETRAL DE SIGNIFICADO, NO LA PALABRA COMO TAL !!!!!
			
			if (varX=="terminar") { // HACER UNA FUNCION PARA SABER LOS TERMINALES !!!!!!!
				
			}
			
		} while (!varX.equals("$"));
		
		return resultado;
	}
	
	// PASA LA LISTA ARRAY A UN FORMATO DE PILA AÑADIENDO PRIMERAMENTE UN SIGNO DE DOLAR
	public Stack llenar_Pila_Lexica() {
		Stack pila = new Stack<>();
		
		// INDICARA AL FINAL SI LA PILA LEXICA FUI LEIDA EN SU TOTALIDAD Y DE FORMA CORRECTA
		pila.push("$");
		
		if (!listaLexica.isEmpty()) {
			for (int i = listaLexica.size()-1; i >= 0; i--) {
				PropiedadesCadena renglon = listaLexica.get(i);
				pila.push(renglon.getCadena());
			}
		}
		
		return pila;
	}
	
	// PRUEBA RAPIDA DE LO QUE ENTRA DE LA TABLA LEXICA
	public String pruebas() {
		String resultado = "";
		String token = "";
		
		if (!listaLexica.isEmpty()) {
			for (int i = listaLexica.size()-1; i >= 0; i--) {
				PropiedadesCadena renglon = listaLexica.get(i);
				resultado += renglon.getCadena()+" | ";
			}
		}else {
			resultado = "no encontro nada";
		}
		System.out.println("Resultado: "+resultado);
		return resultado;
	}
	
	// GETTERS AND SETTERS
	public List<PropiedadesCadena> getlistaLexica() {
		return listaLexica;
	}

	public void setlistaLexica(List<PropiedadesCadena> listaLexica) {
		this.listaLexica = listaLexica;
	}
	//
}