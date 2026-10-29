package avl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVLTree arvore = new AVLTree();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==================================================");
            System.out.println("              MENU - ÁRVORE AVL                   ");
            System.out.println("==================================================");
            System.out.println("1 - Inserir nó");
            System.out.println("2 - Remover nó");
            System.out.println("3 - Buscar nó");
            System.out.println("4 - Mostrar árvore (com FB)");
            System.out.println("5 - Executar bateria de testes automáticos");
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
                    System.out.print("Digite a chave (int) para inserir: ");
                    try {
                        int chaveIns = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Digite o valor associado (string): ");
                        String valIns = scanner.nextLine().trim();
                        arvore.insert(chaveIns, valIns.isEmpty() ? "Obj " + chaveIns : valIns);
                        System.out.println("-> Chave " + chaveIns + " inserida com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Valor de chave inválido.");
                    }
                    break;

                case 2:
                    System.out.print("Digite a chave (int) para remover: ");
                    try {
                        int chaveRem = Integer.parseInt(scanner.nextLine().trim());
                        AvlNode noRem = arvore.findNode(chaveRem);
                        if (noRem != null) {
                            Object removido = arvore.remove(noRem);
                            System.out.println("-> Chave " + chaveRem + " removida com sucesso! (Objeto: " + removido + ")");
                        } else {
                            System.out.println("-> Chave " + chaveRem + " não encontrada na árvore.");
                        }
                    } catch (Exception e) {
                        System.out.println("Valor de chave inválido.");
                    }
                    break;

                case 3:
                    System.out.print("Digite a chave (int) para buscar: ");
                    try {
                        int chaveBusca = Integer.parseInt(scanner.nextLine().trim());
                        AvlNode noBusca = arvore.findNode(chaveBusca);
                        if (noBusca != null) {
                            System.out.println("-> Nó encontrado! Chave: " + noBusca.getKey() + " | FB: " + noBusca.getFb() + " | Objeto: " + noBusca.getObject());
                        } else {
                            System.out.println("-> Chave " + chaveBusca + " não encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Valor de chave inválido.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- Estrutura da Árvore (Chave[FB]) ---");
                    if (arvore.isEmpty()) {
                        System.out.println("(Árvore vazia)");
                    } else {
                        arvore.showTree();
                    }
                    break;

                case 5:
                    executarBateriaTestes();
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    public static void executarBateriaTestes() {
        System.out.println("\n==================================================");
        System.out.println("         BATERIA DE TESTES - ÁRVORE AVL           ");
        System.out.println("==================================================\n");

        testeRotacaoSimplesEsquerda();
        testeRotacaoSimplesDireita();
        testeRotacaoDuplaEsquerda();
        testeRotacaoDuplaDireita();
        testeInsercaoComplexa();
        testeRemocao();

        System.out.println("\n=== Fim dos Testes AVL ===");
    }

    /**
     * Caso 1: Rotação Simples à Esquerda (RSE / RR Case)
     * Inserção sequencial crescente: 10 -> 20 -> 30
     * Esperado após rotação:
     *        20
     *       /  \
     *     10    30
     */
    public static void testeRotacaoSimplesEsquerda() {
        System.out.println("--- 1. Teste: Rotação Simples à Esquerda (RSE) ---");
        System.out.println("Inserindo chaves em ordem crescente: 10, 20, 30...");

        AVLTree arvore = new AVLTree();
        arvore.insert(10, "Dez");
        arvore.insert(20, "Vinte");
        arvore.insert(30, "Trinta");

        System.out.println("Estrutura resultante:");
        arvore.showTree();

        exibirFatores(arvore, new int[]{10, 20, 30});
        System.out.println();
    }

    /**
     * Caso 2: Rotação Simples à Direita (RSD / LL Case)
     * Inserção sequencial decrescente: 30 -> 20 -> 10
     * Esperado após rotação:
     *        20
     *       /  \
     *     10    30
     */
    public static void testeRotacaoSimplesDireita() {
        System.out.println("--- 2. Teste: Rotação Simples à Direita (RSD) ---");
        System.out.println("Inserindo chaves em ordem decrescente: 30, 20, 10...");

        AVLTree arvore = new AVLTree();
        arvore.insert(30, "Trinta");
        arvore.insert(20, "Vinte");
        arvore.insert(10, "Dez");

        System.out.println("Estrutura resultante:");
        arvore.showTree();

        exibirFatores(arvore, new int[]{10, 20, 30});
        System.out.println();
    }

    /**
     * Caso 3: Rotação Dupla à Esquerda (RDE / RL Case: Direita-Esquerda)
     * Inserção: 10 -> 30 -> 20
     * Esperado após rotação:
     *        20
     *       /  \
     *     10    30
     */
    public static void testeRotacaoDuplaEsquerda() {
        System.out.println("--- 3. Teste: Rotação Dupla à Esquerda (RDE / RL) ---");
        System.out.println("Inserindo chaves (Direita -> Esquerda): 10, 30, 20...");

        AVLTree arvore = new AVLTree();
        arvore.insert(10, "Dez");
        arvore.insert(30, "Trinta");
        arvore.insert(20, "Vinte");

        System.out.println("Estrutura resultante:");
        arvore.showTree();

        exibirFatores(arvore, new int[]{10, 20, 30});
        System.out.println();
    }

    /**
     * Caso 4: Rotação Dupla à Direita (RDD / LR Case: Esquerda-Direita)
     * Inserção: 30 -> 10 -> 20
     * Esperado após rotação:
     *        20
     *       /  \
     *     10    30
     */
    public static void testeRotacaoDuplaDireita() {
        System.out.println("--- 4. Teste: Rotação Dupla à Direita (RDD / LR) ---");
        System.out.println("Inserindo chaves (Esquerda -> Direita): 30, 10, 20...");

        AVLTree arvore = new AVLTree();
        arvore.insert(30, "Trinta");
        arvore.insert(10, "Dez");
        arvore.insert(20, "Vinte");

        System.out.println("Estrutura resultante:");
        arvore.showTree();

        exibirFatores(arvore, new int[]{10, 20, 30});
        System.out.println();
    }

    /**
     * Caso 5: Inserções sucessivas
     * Inserção: 3, 2, 4, 5, 6
     */
    public static void testeInsercaoComplexa() {
        System.out.println("--- 5. Teste: Inserções Sucessivas (3, 2, 4, 5, 6) ---");
        AVLTree arvore = new AVLTree();
        int[] chaves = {3, 2, 4, 5, 6};

        for (int k : chaves) {
            arvore.insert(k, "Val " + k);
        }

        System.out.println("Estrutura resultante:");
        arvore.showTree();

        exibirFatores(arvore, chaves);
        System.out.println();
    }

    /**
     * Caso 6: Remoção com Rebalanceamento
     * Inserção: 10, 5, 15, 2, 8, 22, 25
     * Remoção de nó folha e nó com 2 filhos
     */
    public static void testeRemocao() {
        System.out.println("--- 6. Teste: Remoção com Rebalanceamento ---");
        AVLTree arvore = new AVLTree();
        int[] chaves = {10, 5, 15, 2, 8, 22, 25};

        for (int k : chaves) {
            arvore.insert(k, "Val " + k);
        }

        System.out.println("Árvore antes da remoção:");
        arvore.showTree();

        System.out.println("\nRemovendo o nó 5...");
        AvlNode no5 = arvore.findNode(5);
        if (no5 != null) {
            arvore.remove(no5);
        }

        System.out.println("Árvore resultante após remoção de 5:");
        arvore.showTree();
        System.out.println();
    }

    /**
     * Método auxiliar para exibir os fatores de balanceamento dos nós existentes
     */
    private static void exibirFatores(AVLTree arvore, int[] chaves) {
        System.out.print("Fatores de Balanceamento (FB): ");
        for (int k : chaves) {
            AvlNode n = arvore.findNode(k);
            if (n != null) {
                System.out.print("[" + k + " (FB: " + n.getFb() + ")] ");
            }
        }
        System.out.println();
    }
}

