package co.edu.poli.examen2_Soto.servicios;

import java.util.List;

/**
 * Define operaciones CRUD comunes para objetos de una entidad.
 *
 * @param <T> tipo de entidad gestionada
 */
public interface CRUD<T> {

	/**
	 * Crea y persiste una entidad.
	 *
	 * @param t entidad que se desea crear
	 * @return mensaje con el resultado de la operación
	 * @throws Exception si ocurre un error durante la operación
	 */
	String create(T t) throws Exception;

	/**
	 * Busca una entidad mediante su identificador.
	 *
	 * @param id identificador de búsqueda
	 * @param <K> tipo del identificador
	 * @return entidad encontrada o {@code null} si no existe
	 * @throws Exception si ocurre un error durante la consulta
	 */
	<K> T readone(K id) throws Exception;
	
	/**
	 * Recupera todas las entidades disponibles.
	 *
	 * @return lista de entidades
	 * @throws Exception si ocurre un error durante la consulta
	 */
	List<T> readall() throws Exception;
}
