public class LeituraEscritaFinancasPessoaisCSV implements 
    LeituraUsuariosDAO, EscritaUsuariosDAO, 
    LeituraTiposDespesasDAO, EscritaTiposDespesasDAO, 
    LeituraTiposReceitasDAO, EscritaTiposReceitasDAO, 
    LeituraLancamentosDAO, EscritaLancamentosDAO {
    
    private List<Usuario> usuarios = new ArrayList<>();
    private List<TipoDespesa> tipos_despesas = new ArrayList<>();
    private List<TipoReceita> tipos_receitas = new ArrayList<>();

    // DUVIDA: preciso colocar de novo as classes de excecao 
    // ficou falatando a classe de teste

    @Override
    public List<Usuario> leUsuarios(String nomeArquivoUsuarios) {
        List<Usuario> usuarios = new ArrayList<>();
        Set<String> apelidos = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoUsuarios))) {
            reader.readLine(); // IGNORA A PRIMEIRA LINHA 
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                String apelido = partes[0];
                String nome = partes[1];
                if (!apelidos.add(apelido)) {
                    throw new ApelidoDuplicadoException("Apelido Duplicado Encontrado: " + apelido);
                }
                usuarios.add(new Usuario(apelido, nome));
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoUsuarios);
        }
        this.usuarios = usuarios;
        return usuarios;
    }

    @Override
    public void escreveUsuarios(String nomeArquivoUsuarios, List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoUsuarios))) {
            writer.write("apelido,nome\n");
            for (Usuario usuario : usuarios) {
                writer.write(usuario.getApelido() + "," + usuario.getNome() + "\n");
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoUsuarios);
        }
    }

    @Override
    public List<TipoDespesa> leTiposDespesas(String nomeArquivoTiposDespesas) {
        List<TipoDespesa> tipos_despesas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoTiposDespesas))) {
            reader.readLine(); // IGNORA A PRIMEIRA LINHA 
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                String categoria = partes[0]; 
                String subcategoria = partes[1];
                tipos_despesas.add(new TipoDespesa(categoria, subcategoria));
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoTiposDespesas);
        }
        this.tipos_despesas = tipos_despesas;
        return tipos_despesas;
    }

    @Override
    public void escreveTiposDespesas(String nomeArquivoTiposDespesas, List<TipoDespesa> tiposDespesas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoTiposDespesas))) {
            writer.write("categoria,subcategoria\n");
            for (TipoDespesa tipo : tiposDespesas) {
                writer.write(tipo.getCategoria() + "," + tipo.getSubcategoria() + "\n");
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoTiposDespesas);
        }
    }

    @Override
    public List<TipoReceita> leTiposReceitas(String nomeArquivoTiposReceitas) {
        List<TipoReceita> tipos_receitas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoTiposReceitas))) {
            reader.readLine(); // IGNORA A PRIMEIRA LINHA 
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                String categoria = partes[0]; 
                String subcategoria = partes[1];
                tipos_receitas.add(new TipoReceita(categoria, subcategoria));
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoTiposReceitas);
        }
        this.tipos_receitas = tipos_receitas;
        return tipos_receitas;
    }

    @Override
    public void escreveTiposReceitas(String nomeArquivoTiposReceitas, List<TipoReceita> tiposReceitas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoTiposReceitas))) {
            writer.write("categoria,subcategoria\n");
            for (TipoReceita tipo : tiposReceitas) {
                writer.write(tipo.getCategoria() + "," + tipo.getSubcategoria() + "\n");
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoTiposReceitas);
        }
    }

    @Override
    public List<Lancamento> leLancamentos(String nomeArquivoLancamentos) {
        List<Lancamento> lancamentos = new ArrayList<>();
        Set<Long> identificadores = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoLancamentos))) {
            reader.readLine(); // IGNORA A PRIMEIRA LINHA 
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
                lancamentos.add(new Lancamento(identificador, data, despesa, getUsuario(apelido), getTipo(subcategoria, despesa), descricao, valor));
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoLancamentos);
        }
        return lancamentos;
    }

    @Override
    public void escreveLancamentos(String nomeArquivoLancamentos, List<Lancamento> lancamentos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoLancamentos))) {
            writer.write("identificador,data,apelido,despesa,subcategoria,valor,descricao\n");
            for (Lancamento lancamento : lancamentos) {
                writer.write(lancamento.getIdentificador() + "," +
                             lancamento.getData() + "," +
                             lancamento.getUsuario().getApelido() + "," +
                             (lancamento.isDespesa() ? "TRUE" : "FALSE") + "," +
                             lancamento.getTipo().getSubcategoria() + "," +
                             lancamento.getValor() + "," +
                             lancamento.getDescricao() + "\n");
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado: " + nomeArquivoLancamentos);
        }
    }

    // Funções auxiliares
    public Usuario getUsuario(String apelido) {
        for (Usuario usuario : usuarios) {
            if (usuario.getApelido().equals(apelido)) {
                return usuario;
            }
        }
        return null;
    }

    public TipoLancamento getTipo(String subcategoria, boolean despesa) {
        if (despesa) {
            for (TipoDespesa tipo : tipos_despesas) {
                if (tipo.getSubcategoria().equals(subcategoria)) {
                    return tipo;
                }
            }
        } else {
            for (TipoReceita tipo : tipos_receitas) {
                if (tipo.getSubcategoria().equals(subcategoria)) {
                    return tipo;
                }
            }
        }
        return null;
    }
}
