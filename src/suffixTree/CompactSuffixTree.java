package suffixTree;

import java.util.ArrayList;
import java.util.Collection;

public class CompactSuffixTree extends AbstractSuffixTree {

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
		ArrayList<Integer> ocurrences = new ArrayList<Integer>();

		Collection<SuffixTreeNode> children = root.getChildren();
		String nextElement = Character.toString(pattern.charAt(0));
		SuffixTreeNode goodChildren = getElement(children, nextElement);

		System.out.println("Pattern " + pattern);
		if (goodChildren != null) {
			return searchAll(ocurrences, goodChildren, pattern,"");
		}
		else {
			return ocurrences;
		}
	}

	private ArrayList<Integer> searchAll(ArrayList<Integer> ocurrences, SuffixTreeNode current, String pattern, String patternFound) {
		
		System.out.println(pattern + "  " + patternFound);
		
		if (pattern.equals(patternFound)) {		// COINCIDE EL PATRON, BUSCAR SIGUIENTES SI PROCEDE
			System.out.println("Coincide el patron	" + pattern + "	" + patternFound);
			ocurrences.add(1);
			return ocurrences;
		}
		else  {		// COINCIDE EL SIGUIENTE CARACTER DEL PATRON
			
			Collection<SuffixTreeNode> children = current.getChildren();
			String nextElement = Character.toString(pattern.charAt(patternFound.length()));
			SuffixTreeNode goodChildren = getElement(children,nextElement);
			
			System.out.println("Incoming " + current.incomingEdge.label + " Next element " + nextElement);
			
			if (current.incomingEdge.label.equals(nextElement)) {
				patternFound += pattern.charAt(patternFound.length());
				
				if (pattern.equals(patternFound)) {
					ocurrences.add(1);
					return ocurrences;
				}
				else {
					if (goodChildren!= null) {		// COINCIDE EL PATRON, BUSCAR SIGUIENTES SI PROCEDE
						return searchAll(ocurrences, goodChildren, pattern, patternFound);
					}
					else {
						System.out.println("No hay hijos buenos");
						return ocurrences;
					}
				}
			}
			
			else {					// NO PUEDE HABER MAS PATRONES
				System.out.println("No puede haber más patrones");
				return ocurrences;
			}
		
		}
		
		
	}

	private SuffixTreeNode getElement(Collection<SuffixTreeNode> children, String element) {

		for (SuffixTreeNode ch : children) {
			System.out.println("Element " + ch.incomingEdge.label);
			if (ch.incomingEdge.label.equals(element)) {
				return ch;
			}
		}
		return null;
	}
}
