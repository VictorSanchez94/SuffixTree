package suffixTree;

import java.util.ArrayList;
import java.util.Collection;

public class CompactSuffixTree extends AbstractSuffixTree {
	
	private ArrayList<Integer> ocurrences;

	/**
	 * Create a CompactSuffixTree from a SimpleSuffixTree
	 */
	public CompactSuffixTree(SimpleSuffixTree simpleSuffixTree) {
		super(simpleSuffixTree.text);
		super.root = compactNodes(simpleSuffixTree.root, 0);
	}

	/**
	 * Compact the node SuffixTree
	 */
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

	/**
	 * If searchDocs = true -> return documents's id who contains the specified pattern
	 * If searchDocs = false -> return all positions where pattern appears in the text
	 */
	public ArrayList<Integer> searchAll(String pattern, boolean searchDocs) {
		this.ocurrences = new ArrayList<Integer>();
		
		Collection<SuffixTreeNode> children = root.getChildren();
		String nextElement = Character.toString(pattern.charAt(0));
		SuffixTreeNode goodChildren = getElement(children, nextElement);

		if (goodChildren != null) {
			return searchAllAux(goodChildren, pattern,"", searchDocs);
		}
		else {
			return ocurrences;
		}
	}
	
	/**
	 * Search the pattern from the current node
	 */
	private ArrayList<Integer> searchAllAux(SuffixTreeNode current, String pattern, String patternFound, boolean searchDocs) {
		
		String[] labels = current.incomingEdge.label.split(", ");
		String auxPatternFound = matchLabel(pattern, patternFound, labels, current.charPosition, current, searchDocs);
		
		if (searchDocs && ocurrences.size()!=0) {
			return ocurrences;
		}
		
		patternFound = auxPatternFound;
		
		if (pattern.equals(patternFound)) {		// Matching
			patternFound = pattern.substring(1);
		}
			
		Collection<SuffixTreeNode> children = current.getChildren();
		String nextElement = Character.toString(pattern.charAt(patternFound.length()));
		SuffixTreeNode goodChildren = getElement(children,nextElement);
		
		if (goodChildren!= null) {
			return searchAllAux(goodChildren, pattern, patternFound, searchDocs);
		}
		else {
			return ocurrences;
		}
		
	}

	/**
	 * Check pattern matching
	 */
	private String matchLabel(String pattern, String patternFound, String[] labels, int charPosition, SuffixTreeNode current, boolean searchDocs) {

		String auxPatternFound = patternFound + printLabels(labels);
		
		if (auxPatternFound.length() < pattern.length()) {
			return auxPatternFound;
		}
		
		boolean skip = false;
		
		for (int i=0; i<=auxPatternFound.length()-pattern.length(); i++) {
			String currentPattern = "";
			for (int j=0, k=i; j<pattern.length(); j++, k++) {
				currentPattern += Character.toString(auxPatternFound.charAt(k));
			}
			if (currentPattern.equals(pattern)) {
				
				if (searchDocs) {
					ocurrences.addAll(current.docsNode);
					skip = true;
					break;
				}
				else {
					ocurrences.add(charPosition+i-patternFound.length());
				}
			}
		}
		
		String bestLastPattern = "";
		for (int i=auxPatternFound.length()-pattern.length(); i<auxPatternFound.length() && !skip; i++) {
			if (i<0) i=0;
			int patternIndex = 0;
			String lastPattern = "";

			for (int j=i; j<auxPatternFound.length(); j++) {
				if (Character.toString(auxPatternFound.charAt(j)).equals(Character.toString(pattern.charAt(patternIndex)))) {
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
		
		return bestLastPattern;
	}
	
	/**
	 * Return the best children
	 */
	private SuffixTreeNode getElement(Collection<SuffixTreeNode> children, String element) {
		for (SuffixTreeNode ch : children) {
			String[] labels = ch.incomingEdge.label.split(", ");
			if (labels[0].equals(element)) {
				return ch;
			}
		}	
			
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
