package br.com.renan.somaacumulada;

import java.util.Arrays;

public class PrefixSum {

	public static void main(String[] args) {

		// Exemplo de um Faturamento diário de uma loja ao longo de uma semana
		double[] faturamentoDiario = { 1200.50, 800.25, 1500.75, 950.30, 2000.00, 0.00, 1750.40 };

		// Passo 1: pré-calcular o acumulado geral
		double[] acumulado = calcularAcumulado(faturamentoDiario);

		// Passo 2: pré-calcular o intervalo, passando o acumulado e as posicoes dos
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

		// Se tentássemos fazer acumulado[0] = acumulado[-1] + valores[0] → ERRO!
		// (índice -1 não existe)
		// Primeiro dia não tem acumulado anterior, entao atribuimos o valor do indice
		// zero no acumulado e no for, pulamos esse indice ja atribuido abaixo
		// com acumulado[0] = valores [0] e iniciamos iterando pelo segundo elemento do
		// indice, indice na posicao [1].
		acumulado[0] = valores[0];

		// "Para cada posição i do array (começando da segunda posição), quero que
		// acumulado[i] seja a soma de todos os valores desde o início até i."
		for (int i = 1; i < valores.length; i++) {
			// acumulado[i-1] se refere ao acumulado na posicao zero, (posicao anterior),
			// para poder somar com o valor da posicao atual
			acumulado[i] = acumulado[i - 1] + valores[i];
			System.out.println(Arrays.toString(acumulado));
		}
		// retornando o objetivo do metodo, (o acumulado)
		return acumulado;
	}

	// Crie o metodo que calcula a soma dos intervalos requeridos, quanto ao
	// faturamento.
	/**
	 * Calcula a soma em um intervalo usando o array acumulado.
	 * 
	 * 🔥 FÓRMULA PRINCIPAL (MAGIA DO PREFIX SUM): soma = acumulado[fim] -
	 * acumulado[início - 1]
	 * 
	 * Por que funciona? - acumulado[fim] = soma de tudo até o final do intervalo -
	 * acumulado[início-1] = soma dos valores ANTES do intervalo - Subtraindo,
	 * obtemos apenas os valores DENTRO do intervalo!
	 */
	private static double calcularSomaIntervalo(double[] acumulado, int inicio, int fim) {

		/*
		 * Se forem requerer o valor referente a venda acumulada contemplando o primeiro
		 * dia, ("array 0") como "início": O array fim, já representa a soma de todos os
		 * períodos do array zero até o fim, portanto basta entregar o último período do
		 * acumulado que contem todas as somatorias desde o elemento zero, se o
		 * relatorio requeresse do dia 2 ate o dia 4, teriamos que fazer: [(acumulado
		 * ate o dia 4) - (acumulado ate o dia 2-1)] para escluir a data nao requerida
		 * do dia 1.
		 */
		if (inicio == 0) {
			return acumulado[fim];
		}
		// retornando o objetivo do metodo, o acumulado no periodo.
		// Essa técnica é muito eficiente porque calcula qualquer intervalo em tempo
		// constante O(1), após o pré-processamento do array acumulado!
		// FÓRMULA-CHAVE DO ALGORITMO
		return acumulado[fim] - acumulado[inicio - 1];
	}

}
