package suffixTree;

import java.util.ArrayList;

public class SuffixTree {

	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {

		if(args.length == 2){		//Buscar todas las apariciones del patron en un texto
			String pattern = args[0];
			String text = args[1];
			System.out.println("Creando arbol compacto de sufijos...");
			SimpleSuffixTree sTree = new SimpleSuffixTree(text);
			CompactSuffixTree cTree = new CompactSuffixTree(sTree);
			System.out.println("Buscando patron...");
			ArrayList<Integer> list = cTree.searchAll(pattern, false);
			if(list.isEmpty()){
				System.out.printf("No se ha encontrado el patrón '%s' en el texto.\n", pattern);
			}else if(list.size() == 1){
				System.out.printf("El patron '%s' aparece en la posicion %d del texto.\n", pattern, list.get(0));
			}else{
				System.out.printf("El patron '%s' aparece en el texto en las posiciones:\n", pattern);
				System.out.print("\t");
				for (Integer i : list){
					System.out.printf("%d ", i);
				}
			}
			
		}else if(args.length >= 3){		//Buscar los textos que contienen el patron
			String pattern = args[0];
			int numTexts = 0;
			try{
				numTexts = Integer.parseInt(args[1]);
			}catch(NumberFormatException ex){
				System.err.println("Entrada invalida. Formato de ejecucion:\n"
						+ "\tSuffixTree <patron> <texto>\n"
						+ "\tSuffixTree <patron> <numTextos> <texto>{numTextos}");
				System.exit(1);
			}
			if(args.length == (numTexts+2)){
				System.out.println("Creando arbol compacto de sufijos...");
				SimpleSuffixTree sTree = new SimpleSuffixTree(args[3]);
				for(int i=4, j=2; i<args.length; i++, j++){
					sTree.addText(args[i], j);
				}
				CompactSuffixTree cTree = new CompactSuffixTree(sTree);
				System.out.println("Buscando patron...");
				ArrayList<Integer> docs = cTree.searchAll(pattern, true);
				if(docs.isEmpty()){
					System.out.printf("No se ha encontrado el patrón '%s' en los textos.\n", pattern);
				}else if(docs.size() == 1){
					System.out.printf("El patron '%s' aparece en el texto %d.\n", pattern, docs.get(0));
				}else{
					System.out.printf("El patron '%s' aparece en los textos:\n", pattern);
					System.out.print("\t");
					for (Integer i : docs){
						System.out.printf("%d ", i);
					}
				}
			}else{
				System.err.println("ERROR. Numero de textos introducidos por parametros no coincide con el numero\n"
						+ "de textos introducidos");
			}
			
		}else{
			System.err.println("Entrada invalida. Formato de ejecucion:\n"
					+ "\tSuffixTree <patron> <texto>\n"
					+ "\tSuffixTree <patron> <numTextos> <texto>{numTextos}");
		}

		
		
//		SimpleSuffixTree sTree = new SimpleSuffixTree("aaabbbc");
//		sTree.addText("aaab", 2);
//		sTree.addText("abb", 3);
//		System.out.println(sTree.root);
//		
//		CompactSuffixTree tree = new CompactSuffixTree(sTree);
//		System.out.println(tree.root);
//		
//		String pattern = "abb";
//		ArrayList<Integer> result = tree.searchAll(pattern, true);
//		System.out.println(result);
//		System.out.println("Num matches: " + result.size());
//		System.out.println("Text: " + tree.text);
//		System.out.println("Pattern: " + pattern);
		
	}

}
