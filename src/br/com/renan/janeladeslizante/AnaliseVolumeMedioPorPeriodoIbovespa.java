package br.com.renan.janeladeslizante;

public class AnaliseVolumeMedioPorPeriodoIbovespa {
	public static void main(String[] args) {
		/**
		 * Faça um array que simula o volume negociado (em milhões de reais) a cada hora
		 * do pregão Das 10h às 18h (9 horas de negociação típica do Ibovespa) Índice 0
		 * = 10h, Índice 1 = 11h, ..., Índice 8 = 18h
		 */
		double[] volumesPorHora = { 150.5, 320.8, 280.3, 450.6, 380.9, 420.7, 350.2, 290.4, 180.1 };

		/**
		 * Defina o tamanho da janela deslizante para cálculo da média móvel (3 horas)
		 * Represente quantas horas consecutivas serão consideradas em cada cálculo de
		 * média
		 */
		int tamanhoDaJanela = 3;

		/**
		 * Chame o método principal que calcula o maior volume médio em qualquer
		 * janelade 3 horas
		 */
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
	 * @param volumesPorHora  Array com os volumes negociados por hora (em milhões de reais)
	 * @param tamanhoDaJanela Quantidade de horas consecutivas para calcular cada média
	 * @return O maior volume médio encontrado em qualquer janela do tamanho especificado
	 */
	private static double encontrarMaiorVolumeMedio(double[] volumesPorHora, int tamanhoDaJanela) {
		/**
		 * FAÇA UMA VERIFICAÇÃO DE SEGURANÇA:
		 * Garanta que o array tem horas suficientes para a janela solicitada
		 * Se não tiver, exiba a mensagem de erro e retorne zero
		 */
		if (volumesPorHora.length < tamanhoDaJanela) {
			System.out.println(
					"Para o cálculo da média negociada, são necessárias" + tamanhoDaJanela + "horas de pregão");
			return 0;
		}

		/**
		 * @param maiorSoma Armazenará a MAIOR SOMA de volumes encontrada em qualquer janela do tamanho especificado. 
		 * Inicializada com zero, será atualizada durante a execução do algoritmo
		 */
		double maiorSoma = 0;

		/**
		 * CALCULE A SOMA INICIAL.
		 * Criarei uma iteraçao com o tamanhoDaJanela de cálculo para somar os primeiros 'tamanhoDaJanela'' elementos, para criar a primeira janela.
		 * Raciocínio: Para tamanhoDaJanela'=3, somarei os volumes das posições 0, 1 e 2 (10h, 11h,12h).
		 */
		for (int i = 0; i < tamanhoDaJanela; i++) {

			/**
			 * VolumePorHora receberá a iteraçao limitada pelo tamanhoDaJanela e guardarei estes x elementos na variável maiorSoma. 
			 */
			maiorSoma += volumesPorHora[i];
		}

		/**
		 * Vou criar aqui uma variável para manter a soma dos volumes da janela atual que está sendo analizada em tempo "ao vivo".
		 * Inicialmene é igual à soma dos primeiros 'tamanhoDaJanela' elementos
		 */
		double somaAtual = maiorSoma;

		// LOOP PRINCIPAL: PROCESSO DE DESLIZAMENTO DA JANELA
		// Percorre o array a partir do elemento logo após a primeira janela completa
		// i começa em 'tamanhoDaJanela'' porque já processamos os primeiros
		// 'tamanhoDaJanela'' elementos
		
		/**
		 * Farei o loop principa: PROCESSO DE DESLIZAMENTO DA JANELA
		 * A iteraçao deve iniciar a percorrer o array apartir do elemento logo após o primeiro "tamanhoDaJanela" elementos, ou seja, na posicao "3".
		 * Já que o "tamanhoDaJanela" possui 3 posicoes de horas, "0, 1, 2" Passamos a dar continuidade a terceira hora de avaliaçao desta forma.  
		 */
		for (int i = tamanhoDaJanela; i < volumesPorHora.length; i++) {

			// VARIÁVEL: volumeQueSai
			// Representa o volume da hora que está saindo da janela (a mais antiga)
			// Calculado como: array[i - tamanhoDaJanela']
			// Ex: Como i=3 (13h) e tamanhoDaJanela'=3, volumeQueSai = volumesPorHora[0] (10h)
			// No loop, quando i = 3 (PRIMEIRA ITERAÇAO) e tamanhoDaJanela = 3: ENTÃO i -
			// tamanhoDaJanela = 3 - 3 = 0
			// volumesPorHora[0] = 150.5 (o elemento que está saindo)
			
			/**
			 * Array sendo preenchido acima, devemos pensar agora na hora que vai sair do calculo da media, para podermos adicionar a nova hora:
			 * @param volumeQueSai Representa o volume da hora que está saindo da janela (a mais antiga).
			 * Calculando como: array[i - tamanhoDaJanela'] que será igual a posiçao 0 do array, pois: 
			 * Ex: Como i=3 (13h) e tamanhoDaJanela'=3, "volumeQueSai" = volumesPorHora[0] (10h).
			 *  No loop, quando i = 3 (PRIMEIRA ITERAÇAO) e tamanhoDaJanela = 3: ENTÃO i - tamanhoDaJanela = 3 - 3 = 0
			 *  Portanto o elemento que está saindo do array é o volumesPorHora[0] = 150.5
			 */
			double volumePorHoraQueSai = volumesPorHora[i - tamanhoDaJanela];

			// VARIÁVEL: volumePorHoraQueEntra
			/**
			 * No loop principal que fizemos anteriormente, iniciamos a iteraçao a partir do tamanho da janela: "for (int i = tamanhoDaJanela;..."
			 * Ou seja, o tamanho da janela = 3. E é justamente a posicao que entra após o array ja calculado do tamanhoDaJanela anterior: [0, 1, 2].
			 * Sendo assim iremos abrigar na nossa variavel "volumePorHoraQueEntra" o "volumesPorHoraQueEntra recebendo este: [i] (=3),
			 * POIS no loop inicia e é igual a 3. (tamanhoDaJanela =3)."
			 */
			double volumePorHoraQueEntra = volumesPorHora[i];

			/**
			 * Agora com estes volumes que entra e saem, precisamos: ATUALIZE A SOMA ATUAL:
			 * A nova soma, "somaAtual" é calculada subtraindo o "volumePorHoraQueSai" que sai com a entrada de mais uma hora,
			 * E adicionarei a nova hora, e o seu "volumePorHoraQueEntra".
			 * Isso é muito mais eficiente que recalcular a soma total da janela a cada passo.
			 * Complexidade O(1) por janela em vez de O(k) onde k é o tamanho da janela
			 */
			somaAtual = somaAtual - volumePorHoraQueSai + volumePorHoraQueEntra;
			
			/**
			 * Tendo a soma atual, proximo passo é sempre comparar a soma atual (da janela recém-calculada) com a maior soma histórica 
			 * Se a soma atual for maior, atualiza a maiorSoma. 
			 */
			if (somaAtual > maiorSoma) {
				maiorSoma = somaAtual;
			}

		}
		// FAÇA O CÁLCULO FINAL: CONVERSÃO DE SOMA PARA MÉDIA
		// A variavel "maiorSoma" representa a maior soma de volumes em todos os 'tamanhoDaJanela'' elementos/horas consecutivos (as)
		// Para obter a média, dividimos essa soma pelo número de horas na janela
		/**
		 * A variavel "maiorSoma" representa a maior soma de volumes em todos os 'tamanhoDaJanela'' elementos/horas consecutivos (as)
		 * Para obter esta média, simplesmente dividimos essa maiorSoma pelo tamanhoDaJanela que queremos a média de volumes negociados:
		 */
		return maiorSoma / tamanhoDaJanela;
	}
	String teste;

}
