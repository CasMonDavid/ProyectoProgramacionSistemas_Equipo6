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
								  {"d","601"},//CONSTANTE NUMERICAS
								  {"a","602"},//CONSTANTE ALFANUMERICAS
								  {"+","70"},//OPERADORES
								  {"-","71"},
								  {"*","72"},
								  {"/","73"},
								  {">","8"},//RELACIONALES 81
								  {"<","8"},
								  {"=","8"},
								  {">=","8"},
								  {"<=","8"}};
		String[] producciones = {
				// INSERTEN PRODUCCIONES (REGLAS) actuaizacion: ya no xd
		};
		
		this.producciones = producciones;
		this.terminales = terminales;
	}
	
	// PROCESADOR CENTRAL DE LA SINTAXIS
	public boolean procesarSintaxis() {
		boolean resultado = false;
		Stack<String> pilaReglas = new Stack<String>();
		ArrayList<String> pilaLexica = llenar_Pila_Lexica();
		int apuntador = 0;
		String varX="", varK=""; // varX = REGLAS ; varK = CÓDIGO/VALOR DE LO QUE ESCRIBIO EL USUARIO
		ArrayList<String> produccion = new ArrayList<String>();
		
		// SIEMPRE INICIARA CON UN SIMBOLO DE DOLAR AL FONDO Y LA UNICA REGLA INICIAL QUE HAREMOS "SELECT" CON EL SIMBOLO "Q"
		pilaReglas.push("199");
		pilaReglas.push("300");
		//
		
		// ESTE ES EL CICLO QUE SE VE EN EL VIDEO, EL CICLO SE MANTENDRA HASTA QUE LLEGEMOS AL FINAL DE LA VARIABLE "Pila"
		do { System.out.println("");
			varX = pilaReglas.pop();
			varK = devolverValorTerminal(pilaLexica.get(apuntador));
			System.out.println("varX: "+varX+", varK: "+varK+", Apuntador: "+apuntador+", pilaLexica: "+pilaLexica.get(apuntador));
			
			if (esTerminal(varX) || varX.equals("$")) {
				if (varX.equals(varK)) {
					apuntador++; // AVANZAR AL SIGUIENTE ELEMENTO
					System.out.println("Apuntador avanza");
				}else {
					error();//LA FUNCION ERROR NO HACE NADA, LA CREE SOLO PARA QUE VEAN DONDE DEBE DE TRONAR SI HAY ERRORES PARA QUE LA LLENEN DESPUES.
					System.out.println("Error 1, "+"varX: "+varX+", varK: "+varK+", Apuntador: "+apuntador);
					break;
				}
			}else {
				if (esProduccion(varX, varK)) {// ESTA FUNCION ESTA VACIA, SEGUN LA VAR "X" Y "K" DEBE DEVOLVER SI TIENE ALGUN TIPO DE REGLA.
					produccion.clear();
					produccion.addAll(devolverProduccion(varX, varK));// DEVUELVE UNA LISTA DE LAS PRODUCCION Y LAS SELECCIONA EN LAS PRODUCCIONES ACTUALES EJEMPLO: [317][311]
					if (produccion.get(0)!="99") {// ESTA FUNCION TAMBIEN ESTA VACIA , DEBERIA REGRESAR EL CONJUNTO DE REGLAS Y COMPROBAR LA CONDICION
						for (int i=produccion.size()-1;i>=0;i--) {//LAS PRODUCCIONES SE INSERTAN DE DERECHA A IZQUIERDA EN LA PILA, RECURDEN "first in, first out"
							pilaReglas.push(produccion.get(i));
						}
					}
				}else {
					error();//LA FUNCION ERROR NO HACE NADA, LA CREE SOLO PARA QUE VEAN DONDE DEBE DE TRONAR SI HAY ERRORES PARA QUE LA LLENEN DESPUES.
					System.out.println("Error 2, "+"varX: "+varX+", varK: "+varK+", Apuntador: "+apuntador);
					break;
				}
			}
			
		} while (!varX.equals("$"));
		
		if (varX.equals(varK)) {
			System.out.println("LA SINTACTICA ES CORRECTA");
		}
		
		return resultado;
	}
	
	// DEBERIA REGRESAR EL CONJUNTO DE REGLAS EN DADO CASO QUE LAS CONTENGA
	public ArrayList<String> devolverProduccion(String varX, String varK) {
		ArrayList<String> resultado= new ArrayList<String>(); // LUEGO HACEN LA LOGICA COMO GUSTEN ESTO LO HAGO PARA QUE NO TRUENE
		String resultadoReglas = tablaSintactica(varX, varK);
		String[] reglas = resultadoReglas.split(" ");
		
		if (resultadoReglas!="-1") {
			for (int i=0;i<reglas.length;i++) {
				resultado.add(reglas[i]);
				System.out.println(reglas[i]+"|");
			}
		}
		
		return resultado;
	}
	
	// PREGUNTA SI EL CRUCE DE LA REGLA Y LA TERMINAL DA UNA REGLA
	public boolean esProduccion(String varX, String varK) {
		if (tablaSintactica(varX, varK)!="-1") {
			return true;
		}
		return false;
	}
	
	// PASA LA LISTA ARRAY A UN FORMATO DE PILA AÑADIENDO PRIMERAMENTE UN SIGNO DE DOLAR
	public ArrayList<String> llenar_Pila_Lexica() {
		ArrayList<String> pila = new ArrayList<String>();
		
		// INDICARA AL FINAL SI LA PILA LEXICA FUI LEIDA EN SU TOTALIDAD Y DE FORMA CORRECTA
		
		if (!listaLexica.isEmpty()) {
			for (int i = 0; i < listaLexica.size(); i++) {
				PropiedadesCadena renglon = listaLexica.get(i);
				pila.add(renglon.getCadena());
			}
		}
		pila.add("$");
		
		return pila;
	}
	
	// DA EL DATO QUE ESTA ANALIZANDO Y DEBE REGRESAR SU VALOR DE LA TABLA DE SIMBOLOS
	public String devolverValorTerminal(String elemento) {
		String resultado = "-1";
		
		switch (elemento) {
		case "SELECT":
			resultado = "10";
			break;
		case "FROM":
			resultado = "11";
			break;
		case "WHERE":
			resultado = "12";
			break;
		case "IN":
			resultado = "13";
			break;
		case " AND ":// POR ALGUNA RAZON DESCONOCIDA EN ALGUN PUNTO AGARRA DOS ESPACIOS A LOS LADOS SOLO LA PALABRA RESRVADA "AND"
			resultado = "14";
			break;
		case "OR":
			resultado = "15";
			break;
		case "CREATE":
			resultado = "16";
			break;
		case "TABLE":
			resultado = "17";
			break;
		case "CHAR":
			resultado = "18";
			break;
		case "NUMERIC":
			resultado = "19";
			break;
		case "NOT":
			resultado = "20";
			break;
		case "NULL":
			resultado = "21";
			break;
		case "CONSTRAINT":
			resultado = "22";
			break;
		case "KEY":
			resultado = "23";
			break;
		case "PRIMARY":
			resultado = "24";
			break;
		case "FOREING":
			resultado = "25";
			break;
		case "REFERENCES":
			resultado = "26";
			break;
		case "INSERT":
			resultado = "27";
			break;
		case "INTO":
			resultado = "28";
			break;
		case "VALUES":
			resultado = "29";
			break;
		case ",":
			resultado = "50";
			break;
		case ".":
			resultado = "51";
			break;
		case "(":
			resultado = "52";
			break;
		case ")":
			resultado = "53";
			break;
		case "'":
			resultado = "54";
			break;
		case "d": // CONSTANTES, NO SE USAN ESTOS EN NUESTRO CÓDIGO
			resultado = "61";
			break;
		case "a":
			resultado = "62";
			break;//
		case "+":
			resultado = "70";
			break;
		case "-":
			resultado = "71";
			break;
		case "*":
			resultado = "72";
			break;
		case "/":
			resultado = "73";
			break;
		case ">": // RELACIONALES EMPIEZAN EN 81, LOS REDUJE A 8 POR LA TABLA SINTACTICA
			resultado = "8";
			break;
		case "<":
			resultado = "8";
			break;
		case "=":
			resultado = "8";
			break;
		case ">=":
			resultado = "8";
			break;
		case "<=":
			resultado = "8";
			break;
		case "$":
			resultado = "$";
			break;
		default:break;
		}
		
		if (resultado=="-1") {
			if (elemento.matches("\\s?[a-zA-Z_][a-zA-Z_0-9-_#]*\\s?")) {
				// IDENTIFICADORES
				resultado = "400";
				System.out.println("Elemento: "+elemento+" entro como IDENTIFICADOR");
			} else if (elemento.matches("[']\\w+[']")) {
				// CONSTANTE ALFANUMERICO
				resultado = "602";
				System.out.println("Elemento: "+elemento+" entro como CONSTANTE ALFANUMERICO");
			}else if (elemento.matches("[0-9]+")) {
				// CONSTANTE NUMERICO
				resultado = "601";
				System.out.println("Elemento: "+elemento+" entro como CONSTANTE NUMERICO");
			}
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
		if (varX.equals("400")) {
			return true;
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
				resultado += renglon.getCadena()+"|";
			}
		}else {
			resultado = "no encontro nada";
		}
		System.out.println("Resultado:"+resultado);
		return resultado;
	}

	//Tabla sintactica 4 = 400
	public String tablaSintactica(String varX, String varY) {

		if (varX.equals("300") && varY.equals("10")) {
			return "10 301 11 306 310";
		} else if (varX.equals("301") && varY.equals("400")) {
			return "302";
		} else if (varX.equals("301") && varY.equals("72")) {
			return "72";
		} else if (varX.equals("302") && varY.equals("400")) {
			return "304 303";
		} else if (varX.equals("303") && varY.equals("11")) {
			return "99";
		} else if (varX.equals("303") && varY.equals("50")) {
			return "50 302";
		} else if (varX.equals("303") && varY.equals("199")) {
			return "99";
		} else if (varX.equals("304") && varY.equals("400")) {
			return "400 305";
		} else if (varX.equals("305") && varY.equals("8")) {
			return "99";
		} else if (varX.equals("305") && varY.equals("11")) {
			return "99";
		} else if (varX.equals("305") && varY.equals("13")) {
			return "99";
		} else if (varX.equals("305") && varY.equals("14")) {
			return "99";
		} else if (varX.equals("305") && varY.equals("15")) {
			return "99";
		} else if (varX.equals("305") && varY.equals("50")) {
			return "99";
		} else if (varX.equals("305") && varY.equals("51")) {
			return "51 400";
		} else if (varX.equals("305") && varY.equals("53")) {
			return "99";
		} else if (varX.equals("305") && varY.equals("199")) {
			return "99";
		} else if (varX.equals("306") && varY.equals("400")) {
			return "308 307";
		} else if (varX.equals("307") && varY.equals("12")) {
			return "99";
		} else if (varX.equals("307") && varY.equals("50")) {
			return "50 306";
		} else if (varX.equals("307") && varY.equals("53")) {
			return "99";
		} else if (varX.equals("307") && varY.equals("199")) {
			return "99";
		} else if (varX.equals("308") && varY.equals("400")) {
			return "400 309";
		} else if (varX.equals("309") && varY.equals("400")) {
			return "400";
		} else if (varX.equals("309") && varY.equals("12")) {
			return "99";
		} else if (varX.equals("309") && varY.equals("50")) {
			return "99";
		} else if (varX.equals("309") && varY.equals("53")) {
			return "99";
		} else if (varX.equals("309") && varY.equals("199")) {
			return "99";
		} else if (varX.equals("310") && varY.equals("12")) {
			return "12 311";
		} else if (varX.equals("310") && varY.equals("53")) {
			return "99";
		} else if (varX.equals("310") && varY.equals("199")) {
			return "99";
		} else if (varX.equals("311") && varY.equals("400")) {
			return "313 312";
		} else if (varX.equals("312") && varY.equals("14")) {
			return "317 311";
		} else if (varX.equals("312") && varY.equals("15")) {
			return "317 311";
		} else if (varX.equals("312") && varY.equals("53")) {
			return "99";
		} else if (varX.equals("312") && varY.equals("199")) {
			return "99";
		} else if (varX.equals("313") && varY.equals("400")) {
			return "304 314";
		} else if (varX.equals("314") && varY.equals("8")) {
			return "315 316";
		} else if (varX.equals("314") && varY.equals("13")) {
			return "13 52 300 53";
		} else if (varX.equals("315") && varY.equals("8")) {
			return "8";
		} else if (varX.equals("316") && varY.equals("400")) {
			return "304";
		} else if (varX.equals("316") && varY.equals("54")) {
			return "54 318 54";
		} else if (varX.equals("316") && varY.equals("601")) {
			return "319";
		} else if (varX.equals("317") && varY.equals("14")) {
			return "14";
		} else if (varX.equals("317") && varY.equals("15")) {
			return "15";
		} else if (varX.equals("318") && varY.equals("602")) {
			return "602";
		} else if (varX.equals("319") && varY.equals("601")) {
			return "601";
		}
		return "-1";
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
    
