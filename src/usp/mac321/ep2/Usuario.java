package usp.mac321.ep2;

public class Usuario {
	private String apelido;
	private String nome;
	
	public Usuario (String apelido, String nome) {
		this.apelido = apelido;
		this.nome = nome;
	}
	
	public String getApelido() {
		return apelido;
	}

	public String getNome() {
		return nome;
	}
}
