package usp.mac321.ep2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestaLeitura {

	
	List<Usuario>      usuarios;
	List<TipoDespesa>  tiposDespesas;
	List<TipoReceita>  tiposReceitas;
	List<Lancamento>   lancamentos;
	LeitorFinancasPessoaisDAO leitor;

	
	@BeforeEach
	void setUp() throws Exception {
		// leitor = ...
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	public void testTiposDespesas(){
		tiposDespesas = leitor.leTiposDespesas("tiposDespesas.csv");
		assertEquals(6, tiposDespesas.size());
	}
	
	@Test
	public void testTiposReceitas(){
		tiposReceitas = leitor.leTiposReceitas("tiposReceitas.csv");
		assertEquals(4, tiposReceitas.size());
	}
	
	@Test
	public void testUsuarios(){
		usuarios = leitor.leUsuarios("usuarios.csv");
		assertEquals(2, usuarios.size());
	}
	
	@Test
	public void testLancamentosOK(){
		lancamentos = leitor.leLancamentos("lancamentos.csv");
		assertEquals(8, lancamentos.size());
	}

	@Test
	public void testLancamentoUsuarioDesconhecido(){
		// TODO
		fail();
	}
	@Test
	public void testLancamentoDespesaDesconhecida(){
		// TODO
		fail();
	}
	@Test
	public void testLancamentoReceiraDesconhecida(){
		// TODO
		fail();
	}
	

}
