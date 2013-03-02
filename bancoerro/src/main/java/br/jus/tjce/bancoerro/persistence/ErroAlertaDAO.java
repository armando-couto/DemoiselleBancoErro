package br.jus.tjce.bancoerro.persistence;

import java.util.List;

import javax.persistence.Query;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.jus.tjce.bancoerro.domain.ErroAlerta;

@PersistenceController
public class ErroAlertaDAO extends JPACrud<ErroAlerta, Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * Pesquisar Erros/Alertas atrav�s de seus par�metros.
	 * @param codSistema
	 * @param codErroAlerta
	 * @return Um objeto do tipo Erro/Alerta
	 */
	
	public ErroAlerta pesquisarErroAlerta(String codSistema, String codErroAlerta) {

		Query query = createQuery("SELECT ea FROM ErroAlerta ea WHERE ea.codigo = :codErroAlerta AND ea.sistema.codigo = :codSistema");

		query.setParameter("codErroAlerta", codErroAlerta);
		query.setParameter("codSistema", codSistema);

		if (query.getResultList().isEmpty()){
			return null;
		}
		else {
			return (ErroAlerta) query.getSingleResult();
		}
	}
	
	/**
	 * Pesquisar maior 'Id' do Erro/Alerta por um c�digo do sistema.
	 * @param codigo_sistema
	 * @return O �ltimo Erro/Alerta inserido.
	 */
	
	public String pesquisarMaiorIdErroAlertaByCodSistema(String codigo_sistema) {
		Query query = createQuery("SELECT max(ea.codigo) FROM ErroAlerta ea WHERE ea.sistema.codigo = :codigo");
		
		query.setParameter("codigo", codigo_sistema);

		return (String) query.getSingleResult();
	}
	
	/**
	 * Pesquisar Erros/Alertas por um determinado c�digo do sistema 
	 * @param codigo_sistema
	 * @return Lista de Erros/Alertas 
	 */
	
	@SuppressWarnings("unchecked")
	public List<ErroAlerta> pesquisarErroAlertaByCodSistema(String codigo_sistema) {
		Query query = 
			createQuery("SELECT ea FROM ErroAlerta ea WHERE ea.sistema.codigo = :codigo");
		
		query.setParameter("codigo", codigo_sistema);

		return query.getResultList();
	}
}