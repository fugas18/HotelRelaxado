/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexao;
//Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

//Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira        

/**
 *
 * @author Acer
 */
public class MyConnection {
//Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira    
    private static String URL = "jdbc:mysql://localhost:3306/hotel?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection conn = null; 
//Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira     
   public Connection getConnection() throws SQLException {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida!");
            
            
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado!");
           e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Falha na conexão com a base de dados!");
            e.printStackTrace();
        }
        return conn;
 //Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira      
    }
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }
//Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
    public void close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

}

//Projeto Desenvolvido por Guilherme Martins e Hugo Oliveira
