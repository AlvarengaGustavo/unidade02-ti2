package com.ti2cc;

public class Automovel {
    private int codigo;
    private String modelo;
    private String marca;
    private int anoFabricacao;

    public Automovel() {
        this.codigo = -1;
        this.modelo = "";
        this.marca = "";
        this.anoFabricacao = -1;
    }

    public Automovel(int codigo, String modelo, String marca, int anoFabricacao) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.anoFabricacao = anoFabricacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    @Override
    public String toString() {
        return "Automovel [codigo=" + codigo + ", modelo=" + modelo + ", marca=" + marca + ", anoFabricacao=" + anoFabricacao + "]";
    }
}
