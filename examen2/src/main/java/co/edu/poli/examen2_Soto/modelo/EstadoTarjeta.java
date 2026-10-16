package co.edu.poli.examen2_Soto.modelo;

/**
 * Estados posibles de una tarjeta.
 */
public enum EstadoTarjeta {
	/** La tarjeta está habilitada para su utilización. */
	ACTIVA,
	/** La tarjeta está temporalmente inhabilitada. */
	BLOQUEADA,
	/** La tarjeta está cancelada y no debe utilizarse. */
	CANCELADA
}
