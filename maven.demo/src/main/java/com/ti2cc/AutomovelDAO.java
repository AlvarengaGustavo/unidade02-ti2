package com.ti2cc;

import java.sql.*;

public class AutomovelDAO {
    private Connection conexao;

    public AutomovelDAO() {
        conexao = null;
    }

    public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;
		
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexao efetuada com o postgres!");
		} catch (ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}
		
		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

    public boolean inserirAutomovel(Automovel automovel) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO automovel (codigo, modelo, marca, ano_fabricacao) "
                            + "VALUES (" + automovel.getCodigo() + ", '" + automovel.getModelo() + "', '"
                            + automovel.getMarca() + "', " + automovel.getAnoFabricacao() + ");");
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean atualizarAutomovel(Automovel automovel) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE automovel SET modelo = '" + automovel.getModelo() + "', marca = '"
                    + automovel.getMarca() + "', ano_fabricacao = " + automovel.getAnoFabricacao()
                    + " WHERE codigo = " + automovel.getCodigo();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean excluirAutomovel(int codigo) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM automovel WHERE codigo = " + codigo);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Automovel[] getAutomoveis() {
        Automovel[] automoveis = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM automovel");
            if (rs.next()) {
                rs.last();
                automoveis = new Automovel[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    automoveis[i] = new Automovel(rs.getInt("codigo"), rs.getString("modelo"),
                            rs.getString("marca"), rs.getInt("ano_fabricacao"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return automoveis;
    }
    
    public Automovel getAutomovelPorCodigo(int codigo) {
        Automovel automovel = null;

        try {
            PreparedStatement ps = conexao.prepareStatement("SELECT * FROM automovel WHERE codigo = ?");
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                automovel = new Automovel(rs.getInt("codigo"), rs.getString("modelo"),
                        rs.getString("marca"), rs.getInt("ano_fabricacao"));
            }

            ps.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return automovel;
    }
}
