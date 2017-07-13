

import java.io.*;
import java.util.*;

public class Automato{

		public static void main (String[] args) throws FileNotFoundException{
		
		File arquivo;
		Scanner conteudo;
		String linhas[];
		Builder b = new Builder();

		try{
			arquivo = new File(args[0]);
			conteudo = new Scanner(arquivo);
			long tempoInicio = System.currentTimeMillis();	

			int i = 0, count;
			
			// pega o tamanho
		    long tamanhoArquivo = arquivo.length();
		    FileInputStream fs = new FileInputStream(arquivo);
	        DataInputStream in = new DataInputStream(fs);

	        LineNumberReader lineRead = new LineNumberReader(new InputStreamReader(in));
		    lineRead.skip(tamanhoArquivo);
		    // conta o numero de linhas do arquivo, começa com zero, por isso adiciona 1
		    count = lineRead.getLineNumber();
			System.out.println(count);
			linhas  = new String[count];
			while(conteudo.hasNext()){
				String g = conteudo.nextLine();
			//	System.out.println("add a linha: "+ i + "  info: "+g);
				linhas[i] = g;
				i++;
			}
			
			b.build(linhas, count);
			
		
		}catch(Exception e){
				System.out.println("Nao e possivel ler o arquivo: " + (args.length > 0 ? args[0] : "(desconhecido)"));
				System.out.println("Uso:\n java -jar Fikken.jar  /caminho/para/arquivo.fik");
				return;
				
		}
	}


}