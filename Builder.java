
import java.io.FileWriter;
import java.util.*;

public class Builder{


	
	//funcao onde é feito toda a criacao do automato
	// recebe como entrada um vetor de String, contendo as linahs presente no arquivo de entrada 
	// e um segundo parametro sendo um int com o tamanho do vetor de linhas
	// nessa abordagem utilizamos aprenas uma lista com as transicoes possiveis dentro do automato
	
	public void build(String  l[], int c){

		ArrayList<Estrutura> s = new ArrayList<Estrutura>();
		int countL = 0, j = 0, countE = 1, atual = 0, b = 0, flag2 = 1, indD = 0, auxF = 0;
		boolean flag = true, flagA = true, flagP = true, flagLetra = true;
		Estrutura s2 = new Estrutura();
		char ka = ' ', kp = ' ';
		//ArrayList<String> l2 = new ArrayList<String>();
		ArrayList<String> l2 = new ArrayList<String>();
		String z[], var;
		ArrayList<String> aux = new ArrayList<String>();	
		ArrayList<Character> letras = new ArrayList<Character>();
		ArrayList<Integer> estadosFinais = new ArrayList<Integer>();

		System.out.println("c : "+c);

		
		//INICIO - leitura e divisao dos caracteres de um comando
		// Cria uma estrutura com as transicoes de estados
		// Faz apenas o reconhecimento dos tokens de uma sequancia da gramatica, ou seja, 
		// le apenas os comandos no arquivo, se for a gramatica para nome de variaveis, grava numa lista auxiliar
		// que é feita no proximo passo da funcao
		// para cada linha do arquivo de entrada sera feita uma interacao nesse laco
		// cada linhas é verificada se possui a descricao de uma gramatica, sendo a descricao, entao é salva em uma estrutura auxliar
		// se nao for entao 
		// tem se um novo laco onde percore todos os tokens da linhas, removendo os espacos no comeco e fim da linhas
		// apos é criada uma nova variavel auxiliar do tipo estrutura, que setada com os parametros validos de uma transicao
		// e futuramente adicionada la lista de transicoes
		for(int i = 0; i < c; i++){
			System.out.println(i+": "+l[i]);
			l[i] = l[i].trim();
			if (l[i].charAt(0) == '<'){
				//reconhece as letras do nome de uma variavel
				//informada como uma gramatica
				System.out.println("add: "+l[i]);
				l2.add(l[i]);
			}else{
				atual = 0;
				//Reconhece cada letra de cada comando
				System.out.println("add2: "+l[i]);
				for (j = 0; j < l[i].length();j++ ) {
					flagLetra = true;
					s2 = new Estrutura();
					s2.letra = l[i].charAt(j);
					s2.prox = countE;
					s2.atual = atual;
					atual = countE;
					countE++;
					s2.flag = true;
					if((j + 1) == l[i].length()) {
						s2.fim = true;
					}else {
						s2.fim = false;
					}
					
					for (int w = 0; w < letras.size(); w++) {
						if(letras.get(w) == l[i].charAt(j)) {
							flagLetra = false;
							break;
						}
					}
					
					if(flagLetra) {
						letras.add(l[i].charAt(j));
					}
					
					s.add(s2);
				}
			}
		}
		//FIM - - leitura e divisao dos caracteres de um comando

		// INICIO - for para criar lista com estados para declaracao de variavel
		// nesse segmento do codigo, é feito o tratamento para gramaticas que indiquem os nomes de variaveis, pois nesse 
		// é feito a divisao e o reconhecimento dos estados das transicoes da gramatica
		// para serem feitas as transicoes no proximo segmento
		for(int i = 0; i < l2.size(); i++){
			
			var = l2.get(i);

			if(var.charAt(1) == 'S'){
				aux.add(var.charAt(1) + "");
				aux.add("0");
			}else{
				aux.add(var.charAt(1) + "");
				aux.add(countE + "");
				countE++;
			}
			var = var.replace("|", "@");
			z = var.split("=");

			z = z[1].split(" @");

			System.out.println(var);
		
		}
		// FIM  - for para criar lista com estados para declaracao de variavel

		
		//Inicio - criar a transicao dos estados
		// nesse segmento é feito as transicoes que que estao presentes na gramatica para o reconhecimento de variaveis,
		// utilizando as estrtuturas auxilias criadas nos segments anteriores do codigo,
		// utiliza-se as linhas e os estados de transicoes, para criar as transicoes 
		for(int i = 0; i < l2.size(); i++){
			
			var = l2.get(i);
			ka = l2.get(i).charAt(1);
			var = var.replace("|", "@");
			
			if(var.contains(" #") || l2.get(i + 1).contains(" #")) {
				auxF = 1;
			}else {
				auxF = 0;
			}
				
			
			z = var.split("=");
			z = z[1].split(" @");
			

			for (int t = 0; t < z.length ; t++) {
				flagLetra = true;
				z[t] = z[t].trim();
				if(z[t].equals("#")){
					System.out.println("#");
					continue;
				}else{
					
					kp = z[t].charAt(2);
					s2 = new Estrutura();
					s2.letra = z[t].charAt(0);
					s2.atual = 0;
					s2.prox = 0;
					
					for (int q = 0; q < aux.size(); q++) {
						if(aux.get(q).charAt(0) == ka){
							s2.atual = Integer.parseInt(aux.get(q+1));	
						}
						if(aux.get(q).charAt(0) == kp){
							s2.prox = Integer.parseInt(aux.get(q+1));		
						}
						q++;
					}	
					atual = countE;
					s2.flag = true;
					if(auxF != 0) {
						s2.fim = true;
					}else {
						s2.fim = false;
					}

					for (int w = 0; w < letras.size(); w++) {
						if(letras.get(w) == z[t].charAt(0)) {
							flagLetra = false;
							break;
						}
					}
					
					if(flagLetra) {
						letras.add(z[t].charAt(0));
					}
					
					s.add(s2);
				}

			}
		}
		//FIM - criar a transicao dos estados
		
		// Segmento de codigo, para mostrar as transicoes adicionadas, ainda com determinismo.
		// mostrando tambem que quando criada uma transicoa sempre é valida, mesmo tendo indeterminismo
		// mostrando tambem a quantia de estados necessarios ate o atual momento
		for(int i = 0; i < aux.size(); i++){		
			System.out.println(aux.get(i));
		}

		for (int ii = 0 ; ii < s.size() ;ii++ ) {
			if (s.get(ii).prox == (-1)) {
				s.get(ii).prox = countE;
			}
			System.out.println(" Estado atual: "+ s.get(ii).atual+" | letra: " + s.get(ii).letra + " | prox estado: "+ s.get(ii).prox + " | usado: "+s.get(ii).flag  );
		}
		System.out.println("Quantia de estados necessarios: " + countE);

		
		//INCIO - Determiniza automato
	
		// nesse segmento de codigo é realizada a determinizacao,
		// sendo que exista uma transicao, que apartir de um estado atual com um mesmo token vai para dois estados futuros diferentes,
		// que dizer que esses 2 transicoes sao indeterministicas, portante é alocado uma nova estrutura que representa uma transicao
		// adiconando os dados, atual e letra como dos dois anteriosres, porem substituindo o proximo estado como um estado disponivel
		// ou seja, é alocado um estado depois do ultimo que é removido o indeterminismo, 
		// e setando as duas transicoes que geraram o indeterminismo, como sendo transicoes irregulares, ou que nao podem ser usadas
		// por esse motivo, nao sao criados estados mostos, e estados onde nao exista uma transicao para chegar, portanto eliminando
		// o procedimento de minimizacao, pois ja estamos apenas considerando transacoes validas a partir de um estado inicial
		// se alguma das transicoes que geram indeterminismo for uma trasicao para estado final, logo esse estado tambem sera final,
		// contando que o proximo estado seja para reconhecer uma cadeia da gramatica
		Estrutura s3;
		int prox1 = 0, prox2 = 0;
		for (int i = 0; i < s.size(); i++) {
			indD = i;
			s3 = s.get(i);
			System.out.println("i: "+i+"  flag i " + s3.flag);
			if(s.get(i).flag == false) {
				System.out.println("false i");
				continue;
			}
			for (int k = i; k < s.size(); k++) {
				prox1 = 0;
				prox2 = 0;
				if(s.get(k).flag == false || s.get(i).flag == false) {
					continue;
				}
				if(k != i) {
					if(s.get(i).atual == s.get(k).atual && s.get(i).letra == s.get(k).letra && s.get(i).prox != s.get(k).prox) {
						for (int d = 0; d < s.size(); d++) {
							if(s.get(d).atual == s.get(i).prox) {
								prox1 = d;
							}
							if(s.get(d).atual == s.get(k).prox) {
								prox2 = d;
							}
						}
						s2 = new Estrutura();
						s2.flag = true;
						s2.atual = s.get(i).atual;
						s2.letra = s.get(i).letra;
						s2.prox = countE;
						if(s.get(i).fim || s.get(i).fim) {
							s2.fim = true;
						}else {
							s2.fim = false;
						}
						s.add(s2);
						
						if(prox1 != 0) {
							s.get(prox1).atual = countE;
						}
						if(prox2 != 0) {
							s.get(prox2).atual = countE;
						}
						
						countE+=1;
						s.get(i).flag = false;
						s.get(k).flag = false;
			//			break;
					}
				}
				
			}
		}
		
		//FIM - Determiniza automato
		

		// segmento para mostrar as transicoes depois de o automato ser determinizado
		// mostrando novamente a quantia de estados utlizados, contando com estados mortos ja eliminados
		for (int ii = 0 ; ii < s.size() ;ii++ ) {
			if (s.get(ii).prox == (-1)) {
				s.get(ii).prox = countE;
			}
			System.out.println(" Estado atual: "+ s.get(ii).atual+" | letra: " + s.get(ii).letra + " | prox estado: "+ s.get(ii).prox + " | usado: "+s.get(ii).flag +" |  transicao para estado final: "+s.get(ii).fim+" | --trnsicao: "+ii );
		}
		System.out.println("Quantia de estados necessarios: " + countE);
		
		
		// INICIO - cria uma lista com os estados finais
		// é percorida toda a lista de transicoes, encontrando os estados finais, e guardando-os em uma lista de de int para marcar
		// como estado final no arquivo de saida
		boolean flagEstado = true;
		for (int i = 0; i < s.size(); i++) {
			flagEstado = true;
			if(s.get(i).flag) {
				if(s.get(i).fim) {
					if(estadosFinais.size() == 0) {
						estadosFinais.add(s.get(i).prox);
					}else{
						for (int m = 0; m < estadosFinais.size(); m++) {
							if(estadosFinais.get(m) == s.get(i).prox) {
								flagEstado = false;
								break;
							}
						}
						if(flagEstado) {
							estadosFinais.add(s.get(i).prox);
						}
					}
				}
			}
		}
		
		// FIM - cria uma lista com os estados finais
		
		// laco para mostrar os estados finais presente no automato
		System.out.print("Estados finais: ");
		for (int i = 0; i < estadosFinais.size(); i++) {
			System.out.print(estadosFinais.get(i)+" | ");
		}
		System.out.println(" ");
		
		
		//criando o arquivo de saida
		
		
		// INICIO - escrita arquivo de saida
		// utilizando os tratamentos de execoes que sao implementados pelo proprio java
		// esse segmento do codigo so sera feito se for possivel criar e escrever o arquivo de saida,
		// senao o programa finaliza
		try {
			FileWriter w = new FileWriter("AFD.csv");
			
			// segemento onde é feito o reconhecimento das letras presentes no automato,
			// ou os tokens que geram transicoes
			// e sao adicionadas numa estrutura auxilar para a montagem da tabela de saida
			// tambem é feita a escrata do cabecalho com os tokens de transicao do AFD
			System.out.println("letras presentes: ");
			System.out.print("     |");
			w.append(" |");
			for (int i = 0; i < letras.size(); i++) {
				if((i+1) ==  letras.size()) {
					System.out.print(letras.get(i));
					w.append(letras.get(i));
				}else {
					System.out.print(letras.get(i)+" | ");
					w.append(letras.get(i)+" | ");
				}
			}
			w.append('\n');
			System.out.println(" ");
			
			ArrayList<Integer> states = new ArrayList<Integer>();
			boolean v = false, p = false;
			// laco que vai de 0 ate o numero total de estados necessarios para o AFD
			// porem so sao cosiderados os estados, onde as transicoes sao validas, 
			// nesse for é feita a escrita de cada linhas do arquivo de saida
			// cada interacao é escrita uma linha no arquivo de saida
			for (int state = 0; state < countE; state++) {
				v = false;
				p = true;
				
				//for para reconhecer todas as transicoes em que o estado atual é o estado que esta sendo escrito no arquivo
				// é adicionado a uma lista auxiliar a posicao desses transicoes, para o controle de transicoes dentro do AFD
				// so é considerada a transicao se ela for valida e o estado atual for o mesmo que esta sedo escrito no arquivo
				// ou se a transicoa vor valida e o priximo estado for um estado de reconhecimento de cadeia
				for(int t = 0; t < s.size(); t++) {
					if(s.get(t).atual == state && s.get(t).flag  || (s.get(t).prox == state && s.get(t).fim) && s.get(t).flag) {
						states.add(t);
						v = true;
					}
				}
				
				// apenas sao escrias as transicoes que fora aceitas e foram guardadas no teste acima
				// entao se o estado for final, sera escrito o simbolo *
				// entao é escrito o estado com o nome de  S + o intero que representa o estado
				// depois sao escritas as transicoes, se tiverem e em orem de cada letra presente na lista de letras
				// se nao ouver uma transicao entao é colocado um X representando um estado de erro
				if(v) {
					for (int i = 0; i < estadosFinais.size(); i++) {
						if(estadosFinais.get(i) == state) {
							System.out.print("*");
							w.append("*");
							break;
						}
					}
					System.out.print("S"+ state+" | ");
					w.append("S"+ state+ " | ");

					for (int i = 0; i < letras.size(); i++) {
						p = true;
						for (int k = 0; k < states.size(); k++) {
							if(s.get(states.get(k)).letra == letras.get(i)) {
								System.out.print("S"+ s.get(states.get(k)).prox + " | ");
								w.append("S"+ s.get(states.get(k)).prox + " | ");
								p = false;
								break;
							}
						}
						if(p) {
							System.out.print(" X | ");
							w.append(" X | ");
						}
					}
					// depois de cada linha escrita limpamos a variavel auiliar de transicoes
					// e escrevemos um \n representado o simbolo de quebra de linhas
					states.clear();
					System.out.print("\n");
					w.append("\n");
				}
			}
			//adiciao do estado de erro
			System.out.print("*X |");
			w.append("*X |");
			
			for (int i = 0; i < letras.size(); i++) {
				System.out.print(" X |");
				w.append(" X |");
			}
			
			//  é escrito e fechado o arquivo de saida com o AFD pronto
			System.out.print("\n");
			w.append("\n");
			w.flush();
	        w.close();
	        
		}catch (Exception e) {
				e.printStackTrace();
		}
	}

	// FIM - escrita arquivo de saida
}
