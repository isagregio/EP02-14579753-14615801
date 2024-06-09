package usp.mac321.ep2;

import java.util.ArrayList;
import java.util.List;

public class Lancamento {
	private static long contador = 1; // contador que só aumenta? checar com o miguel.
	private List<Integer> data; // lista de 3 elementos. checar se vamos precisar dos valores como ints mesmo ou só strings mais pra frente.
	private Long identificador_unico;
	private boolean despesa;
	private Usuario usuario;
	private TipoLancamento categoria;  
	// TipoLancamento seria uma interface a ser implementada que controla TipoDespesa e TipoReceita juntos. ver com o miguel se seria assim.
	private String descricao;
	private double valor;
	
	
	public Lancamento (Long identificador_unico, String data_em_texto, boolean despesa, Usuario usuario, TipoLancamento categoria, String descricao, double valor) {
		this.identificador_unico = identificador_unico;
		
		// conversao de data
        ArrayList<Integer> data = new ArrayList<>();
        for (String numero : data_em_texto.split("/")) {
        	data.add(Integer.parseInt(numero));
        }
		this.data = data;
		
		this.despesa = despesa;
		this.categoria = categoria;
		this.descricao = descricao;
		this.valor = valor;
		this.usuario = usuario;
		

	}
	
	public Long getIdentificador() {
		return identificador_unico;
	}

	public TipoLancamento getCategoria() {
		return categoria;
	}
	
	public double getValor() {
		return valor;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
}
