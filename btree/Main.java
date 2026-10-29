package btree;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BTree arvore = new BTree(3);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==================================================");
            System.out.println("            MENU - ÁRVORE B (IFRN / TADS)         ");
            System.out.println("==================================================");
            System.out.println("Grau mínimo atual (t): " + arvore.getT());
            System.out.println("1 - Inserir chave");
            System.out.println("2 - Remover chave");
            System.out.println("3 - Buscar chave");
            System.out.println("4 - Exibir árvore");
            System.out.println("5 - Executar bateria de testes (Exemplo dos Slides)");
            System.out.println("6 - Redefinir árvore (alterar grau t)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite a chave (número ou letra) para inserir: ");
                    try {
                        String entrada = scanner.nextLine().trim();
                        int chave = parseKey(entrada);
                        arvore.insert(chave);
                        System.out.println("-> Chave " + formatKey(chave) + " inserida com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao inserir: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Digite a chave (número ou letra) para remover: ");
                    try {
                        String entrada = scanner.nextLine().trim();
                        int chave = parseKey(entrada);
                        if (arvore.contains(chave)) {
                            arvore.remove(chave);
                            System.out.println("-> Chave " + formatKey(chave) + " removida com sucesso!");
                        } else {
                            System.out.println("-> Chave " + formatKey(chave) + " não encontrada na árvore.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao remover: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Digite a chave (número ou letra) para buscar: ");
                    try {
                        String entrada = scanner.nextLine().trim();
                        int chave = parseKey(entrada);
                        BTreeNode encontrado = arvore.search(chave);
                        if (encontrado != null) {
                            System.out.println("-> Chave " + formatKey(chave) + " ENCONTRADA!");
                            System.out.println("   Tipo do nó: " + (encontrado.isLeaf() ? "Folha" : "Nó Interno"));
                            System.out.println("   Total de chaves no nó: " + encontrado.getN());
                            System.out.print("   Chaves da página: [");
                            for (int i = 0; i < encontrado.getN(); i++) {
                                System.out.print(formatKey(encontrado.getKeys()[i]));
                                if (i < encontrado.getN() - 1) {
                                    System.out.print(" | ");
                                }
                            }
                            System.out.println("]");
                        } else {
                            System.out.println("-> Chave " + formatKey(chave) + " NÃO encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("\n--- Estrutura Hierárquica da Árvore B ---");
                    arvore.printTree();
                    System.out.println("\n--- Chaves em Ordem Crescente ---");
                    arvore.printInOrder();
                    break;

                case 5:
                    arvore = executarBateriaTestes();
                    break;

                case 6:
                    System.out.print("Digite o novo grau mínimo t (t >= 2): ");
                    try {
                        int novoT = Integer.parseInt(scanner.nextLine().trim());
                        arvore = new BTree(novoT);
                        System.out.println("-> Nova Árvore B criada com t = " + novoT);
                    } catch (Exception e) {
                        System.out.println("Grau inválido: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha entre 0 e 6.");
                    break;
            }
        }
        scanner.close();
    }

    private static int parseKey(String input) {
        input = input.trim();
        if (input.matches("-?\\d+")) {
            return Integer.parseInt(input);
        }
        if (input.length() == 1) {
            return (int) Character.toUpperCase(input.charAt(0));
        }
        throw new IllegalArgumentException("Entrada inválida. Digite um número inteiro ou uma letra.");
    }

    private static String formatKey(int k) {
        if (k >= 65 && k <= 90) {
            return "'" + (char) k + "'";
        }
        return String.valueOf(k);
    }

    public static BTree executarBateriaTestes() {
        System.out.println("\n========================================================");
        System.out.println("   EXECUÇÃO DA BATERIA DE TESTES (SLIDES DE AULA - t=3) ");
        System.out.println("========================================================");

        BTree tree = new BTree(3);

        char[] cargaInicial = {'G', 'M', 'P', 'X', 'A', 'C', 'D', 'E', 'J', 'K', 'N', 'O', 'R', 'S', 'T', 'U', 'V', 'Y', 'Z'};
        System.out.println("\n1. Inserindo nós da árvore base do slide (páginas 8-12)...");
        for (char c : cargaInicial) {
            tree.insert((int) c);
        }
        tree.printTree();

        System.out.println("\n2. Inserindo chave 'B' (Slide 12 - folha [A C D E] -> [A B C D E])...");
        tree.insert((int) 'B');
        tree.printTree();

        System.out.println("\n3. Inserindo chave 'Q' (Slide 13 - cisão do nó com overflow)...");
        tree.insert((int) 'Q');
        tree.printTree();

        System.out.println("\n4. Inserindo chave 'L' (Slide 14 - nova cisão subindo para raiz)...");
        tree.insert((int) 'L');
        tree.printTree();

        System.out.println("\n5. Buscas nos slides (Slide 10):");
        int buscaR = (int) 'R';
        System.out.println("-> Busca por 'R': " + (tree.contains(buscaR) ? "Encontrado!" : "Não encontrado"));
        int buscaW = (int) 'W';
        System.out.println("-> Busca por 'W': " + (tree.contains(buscaW) ? "Encontrado!" : "Não encontrado"));

        System.out.println("\n6. Remoção de 'E' (Slide 16 / Caso 1 - remoção de folha)...");
        tree.remove((int) 'E');
        tree.printTree();

        System.out.println("\n7. Remoção de 'M' (Slide 17 / Caso 2a - nó interno com antecessor L)...");
        tree.remove((int) 'M');
        tree.printTree();

        System.out.println("\n8. Remoção de 'G' (Slide 24)...");
        tree.remove((int) 'G');
        tree.printTree();

        System.out.println("\n9. Remoção de 'D' (Slide 25)...");
        tree.remove((int) 'D');
        tree.printTree();

        System.out.println("\n10. Remoção de 'S' (Slide 26)...");
        tree.remove((int) 'S');
        tree.printTree();

        System.out.println("\n11. Remoção de 'U' (Slides 27-28 - fusões e balanceamento)...");
        tree.remove((int) 'U');
        tree.printTree();

        System.out.println("\n-> Percurso em ordem final:");
        tree.printInOrder();

        System.out.println("\n========================================================");
        System.out.println("   BATERIA DE TESTES DOS SLIDES CONCLUÍDA COM SUCESSO!  ");
        System.out.println("========================================================");

        return tree;
    }
}
