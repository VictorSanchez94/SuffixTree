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
		SuffixTreeNode goodChildren = getElement(children,nextElement);
		
		return searchAll(ocurrences, goodChildren, pattern, Character.toString(pattern.charAt(0)));
	}
	
	private ArrayList<Integer> searchAll(ArrayList<Integer> ocurrences, SuffixTreeNode current, String pattern, String patternFound) {
		
		if (patternFound == null) {
			System.out.println("currentnull");
		}
		if (pattern.equals(patternFound)) {		// COINCIDE EL PATRON, BUSCAR SIGUIENTES SI PROCEDE
			ocurrences.add(1);
			return ocurrences;
		}
		else if (current.incomingEdge.label.equals(pattern.charAt(patternFound.length()))) {		// COINCIDE EL SIGUIENTE CARACTER DEL PATRON
			patternFound += pattern.charAt(patternFound.length());

			Collection<SuffixTreeNode> children = root.getChildren();
			String nextElement = Character.toString(pattern.charAt(patternFound.length()));
			SuffixTreeNode goodChildren = getElement(children,nextElement);
		
			if (goodChildren!= null) {
				return searchAll(ocurrences, goodChildren, pattern, patternFound);
			}
			else {					// NO PUEDE HABER MAS PATRONES
				return ocurrences;
			}
		
		}
		
		
		
		
		return ocurrences;
	}
	
	
	private SuffixTreeNode getElement(Collection<SuffixTreeNode> children, String element) {
		
		for (SuffixTreeNode ch:children) {
			if (ch.incomingEdge.label.equals(element)) {
				return ch;
			}
		}
		return null;
	}
}
