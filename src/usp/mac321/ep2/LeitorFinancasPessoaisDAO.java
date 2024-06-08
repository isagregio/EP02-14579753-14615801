package usp.mac321.ep2;
import java.util.List;


public interface LeitorFinancasPessoaisDAO {
	public List<Usuario>      leUsuarios(String nomeArquivoUsuarios);
	public List<TipoDespesa>  leTiposDespesas(String nomeArquivoTiposDespesas);
	public List<TipoReceita>  leTiposReceitas(String nomeArquivoTiposReceitas);
	public List<Lancamento>   leLancamentos(String  nomeArquivoLancamentos);
}
