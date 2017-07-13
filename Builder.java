import java.util.*;

public class Builder{


	public void build(String  l[], int c){

		System.out.println("entro");
		ArrayList<Struct> s = new ArrayList<Struct>();
		int countL = 0, j = 0, countE = 1, atual = 0, b = 0, flag2 = 1;
		boolean flag = true, flagA = true, flagP = true;
		Struct s2;
		char ka = ' ', kp = ' ';
		//ArrayList<String> l2 = new ArrayList<String>();
		ArrayList<String> l2 = new ArrayList<String>();
		String z[], var;
		ArrayList<String> aux = new ArrayList<String>();		

		System.out.println("c : "+c);

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
					s2 = new Struct();
					s2.letra = l[i].charAt(j);
					s2.prox = countE;
					s2.atual = atual;
					atual = countE;
					countE++;
					s.add(s2);
					
				
				}
				

			}
		}

		// for para criar lista com estados para declaracao de variavel
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

		//criar a transicao dos estados
		for(int i = 0; i < l2.size(); i++){
			
			var = l2.get(i);
			ka = l2.get(i).charAt(1);
			var = var.replace("|", "@");
			z = var.split("=");
			z = z[1].split(" @");

			System.out.println("Atual: " + ka);
			System.out.println("Prox: "+ kp);

			for (int t = 0; t < z.length ; t++) {
				z[t] = z[t].trim();
				if(z[t].equals("#")){
					System.out.println("#");
					continue;
				}else{
					
					kp = z[t].charAt(2);
					s2 = new Struct();
					s2.letra = z[t].charAt(0);
					s2.atual = 0;
					s2.prox = 0;
					
					System.out.println("transicao: " + s2.letra);
					for (int q = 0; q < aux.size(); q++) {
						if(aux.get(q).charAt(0) == ka){
							s2.atual = Integer.parseInt(aux.get(q+1));	
						}
						if(aux.get(q).charAt(0) == kp){
							s2.prox = Integer.parseInt(aux.get(q+1));		
						}
						q++;
						System.out.println("add atual " + s2.atual );
						System.out.println("add prox " + s2.prox );
						
					}	
					atual = countE;
					s.add(s2);
				}

			}
				System.out.println("saiu for");
		}

			System.out.println("so mostra");


		for(int i = 0; i < aux.size(); i++){		
			System.out.println(aux.get(i));
		}

		for (int ii = 0 ; ii < s.size() ;ii++ ) {
			if (s.get(ii).prox == (-1)) {
				s.get(ii).prox = countE;
			}
			System.out.println(" Estado atual: "+ s.get(ii).atual+"  letra: " + s.get(ii).letra + " prox estado: "+ s.get(ii).prox );
		}
	
		System.out.println("Quantia de letras de entrada: " + countL);
		System.out.println("Quantia de estados necessarios: " + countE);



	}

}