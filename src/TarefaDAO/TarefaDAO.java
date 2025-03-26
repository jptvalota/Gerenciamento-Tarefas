/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TarefaDAO;

import beans.Tarefa;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rose
 */
public class TarefaDAO {
     private Conexao conexao;
                private Connection conn; 
                 
                public TarefaDAO() {
                this.conexao = new Conexao();
                this.conn = this.conexao.getConexao();
            }
                
                 public void inserir(Tarefa tarefa) {
                String sql = "INSERT INTO tarefas(nome, descricao, estado) VALUES "
                    + "(?, ?, ?)";
                try {
                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                    stmt.setString(1, tarefa.getNomeTarefa());
                     stmt.setString(2, tarefa.getDescricao());
                     stmt.setString(3, tarefa.getEstado());
                       stmt.execute();
            
                } catch (Exception e) {
                    System.out.println("Erro ao inserir tarefa: " + e.getMessage());
                }
            }
                 
                              public Tarefa getTarefa (int id){
      String sql = "SELECT * FROM tarefas WHERE id = ?";
      try {
                  
          PreparedStatement stmt = this.conn.prepareStatement(sql);
          stmt.setInt(1, id);
          ResultSet rs = stmt.executeQuery();
        
          Tarefa tarefa = new Tarefa();
          
          rs.next(); 
          tarefa.setId(id);
          tarefa.setNomeTarefa(rs.getString("nome"));
          tarefa.setDescricao(rs.getString("descricao")); 
          tarefa.setEstado(rs.getString("estado"));
          
          
          return tarefa;
          
          //tratando o erro, caso ele ocorra
      } catch (Exception e) {
          System.out.println("erro: " + e.getMessage());
          return null;
      }
  }
                              public List<Tarefa> getTarefa(String nomeTarefa ) { //parâmetro para buscar a empresa pelo nome
                String sql = "SELECT * FROM tarefas WHERE nome LIKE ?"; //LIKE nos permite pesquisar por partes de um nome, ao invés de pesquisarmos por todo nome
                
                try {
                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                    //Como temos um parâmetro, devemos defini-lo
                    stmt.setString(1,"%" + nomeTarefa + "%"); //Conforme for a palavra ou letra digitada para pesquisa, será buscada antes, no meio e no fim
                    //Método para poder executar o SELECT.
                    //Os resultados obtivos pela consulta serão armazenados na variavel ResultSet
                    ResultSet rs = stmt.executeQuery();            
                    
                    //Vamos criar um objeto do tipo List
                    //Faça a importação do ArrayList
                    List<Tarefa> listaTarefas = new ArrayList<>();
                    //percorrer o resultSet e salvar as informações dentro de uma variável "Empresa"
                    //Depois salva esse objeto dentro da lista
                    
                    //Estrutura de repetição While
                    while (rs.next()) { //.next retorna verdadeiro caso exista uma próxima posição dentro do array
                    Tarefa tarefa = new Tarefa();
                    //Salvar dentro do objeto empresa as informações            
                    tarefa.setId(rs.getInt("id"));
                    tarefa.setNomeTarefa(rs.getString("nome"));
                    tarefa.setDescricao(rs.getString("descricao"));
                    tarefa.setEstado(rs.getString("estado"));
                    //Adicionando os elementos na lista criada
                    listaTarefas.add(tarefa);
                            
                    }
                    //Após finalizar o while, o retorno será a listaEmpresas, onde cada posição é um registro do banco de dados
                    return listaTarefas;
                    
                    //Se o método entrar no "Catch" quer dizer que não encontrou nenhuma empresa, então damos um "return null"
                } catch (Exception e) {
                    return null;
                }
                
            }
       
                               public void editar (Tarefa tarefa){
                //string sql com o código de update para o banco de dados
                String sql = "UPDATE tarefas SET nome=?, descricao=?, estado=? WHERE id=?";
                try {
                    //esse trecho é igual ao método inserir
                    PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    //Setando os parâmetros
                    stmt.setString(1, tarefa.getNomeTarefa());
                    stmt.setString(2, tarefa.getDescricao());
                    stmt.setString(3, tarefa.getEstado());
                    stmt.setInt(4, tarefa.getId());
                    //Executando a query
                    stmt.execute();
                    
      
                    //tratando o erro, caso ele ocorra
                } catch (Exception e) {
                    System.out.println("Erro ao editar tarefa: " + e.getMessage());
                }
            }
                                              public void excluir (int id){
                
                String sql = "DELETE FROM tarefas WHERE id = ?";
                try {
                    //esse trecho é igual ao método editar e inserir
                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    
                    //Executando a query
                    stmt.execute();
                    //tratando o erro, caso ele ocorra
                } catch (Exception e) {
                    System.out.println("Erro ao excluir tarefa: " + e.getMessage());
                }
                
            }
}
