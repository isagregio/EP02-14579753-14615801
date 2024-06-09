package usp.mac321.ep2.ex1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import usp.mac321.ep2.Lancamento;
import usp.mac321.ep2.LeitorFinancasPessoaisDAO;
import usp.mac321.ep2.TipoDespesa;
import usp.mac321.ep2.TipoLancamento;
import usp.mac321.ep2.TipoReceita;
import usp.mac321.ep2.Usuario;

// TO DO LIST

// [] adicionar exceção de arquivo nao encontrado em tds as 4 funções
 

public class LeitorFinancasPessoaisCSV implements LeitorFinancasPessoaisDAO {
	private ArrayList<Usuario> usuarios;
	private HashSet<String> apelidos;
	private ArrayList<TipoDespesa> tipos_despesas;
	private HashSet<String> subcategorias_despesas;
	private ArrayList<TipoReceita> tipos_receitas;
	private HashSet<String> subcategorias_receitas;
	
	@Override
	public List<Usuario> leUsuarios(String nomeArquivoUsuarios) {
		usuarios = new ArrayList<>();
		apelidos = new HashSet<>();
		        
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoUsuarios))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                
                // checar se essa é a ordem no csv de teste do prof
                String apelido = partes[0];
                String nome = partes[1];
                
                if (!apelidos.add(apelido)) {
                    throw new ApelidoDuplicadoException("Apelido Duplicado Encontrado: " + apelido);
                }
                
                usuarios.add(new Usuario(apelido, nome));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ApelidoDuplicadoException e) {
        	return null; // é isso? o usuario nao recebe a exceção tb? 
        }
		return usuarios;
	}

	@Override
	public List<TipoDespesa> leTiposDespesas(String nomeArquivoTiposDespesas) {
		tipos_despesas = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoTiposDespesas))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                
                String categoria = partes[0]; 
                String subcategoria = partes[1];  
                
                subcategorias_despesas.add(partes[1]);
                
                tipos_despesas.add(new TipoDespesa(categoria, subcategoria));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return tipos_despesas;
	}

	@Override
	public List<TipoReceita> leTiposReceitas(String nomeArquivoTiposReceitas) {
		tipos_receitas = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoTiposReceitas))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                
                String categoria = partes[0]; 
                String subcategoria = partes[1];

                subcategorias_receitas.add(partes[1]);
                
                tipos_receitas.add(new TipoReceita(categoria, subcategoria));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return tipos_receitas;
	}

	@Override
	public List<Lancamento> leLancamentos(String nomeArquivoLancamentos) {
		ArrayList<Lancamento> lancamentos = new ArrayList<>();
		
        Set<Long> identificadores = new HashSet<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoLancamentos))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                
                Long identificador = Long.valueOf(partes[0]);
                String data = partes[1];               
                String apelido = partes[2];
                boolean despesa = partes[3].equals("TRUE");
                String subcategoria = partes[4];
                double valor = Double.parseDouble(partes[5]);
                String descricao = partes[6];
                
                if (!identificadores.add(identificador)) {
                    throw new IdentificadorDuplicadoException("Identificador Duplicado Encontrado: " + partes[0]);
                }
                if ((despesa && subcategorias_receitas.contains(subcategoria)) || (!despesa && subcategorias_receitas.contains(subcategoria))) {
                	throw new SubcategoriaIncoerenteException("Subategoria Incoerente:" + subcategoria); // melhorar essa descrição de exceção
                }
                if (apelidos.add(apelido)) {
                	apelidos.remove(apelido); 
                	throw new UsuarioInexistenteException("Usuario Inexistente Encontrado:" + apelido);
                }
                if (valor < 0) {
                	throw new ValorNegativoException("Valor Negativo Encontrado: " + partes[6]);
                }
                lancamentos.add(new Lancamento(identificador, data, despesa, getUsuario(apelido), getTipo(subcategoria, despesa), descricao, valor));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValorNegativoException | IdentificadorDuplicadoException | UsuarioInexistenteException | SubcategoriaIncoerenteException e) {
        	return null; // é isso? o usuario nao recebe a exceção tb? 
        }
		return lancamentos;
	}
	
	
	// funções getters auxiliares
	
	public Usuario getUsuario(String apelido) {
		for (Usuario usuario : usuarios) {
			if (usuario.getApelido() == apelido)
				return usuario;
		}
		return null;
	}
	
	public TipoLancamento getTipo(String subcategoria, boolean despesa) {
		List<?> tipos;
		if (despesa) {
			tipos = tipos_despesas;
		}
		else {
			tipos = tipos_receitas;
		}
		for (Object tipo : tipos) {
			if (((TipoLancamento) tipo).getSubcategoria() == subcategoria) return (TipoLancamento) tipo;
		}
		return null;
	}
}



