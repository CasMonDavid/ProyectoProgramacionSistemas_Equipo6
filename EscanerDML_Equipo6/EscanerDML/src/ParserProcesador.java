import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// ESTE SERA EL MAIN DEL AVANCE DOS, AQUI SE RECEBIRA LOS DATOS Y SE ENVIARA EL RESULTADO

public class ParserProcesador {
	
    public List<PropiedadesCadena> listaLexica;
    public String[][] terminales;
    public String[] producciones;
    
	public ParserProcesador() {
		String[][] terminales = { {"SELECT","10"},//RESERVADAS
								  {"FROM","11"},
								  {"WHERE","12"},
								  {"IN","13"},
								  {"AND","14"},
								  {"OR","15"},
								  {"CREATE","16"},
								  {"TABLE","17"},
								  {"CHAR","18"},
								  {"NUMERIC","19"},
								  {"NOT","20"},
								  {"NULL","21"},
								  {"CONSTRAINT","22"},
								  {"KEY","23"},
								  {"PRIMARY","24"},
								  {"FOREING","25"},
								  {"REFERENCES","26"},
								  {"INSERT","27"},
								  {"INTO","28"},
								  {"VALUES","29"},
								  {",","50"},//DELIMITADORES | VOY A UTILIZAR EL CÓDIGO NUMERICO PORQUE NO SE CUAL SEA CON LETRAS
								  {".","51"},
								  {"(","52"},
								  {")","53"},
								  {"'","54"},
								  {"d","61"},//CONSTANTE NUMERICAS
								  {"a","62"},//CONSTANTE ALFANUMERICAS
								  {"+","70"},//OPERADORES
								  {"-","71"},
								  {"*","72"},
								  {"/","73"},
								  {">","81"},//RELACIONALES
								  {"<","82"},
								  {"=","83"},
								  {">=","84"},
								  {"<=","85"}};
		String[] producciones = {
				// INSERTEN PRODUCCIONES (REGLAS)
		};
		
		this.producciones = producciones;
		this.terminales = terminales;
	}
	
	// PROCESADOR CENTRAL DE LA SINTAXIS
	public boolean procesarSintaxis() {
		boolean resultado = false;
		Stack<String> pilaReglas = new Stack<String>();
		Stack<String> pilaLexica = llenar_Pila_Lexica();
		String varX="", varK=""; // varX = REGLAS ; varK = CÓDIGO/VALOR DE LO QUE ESCRIBIO EL USUARIO
		ArrayList<String> produccion = new ArrayList<String>();
		
		// SIEMPRE INICIARA CON UN SIMBOLO DE DOLAR AL FONDO Y LA UNICA REGLA INICIAL QUE HAREMOS "SELECT" CON EL SIMBOLO "Q"
		pilaReglas.push("$");
		pilaReglas.push("10");
		//
		
		// ESTE ES EL CICLO QUE SE VE EN EL VIDEO, EL CICLO SE MANTENDRA HASTA QUE LLEGEMOS AL FINAL DE LA VARIABLE "Pila"
		do {
			varX = (String) pilaReglas.pop();
			varK = devolverValorTerminal((String) pilaLexica.pop());
			
			if (esTerminal(varX) || varX.equals("$")) {
				if (varX.equals(varK)) {
					varK = devolverValorTerminal((String) pilaLexica.pop());
				}else {
					error();//LA FUNCION ERROR NO HACE NADA, LA CREE SOLO PARA QUE VEAN DONDE DEBE DE TRONAR SI HAY ERRORES PARA QUE LA LLENEN DESPUES.
					System.out.println("Error 1");
				}
			}else {
				if (esProduccion(varX, varK)) {// ESTA FUNCION ESTA VACIA, SEGUN LA VAR "X" Y "K" DEBE DEVOLVER SI TIENE ALGUN TIPO DE REGLA.
					produccion.clear();
					produccion.addAll(devolverProduccion(varX, varK));// DEVUELVE UNA LISTA DE LAS PRODUCCION Y LAS SELECCIONA EN LAS PRODUCCIONES ACTUALES EJEMPLO: [317][311]
					if (((String) produccion.get(0))!="99") {// ESTA FUNCION TAMBIEN ESTA VACIA , DEBERIA REGRESAR EL CONJUNTO DE REGLAS Y COMPROBAR LA CONDICION
						for (int i=produccion.size()-1;i>=0;i--) {//LAS PRODUCCIONES SE INSERTAN DE DERECHA A IZQUIERDA EN LA PILA, RECURDEN "first in, first out"
							pilaReglas.push(produccion.get(i));
						}
					}else {
						error();//LA FUNCION ERROR NO HACE NADA, LA CREE SOLO PARA QUE VEAN DONDE DEBE DE TRONAR SI HAY ERRORES PARA QUE LA LLENEN DESPUES.
						System.out.println("Error 2");
					}
				}
			}
			
		} while (!varX.equals("$"));
		
		return resultado;
	}
	
	public ArrayList<String> devolverProduccion(String varX, String varK) {
		ArrayList<String> resultado= new ArrayList<String>(); // LUEGO HACEN LA LOGICA COMO GUSTEN ESTO LO HAGO PARA QUE NO TRUENE
		
		// DEBERIA REGRESAR EL CONJUNTO DE REGLAS EN DADO CASO QUE LAS CONTENGa
		
		return resultado;
	}
	
	public boolean esProduccion(String varX, String varK) {
		
		// REGLAS
		
		return false;
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
	
	// DA EL DATO QUE ESTA ANALIZANDO Y DEBE REGRESAR SU VALOR DE LA TABLA DE SIMBOLOS
	public String devolverValorTerminal(String apuntador) {
		String resultado = "";	
		
		switch (apuntador) {
		case "10":
			resultado = "SELECT";
			break;
		case "11":
			resultado = "FROM";
			break;
			//Y ASI CON TODOS LOS TERMINALES (AVANZAR: URGE)
		default:break;
		}
		
		return resultado;
	}
	
	// PREGUNTA SI LA REGLA SE TRATA DE UN TERMINAL Y REGRESA SI LO ENCONTRO O NO COMO TRUE O FALSE RESPECTIVAMENTE
	public boolean esTerminal(String varX) {
		for (int i=0;i<35;i++) {
			if (varX.equals(terminales[i][1])) {
				System.out.println("Es terminal verdadero en: "+terminales[i][1]);
				return true;
			}
		}
		return false;
	}
	
	public void error() {
		// FALTA HACER ERRORES PERSONALIZADOS DENTRO O FUERA DE ESTA FUNCION COMO QUIERAN (AVANZAR: URGENTE)
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