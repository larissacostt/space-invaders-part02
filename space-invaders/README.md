# Space Invaders

Jogo 2D feito em Java (Swing), inspirado no clássico Space Invaders.
Projeto desenvolvido para o trabalho **Jogo 2D com Manutenção de Estado**.

O jogador controla uma nave e precisa destruir todos os inimigos antes que eles cheguem embaixo. São duas fases, e a segunda é mais difícil. O jogo pode ser **pausado** e **salvo em arquivo**, e depois continua exatamente de onde parou.

---

## Como jogar

| Tecla | Ação |
|---|---|
| `A` / `D` | Mover a nave para a esquerda / direita |
| `Espaço` | Atirar |
| `P` | Pausar / despausar |
| `S` | Salvar o jogo (na pausa) |
| `M` | Voltar ao menu (na pausa) |
| `Enter` | Começar um jogo novo (no menu) |
| `C` | Continuar o jogo salvo (no menu) |

**Objetivo:** destruir todos os inimigos das duas fases.
**Derrota:** um inimigo chegar até a altura da nave.

---

## Como executar

**Requisitos:** Java 8 ou superior.

**No VS Code:** abra o arquivo `App.java` e clique em **Run**.

## Estrutura do projeto

```
src/
├── App.java              → ponto de entrada: cria a janela
├── imagens/
│   ├── nave.png
│   └── inimigo.png
├── game/                 → elementos do jogo
│   ├── SpaceInvaders.java   painel principal; repassa tudo para o estado atual
│   ├── Nave.java            nave controlada pelo jogador
│   ├── Inimigo.java         inimigos que andam e descem
│   └── Tiro.java            tiro da nave e colisão
└── state/                → estados do jogo (padrão State)
    ├── EstadoJogo.java      interface que todos os estados seguem
    ├── EstadoMenu.java      tela inicial
    ├── EstadoJogando.java   a partida em andamento
    ├── EstadoPausado.java   jogo congelado
    ├── EstadoTransicao.java tela "FASE 2" entre as fases
    ├── EstadoFim.java       vitória ou derrota
    └── Salvamento.java      grava e carrega o jogo em arquivo
```

---

## Padrão State

O jogo sempre está em **um estado de cada vez**, e cada estado é uma classe que implementa a interface `EstadoJogo`:

```java
public interface EstadoJogo {
    void atualizar();
    void pintar(Graphics2D g);
    void teclaPressionada(int tecla);
    void teclaSolta(int tecla);
}
```

O `SpaceInvaders` só guarda o estado atual e repassa para ele a atualização, o desenho e as teclas. Para trocar de tela, um estado chama `jogo.mudarEstado(novoEstado)`.

```
MENU ──Enter──► JOGANDO (Fase 1) ──► TRANSIÇÃO ──► JOGANDO (Fase 2) ──► FIM (vitória)
                    │  ▲                                  │
                  P │  │ P                   inimigo chegou embaixo
                    ▼  │                                  ▼
                  PAUSADO ──S──► save.dat            FIM (derrota) ──Enter──► MENU
```

---

## Manutenção de estado

Toda a partida (fase, pontos, nave, tiros e inimigos) fica dentro de um único objeto: o `EstadoJogando`. Manter o estado do jogo significa guardar esse objeto, o que é feito de duas formas.

### 1. Na memória: pausa

Ao apertar `P`, o `EstadoJogando` se entrega para o `EstadoPausado`:

```java
jogo.mudarEstado(new EstadoPausado(jogo, this));
```

O `atualizar()` do `EstadoPausado` é vazio, então nada se mexe. Ao apertar `P` de novo, ele devolve **o mesmo objeto**, e o jogo continua de onde parou:

```java
jogo.mudarEstado(pausado);
```

### 2. Em arquivo: salvar e continuar

Na pausa, a tecla `S` grava a partida inteira no arquivo `save.dat` usando serialização do Java:

```java
saida.writeObject(estado);
```

No menu, a tecla `C` lê o arquivo e a partida volta no mesmo frame em que foi salva:

```java
EstadoJogando estado = (EstadoJogando) entrada.readObject();
```

Para isso funcionar:
- `EstadoJogando`, `Nave`, `Inimigo` e `Tiro` implementam `Serializable`.
- A referência à janela (`SpaceInvaders`) é `transient`, porque não pode ser salva. Ela é religada com `setJogo()` depois de carregar.
- As imagens são `static`, então não vão para o arquivo e são carregadas de novo quando o jogo abre.
- Quando a partida termina, o `save.dat` é apagado.

---

## Requisitos do trabalho

| Requisito | Como foi atendido |
|---|---|
| Controle via teclado | A nave se move com `A`/`D` e atira com `Espaço` |
| Interação entre elementos | Colisão entre os tiros e os inimigos; inimigos que chegam à nave causam derrota |
| Objetivo claro | Destruir todos os inimigos das duas fases |
| Manutenção do estado | Padrão State, pausa que preserva a partida, e salvar/carregar em arquivo |
