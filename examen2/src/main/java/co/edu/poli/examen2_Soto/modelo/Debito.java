package co.edu.poli.examen2_Soto.modelo;

/**
 * Representa una tarjeta de débito con un saldo disponible.
 */
public class Debito extends Tarjeta {

	private double saldo;

	/**
	 * Crea una tarjeta de débito a partir del estado booleano legado.
	 *
	 * @param numero número identificador de la tarjeta
	 * @param fechaExp fecha de expiración
	 * @param estado {@code true} si está activa; {@code false} si está bloqueada
	 * @param titular titular de la tarjeta
	 * @param saldo saldo inicial disponible
	 */
	public Debito(String numero, String fechaExp, boolean estado, Titular titular, double saldo) {
		super(numero, fechaExp, estado, titular);
		this.saldo = saldo;
	}

	/**
	 * Crea una tarjeta de débito con un estado explícito.
	 *
	 * @param numero número identificador de la tarjeta
	 * @param fechaExp fecha de expiración
	 * @param estado estado de la tarjeta
	 * @param titular titular de la tarjeta
	 * @param saldo saldo inicial disponible
	 */
	public Debito(String numero, String fechaExp, EstadoTarjeta estado, Titular titular, double saldo) {
		super(numero, fechaExp, estado, titular);
		this.saldo = saldo;
	}

	/**
	 * Obtiene el saldo disponible.
	 *
	 * @return saldo de la tarjeta
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Actualiza el saldo disponible.
	 *
	 * @param saldo nuevo saldo
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * Devuelve la representación textual de la tarjeta y su saldo.
	 *
	 * @return datos de la tarjeta de débito
	 */
	@Override
	public String toString() {
		return "Debito [" + super.toString() + ", saldo=" + saldo + "]";
	}
}
