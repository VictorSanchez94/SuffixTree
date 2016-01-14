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
			SimpleSuffixTree sTree = new SimpleSuffixTree("text");
			CompactSuffixTree cTree = new CompactSuffixTree(sTree);
		}else if(args.length >= 3){
			String pattern = args[0];
			int numTexts = Integer.parseInt(args[1]);
			if(args.length == (numTexts+2)){
				for(int i=3; i<args.length; i++){
					
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
