package suffixTree;

import java.util.ArrayList;

public class SuffixTree {

	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {
		if(args.length == 2){
			String pattern = args[0];
			String text = args[1];
			System.out.println("Creando arbol compacto de sufijos...");
			SimpleSuffixTree sTree = new SimpleSuffixTree(text);
			CompactSuffixTree cTree = new CompactSuffixTree(sTree);
			System.out.println("Buscando patron...");
			ArrayList<Integer> list = cTree.searchAll(pattern);
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
		}else if(args.length >= 3){
			String pattern = args[0];
			int numTexts = Integer.parseInt(args[1]);
			if(args.length == (numTexts+2)){
				String[] texts = new String[numTexts];
				for(int i=3, j=0; i<args.length; i++, j++){
					texts[j] = args[i];
				}
			}else{
				System.err.println("ERROR. Numero de textos introducido como segundo parametro no coincide con el numero\n"
						+ "de textos introducidos");
			}
		}else{
			System.err.println("Entrada invalida. Formato de ejecucion:\n"
					+ "\tSuffixTree <patron> <texto>\n"
					+ "\tSuffixTree <patron> <numTextos> <texto>{numTextos}");
		}
		
		
		
	
//		SimpleSuffixTree sTree = new SimpleSuffixTree("aa");
//		sTree.addText("aaaaaac", 2);
//		sTree.addText("aacb", 3);
//		System.out.println(sTree.root);
//		System.out.println("============================================================================================\n");
//		CompactSuffixTree cTree = new CompactSuffixTree(sTree);
//		System.out.println(cTree.root);
//		
//		ArrayList<Integer> list = cTree.searchAll("ac");
//		System.out.println(list);
		
	}

}
