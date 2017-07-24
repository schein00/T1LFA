

// classe que represeta uma transicao 
public class Estrutura{
	
	public int prox; 		// variavel para o estado em que a transicao leva
	public char letra;		// token responsavel pela transicao
	public int atual;		// estado de onde sai a transicao, ou seja, onde reconhece o token da variavel anterior
	public boolean flag;	// true - transicao valida | false - transicao nao valida(desconsidera a transicao)
	public boolean fim;		// true - estado fianl | false - estado nao final
}