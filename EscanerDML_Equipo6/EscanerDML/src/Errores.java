import java.util.Stack;

public class Errores {
	public int numeroLinea;
	public int tipoError;
	public int codigoError;
	public Stack<String> posiblesErrores;
	public String produccionEsperada;
	
	public Errores(int numeroLinea, int tipoError, Stack<String> posiblesErrores) {
		this.numeroLinea = numeroLinea;
		this.tipoError = tipoError;
		this.posiblesErrores = posiblesErrores;
		produccionEsperada="";
		codigoError=0;
	}
	
	public Errores() {
	}
	
	//GETTERS AND SETTERS
	public int getNumeroLinea() {
		return numeroLinea;
	}
	public String getProduccionEsperada() {
		return produccionEsperada;
	}
	public void setProduccionEsperada(String produccionEsperada) {
		this.produccionEsperada = produccionEsperada;
	}
	public int getTipoError() {
		return tipoError;
	}
	public void setTipoError(int tipoError) {
		this.tipoError = tipoError;
	}
	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea = numeroLinea;
	}
	public Stack<String> getPosiblesErrores() {
		return posiblesErrores;
	}
	public void setPosiblesErrores(Stack<String> posiblesErrores) {
		this.posiblesErrores = posiblesErrores;
	}
	//
	
	public int asignarCodigoError() {
		String produccionEsperada = this.produccionEsperada;
		System.out.println("PRODUCCION ESPERADA: "+produccionEsperada);
		
		if (produccionEsperada.equals("10") ||
			produccionEsperada.equals("11") ||
			produccionEsperada.equals("12") ||
			produccionEsperada.equals("13") ||
			produccionEsperada.equals("14") ||
			produccionEsperada.equals("15") ||
			produccionEsperada.equals("16") ||
			produccionEsperada.equals("17") ||
			produccionEsperada.equals("18") ||
			produccionEsperada.equals("19") ||
			produccionEsperada.equals("20") ||
			produccionEsperada.equals("21") ||
			produccionEsperada.equals("22") ||
			produccionEsperada.equals("23") ||
			produccionEsperada.equals("24") ||
			produccionEsperada.equals("25") ||
			produccionEsperada.equals("26") ||
			produccionEsperada.equals("27") ||
			produccionEsperada.equals("28") ||
			produccionEsperada.equals("29")) {
			return 201;
		}
		if (produccionEsperada.equals("400")) {
			return 204;
		}
		if (produccionEsperada.equals("50") ||
			produccionEsperada.equals("51") ||
			produccionEsperada.equals("52") ||
			produccionEsperada.equals("53") ||
			produccionEsperada.equals("54")) {
			return 205;
		}
		if (produccionEsperada.equals("601") ||
			produccionEsperada.equals("602")) {
			return 206;
		}
		if (produccionEsperada.equals("70") ||
			produccionEsperada.equals("71") ||
			produccionEsperada.equals("72") ||
			produccionEsperada.equals("73")) {
			return 207;
		}
		if (produccionEsperada.equals("81") ||
			produccionEsperada.equals("82") ||
			produccionEsperada.equals("83") ||
			produccionEsperada.equals("84") ||
			produccionEsperada.equals("85")) {
			return 208;
		}
		if (produccionEsperada.equals("300")) {
			return 201;
		}
		if (produccionEsperada.equals("301")) {
			return 204;
		}
		if (produccionEsperada.equals("302")) {
			return 204;
		}
		if (produccionEsperada.equals("303")) {
			return 205;
		}
		if (produccionEsperada.equals("304")) {
			return 204;
		}
		if (produccionEsperada.equals("305")) {
			return 205;
		}
		if (produccionEsperada.equals("306")) {
			return 204;
		}
		if (produccionEsperada.equals("307")) {
			return 205;
		}
		if (produccionEsperada.equals("308")) {
			return 204;
		}
		if (produccionEsperada.equals("309")) {
			return 204;
		}
		if (produccionEsperada.equals("310")) {
			return 201;
		}
		if (produccionEsperada.equals("311")) {
			return 204;
		}
		if (produccionEsperada.equals("312")) {
			return 201;
		}
		if (produccionEsperada.equals("313")) {
			return 204;
		}
		if (produccionEsperada.equals("314")) {
			return 208;
		}
		if (produccionEsperada.equals("315")) {
			return 208;
		}
		if (produccionEsperada.equals("316")) {
			return 204;
		}
		if (produccionEsperada.equals("317")) {
			return 201;
		}
		if (produccionEsperada.equals("318")) {
			return 206;
		}
		if (produccionEsperada.equals("319")) {
			return 206;
		}
		
		return -1;
	}
	
	public String devolverMensajePorCodigo() {
		String resultado="No mando el mensaje de error, no se encontro el codigo";
		int codError = asignarCodigoError();
		codigoError = codError;
		switch (codigoError) {
		case 101:
			resultado="Simbolo desconocido.";
			break;
		case 200:
			resultado="Sin error.";
			break;
		case 201:
			resultado="Se esperaba Palabra Reservada.";
			break;
		case 204:
			resultado="Se esperaba Identificador.";
			break;
		case 205:
			resultado="Se esperaba Delimitador.";
			break;
		case 206:
			resultado="Se esperaba Constante.";
			break;
		case 207:
			resultado="Se esperaba Operador.";
			break;
		case 208:
			resultado="Se esperaba Operador Relacional.";
			break;
		default:break;
		}
		
		return resultado;
	}
	
	public String devolverMensajeError () {
		String resultado="No mando el mensaje de error, hay un error en el error";
		String mensaje = devolverMensajePorCodigo();
		switch (tipoError) {
		case 1:
			resultado="Error tipo 1:"+codigoError+" Linea "+numeroLinea+". "+mensaje;
			break;
		case 2:
			resultado="Error tipo 2:"+codigoError+" Linea "+numeroLinea+". "+mensaje;
			break;
		default:break;
		}
		
		return resultado;
	}
}
