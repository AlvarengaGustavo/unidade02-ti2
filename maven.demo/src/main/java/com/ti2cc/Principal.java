package com.ti2cc;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        AutomovelDAO automovelDAO = new AutomovelDAO();
        automovelDAO.conectar();

        Scanner scanner = new Scanner(System.in);

        int escolha;
        do {
            System.out.println("\n==== Menu ====");
            System.out.println("1) Listar Automóveis");
            System.out.println("2) Inserir Automóvel");
            System.out.println("3) Excluir Automóvel");
            System.out.println("4) Atualizar Automóvel");
            System.out.println("5) Sair");

            System.out.print("Escolha a opção desejada: ");
            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    listarAutomoveis(automovelDAO);
                    break;
                case 2:
                    inserirAutomovel(automovelDAO, scanner);
                    break;
                case 3:
                    excluirAutomovel(automovelDAO, scanner);
                    break;
                case 4:
                    atualizarAutomovel(automovelDAO, scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (escolha != 5);

        automovelDAO.close();
        scanner.close();
    }

    private static void listarAutomoveis(AutomovelDAO automovelDAO) {
        System.out.println("\n==== Listar Automóveis ====");
        Automovel[] automoveis = automovelDAO.getAutomoveis();
        if (automoveis != null) {
            for (Automovel automovel : automoveis) {
                System.out.println(automovel.toString());
            }
        } else {
            System.out.println("Nenhum automóvel encontrado.");
        }
    }

    private static void inserirAutomovel(AutomovelDAO automovelDAO, Scanner scanner) {
        System.out.println("\n==== Inserir Automóvel ====");
        System.out.print("Digite o código do automóvel: ");
        int codigo = scanner.nextInt();
        System.out.print("Digite o modelo do automóvel: ");
        String modelo = scanner.next();
        System.out.print("Digite a marca do automóvel: ");
        String marca = scanner.next();
        System.out.print("Digite o ano de fabricação do automóvel: ");
        int anoFabricacao = scanner.nextInt();

        Automovel automovel = new Automovel(codigo, modelo, marca, anoFabricacao);
        if (automovelDAO.inserirAutomovel(automovel)) {
            System.out.println("Inserção com sucesso -> " + automovel.toString());
        } else {
            System.out.println("Erro ao inserir o automóvel.");
        }
    }

    private static void excluirAutomovel(AutomovelDAO automovelDAO, Scanner scanner) {
        System.out.println("\n==== Excluir Automóvel ====");
        System.out.print("Digite o código do automóvel a ser excluído: ");
        int codigo = scanner.nextInt();

        if (automovelDAO.excluirAutomovel(codigo)) {
            System.out.println("Exclusão realizada com sucesso.");
        } else {
            System.out.println("Erro ao excluir o automóvel. Verifique o código informado.");
        }
    }

    private static void atualizarAutomovel(AutomovelDAO automovelDAO, Scanner scanner) {
        System.out.println("\n==== Atualizar Automóvel ====");
        System.out.print("Digite o código do automóvel a ser atualizado: ");
        int codigo = scanner.nextInt();
        Automovel automovelAtualizado = automovelDAO.getAutomovelPorCodigo(codigo);

        if (automovelAtualizado != null) {
            System.out.print("Digite o novo modelo do automóvel (atual: " + automovelAtualizado.getModelo() + "): ");
            String novoModelo = scanner.next();
            System.out.print("Digite a nova marca do automóvel (atual: " + automovelAtualizado.getMarca() + "): ");
            String novaMarca = scanner.next();
            System.out.print("Digite o novo ano de fabricação do automóvel (atual: " + automovelAtualizado.getAnoFabricacao() + "): ");
            int novoAnoFabricacao = scanner.nextInt();

            automovelAtualizado.setModelo(novoModelo);
            automovelAtualizado.setMarca(novaMarca);
            automovelAtualizado.setAnoFabricacao(novoAnoFabricacao);

            if (automovelDAO.atualizarAutomovel(automovelAtualizado)) {
                System.out.println("Atualização realizada com sucesso -> " + automovelAtualizado.toString());
            } else {
                System.out.println("Erro ao atualizar o automóvel.");
            }
        } else {
            System.out.println("Automóvel não encontrado. Verifique o código informado.");
        }
    }
}
