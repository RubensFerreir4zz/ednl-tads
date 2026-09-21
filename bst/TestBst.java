package bst;
import java.util.Iterator;

public class TestBst {
    public static void main(String[] args) {
        System.out.println("=== Iniciando Testes da ArvoreBin ===");

        Bst arvore = new Bst();

        System.out.println("Está vazia? " + arvore.isEmpty()); // Esperado: true
        System.out.println("Tamanho (size): " + arvore.size()); // Esperado: 0
        System.out.println("Altura (height): " + arvore.height()); // Esperado: -1

        try {
            arvore.showTree();
        } catch (BstException e) {
            System.out.println("Sucesso: Exceção capturada ao tentar mostrar árvore vazia => " + e.getMessage());
        }

        try {
            arvore.root();
        } catch (BstException e) {
            System.out.println("Sucesso: Exceção capturada ao tentar ler raiz de árvore vazia => " + e.getMessage());
        }
        System.out.println();
        System.out.println("Inserindo elementos: 50, 30, 70, 20, 40...");
        arvore.insert(50, "Cinquenta");
        arvore.insert(30, "Trinta");
        arvore.insert(70, "Setenta");
        arvore.insert(20, "Vinte");
        arvore.insert(40, "Quarenta");

        System.out.println("Tamanho após inserções: " + arvore.size()); // Esperado: 5
        System.out.println("Altura após inserções: " + arvore.height()); // Esperado: 2

        System.out.println("\nEstrutura da árvore após as inserções:");
        arvore.showTree();
        System.out.println();

        System.out.println("Buscando a chave 30...");
        Object obj30 = arvore.find(30);
        System.out.println("Encontrado: " + obj30); // Esperado: Trinta

        System.out.println("Substituindo o valor do nó 30...");
        Node no30 = arvore.findNode(30);
        if (no30 != null) {
            Object antigo = arvore.replace(no30, "Novo Trinta");
            System.out.println("Valor antigo: " + antigo); // Esperado: Trinta
            System.out.println("Novo valor buscado: " + arvore.find(30)); // Esperado: Novo Trinta
        }

        System.out.print("Elementos da árvore em-ordem: ");
        Iterator<Object> elemIter = arvore.elements();
        while (elemIter.hasNext()) {
            System.out.print(elemIter.next() + " ");
        }
        System.out.println("\n");

        System.out.println("Removendo o nó 30 (que tem dois filhos)...");
        if (no30 != null) {
            Object removido = arvore.remove(no30);
            System.out.println("Elemento removido: " + removido); // Esperado: Novo Trinta
        }
        System.out.println("Novo tamanho da árvore: " + arvore.size()); // Esperado: 4
        System.out.println("Busca pelo 30 após remoção: " + arvore.find(30)); // Esperado: null

        System.out.println("Estrutura da árvore após remover o 30:");
        arvore.showTree();
        System.out.println();

        System.out.println("=== Todos os Testes Executados com Sucesso! ===");
    }
}
