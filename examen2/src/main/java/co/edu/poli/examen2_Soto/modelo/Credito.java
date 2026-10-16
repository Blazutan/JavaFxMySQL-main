package co.edu.poli.examen2_Soto.modelo;

/**
 * Representa una tarjeta de crédito con un límite de crédito asociado.
 */
public class Credito extends Tarjeta {

	private double limite;

	/**
	 * Crea una tarjeta de crédito a partir del estado booleano legado.
	 *
	 * @param numero número identificador de la tarjeta
	 * @param fechaExp fecha de expiración
	 * @param estado {@code true} si está activa; {@code false} si está bloqueada
	 * @param titular titular de la tarjeta
	 * @param limite límite de crédito asignado
	 */
	public Credito(String numero, String fechaExp, boolean estado, Titular titular, double limite) {
		super(numero, fechaExp, estado, titular);
		this.limite = limite;
	}

	/**
	 * Crea una tarjeta de crédito con un estado explícito.
	 *
	 * @param numero número identificador de la tarjeta
	 * @param fechaExp fecha de expiración
	 * @param estado estado de la tarjeta
	 * @param titular titular de la tarjeta
	 * @param limite límite de crédito asignado
	 */
	public Credito(String numero, String fechaExp, EstadoTarjeta estado, Titular titular, double limite) {
		super(numero, fechaExp, estado, titular);
		this.limite = limite;
	}

	/**
	 * Obtiene el límite de crédito asignado.
	 *
	 * @return límite de crédito
	 */
	public double getLimite() {
		return limite;
	}

	/**
	 * Actualiza el límite de crédito asignado.
	 *
	 * @param limite nuevo límite de crédito
	 */
	public void setLimite(double limite) {
		this.limite = limite;
	}

	/**
	 * Devuelve la representación textual de la tarjeta y su límite.
	 *
	 * @return datos de la tarjeta de crédito
	 */
	@Override
	public String toString() {
		return "Credito [" + super.toString() + ", limite=" + limite + "]";
	}
}
