package rubro_negra;

import bst.Node;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RNTree arvore = new RNTree();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n==================================================");
            System.out.println("          MENU - ÁRVORE RUBRO-NEGRA (IFRN)        ");
            System.out.println("==================================================");
            System.out.println("1 - Inserir nó");
            System.out.println("2 - Remover nó");
            System.out.println("3 - Buscar nó");
            System.out.println("4 - Mostrar árvore (Hierárquica com Cores)");
            System.out.println("5 - Mostrar árvore (Grade Compacta)");
            System.out.println("6 - Executar Bateria de Testes (Exemplos dos Slides)");
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
                        System.out.print("Digite o valor/objeto associado (opcional): ");
                        String valIns = scanner.nextLine().trim();
                        arvore.insert(chaveIns, valIns.isEmpty() ? "Obj-" + chaveIns : valIns);
                        System.out.println("-> Chave " + chaveIns + " inserida com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Valor de chave inválido: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Digite a chave (int) para remover: ");
                    try {
                        int chaveRem = Integer.parseInt(scanner.nextLine().trim());
                        Node noRem = arvore.findNode(chaveRem);
                        if (noRem != null) {
                            Object removido = arvore.remove(noRem);
                            System.out.println("-> Chave " + chaveRem + " removida com sucesso! (Objeto: " + removido + ")");
                        } else {
                            System.out.println("-> Chave " + chaveRem + " não encontrada na árvore.");
                        }
                    } catch (Exception e) {
                        System.out.println("Valor de chave inválido: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Digite a chave (int) para buscar: ");
                    try {
                        int chaveBusca = Integer.parseInt(scanner.nextLine().trim());
                        Node noBusca = arvore.findNode(chaveBusca);
                        if (noBusca != null) {
                            RNNode rnNo = (RNNode) noBusca;
                            String corNome = rnNo.isRubro() ? "RUBRO" : "NEGRO";
                            System.out.println("-> Nó encontrado!");
                            System.out.println("   Chave : " + rnNo.getKey());
                            System.out.println("   Cor   : " + corNome);
                            System.out.println("   Objeto: " + rnNo.getObject());
                            System.out.println("   Pai   : " + (rnNo.getParent() != null ? rnNo.getParent().getKey() : "raiz"));
                            System.out.println("   Filho Esquerdo: " + (rnNo.getLeftChild() != null ? rnNo.getLeftChild().getKey() : "null"));
                            System.out.println("   Filho Direito : " + (rnNo.getRightChild() != null ? rnNo.getRightChild().getKey() : "null"));
                        } else {
                            System.out.println("-> Chave " + chaveBusca + " não encontrada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Valor de chave inválido: " + e.getMessage());
                    }
                    break;

                case 4:
                    arvore.showTree();
                    break;

                case 5:
                    System.out.println("\n--- Grade Compacta ---");
                    arvore.showGridTree();
                    break;

                case 6:
                    executarBateriaTestes();
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida! Escolha um valor entre 0 e 6.");
            }
        }
        scanner.close();
    }

    public static void executarBateriaTestes() {
        System.out.println("\n=======================================================");
        System.out.println("   BATERIA DE TESTES - ÁRVORE RUBRO-NEGRA (SLIDES)    ");
        System.out.println("=======================================================\n");

        System.out.println("--- Teste 1: Exemplo do Slide 16 (Inserção 1 até 8) ---");
        RNTree t1 = new RNTree();
        int[] chavesSlide16 = {1, 2, 3, 4, 5, 6, 7, 8};
        for (int k : chavesSlide16) {
            t1.insert(k, "Val-" + k);
        }
        t1.showTree();

        System.out.println("\n--- Teste 2: Remoção com Duplo Negro ---");
        RNTree t2 = new RNTree();
        int[] chavesP21 = {7, 2, 11, 1, 5, 9, 15};
        for (int k : chavesP21) {
            t2.insert(k, "Val-" + k);
        }
        System.out.println("Árvore antes de remover 5:");
        t2.showTree();
        System.out.println("\nRemovendo nó 5 (Duplo Negro):");
        Node no5 = t2.findNode(5);
        if (no5 != null) {
            t2.remove(no5);
            System.out.println("-> 5 removido com sucesso!");
        }
        t2.showTree();

        System.out.println("\n--- Teste 3: Rotações e Casos Específicos ---");
        RNTree t3 = new RNTree();
        t3.insert(10, "10");
        t3.insert(5, "5");
        t3.insert(1, "1");
        System.out.println("Árvore após inserção LL (10, 5, 1):");
        t3.showTree();

        System.out.println("\n=== Fim da Bateria de Testes Rubro-Negra ===");
    }
}
