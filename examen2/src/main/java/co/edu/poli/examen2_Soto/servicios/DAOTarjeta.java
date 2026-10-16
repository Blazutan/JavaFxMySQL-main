package co.edu.poli.examen2_Soto.servicios;

import co.edu.poli.examen2_Soto.modelo.Credito;
import co.edu.poli.examen2_Soto.modelo.Debito;
import co.edu.poli.examen2_Soto.modelo.EstadoTarjeta;
import co.edu.poli.examen2_Soto.modelo.Tarjeta;
import co.edu.poli.examen2_Soto.modelo.Titular;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Accede a las tarjetas persistidas y a sus tablas específicas por tipo.
 */
public class DAOTarjeta implements CRUD<Tarjeta> {

	/**
	 * Actualiza el estado de una tarjeta existente.
	 *
	 * @param numero número identificador de la tarjeta
	 * @param estado nuevo estado
	 * @return mensaje que indica el resultado de la actualización
	 * @throws IllegalArgumentException si el estado es {@code null}
	 * @throws Exception si ocurre un error durante la actualización
	 */
	public String updateEstado(String numero, EstadoTarjeta estado) throws Exception {
		if (estado == null) {
			throw new IllegalArgumentException("El estado de la tarjeta es obligatorio.");
		}
		String sql = "UPDATE tarjeta SET estado = ? WHERE numero = ?";
		Connection con = ConexionBD.getInstancia().getConexion();
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, estado.name());
			ps.setString(2, numero);
			int filasActualizadas = ps.executeUpdate();
			if (filasActualizadas == 0) {
				if (readone(numero) == null) {
					return "No existe una tarjeta con el número " + numero + ".";
				}
			}
			return "Estado de la tarjeta " + numero + " actualizado a " + estado + ".";
		}
	}

	/**
	 * Persiste una tarjeta y los datos correspondientes a su subtipo.
	 *
	 * @param t tarjeta que se desea guardar
	 * @return mensaje que indica el resultado de la operación
	 * @throws Exception si ocurre un error de conexión o persistencia
	 */
	@Override
	public String create(Tarjeta t) throws Exception {

		Connection con = ConexionBD.getInstancia().getConexion();

		con.setAutoCommit(false);

		String SQL_INSERT_TARJETA = "INSERT INTO tarjeta (numero, fecha_exp, estado, titular_id) "
				+ "VALUES (?, ?, ?, ?)";

		PreparedStatement ps = con.prepareStatement(SQL_INSERT_TARJETA);
		ps.setString(1, t.getNumero());
		ps.setString(2, t.getFechaExp());
		ps.setString(3, t.getEstado().name());
		ps.setString(4, t.getTitular().getId());
		ps.executeUpdate();

		String SQL_INSERT_DEBITO = "INSERT INTO tarjeta_debito (numero, saldo) VALUES (?, ?)";
		String SQL_INSERT_CREDITO = "INSERT INTO tarjeta_credito (numero, limite) VALUES (?, ?)";

		String sql = (t instanceof Debito) ? SQL_INSERT_DEBITO : SQL_INSERT_CREDITO;
		ps = con.prepareStatement(sql);
		ps.setString(1, t.getNumero());
		if (t instanceof Debito)
			ps.setDouble(2, ((Debito) t).getSaldo());
		else
			ps.setDouble(2, ((Credito) t).getLimite());
		
		try {
			ps.executeUpdate();
			con.commit();
			return "✔ " + t.getClass().getSimpleName() + " [" + t.getNumero() + "] guardada correctamente.";
		} catch (Exception e) {
			con.rollback();
			return e.getMessage();
		} finally {
			con.setAutoCommit(true);
		}
	}

	/**
	 * Busca una tarjeta por número, consultando sus datos de subtipo.
	 *
	 * @param num número identificador de la tarjeta
	 * @param <K> tipo del identificador recibido
	 * @return tarjeta encontrada o {@code null} si no existe
	 * @throws Exception si ocurre un error durante la consulta
	 */
	@Override
	public <K> Tarjeta readone(K num) throws Exception {

		Connection con = ConexionBD.getInstancia().getConexion();

		String SQL_SELECT_DEBITO = "SELECT  t.numero, t.fecha_exp, t.estado, "
				+ "        ti.id AS titular_id, ti.nombre AS titular_nombre, " + "        d.saldo "
				+ "FROM    tarjeta_debito d " + "INNER JOIN tarjeta  t  ON d.numero     = t.numero "
				+ "INNER JOIN titular  ti ON t.titular_id = ti.id " + "WHERE   d.numero = ?";

		PreparedStatement ps = con.prepareStatement(SQL_SELECT_DEBITO);
		ps.setString(1, (String) num);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return new Debito(rs.getString("numero"), rs.getString("fecha_exp"),
					EstadoTarjeta.valueOf(rs.getString("estado")),
					new Titular(rs.getString("titular_id"), rs.getString("titular_nombre")), rs.getDouble("saldo"));
		}

		String SQL_SELECT_CREDITO = "SELECT  t.numero, t.fecha_exp, t.estado, "
				+ "        ti.id AS titular_id, ti.nombre AS titular_nombre, " + "        c.limite "
				+ "FROM    tarjeta_credito c " + "INNER JOIN tarjeta  t  ON c.numero     = t.numero "
				+ "INNER JOIN titular  ti ON t.titular_id = ti.id " + "WHERE   c.numero = ?";

		ps = con.prepareStatement(SQL_SELECT_CREDITO);
		ps.setString(1, (String) num);
		rs = ps.executeQuery();
		if (rs.next()) {
			return new Credito(rs.getString("numero"), rs.getString("fecha_exp"),
					EstadoTarjeta.valueOf(rs.getString("estado")),
					new Titular(rs.getString("titular_id"), rs.getString("titular_nombre")), rs.getDouble("limite"));
		}

		return null;
	}

	/**
	 * Operación de lectura múltiple; aún no está implementada.
	 *
	 * @return actualmente devuelve {@code null}
	 */
	@Override
	public List<Tarjeta> readall() {
		return null;
	}
}
