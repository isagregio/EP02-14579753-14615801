public interface EscritaLancamentosDAO {
    void escreveLancamentos(String nomeArquivoLancamentos, List<Lancamento> lancamentos);
}