import java.util.*;

public class Builder{


	public void build(String  l[], int c){
		ArrayList<Struct> s = new ArrayList<Struct>();
		int countL = 0, j = 0, countE = 1, atual = 0;
		boolean flag = true;
		char aux;
		Struct s2;
		String z[];


		System.out.println("c : "+c);

		for(int i = 0; i < c; i++){
			System.out.println(i+": "+l[i]);
			if (l[i].charAt(0) == '<'){
				//reconhece as letras do nome de uma variavel
				//informada como uma gramatica
				l[i] = l[i].replace('|', '#');
				

				z = l[i].split("#");
				for (int d = 0; d < z.length; d++ ) {
					System.out.println(z[d]);
				}

			}else{
				atual = 0;
				//Reconhece cada letra de cada comando
				
				for (j = 0; j < l[i].length();j++ ) {
					if(s.size() > 0){
						s2 = new Struct();
						for (int t = 0; t < s.size() ; t++ ) {
							System.out.println("tamano s"+ s.size() +"  t: "+t);
							if (l[i].charAt(j) == s.get(t).letra && atual == s.get(t).atual) {
								if (s.get(s.get(t).prox).letra == l[i].charAt(j+1)) {
									System.out.println("entrada e estado iguais");
									System.out.println("insere :  "+l[i].charAt(j));
									s2.letra = l[i].charAt(j);
									s2.prox = s.get(t).prox;
									s2.atual = s.get(t).atual;
									atual = s.get(t).prox;
									flag = false;
								}
							}		
						}
						if (flag) {
							System.out.println("entrada e estado n iguais");
							System.out.println("insere :  "+l[i].charAt(j));
							s2.letra = l[i].charAt(j);
							s2.prox = countE;
							s2.atual = atual;
							atual = countE;
							countE++;
						}
						s.add(s2);
						flag = true;
					}else{
							System.out.println("primeiro elemento");
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