package br.jus.tjce.bancoerro.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * Classe utilit�ria para opera��es em datas.
 * 
 * @author d333379
 * 
 */
public class DateUtil {

	/**
	 * Fomato do Pattern padr�o para data no sistema.
	 */
	public static final String FORMATO_DATA_PADRAO = "dd/MM/yyyy";
	

	/**
	 * Adiciona uma quantidade de dias na data.
	 * 
	 * @param dias
	 * @param data
	 * @return A data com a quantidade de dias adicionados
	 */
   public static final Date adicionaDias(int dias, Date data)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(6, dias);
        return calendar.getTime();
    }
	
	
	/**
	 * Verifica se a data inicial � maior que a data final. Se pelo menos uma
	 * das datas for null, retorna true.
	 * 
	 * @param dtInicio
	 * @param dtFim
	 * @return
	 */
	public static boolean isDataInicialMaiorQueFinal(Date dtInicio, Date dtFim) {
		if (dtInicio == null || dtFim == null)
			return true;

		// Critica se data inicial � maior que a data final.
		if (dtInicio.after(dtFim)) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se a data final � maior que a data atual. Se a data final for
	 * null, retorna true.
	 * 
	 * @param dtFim
	 * @return
	 */
	public static boolean isDataFinalMaiorQueAtual(Date dtFim) {
		if (dtFim == null)
			return true;

		// Critica se data final � maior que a data atual.
		if (dtFim.after(new Date())) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se a data inicial � anterior � data atual mais do que a
	 * quantidade de meses passada por par�metro. Se a data inicial for null,
	 * retorna true.
	 * 
	 * @param dtInicio
	 * @param meses
	 * @return
	 */
	public static boolean isDataInicialAnterior(Date dtInicio, int meses) {
		if (meses <= 0)
			return true;

		Calendar calendario = new GregorianCalendar();
		calendario.setTime(dtInicio);
		calendario.add(GregorianCalendar.MONTH, meses);
		calendario.add(GregorianCalendar.DAY_OF_MONTH, 1);
		if (calendario.getTime().before(new Date()))
			return true;
		return false;
	}
	
	/**
	 * Verifica se a data inicial � anterior � data final mais do que a
	 * quantidade de dias passado por par�metro. Se a data inicial for null,
	 * retorna true.
	 * 
	 * @param dtInicio
	 * @param meses
	 * @return
	 */
	public static boolean isDiferencaDatasInferiorXDias(Date dtInicio, Date dtFim, int dias) {
		if (dias <= 0)
			return true;

		Calendar calendario = new GregorianCalendar();
		calendario.setTime(dtInicio);
		calendario.add(GregorianCalendar.DAY_OF_MONTH, dias);
		if (calendario.getTime().before(dtFim))
			return true;
		return false;
	}

	/**
	 * Esse m�todo recebe uma String representando uma data no formato utilizado
	 * no Brasil (dd/MM/yyyy) e retorna um objeto <code>java.util.Date</code>
	 * representativo dessa mesma data.
	 *
	 * A informa��o de dia e m�s pode conter um ou dois d�gitos.
	 *
	 * @return java.util.Date representando a data informada como String.
	 *         Retorna <code>null</code> se a data for <code>null</code>.
	 * @param data
	 *            - data a ser reformatada.
	 * @throw IllegalArgumentException - se a data n�o representar um formato de
	 *        datas utilizado no Brasil ou n�o representar uma data v�lida.
	 */
	public static Date parseString2Date(String data) {
		Date retorno = null;

		if ((data == null) || (data.trim().equals(""))) {
			return retorno;
		}

		DateFormat df = new SimpleDateFormat(FORMATO_DATA_PADRAO);
		df.setLenient(false);
		try {
			retorno = df.parse(data);
		} catch (ParseException e) {
			throw new IllegalArgumentException("A data [" + data
					+ "] n�o representa uma data v�lida!");
		}
		return retorno;
	}

	/**
	 * Esse m�todo recebe um objeto <code>java.util.Date</code> representando
	 * uma data obtida no banco de dados e retorna a representa��o dessa data em
	 * String no formato de data utilizada no Brasil (dd/MM/yyyy).
	 *
	 * @return String representando a data no formato brasileiro (dd/MM/yyyy).
	 *         Retorna <code>null</code> se a data for <code>null</code>.
	 * @param data
	 *            - data a ser reformatada.
	 */
	public static String parseDate2String(final Date data) {

		String retorno = null;

		if (data == null) {
			return retorno;
		}

		DateFormat df = new SimpleDateFormat(FORMATO_DATA_PADRAO);
		retorno = df.format(data);

		return retorno;
	}

	/**
	 * Valida se a data inicial � inferior a 10 anos da data atual Se a data
	 * inicial for inferir a data atual retorna 'true'
	 * 
	 * @param dataInicio
	 *            tipo String
	 * 
	 */
	public static boolean isDataInicialInferior10Anos(String dataInicio) {
		int anos = 10;
		Date dtAtual = new Date();
		Date dtInicio = parseString2Date(dataInicio);
		// System.out.println("Data Atual: " + dtAtual);

		// DataLimite (10 anos antes da atual)
		return isDiferencaDatasInferiorXAnos(dtInicio, dtAtual, anos);
	}

	/**
	 * Valida se a diferen�a entre as datas � menor que o intervalo em anos informado.
	 * @param dataInicio Data de in�cio do intervalo
	 * @param dataFim Data finaL do intervalo
	 * @param anos N�mero de anos do intervalo (Positivo)
	 * @return
	 */
	public static boolean isDiferencaDatasInferiorXAnos(Date dataInicio, Date dataFim, int anos) {
		Calendar cLimite = Calendar.getInstance();
		cLimite.setTime(dataFim);
		cLimite.add(Calendar.YEAR, -anos);
		Date dtLimite = cLimite.getTime();

		// data inicial tem X anos ou mais da data atual
		if (dataInicio.before(dtLimite)) {
			return false;
		} else {// Data inicio tem menos de X anos da data atual
			return true;
		}
	}

	// private static void validarData(String data)
	// {
	// if (!(Pattern.matches("\\d\\d?/\\d\\d?/\\d\\d\\d\\d", data)))
	// throw new IllegalArgumentException("A data [" + data +
	// "] n�o representa um formato v�lido!");
	// }

	/**
	 * Retorna a data inicial m�nima (formato dd/mm/yyyy) em rela��o � data
	 * atual de acordo com a quantidade de meses passada por par�metro. �til
	 * para limitar a data m�nima no componente de calend�rio.
	 * 
	 * @param meses
	 * @return
	 */
	public static String getDataMinima(int meses) {
		if (meses <= 0)
			return "";

		Calendar calendario = new GregorianCalendar();
		calendario.add(GregorianCalendar.MONTH, -meses);
		return new SimpleDateFormat(FORMATO_DATA_PADRAO).format(calendario.getTime());

	}

	/**
	 * Valida se a data inicial � inferior a 2 anos da data final Se a data
	 * inicial for inferir a data final retorna 'true'
	 * 
	 * @param dataInicio
	 *            tipo String
	 * @param dataFim
	 *            tipo String
	 */
	public static boolean isDataInicialFinalMaior2Anos(String dataInicio, String dataFim) {
	
		Date dtInicio = parseString2Date(dataInicio);
		Date dtFim = parseString2Date(dataFim);
	
		// DataLimite (2 anos antes da final)
		Calendar cLimite = Calendar.getInstance();
		cLimite.setTime(dtInicio);
		cLimite.add(Calendar.YEAR, 2);
		Date dtLimite = cLimite.getTime();
		// data inicial maior de 2 anos da data final
		if (dtLimite.before(dtFim)) {
			return false;
		} else {// Data inicio tem 2 anos ou menos da data atual
			return true;
		}
	}

	public DateUtil() {
		super();
	}
	
	/**
	 * Valida se a data est� no formato correto (dd/mm/aaaa) Se a data est� no
	 * formato correto retorna 'true'
	 * 
	 * @param data
	 *            tipo String
	 * @param nomeCampo
	 *            tipo String
	 */
	public static boolean validarData(String data, String nomeCampo) {

		// N�o escolheu op��o v�lida
		if (data == null || data.equals("")) {
			MensagemUtil.exibirMsgErro(nomeCampo+" � obrigat�rio(a)");
			return false;
		}
		// Verifica se a data informada pelo usu�rio est� no formato correto
		// (dd/MM/yyyy)
		if (!(Pattern.matches("\\d\\d?/\\d\\d?/\\d\\d\\d\\d", data))) {
			MensagemUtil.exibirMsgErro(nomeCampo+" inv�lido(a)");
			return false;
		}

		return true;
	}

}