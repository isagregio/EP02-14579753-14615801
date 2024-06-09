package usp.mac321.ep2;

public class TipoLancamento {
	private String categoria;
	private String subcategoria;
	
	public TipoLancamento (String categoria, String subcategoria) {
		this.categoria = categoria;
		this.subcategoria = subcategoria;
	}
	
	public String getCategoria() {
		return categoria;
	}

	public String getSubcategoria() {
		return subcategoria;
	}
}

