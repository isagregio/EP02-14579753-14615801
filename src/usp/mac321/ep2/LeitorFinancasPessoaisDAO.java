package usp.mac321.ep2;
import java.util.List;


public interface LeitorFinancasPessoaisDAO {
	public List<Usuario>      leUsuarios(String nomeArquivoUsuarios);
	public List<TipoDespesa>  leTiposDespesas(String nomeArquivoTiposDespesas);
	public List<TipoReceita>  leTiposReceitas(String nomeArquivoTiposReceitas);
	public List<Lancamento>   leLancamentos(String  nomeArquivoLancamentos);
   
    // parte do exercicio 2 para escrita nos documentos
	// DUVIDA: depois de refatorar usando a segregacao ainda deixa essa parte?
    void escreveUsuarios(String nomeArquivoUsuarios, List<Usuario> usuarios);
    void escreveTiposDespesas(String nomeArquivoTiposDespesas, List<TipoDespesa> tiposDespesas);
    void escreveTiposReceitas(String nomeArquivoTiposReceitas, List<TipoReceita> tiposReceitas);
    void escreveLancamentos(String nomeArquivoLancamentos, List<Lancamento> lancamentos);
}
