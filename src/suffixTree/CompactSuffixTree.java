package suffixTree;

import java.util.ArrayList;
import java.util.Collection;

public class CompactSuffixTree extends AbstractSuffixTree {
	
	private ArrayList<Integer> ocurrences;

	public CompactSuffixTree(SimpleSuffixTree simpleSuffixTree) {
		super(simpleSuffixTree.text);
		super.root = compactNodes(simpleSuffixTree.root, 0);
	}

	private SuffixTreeNode compactNodes(SuffixTreeNode node, int nodeDepth) {
		node.nodeDepth = nodeDepth;
		for (SuffixTreeNode child : node.children) {
			while (child.children.size() == 1) {
				SuffixTreeNode grandchild = child.children.iterator().next();
				child.incomingEdge.label += ", " + grandchild.incomingEdge.label;
				child.stringDepth += grandchild.incomingEdge.label.length();
				child.children = grandchild.children;
				for (SuffixTreeNode grandchildAux : child.children)
					grandchildAux.parent = node;
			}
			child = compactNodes(child, nodeDepth + 1);
		}
		return node;
	}

	public ArrayList<Integer> searchAll(String pattern) {
		System.out.println("Text " + text);
		System.out.println("Pattern " + pattern);

		this.ocurrences = new ArrayList<Integer>();
		
		Collection<SuffixTreeNode> children = root.getChildren();
		String nextElement = Character.toString(pattern.charAt(0));
		SuffixTreeNode goodChildren = getElement(children, nextElement);
//		System.out.println("Next element " + nextElement);

		if (goodChildren != null) {
			return searchAll2(goodChildren, pattern,"");
		}
		else {
			return ocurrences;
		}
	}
	
	
	private ArrayList<Integer> searchAll2(SuffixTreeNode current, String pattern, String patternFound) {
		
		
//		String nextElement = Character.toString(pattern.charAt(patternFound.length()));
		
		String[] labels = current.incomingEdge.label.split(", ");
		String auxPatternFound = matchLabel3(pattern, patternFound, labels, current.charPosition);
		
//		System.out.println(pattern + "   " + auxPatternFound + "  " + ocurrences.size());
		
//			
		patternFound = auxPatternFound;
		
//		
		
		if (pattern.equals(patternFound)) {		// Matching
//					ocurrences.add(1);
			/*System.out.println(labels[0]);
			for (SuffixTreeNode soon:current.getChildren()) {
				ocurrences.add(soon.charPosition-pattern.length());
			}*/
//					
			System.out.println("NULLEANDO PATTERN");
			patternFound = pattern.substring(1);
		}
//				
		Collection<SuffixTreeNode> children = current.getChildren();
		String nextElement = Character.toString(pattern.charAt(patternFound.length()));
//		System.out.println("next element: " + nextElement);
		SuffixTreeNode goodChildren = getElement(children,nextElement);
		
//						System.out.println("; Next element " + nextElement + "; length " + patternFound.length());

		if (goodChildren!= null) {		// COINCIDE EL PATRON, BUSCAR SIGUIENTES SI PROCEDE
			return searchAll2(goodChildren, pattern, patternFound);
		}
		else {
//							System.out.println("No hay hijos buenos");
//			ArrayList<Integer> nextOcurrences = matchAllLabel(pattern, patternFound, labels, current.charPosition);
//			ocurrences.addAll(nextOcurrences);
			
			System.out.println("Fin");
			return ocurrences;
		}
		
	}


	private String matchLabel3(String pattern, String patternFound, String[] labels, int charPosition) {

//		System.out.println(patternFound + "|" + printLabels(labels));
		String auxPatternFound = patternFound + printLabels(labels);
//		System.out.println("auxPatternFoundPlus: " + auxPatternFound);
		
		if (auxPatternFound.length() < pattern.length()) {
			return auxPatternFound;
		}
		
		for (int i=0; i<=auxPatternFound.length()-pattern.length(); i++) {
			String currentPattern = "";
			for (int j=0, k=i; j<pattern.length(); j++, k++) {
				currentPattern += Character.toString(auxPatternFound.charAt(k));
			}
//			System.out.println("currentPattern: " + currentPattern);
			if (currentPattern.equals(pattern)) {
//				System.out.println("MATCH: " + (charPosition+i-patternFound.length()));
				ocurrences.add(charPosition+i-patternFound.length());
			}
		}
		
		// Leer patrón devuelto
		String bestLastPattern = "";
		for (int i=auxPatternFound.length()-pattern.length(); i<auxPatternFound.length(); i++) {
			if (i<0) i=0;
			int patternIndex = 0;
			String lastPattern = "";

			for (int j=i; j<auxPatternFound.length(); j++) {
	//			System.out.println(i + " " + patternIndex);
				if (Character.toString(auxPatternFound.charAt(j)).equals(Character.toString(pattern.charAt(patternIndex)))) {
//					System.out.println(i + " " + j + " Añadiendo " + Character.toString(auxPatternFound.charAt(i)));
					lastPattern += Character.toString(auxPatternFound.charAt(j));
					patternIndex++;
				}
				else {
					lastPattern = "";
					break;
				}
			}
			
			if (bestLastPattern.length() < lastPattern.length()) {
				bestLastPattern = lastPattern;
			}
			
		}
		
		
//		System.out.println("Last pattern found: " + bestLastPattern);
		return bestLastPattern;
		
	}
	
	
	private SuffixTreeNode getElement(Collection<SuffixTreeNode> children, String element) {
		for (SuffixTreeNode ch : children) {
			String[] labels = ch.incomingEdge.label.split(", ");
			for (String s:labels) {
				if (s.equals(element)) {
					return ch;
				}
			}
		}
		return null;
	}
	
	private String printLabels(String[] labels) {
		String s = "";
		for (String aux:labels) s += aux;
		return s;
	}
	

	
}
