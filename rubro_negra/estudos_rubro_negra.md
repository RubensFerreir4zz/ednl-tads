# Guia de Estudos - Árvores Rubro-Negra (Red-Black Tree)
**IFRN - Tecnologia em Análise e Desenvolvimento de Sistemas (TADS)**  
**Disciplina:** Estruturas de Dados Não Lineares  
**Docente:** Prof. Robinson Alves  

---

## 1. O que é uma Árvore Rubro-Negra?

Uma Árvore Rubro-Negra é uma **Árvore Binária de Pesquisa (BST)** auto-balanceável onde cada nó possui um atributo adicional: sua **cor** (`RUBRO` ou `NEGRO`).

Diferente da árvore AVL, que mantém um fator de balanceamento estrito baseado na diferença de alturas $|he - hd| \le 1$, a Rubro-Negra utiliza coloração e propriedades estruturais para garantir que:
> **O maior caminho da raiz até uma folha externa é no máximo duas vezes maior que o menor caminho.**  
> Isso assegura que a altura da árvore seja sempre limitada por $h \le 2 \log_2(n + 1)$, garantindo operações básicas (busca, inserção e remoção) em tempo rigorosamente **$O(\log n)$**.

---

## 2. Os 4 Critérios Fundamentais (Slide 2)

Para que uma árvore binária de pesquisa seja considerada Rubro-Negra, as seguintes 4 condições devem ser **estritamente satisfeitas**:

1. **Critério I (Nós Externos):** Todo nó externo (folha nula / `null`) é **NEGRO**.
2. **Critério II (Raiz):** O nó raiz é **NEGRO**.
3. **Critério III (Sem Rubros Consecutivos):** Se um nó $v$ é **RUBRO**, então **ambos os seus filhos devem ser NEGROS** (ou seja, nunca podem existir dois nós rubros consecutivos em um caminho pai-filho).
4. **Critério IV (Altura Negra Constante):** Todos os caminhos de qualquer nó $v$ para seus nós descendentes externos (folhas nulas) contêm o **mesmo número idêntico de nós negros**.

### Altura Negra ($hb(v)$)
É a quantidade de nós negros encontrados no caminho de um nó $v$ (não contando o próprio $v$) até qualquer folha externa descendente.

---

## 3. Inserção passo a passo (Slides 6 a 16)

### Regra de Ouro da Inserção:
> **Todo novo nó é inserido como RUBRO na árvore.**  
> *Por quê?* Porque pintar o novo nó de rubro preserva o **Critério IV** (a quantidade de nós negros nos caminhos não é alterada). O único critério que pode ser temporariamente violado é o **Critério III** (se o pai também for rubro).

### Os Casos de Inserção:

#### Caso 0: A árvore estava vazia
* O novo nó torna-se a raiz e é pintado de **NEGRO** (Critério II).

#### Caso 1 (Slide 8): O Pai $w$ do novo nó é NEGRO
* **Ação:** Nada precisa ser feito!  
* A árvore continua válida imediatamente, pois não há conflito rubro-rubro e a altura negra foi mantida.

#### Se o Pai $w$ for RUBRO (Conflito Rubro-Rubro!):
Como o pai é rubro, ele não é a raiz (a raiz é negra), logo o **avô $t$ existe e é NEGRO**.  
Para decidir a ação, **olhamos para a cor do TIO $u$** (o outro filho do avô $t$):

---

### Caso 2 (Slides 9 e 10): O Tio $u$ é RUBRO
* **Ação (Apenas Recoloração):**
  1. Pai $w$ vira **NEGRO**.
  2. Tio $u$ vira **NEGRO**.
  3. Avô $t$ vira **RUBRO**.
* **Propagação (Slide 10):**
  Como o avô $t$ virou rubro, ele pode ter gerado conflito com o bisavô (se o bisavô for rubro).  
  Portanto, o algoritmo sobe fazendo $v = t$ e repete a verificação no nível de cima.  
  *(No final, se a raiz virar rubra, ela é simplesmente repintada de negro).*

---

### Caso 3 (Slides 11 a 15): O Tio $u$ é NEGRO (ou `null`)
* **Ação (Exige Rotação):**  
  Dependendo da posição relativa de $v$, do pai $w$ e do avô $t$, executamos uma das 4 rotações:

1. **Caso 3a (Slide 12) - Rotação Direita Simples (LL):**
   * Pai $w$ é filho esquerdo do avô $t$ e $v$ é filho esquerdo de $w$.
   * **Ação:** Rotação simples à direita no avô $t$.
   * **Cores:** $w$ vira NEGRO, $t$ vira RUBRO.
   * **Fim:** Balanceamento concluído!

2. **Caso 3b (Slide 13) - Rotação Esquerda Simples (RR):**
   * Pai $w$ é filho direito do avô $t$ e $v$ é filho direito de $w$.
   * **Ação:** Rotação simples à esquerda no avô $t$.
   * **Cores:** $w$ vira NEGRO, $t$ vira RUBRO.
   * **Fim:** Balanceamento concluído!

3. **Caso 3c (Slide 14) - Rotação Esquerda Dupla (RL - Zig-zag):**
   * Pai $w$ é filho direito do avô $t$ e $v$ é filho esquerdo de $w$.
   * **Ação:** Rotação simples à direita no pai $w$ (converte no caso 3b), depois rotação simples à esquerda no avô $t$.

4. **Caso 3d (Slide 15) - Rotação Direita Dupla (LR - Zig-zag):**
   * Pai $w$ é filho esquerdo do avô $t$ e $v$ é filho direito de $w$.
   * **Ação:** Rotação simples à esquerda no pai $w$ (converte no caso 3a), depois rotação simples à direita no avô $t$.

---

## 4. Remoção e o "Duplo Negro" (Slides 17 a 33)

### Passo 1: Remoção Estrutural da BST
1. Se o nó a ser removido tiver dois filhos, encontramos o **sucessor imediato** (menor nó da subárvore direita), copiamos seu valor e removemos fisicamente o sucessor.
2. O nó fisicamente desconectado terá no máximo 1 filho. Chamamos de $v$ o nó que saiu e de $x$ o filho substituto (que pode ser uma folha nula).

### As 4 Situações de Remoção (Slide 18):

| Situação | Nó removido ($v$) | Substituto ($x$) | Ação |
| :---: | :---: | :---: | :--- |
| **1** (Slide 19) | **Rubro** | **Rubro** | Nada a fazer. Árvore continua válida. |
| **2** (Slide 20) | **Negro** | **Rubro** | **Pinte $x$ de NEGRO e pare.** A altura negra é restaurada. |
| **3** (Slide 21) | **Negro** | **Negro** | **DUPLO NEGRO!** Falta 1 negro naquele caminho. Exige balanceamento. |
| **4** (Slide 32) | **Rubro** | **Negro** | Pinte $x$ de rubro e proceda como na Situação 3 (ou nada a fazer se folha). |

---

### Os Casos do Duplo Negro (Situação 3):
Supondo que $x$ é o nó com duplo negro e $w$ é seu irmão (outro filho do pai):

* **Caso 1 (Slide 22): Irmão $w$ é RUBRO e Pai é NEGRO**
  * Rotação simples no pai no sentido de $x$.
  * Pinte $w$ de NEGRO e o pai de RUBRO.
  * Reavalia os casos novamente a partir de $x$.

* **Caso 2a (Slide 24): Irmão $w$ é NEGRO, filhos de $w$ são NEGROS e Pai é NEGRO**
  * Pinte o irmão $w$ de RUBRO.
  * O duplo negro sobe para o pai de $x$. O loop continua avaliando o nível superior.

* **Caso 2b (Slide 26): Irmão $w$ é NEGRO, filhos de $w$ são NEGROS e Pai é RUBRO**
  * Pinte o irmão $w$ de RUBRO.
  * Pinte o pai de $x$ de NEGRO.
  * **Duplo negro é absorvido! Fim da remoção.**

* **Caso 3 (Slide 28): Irmão $w$ é NEGRO, filho interno de $w$ é RUBRO e externo é NEGRO**
  * Rotação simples em $w$ no sentido inverso.
  * Troca as cores de $w$ com seu filho.
  * Cai diretamente no **Caso 4**.

* **Caso 4 (Slide 30): Irmão $w$ é NEGRO e filho externo de $w$ é RUBRO**
  * Rotação simples no pai no sentido de $x$.
  * Pinte o pai de NEGRO.
  * $w$ assume a cor original do pai.
  * Pinte o filho externo de $w$ de NEGRO.
  * **Duplo negro é absorvido! Fim da remoção.**

---

## 5. Roteiro Prático para Provas e Exercícios

Quando o professor der uma árvore na prova e pedir para **inserir** ou **remover** nós no papel:

1. **Para Inserção:**
   * Desenhe o novo nó em vermelho na posição correta da BST.
   * O pai é preto? $\rightarrow$ **Pronto! Acabou.**
   * O pai é vermelho? $\rightarrow$ Olhe para o **TIO**:
     * Tio vermelho? $\rightarrow$ **Recoloração:** Pai e Tio pretos, Avô vermelho. Cheque o avô.
     * Tio preto (ou nulo)? $\rightarrow$ **Rotação:** Identifique se é LL, RR, LR ou RL. Faça a rotação e ajuste as cores (o novo topo vira preto, os filhos viram vermelhos).

2. **Para Testar se uma Árvore é Válida:**
   * A raiz é preta?
   * Existem dois nós vermelhos seguidos? (Se sim, inválida!).
   * Conte os nós pretos de qualquer caminho da raiz até cada folha `null`. A contagem deve ser **rigorosamente a mesma** para todos os caminhos.

3. **Complexidade:**
   * Altura máxima: $h \le 2 \log_2(n + 1)$
   * Busca: $O(\log n)$
   * Inserção: $O(\log n)$ (no máximo 2 rotações e $O(\log n)$ recolorações)
   * Remoção: $O(\log n)$ (no máximo 3 rotações e $O(\log n)$ recolorações)
