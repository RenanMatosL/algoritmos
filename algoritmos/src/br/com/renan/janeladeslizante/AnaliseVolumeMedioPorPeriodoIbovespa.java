package br.com.renan.janeladeslizante;

public class AnaliseVolumeMedioPorPeriodoIbovespa {
	public static void main(String[] args) {
		// Faça um array que simula o volume negociado (em milhões de reais) a cada hora
		// do pregão
		// Das 10h às 18h (9 horas de negociação típica do Ibovespa)
		// Índice 0 = 10h, Índice 1 = 11h, ..., Índice 8 = 18h
		double[] volumesPorHora = { 150.5, 320.8, 280.3, 450.6, 380.9, 420.7, 350.2, 290.4, 180.1 };

		// Defina o tamanho da janela deslizante para cálculo da média móvel (3 horas)
		// Representa quantas horas consecutivas serão consideradas em cada cálculo de
		// média
		int tamanhoDaJanela = 3;

		// Chame o método principal que calcula o maior volume médio em qualquer janela
		// de 3 horas
		double maiorVolumeMedio = encontrarMaiorVolumeMedio(volumesPorHora, tamanhoDaJanela);

		// Exiba o resultado formatado para o usuário
		System.out.println("O maior volume financeiro negociado nos últimos períodos de " + tamanhoDaJanela
				+ " Horas consecutivas foi de: ");
		System.out.printf("RS %.2f milhões\n", maiorVolumeMedio);

	}

	/**
	 * Construa o método principal que implementa o algoritmo de janela deslizante
	 * para encontrar o maior volume médio em qualquer intervalo de horas
	 * consecutivas
	 * 
	 * @param volumesPorHora Array com os volumes negociados por hora (em milhões de
	 *                       reais)
	 * @param tamanhoJanela  Quantidade de horas consecutivas para calcular cada
	 *                       média
	 * @return O maior volume médio encontrado em qualquer janela do tamanho
	 *         especificado
	 */
	private static double encontrarMaiorVolumeMedio(double[] volumesPorHora, int tamanhoDaJanela) {
		// VERIFICAÇÃO DE SEGURANÇA:
		// Garante que o array tem horas suficientes para a janela solicitada
		// Se não tiver, exibe mensagem de erro e retorna zero
		if (volumesPorHora.length < tamanhoDaJanela) {
			System.out.println("Períodos de cálculo da janela para a média com horas insuficientes, são necessárias"
					+ tamanhoDaJanela + "horas");
			return 0;
		}

		// VARIÁVEL: somaMaxima
		// Armazenará a MAIOR SOMA de volumes encontrada em qualquer janela do tamanho
		// especificado
		// Inicializada com zero, será atualizada durante a execução do algoritmo
		double somaMaxima = 0;

		// CALCULE A SOMA INICIAL:
		// Some os primeiros 'tamanhoJanela' elementos para criar a primeira janela
		// Ex: para tamanhoJanela=3, soma os volumes das posições 0, 1 e 2 (10h, 11h,
		// 12h)
		for (int i = 0; i < tamanhoDaJanela; i++) {
			// Adiciona cada volume à soma máxima
			somaMaxima += volumesPorHora[i];
			System.out.println(somaMaxima);
		}

		// VARIÁVEL: somaAtual
		// Mantém a soma dos volumes na janela atual que está sendo analisada
		// Inicialmente é igual à soma dos primeiros 'tamanhoJanela' elementos
		double somaAtual = somaMaxima;

		// LOOP PRINCIPAL: PROCESSO DE DESLIZAMENTO DA JANELA
		// Percorre o array a partir do elemento logo após a primeira janela completa
		// i começa em 'tamanhoJanela' porque já processamos os primeiros
		// 'tamanhoJanela' elementos
		for (int i = tamanhoDaJanela; i < volumesPorHora.length; i++) {

			// VARIÁVEL: volumeQueSai
			// Representa o volume da hora que está saindo da janela (a mais antiga)
			// Calculado como: array[i - tamanhoJanela]
			// Ex: Como i=3 (13h) e tamanhoJanela=3, volumeQueSai = volumesPorHora[0] (10h)
			// No loop, quando i = 3 (PRIMEIRA ITERAÇAO) e tamanhoDaJanela = 3: ENTÃO i -
			// tamanhoDaJanela = 3 - 3 = 0
			// volumesPorHora[0] = 150.5 (o elemento que está saindo)
			double volumePorHoraQueSai = volumesPorHora[i - tamanhoDaJanela];

			// VARIÁVEL: volumeQueEntra
			// Representa o volume da hora que está entrando na janela (a mais recente)
			// É simplesmente o elemento atual do array (posição i)
			// Ex: quando i=3, volumeQueEntra = volumes[3] (13h)
			double volumePorHoraQueEntra = volumesPorHora[i];

			// ATUALIZE A SOMA ATUAL:
			// A nova soma é calculada subtraindo o volume que saiu e adicionando o volume
			// que entrou
			// Isso é muito mais eficiente que recalcular a soma total da janela a cada
			// passo
			// Complexidade O(1) por janela em vez de O(k) onde k é o tamanho da janela
			somaAtual = somaAtual - volumePorHoraQueSai + volumePorHoraQueEntra;

			// ATUALIZE A SOMA MÁXIMA:
			// Compara a soma atual (da janela recém-calculada) com a soma máxima histórica
			// Se a soma atual for maior, atualiza a soma máxima
			if (somaAtual > somaMaxima) {
				somaMaxima = somaAtual;
			}

		}
		// FAÇA O CÁLCULO FINAL: CONVERSÃO DE SOMA PARA MÉDIA
		// A soma máxima representa a maior soma de volumes em 'tamanhoJanela' horas
		// consecutivas
		// Para obter a média, dividimos essa soma pelo número de horas na janela
		return somaMaxima / tamanhoDaJanela;
	}

}
