package A2;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class binaryTree<T extends Comparable<? super T>>{

	public Node<T> root; // root variable
	public int size;

	public binaryTree() //tree constructor 
	{
		this.root = null;
		this.size = 0;
	}
	
	/*Inserts the nodes into the tree taking into account the indices of the left and right children*/
	public boolean insert(int index, int leftIndex, int rightIndex,	String name) {
		/* Insert the nodes into the tree in the order they are given.
		System.out.println("node: " + index + leftIndex + rightIndex + name);*/
		Node<T> newNode = new Node<T>(index, leftIndex, rightIndex, name);
		/*If this is the first node encountered and the root is null, make it the root.*/
		if (newNode.index == 1 && this.root == null){ 
			root = newNode;
			size++;
			return true;
		}
		else if(newNode.index > 1){
			/* We need to scan the tree and find the potential place for it 
			 to be inserted into the tree. */
			int newIndex = newNode.index;
			Node<T> newParent = this.treeTraverser(newNode, this.root);
			if (newParent != null)
			{
				if(newParent.leftIndex == newIndex)
					newParent.leftChild = newNode;
				else if (newParent.rightIndex == newIndex) 
					newParent.rightChild = newNode;
				newNode.parent = newParent;
				size++;
				return true;
			}
			System.out.println("Node could not be added.");
			return false;
		}
		inorder(root);
		System.out.println("/n");
		return false;
	}
	
	public Node<T> treeTraverser(Node<T> newNode, Node<T> current){
		/* Traverses the tree to find where we should insert the new node */
		Node<T> nodeTemp = null;

		if (newNode.index == current.leftIndex && (current.leftChild == null))
			return current;
		if (newNode.index == current.rightIndex && current.rightChild == null)
			return current;
		if (current.leftChild != null)
			nodeTemp = treeTraverser(newNode, current.leftChild);
		if (current.rightChild != null)
			nodeTemp = treeTraverser(newNode, current.rightChild);
		if (nodeTemp != null)
			return nodeTemp;
		
		return null;
		
	}
	public void inorder(Node<T> current) 
	{
		if(current != null) 
		{
			inorder(current.leftChild);
			System.out.print(current.name + " ");
			inorder(current.rightChild);
		}
	}  
	 
	public boolean search(String name) {
			if(nodeSearch(name, root) != null)
				return true;
			else 
				return false;
	}

	public boolean insertBST(String name) {
		/* Insert with the binary search property
		 * currently doesn't take into account if the new node exists */
		
		Node<T> newNode = new Node<T>(size, 0, 0, name);
		if (root == null)
		{
			root = newNode;
			size++;
			return true;
		} else if(this.binaryTreeTraverser(newNode, root))
			return false;
		else
		{
			Node<T> newParent = this.findLeaf(newNode, root);
			if (newParent.name.compareTo(newNode.name)> 0){
				newParent.leftChild = newNode;
				newParent.leftIndex = newNode.index;
			}
			else {
				newParent.rightChild = newNode;
				newParent.rightIndex = newNode.index;
			}
			newNode.parent = newParent;			
			size++;
			return true;
		}
	}

	public boolean isBinarySearch(Node<T> newNode) {
		/* If the tree is empty, then it must be binary search*/
		if (newNode.leftChild == null && newNode.rightChild == null)
			return true;
		boolean treeLeft = true;
		boolean treeRight = true;
		if (newNode.leftChild != null)
		{
			if(newNode.name.compareTo(newNode.leftChild.name) < 0)
				return false;
			treeLeft = this.isBinarySearch(newNode.leftChild);
		}
		if (newNode.rightChild != null){
			if (newNode.name.compareTo(newNode.rightChild.name) > 0)
				return false;
			treeRight = this.isBinarySearch(newNode.rightChild);
		}
		/*If the left and right side of the tree follow the binary search property
		 * then the tree is binary search and we return true, else we return false*/
		if (treeRight && treeLeft)
			return true;
		else{
			System.out.println("Tree is not Binary Search");
			return false;
		}
	}

	public boolean binaryTreeTraverser(Node<T> newNode, Node<T> current){
		if(this.root != null && newNode != null){
			if (current.name.compareTo(newNode.name) == 0)
				return true;
			else if (current.name.compareTo(newNode.name) > 0)
			{
				if (current.leftChild != null)
					return binaryTreeTraverser(newNode, current.leftChild);
				else
					return false;
			}
			else if (current.name.compareTo(newNode.name) < 0)
			{
				if (current.rightChild != null)
					return binaryTreeTraverser(newNode, current.rightChild);
				else
					return false;
			}
			else
				return false;
		}
		return false;
	}
	public boolean delete(String name) {
		if (this.root == null)
			return false;
		Node<T> removeNode = this.nodeSearch(name, root);
		/* If the node exists in the list, then it can be removed*/
		if(removeNode != null )
		{	/* If the node has no children, set it to null*/
			if (removeNode.leftChild == null && removeNode.rightChild == null){
				if(removeNode.parent.leftChild == removeNode)
					removeNode.parent.leftChild = null;
				if(removeNode.parent.rightChild == removeNode)
					removeNode.parent.rightChild = null;
				removeNode = null;
			} /*If the node has only the left child*/
			else if(removeNode.leftChild != null && removeNode.rightChild == null)
			{
				removeNode.leftChild.parent = removeNode.parent;
				removeNode.parent.leftChild = removeNode.leftChild;
				removeNode = null;
			}/* If the node has only the right child*/
			else if (removeNode.leftChild == null && removeNode.rightChild != null)
			{
				removeNode.rightChild.parent = removeNode.parent;
				removeNode.parent.rightChild = removeNode.rightChild;
				removeNode = null;
			}
			else  /*If the node has BOTH children*/
			{
				Node<T> tempNode = removeNode.leftChild;
				while (tempNode.rightChild != null)
					tempNode = tempNode.rightChild;
				tempNode.parent.rightChild = null;
				removeNode.leftChild.parent = tempNode;
				removeNode.rightChild.parent = tempNode;
				tempNode.rightChild = removeNode.rightChild;
				tempNode.leftChild = removeNode.leftChild;
				tempNode.parent = removeNode.parent;
				if (removeNode.parent.leftChild.name == name)
					removeNode.parent.leftChild = tempNode;
				else
					removeNode.parent.rightChild = tempNode;
			}
			size--;
			return true;
		}
		return false;
	}
	
	public Node<T> nodeSearch(String nodeName, Node<T> current){	
			if (current.name.compareTo(nodeName) == 0)
				return current;
			/* If the name matches the one we want to delete return the node*/
			else if (current.name.compareTo(nodeName) > 0)
			{
				return this.nodeSearch(nodeName, current.leftChild);
			}
			else if (current.name.compareTo(nodeName) < 0)
			{	 
				if (current.rightChild != null)
					return this.nodeSearch(nodeName, current.rightChild);
				else 
					return null;
			}
			else 
				return null;
	}
	public Node<T> findLeaf(Node<T> newNode, Node<T> current){
		if (current.name.compareTo(newNode.name) > 0){
		/* If the current name is less than the new nodes name, 
		 * then we need to insert the new node to the left of the current node.*/
			if (current.leftChild != null)
				return current;
			else
				return findLeaf(newNode, current.leftChild);
		}
		else if (current.name.compareTo(newNode.name) < 0){
			if(current.rightChild != null)
				return current;
			else 
				return findLeaf(newNode, current.rightChild);
		}else {
			return null;
		}
	}
	// internal node class begins here
	
	public void topDown()
	{
		System.out.print("Top-Down [ ");
		Queue<Node<T>> q = new ArrayBlockingQueue<Node<T>>(size);
		Node<T> element;
		if (root != null)
		{
			q.add(root);
			while (!q.isEmpty())
			{
				element = q.remove();
				System.out.print(element.name + " ");
				
				if (element != null)
				{
					if (element.leftChild != null)
						q.add(element.leftChild);
					if (element.rightChild != null)
						q.add(element.rightChild);
				}
			}
		}
		System.out.println("]");
		
	}
	public class Node<T extends Comparable<? super T>>{
		int size = 0;
		private Node<T> leftChild, rightChild, parent;
		private String name;
		private int index, leftIndex, rightIndex;
		
		public Node(int index, int left, int right, String name){
			this.index = index;
			this.leftIndex = left;
			this.rightIndex = right;
			this.name = name;
		}
		public Node<T> setRight(Node<T> right){
			return this.rightChild = right;
		}
		public Node<T> setLeft(Node<T> left){
			return this.leftChild = left;
		}
		public Node<T> setParent(Node<T> parent){
			return this.parent = parent;
		}
		public boolean hasLeft(Node<T> node){
			return (this.leftChild != null);
		}
		public boolean hasRight(Node<T> node){
			return (this.rightChild != null);
		}
		// Accessor methods
	    public Node<T> getLeft(Node<T> node){
	    	return this.leftChild;
	    }
	    public Node<T> getRight(Node<T> node){
	    	return this.rightChild;
	    }
	    public Node<T> getParent(Node<T> node){
	    	return this.parent;
	    }
	    public String getName(Node<T> node){
	    	return this.name;
	    }
	    public int getIndex(Node<T> node){
	    	return this.index;
	    }
	    public int getLCIndex(Node<T> node){
	    	return this.leftIndex;
	    }
	    public int getRCIndex(Node<T> node){
	    	return this.rightIndex;
	    }
	}// End of internal node class
}
