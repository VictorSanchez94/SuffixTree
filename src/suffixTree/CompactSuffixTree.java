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

	private ArrayList<Integer> searchAll(SuffixTreeNode current, String pattern, String patternFound) {
		
//		System.out.println(pattern + "  " + patternFound);
		
		/*if (pattern.equals(patternFound)) {		// COINCIDE EL PATRON, BUSCAR SIGUIENTES SI PROCEDE
			System.out.println("Coincide el patron	" + pattern + "	" + patternFound);
			ocurrences.add(1);
			return ocurrences;
		}
		else  {		// COINCIDE EL SIGUIENTE CARACTER DEL PATRON
			*/
//			Collection<SuffixTreeNode> children = current.getChildren();
			String nextElement = Character.toString(pattern.charAt(patternFound.length()));
//			SuffixTreeNode goodChildren = getElement(children,nextElement);
			
//			System.out.println("Current " + current.incomingEdge.label + "; Next element " + nextElement + "; length " + patternFound.length());
			
			String[] labels = current.incomingEdge.label.split(", ");
			
			String auxPatternFound = matchLabel2(pattern, patternFound, labels, current.charPosition);
			
			System.out.println(pattern + "   " + auxPatternFound + "  " + ocurrences.size());
			
//			if (labels[0].equals(nextElement)) {
			if (!patternFound.equals(auxPatternFound)) {		// Pattern found updated
//				patternFound += pattern.charAt(patternFound.length());
				patternFound = auxPatternFound;
				
				if (pattern.equals(patternFound)) {		// Matching
//					ocurrences.add(1);
					System.out.println(labels[0]);
					for (SuffixTreeNode soon:current.getChildren()) {
						ocurrences.add(soon.charPosition-pattern.length());
					}
//					
					patternFound = "";
				}
//				else {		// No matching
					
					/*if (labels.length > 1) {		// READ MULTIPLE LABEL
						
						for (int i=1; i<labels.length; i++) {
//							System.out.println("Reading labels: " + labels[i] + "  " + pattern.charAt(patternFound.length()) + "  " + pattern + "  " + patternFound);
							if (labels[i].equals(Character.toString(pattern.charAt(patternFound.length())))) {
//								System.out.println("Matching");
								patternFound += pattern.charAt(patternFound.length());
							}
							else {
//								System.out.println("No matching");
								return ocurrences;
							}
							
							if (pattern.equals(patternFound)) {
//								ocurrences.add(1);
								return ocurrences;
							}
						}
					}
					else {			// READ CHILDREN*/
					
						Collection<SuffixTreeNode> children = current.getChildren();
						nextElement = Character.toString(pattern.charAt(patternFound.length()));
						SuffixTreeNode goodChildren = getElement(children,nextElement);
						
//						System.out.println("; Next element " + nextElement + "; length " + patternFound.length());
	
						if (goodChildren!= null) {		// COINCIDE EL PATRON, BUSCAR SIGUIENTES SI PROCEDE
							return searchAll(goodChildren, pattern, patternFound);
						}
						else {
//							System.out.println("No hay hijos buenos");
							ArrayList<Integer> nextOcurrences = matchAllLabel(pattern, patternFound, labels, current.charPosition);
							ocurrences.addAll(nextOcurrences);
							return ocurrences;
						}
					//}
//				}
			}
			else {					// NO PUEDE HABER MAS PATRONES
				System.out.println("No puede haber más patrones");
				return ocurrences;
			}
		
		/*}*/
	}
	
	
	private ArrayList<Integer> searchAll2(SuffixTreeNode current, String pattern, String patternFound) {
		
		
//		String nextElement = Character.toString(pattern.charAt(patternFound.length()));
		
		String[] labels = current.incomingEdge.label.split(", ");
		
		String auxPatternFound = matchLabel2(pattern, patternFound, labels, current.charPosition);
		
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
			patternFound = "";
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
	
	
	private String matchLabel(String pattern, String patternFound, String[] labels) {
		
		String auxPatternFound = patternFound;
		for (String s:labels) {
			if (!s.equals("$")) {
//				System.out.println(pattern + "   " + auxPatternFound + "  " + labels.length + "   " + s);
				if (s.equals(Character.toString(pattern.charAt(auxPatternFound.length())))) {
					auxPatternFound += Character.toString(pattern.charAt(auxPatternFound.length()));
				}
				
				if (pattern.length() == auxPatternFound.length()) {
					break;
				}
			}
		}
		
		return auxPatternFound;
	}
	
	
	private String matchLabel2(String pattern, String patternFound, String[] labels, int charPosition) {
		
		String auxPatternFound = patternFound;
		
		/*String[] auxLabels = new String[labels.length-1];
		for (int i=0; i<labels.length-1; i++) {
			auxLabels[i] = labels[i];
		}
		labels = auxLabels;*/
		
//		System.out.println("Labels: " + labels.length + "; pattern: " + pattern.length());
		for (int i=0; i<labels.length; i++) {
//			System.out.println(i + " -- " + labels.length + " - " + pattern.length());
			if (!patternFound.equals("")) {
//				System.out.println("Pattern not null");
				boolean match = false;
				for (int j=0; j<pattern.length() && j<labels.length; j++) {
//					System.out.println("NOTNULL " + labels[j]);
					auxPatternFound += labels[j];
					if (auxPatternFound.equals(pattern)) {
						match = true;
						break;
					}
				}
				if (match) {
//					System.out.println("MATCH FOUND " + pattern + "  " + auxPatternFound + "  " + (charPosition-patternFound.length()));
					ocurrences.add(charPosition-patternFound.length());
				}
				auxPatternFound = "";
				patternFound = "";
			}
			else if (patternFound.equals("")/* && (labels.length-pattern.length()-i)>0*/) {
				auxPatternFound = "";
				for (int j=0, k=i; j<pattern.length() && k<labels.length; j++, k++) {
//					System.out.println("Searching " + i + " " + j + " " + k + "   " + Character.toString(pattern.charAt(j)) + " " + labels[k]);
					if (Character.toString(pattern.charAt(j)).equals(labels[k])) {
//						System.out.println("ña");
						auxPatternFound += Character.toString(pattern.charAt(j));
					}
					if (pattern.equals(auxPatternFound)) {
//						System.out.println("MATCH " + pattern + "  " + auxPatternFound + "  " + (charPosition+i));
						ocurrences.add(charPosition+i);
						break;
					}
				}
				
			}
			
		}
		
//		System.out.println("Last patternFound " + auxPatternFound);
		return auxPatternFound;
	}


	private ArrayList<Integer> matchAllLabel(String pattern, String patternFound, String[] labels, int charPosition){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i=0; i<labels.length-pattern.length(); i++) {
			if (patternFound.equals("")) {
				boolean match = true;
				for (int j=0; j<pattern.length(); j++) {
					if (!Character.toString(pattern.charAt(j)).equals(labels[j])) {
						match = false;
						break;
					}
				}
				if (match) {
					list.add(charPosition+i);
				}
			}
			else {
				boolean match = false;
				String auxPatternFound = patternFound;
				for (int j=auxPatternFound.length(); j<pattern.length(); j++) {
					auxPatternFound += Character.toString(pattern.charAt(j));
					if (auxPatternFound.equals(pattern)) {
						match = true;
					}
				}
				if (match) {
					list.add(charPosition-patternFound.length());
				}
			}
		}
		
		
		return list;
	}
	
	
	private SuffixTreeNode getElement(Collection<SuffixTreeNode> children, String element) {

		for (SuffixTreeNode ch : children) {
//			System.out.println("Element " + ch.incomingEdge.label);
			/*if (ch.incomingEdge.label.split(", ")[0].equals(element)) {
//				System.out.println("Element found: " + ch.incomingEdge.label + "; Element " + element );
				return ch;
			}*/
			String[] labels = ch.incomingEdge.label.split(", ");
			for (String s:labels) {
				if (s.equals(element)) {
					return ch;
				}
			}
		}
		return null;
	}
}
