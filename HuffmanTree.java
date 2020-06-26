import java.util.*;

public class HuffmanTree {
	TreeNode root;
	HashMap<String, String> code = new HashMap<>();
	
	HuffmanTree() {
		root = null;
	}
	
	public void constructHuffmanTree(ArrayList<String> characters, ArrayList<Integer> freq) {
		
		// Make Priority Queue
		PriorityQueue<TreeNode> charQ = new PriorityQueue<TreeNode>(freq.size(), 
				new Comparator<TreeNode>() {
		      @Override public int compare(TreeNode a, TreeNode b) {
		        return Integer.compare(a.getFrequency(), b.getFrequency());
		      }
		}); // Prioritizes lower frequency
		

		// Populate priority Queue
		for( int i = 0; i < freq.size(); i++) {
			TreeNode node = new TreeNode();		// Initialize new Node
			node.setValue(characters.get(i));
			node.setFrequency(freq.get(i));
			
			charQ.add(node);
		}
		
		// Assemble tree
		while(charQ.size() > 1) {
			TreeNode L = charQ.poll();	// Discharge nodes
			TreeNode R = charQ.poll();
			
			TreeNode nextNode = new TreeNode();	// Create a parent node
			nextNode.setFrequency(L.getFrequency() + R.getFrequency());
			nextNode.setLeftChild(L); nextNode.setRightChild(R);
			L.setParent(nextNode); R.setParent(nextNode);
			
			root = nextNode;
			
			charQ.add(nextNode);
		}
	
		codeMap(root, code, "");
		
	}
	
	public String encode(String humanMessage) {
		
		String codedMessage = ""; 
		
		// Iterate through the message 
		for (int i = 0; i < humanMessage.length(); i++) {
			char c = humanMessage.charAt(i);	// Iterate by character
			
			codedMessage = codedMessage + code.get(String.valueOf(c));	// Access value from codex and add to string
		}
		
		
	    return codedMessage;
	}
	
	public String decode(String encodedMessage) {
        String decodedMessage = "";
		
		// Iterate through the message 
		for(int i = 0; i < encodedMessage.length(); i++) {
			char c;
			
			// For each character, traverse the tree until you find a leaf (letter)
			TreeNode currNode = root;
			TreeNode L = currNode.getLeftChild();
			TreeNode R = currNode.getRightChild();
			int j = i;
			while(currNode != null && j < encodedMessage.length()) {
				c = encodedMessage.charAt(j);
				
				// If not a leaf, continue down the tree
				if(L != null || R != null) {
					if(c == '0') {
						currNode = currNode.getLeftChild();
					}
					
					else if(c == '1') {
						currNode = currNode.getRightChild();
					}
				}
				
				// Have we reached a leaf?
				L = currNode.getLeftChild();
				R = currNode.getRightChild();
				if(L == null && R == null) {
					decodedMessage += currNode.getValue();	// Add decoded character to return message
					i = j;
					break;
				}
				
				j++;		// Move down the encoded string
			}
			
			
		}
		
		return decodedMessage;
	}
	
	// Assembles the map
	private void codeMap(TreeNode root, HashMap<String, String> code, String bin) {
		// Base case, root is a leaf
		TreeNode L = root.getLeftChild();
		TreeNode R = root.getRightChild();
		if(L == null && R == null) {
			code.put(root.getValue(), bin);	// Assign code
			return;
		}
			
		// Recursive Left Side
		codeMap(L, code, bin + "0");	// Leftwards adds a 0
		
		// Recursive Right Side
		codeMap(R, code, bin + "1");		// Rightward adds a 1
		
	}
	
	
}
