package br.com.renan.somaacumulada;

import java.util.Arrays;

public class PrefixSum {

	public static void main(String[] args) {

		// Simule um exemplo de um Faturamento diário de uma loja ao longo de uma semana
		double[] faturamentoDiario = { 1200.50, 800.25, 1500.75, 950.30, 2000.00, 0.00, 1750.40 };

		// Passo 1: pré-calcule o acumulado geral
		double[] acumulado = calcularAcumulado(faturamentoDiario);

		// Passo 2: pré-calcule o intervalo, passando o acumulado e as posicoes dos
		// indices referente aos dias da semana que se deseja obter o faturamento
		// acumulado
		double faturamentoQuartaASexta = calcularSomaIntervalo(acumulado, 2, 4);

		// printf permite imprimir uma string e um objeto.
		// O %.2f formata o número para ter 2 casas decimais, f → Tipo float/double, \n
		// → Nova linha
		System.out.printf("Faturamento de quarta a sexta foi de R$ %.2f\n", faturamentoQuartaASexta);

		// Passo 2: pré-calcular o intervalo, passando o acumulado e as posicoes dos
		// indices referente aos dias da semana que se deseja obter o faturamento
		// acumulado
		double faturamentoSemanal = calcularSomaIntervalo(acumulado, 0, 6);
		// printf permite imprimir uma string e um objeto.
		// O %.2f formata o número para ter 2 casas decimais, f → Tipo float/double, \n
		// → Nova linha
		System.out.printf("O Faturamento Total é de R$ %.2f\n", faturamentoSemanal);

	}

	private static double[] calcularAcumulado(double[] valores) {
		// Esta linha cria um novo array vazio com o mesmo tamanho do array original,
		// declarado acima e conforme o tamanho do array do codigo que o invoca,
		// preparando o "esqueleto" onde serão armazenadas as somas acumuladas.
		double[] acumulado = new double[valores.length];

		/**
		 * Que estrategia voce teria que fazer prevendo um resultado que nao faz sentido, mas possivel
		 * de ser solicitado, como o acumulado do primeiro dia?
		 * O core de nossa estrategia ocorrera usando o array de acumulados a cada dia com os dias anteriores.
		 * Como o primeiro dia a considerarmos para fazer o acumulado nao e o dia 1, pois dia 1 nao possui um
		 * dia antes para mescla-lo, temos que fazer uma estrategia tambem para este dia.
		 * Para o primeiro dia atribuiremos o valor do indice zero no acumulado [0].
		 * E no for, nao consideramos entao o indice [0], fazendo entao: 
		 * Acumulado[0] = valores [0]. Ou seja, iniciamos iterando pelo segundo elemento do indice posicao [1].
		 * Se tentássemos fazer acumulado[0] = acumulado[-1] + valores[0], que sera a estrategia que adotaremos
		 * para todos os demais elementos do array, ocorreria: ERRO! Pois (índice -1 não existe).

		 */
		acumulado[0] = valores[0];

		/** 
		 * "Agora no for, para cada iterador i do array que queremos percorrer, (começando a iterar pelo 
		 * elemento nao da primeira posicao zero, mas sim da posicao 1, (que e a segunda posição do array), 
		 * quero que acumulado que receve o iterador [i] seja a soma de todos os valores desde o início 1 
		 * (nao o zero!),até a ultima iteracao que [i] e comparada quanto ao tamanho "(.length)".
		 */
		for (int i = 1; i < valores.length; i++) {
			/**
			 * Precisamos de no minimo 2 dias para poder iniciar o calculo do acumulado, primeiro dia ja cuidamos
			 * acima. entao agora no segundo dia, elemento na posicao [1], temos que considerar que ele recebe 
			 * o iterador menos um "-1" para que seja considerado o elemento zero somado ao valor do novo dia, 
			 * dia que ja terminou e possuimos o faturamento, segundo dia alocado na memoria do elemento 1
			 * , segunda posixao do array, dia 2 alocado na posicao um, inicialmente.
			 */
			acumulado[i] = acumulado[i - 1] + valores[i];
			System.out.println(Arrays.toString(acumulado));
		}
		// retornando o objetivo do metodo, (o acumulado) a cada iteracao, ate o fim do tamanho do array
		return acumulado;
	}

	// Crie o metodo que calcula a soma dos intervalos requeridos, quanto ao
	// faturamento.
	/**
	 * Calcula a soma em um intervalo usando o array acumulado.
	 * 
	 * FÓRMULA PRINCIPAL (MAGIA DO PREFIX SUM): 
	 * Por que funciona? - acumulado[fim] = soma de toda semana, até o fim do intervalo,
	 * ao fazeros soma = acumulado[fim] - acumulado[início - 1], o que ocorre...
	 * E correspondente a soma do ultimo dia do intervalo, - acumulado[início - 1], onde este ultimo trecho
	 * de codigo com o -1 retira o dado antes do periodo inicial solicitado do relatorio que seria o dia 01
	 * do acumulado, por isso eliminanoms o periodo nao requisitado com acumulado que recebe o inicio, menos um.
	 * Obtemos assim apenas os valores DENTRO do intervalo!
	 */
	private static double calcularSomaIntervalo(double[] acumulado, int inicio, int fim) {

		/*
		 * Se forem requerer o valor referente a venda acumulada contemplando o primeiro
		 * dia, ("array 0") como "início": A ultima posicao do array de acumulado, 
		 * que colocamos na variavel fim, já representa a soma de todos os
		 * períodos do array (zero {inicio} até o fim), portanto basta entregar o último elemento do
		 * acumulado que contem todas as somatorias desde o elemento zero, se o
		 * relatorio requeresse do dia 2 ate o dia 4, ja seria diferente, teriamos que fazer: 
		 * [(acumulado ate o dia 4) - (acumulado ate o dia 2-1)] para excluir a data nao requerida
		 * do dia 1. Isto e o que ja fizemos quando inserimos acumulado -1 para retirar o acumulado antes do 
		 * periodo inicial solicitado.
		 * Temos que assegurar aqui que, se o inicio solicitado for igual ao dia zero, entao ja retornamos
		 * o final, que ja representa do dia 1, contido no elemento zero do array ate o ultimo dia, 
		 * representado pela variavel "fim": 
		 */
		if (inicio == 0) {
			return acumulado[fim];
		}
		// retornando o objetivo do metodo, o acumulado no periodo.
		// Essa técnica é extremamente eficiente porque calcula qualquer intervalo em tempo
		// constante O(1), após o pré-processamento do array acumulado!
		// FÓRMULA-CHAVE DO ALGORITMO
		return acumulado[fim] - acumulado[inicio - 1];
	}

}
