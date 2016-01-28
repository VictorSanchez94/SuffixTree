package suffixTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SuffixTree {

	private static final String[] VARS = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", 
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	
	/**
	 * References: http://en.literateprograms.org/index.php?title=Special:DownloadCode/Suffix_tree_(Java)&oldid=18684
	 */
	public static void main(String[] args) {

		if(args[0].equalsIgnoreCase("rdm")){	// Create a random suffixTree and pattern to search
			if(args.length == 5){
				int numVar = Integer.parseInt(args[1]);
				int lengthPattern = Integer.parseInt(args[2]);
				int lengthText = Integer.parseInt(args[3]);
				int numTexts = Integer.parseInt(args[4]);
				
				System.out.println("Creando arbol de sufijos con " + numTexts + " textos aleatorios");
				
				SimpleSuffixTree sTree = new SimpleSuffixTree(getRandomString(lengthText, numVar));
				for (int i=1; i<numTexts; i++) {
					sTree.addText(getRandomString(lengthText, numVar), i);
				}
				
				CompactSuffixTree cTree = new CompactSuffixTree(sTree);
				String pattern = getRandomString(lengthPattern, numVar);
				
				long t1 = System.currentTimeMillis();
				ArrayList<Integer> results;
				if (numTexts == 1) {
					System.out.println("Buscando todas las posiciones de un patron de longitud " + lengthPattern + " en el arbolde sufijos");
					results = cTree.searchAll(pattern, false);
				}
				else {
					System.out.println("Buscando los documents que contienen un patron de longitud " + lengthPattern + " en el arbolde sufijos");
					results = cTree.searchAll(pattern, true);
				}
				long t2 = System.currentTimeMillis();
				System.out.println("Tiempo empleado en la busqueda: " + (t2-t1) + " ms.");

			}else{
				System.err.println("ERROR. Funcion con patron y textos aleatorios mal invocada.");
			}
		}else if(args.length > 3){		// Search text with the specified pattern
			if(args[2].equalsIgnoreCase("-f")){
				String pattern = args[0];
				int numTexts = 0;
				try{
					numTexts = Integer.parseInt(args[1]);
				}catch(NumberFormatException ex){
					System.err.println("Entrada invalida. Formato de ejecucion:\n"
							+ "\tSuffixTree <patron> <texto>\n"
							+ "\tSuffixTree <patron> <numTextos> [-f] <texto>{numTextos}");
					System.exit(1);
				}
				System.out.println("Parseando ficheros...");
				String s = parseGen(args[3]);
				System.out.println("Creando arbol compacto de sufijos...");
				SimpleSuffixTree sTree = new SimpleSuffixTree(s);
				for(int i=4,j=2; i<args.length; i++, j++){
					s = parseGen(args[i]);
					sTree.addText(s, j);
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
					SimpleSuffixTree sTree = new SimpleSuffixTree(args[2]);
					for(int i=3, j=2; i<args.length; i++, j++){
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
				
			}
		
		}else if(args.length == 3 && args[1].equalsIgnoreCase("-f")){	// Search all pattern's positions in the specified text
			String pattern = args[0];
			System.out.println("Parseando ficheros...");
			String text = parseGen(args[2]);
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
		}else if(args.length == 2){		//Buscar todas las apariciones del patron en un texto
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
			
		}else{
			System.err.println("Entrada invalida. Formato de ejecucion:\n"
					+ "\tSuffixTree <patron> <texto>\n"
					+ "\tSuffixTree <patron> <numTextos> <texto>{numTextos}");
		}
		
	}
	
	/**
	 * Return a String with the content of the file from path
	 */
	private static String parseGen (String path) {
		Scanner sc;
		try {
			sc = new Scanner(new File(path));
			String s = "";
			String aux;
			while(sc.hasNextLine()){
				aux = sc.nextLine().replaceAll("\n", "");
				if(!aux.substring(0, 1).equals(">")){
					s += aux;
				}
			}
			sc.close();
			return s;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * Return a random String with the specified length and number of variables
	 */
	private static String getRandomString(int length, int numVariables) {
		String s = "";
		Random r  = new Random();
		for (int i=0; i<length; i++) {
			s += VARS[r.nextInt(numVariables)];
		}
		return s;
	}

}
