package co.edu.poli.examen2_Soto.modelo;

import java.util.Objects;

/**
 * Clase base para tarjetas de débito y crédito.
 * Mantiene sus datos comunes y permite cambiar su estado.
 */
public abstract class Tarjeta {

	private String numero;

	private String fechaExp;

	private EstadoTarjeta estado;

	private Titular titular;

	/**
	 * Crea una tarjeta utilizando el estado booleano de compatibilidad.
	 *
	 * @param numero número identificador de la tarjeta
	 * @param fechaExp fecha de expiración
	 * @param estado {@code true} si está activa; {@code false} si está bloqueada
	 * @param titular titular de la tarjeta
	 */
	public Tarjeta(String numero, String fechaExp, boolean estado, Titular titular) {
		this(numero, fechaExp, estado ? EstadoTarjeta.ACTIVA : EstadoTarjeta.BLOQUEADA, titular);
	}

	/**
	 * Crea una tarjeta con un estado explícito.
	 *
	 * @param numero número identificador de la tarjeta
	 * @param fechaExp fecha de expiración
	 * @param estado estado de la tarjeta; no puede ser {@code null}
	 * @param titular titular de la tarjeta
	 * @throws NullPointerException si el estado es {@code null}
	 */
	public Tarjeta(String numero, String fechaExp, EstadoTarjeta estado, Titular titular) {
		this.numero = numero;
		this.fechaExp = fechaExp;
		this.estado = Objects.requireNonNull(estado, "El estado de la tarjeta es obligatorio");
		this.titular = titular;
	}

	/** @return número identificador de la tarjeta */
	public String getNumero() {
		return numero;
	}

	/** @param numero nuevo número identificador de la tarjeta */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/** @return fecha de expiración de la tarjeta */
	public String getFechaExp() {
		return fechaExp;
	}

	/** @param fechaExp nueva fecha de expiración */
	public void setFechaExp(String fechaExp) {
		this.fechaExp = fechaExp;
	}

	/**
	 * Indica si la tarjeta está activa.
	 *
	 * @return {@code true} únicamente cuando su estado es {@link EstadoTarjeta#ACTIVA}
	 */
	public boolean isEstado() {
		return estado == EstadoTarjeta.ACTIVA;
	}

	/**
	 * Cambia el estado usando el formato booleano de compatibilidad.
	 *
	 * @param estado {@code true} para activar; {@code false} para bloquear
	 */
	public void setEstado(boolean estado) {
		this.estado = estado ? EstadoTarjeta.ACTIVA : EstadoTarjeta.BLOQUEADA;
	}

	/** @return estado actual de la tarjeta */
	public EstadoTarjeta getEstado() {
		return estado;
	}

	/**
	 * Establece el estado de la tarjeta.
	 *
	 * @param estado nuevo estado; no puede ser {@code null}
	 * @throws NullPointerException si el estado es {@code null}
	 */
	public void setEstado(EstadoTarjeta estado) {
		this.estado = Objects.requireNonNull(estado, "El estado de la tarjeta es obligatorio");
	}

	/** @return titular de la tarjeta */
	public Titular getTitular() {
		return titular;
	}

	/** @param titular nuevo titular de la tarjeta */
	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	/**
	 * Bloquea la tarjeta.
	 *
	 * @return mensaje que confirma el bloqueo
	 */
	public String bloquear() {
		this.estado = EstadoTarjeta.BLOQUEADA;
		return "Tarjeta " + numero + " BLOQUEADA.";
	}

	/**
	 * Activa la tarjeta.
	 *
	 * @return mensaje que confirma la activación
	 */
	public String activar() {
		this.estado = EstadoTarjeta.ACTIVA;
		return "Tarjeta " + numero + " ACTIVADA.";
	}

	/**
	 * Cancela la tarjeta.
	 *
	 * @return mensaje que confirma la cancelación
	 */
	public String cancelar() {
		this.estado = EstadoTarjeta.CANCELADA;
		return "Tarjeta " + numero + " CANCELADA.";
	}

	/**
	 * Devuelve los datos comunes de la tarjeta en texto.
	 *
	 * @return representación textual de los atributos comunes
	 */
	@Override
	public String toString() {
		return "numero=" + numero + ", fechaExp=" + fechaExp + ", estado=" + estado + ", titular=" + titular;
	}
}
