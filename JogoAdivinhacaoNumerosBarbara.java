/* ------- BÁRBARA CARVALHO ROCHA - RA 22287023-5 -------
------ ATIVIDADE 1 - PROGRAMAÇÃO DE SISTEMAS I ------
 Este projeto foi desenvolvido utilizando a ID VS Code (informação para evitar erros de compilação devido incompatibilidade de IDE):
*/

import java.util.ArrayList; // Para armazenar o histórico de tentativas
import java.util.HashSet;   // Para armazenar palpites únicos
import java.util.List;      // Para definir a lista de histórico de tentativas
import java.util.Random;    // Para gerar números aleatórios
import java.util.Scanner;    // Para capturar a entrada do usuário

public class JogoAdivinhacaoNumerosBarbara {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner para capturar a entrada do usuário
        Random random = new Random(); // Objeto para gerar números aleatórios
        boolean jogarNovamente; // Controla se o jogador deseja reiniciar o jogo

        do {
            limparTela();
            System.out.println();
            System.out.println("===========================================================");
            System.out.println("   Bem-vindo ao Desafio de Adivinhação de Números da Bá!   ");
            System.out.println("===========================================================");

            // Jogador define o intervalo de números
            System.out.println();
            System.out.print("Informe o menor número possível do intervalo: ");
            int min = obterNumero(scanner); // Captura e valida o valor mínimo do intervalo

            System.out.print("Agora, diga o maior número possível do intervalo: ");
            int max = obterNumero(scanner, min); // Captura e valida o valor máximo do intervalo

            // Geração do número aleatório no intervalo definido pelo jogador
            int numeroSecreto = random.nextInt(max - min + 1) + min;
            int tentativas = 0; // Contador de tentativas do jogador
            boolean acertou = false; // Indica se o jogador acertou o número
            List<Integer> historicoTentativas = new ArrayList<>(); // Armazena o histórico das tentativas
            HashSet<Integer> palpitesUnicos = new HashSet<>(); // Armazena palpites únicos

            // Medição de tempo de início do jogo
            long inicioTempo = System.currentTimeMillis();

            limparTela();
            System.out.println();
            System.out.println("===========================================================");
            System.out.println("   Bem-vindo ao Desafio de Adivinhação de Números da Bá!   ");
            System.out.println("===========================================================");
            System.out.println("Vamos lá! Tente descobrir o número secreto entre " + min + " e " + max + ". Boa sorte!");

            // Loop principal do jogo
            while (!acertou) {
                System.out.print("Digite seu número ou escreva 'sair' para desistir: ");
                String entrada = scanner.nextLine(); // Captura a entrada do jogador

                // Verifica se o jogador deseja encerrar o jogo
                if (entrada.equalsIgnoreCase("sair")) {
                    encerrarJogo(numeroSecreto, tentativas); // Chama o método de encerramento
                    break; // Sai do loop se o jogador digitar "sair"
                }

                // Converte a entrada para um número e verifica sua validade
                int palpite = obterPalpite(scanner, entrada, min, max);
                if (palpite == -1) continue; // Se a entrada for inválida, reinicia o loop

                // Verifica se o palpite já foi feito
                if (palpitesUnicos.contains(palpite)) {
                    System.out.println();
                    System.out.println("Esse palpite já foi feito! Tente um número diferente.");
                    continue; // Se o palpite for repetido, reinicia o loop
                }

                tentativas++; // Incrementa o contador de tentativas
                historicoTentativas.add(palpite); // Adiciona o palpite ao histórico
                palpitesUnicos.add(palpite); // Adiciona o palpite ao conjunto de palpites únicos

                // Fornece dicas ao jogador com base no palpite
                fornecerDicas(numeroSecreto, palpite);
                
                // Verifica se o jogador acertou
                if (palpite == numeroSecreto) {
                    mensagemAcerto(tentativas); // Chama o método que exibe a mensagem de acerto
                    acertou = true; // Atualiza a variável para indicar que o jogador acertou
                }
            }

            // Mensagem de encerramento e cálculo do tempo de jogo
            if (acertou) {
                long fimTempo = System.currentTimeMillis();
                long duracao = (fimTempo - inicioTempo) / 1000; // Calcula a duração em segundos
                System.out.println();
                System.out.println("==========================================================");
                System.out.println("   Você levou " + duracao + " segundos para concluir o jogo.");
                System.out.println("   Histórico de suas tentativas: " + historicoTentativas);
                System.out.println("   Você fez um total de " + tentativas + " tentativas.");
                System.out.println("==========================================================");
            }

            // Pergunta se o jogador deseja jogar novamente
            jogarNovamente = perguntarSeJogarNovamente(scanner);

        } while (jogarNovamente); // Loop do jogo, que continua se o jogador quiser jogar novamente

        // Mensagem de fim de jogo
        limparTela();
        System.out.println("\n=================================");
        System.out.println("     FIM DO JOGO, ATÉ A PRÓXIMA");
        System.out.println("=================================");

        scanner.close(); // Fecha o scanner para liberar recursos
        System.exit(0);  // Encerra o programa
    }

    // Método para limpar a tela, adaptado para diferentes sistemas operacionais
    public static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar limpar a tela!");
        }
    }

    // Método para encerrar o jogo informando o número secreto e tentativas
    private static void encerrarJogo(int numeroSecreto, int tentativas) {
        limparTela();
        System.out.println();
        System.out.println("============================================");
        System.out.println("   Você desistiu. O número secreto era " + numeroSecreto + ".");
        System.out.println("   Você fez um total de " + tentativas + " tentativas.");
        System.out.println("   Quem sabe da próxima vez!");
        System.out.println("============================================");
    }

    // Método para obter e validar a entrada do número (valor mínimo ou máximo)
    private static int obterNumero(Scanner scanner) {
        int numero = 0; // Inicializa a variável
        while (true) {
            try {
                numero = Integer.parseInt(scanner.nextLine()); // Tenta converter a entrada para inteiro
                return numero; // Retorna o número válido
            } catch (NumberFormatException e) {
                // Captura exceções de formato inválido e pede uma nova entrada
                System.out.println();
                System.out.println("Opa! Isso não é um número válido. Tente novamente.");
            }
        }
    }

    // Método para obter e validar o valor máximo, garantindo que seja maior que o mínimo
    private static int obterNumero(Scanner scanner, int min) {
        int max = 0;
        while (true) {
            try {
                max = Integer.parseInt(scanner.nextLine()); // Tenta converter a entrada para inteiro
                if (max > min) {
                    return max; // Retorna o valor máximo se for maior que o mínimo
                } else {
                    // Mensagem de erro se o valor máximo for menor ou igual ao mínimo
                    System.out.println();
                    System.out.println("O valor máximo deve ser maior que o valor mínimo.");
                }
            } catch (NumberFormatException e) {
                // Captura exceções de formato inválido e pede uma nova entrada
                System.out.println();
                System.out.println("Opa! Isso não é um número válido. Tente novamente.");
            }
        }
    }

    // Método para validar o palpite do jogador, garantindo que esteja dentro do intervalo
    private static int obterPalpite(Scanner scanner, String entrada, int min, int max) {
        int palpite;
        try {
            palpite = Integer.parseInt(entrada); // Tenta converter a entrada para número inteiro
            if (palpite < min || palpite > max) {
                // Verifica se o palpite está dentro do intervalo
                System.out.println();
                System.out.println("Esse número está fora do intervalo! Escolha algo entre " + min + " e " + max + ".");
                return -1; // Retorna -1 indicando que a entrada foi inválida
            }
            return palpite; // Retorna o palpite válido
        } catch (NumberFormatException e) {
            // Captura exceções de formato inválido e pede uma nova entrada
            System.out.println();
            System.out.println("Entrada inválida. Digite um número dentro do intervalo ou 'sair' para encerrar.");
            return -1; // Retorna -1 indicando que a entrada foi inválida
        }
    }

    // Método para fornecer dicas ao jogador
    private static void fornecerDicas(int numeroSecreto, int palpite) {
        if (palpite < numeroSecreto) {
            System.out.println();
            System.out.println("Dica: Seu palpite foi baixo demais. Tente algo maior!");
        } else if (palpite > numeroSecreto) {
            System.out.println();
            System.out.println("Dica: Está muito alto! Pense em um número mais baixo.");
        }
    }

    // Método para exibir a mensagem de acerto, personalizando se foi na primeira tentativa ou não
    private static void mensagemAcerto(int tentativas) {
        limparTela();
        System.out.println();
        System.out.println("==============================================================");
        if (tentativas == 1) {
            // Mensagem especial para acerto na primeira tentativa
            System.out.println("   Uau! Você acertou o número secreto na primeira tentativa!");
        } else {
            // Mensagem padrão para acertos em outras tentativas
            System.out.println("   Parabéns! Você acertou o número secreto em " + tentativas + " tentativas.");
        }
        System.out.println("==============================================================");
    }

    // Método que pergunta se o jogador deseja jogar novamente
    private static boolean perguntarSeJogarNovamente(Scanner scanner) {
        boolean jogarNovamente; // Variável de controle
        while (true) {
            System.out.print("\nVamos jogar novamente? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase(); // Lê a resposta do usuário e a formata
            if (resposta.equals("s")) {
                jogarNovamente = true; // Se o usuário deseja jogar novamente, ativa a flag
                break;
            } else if (resposta.equals("n")) {
                jogarNovamente = false; // Se o usuário não deseja jogar novamente, desativa a flag
                break; // Sai do loop
            } else {
                // Mensagem para respostas inválidas
                System.out.println("** Resposta inválida! Por favor, digite 's' para sim ou 'n' para não. **");
            }
        }
        return jogarNovamente; // Retorna a decisão do usuário
    }
}
