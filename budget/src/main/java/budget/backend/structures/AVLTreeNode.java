package budget.backend.structures;
import budget.backend.structures.BinaryTreeNode;

public class AVLTreeNode<K,V> extends BinaryTreeNode<K,V>{

	//Additional fields that differ from the thisclass

	/**Height of the sub-tree whose root is this node*/
	private int heightFrom;
	private K key;
	private V value;
	private AVLTreeNode<K,V> parent;
	private AVLTreeNode<K,V> left;
	private AVLTreeNode<K,V> right;

	/**
	 * initialise empty node that is used when figuring out the balanceFactor of the parent
	 */
	public AVLTreeNode(){
		this.heightFrom = -1;
		this.parent = null;
		this.left = null;
		this.right = null;
		this.key = null;
		this.value = null;
	}

	//Key-only constructors

	/**
	 * By default, all pointers are null, when setting up a binary tree.
	 * Used to define a node with undefined relatives
	 * This constructor is to be used for Key-only trees
	 * @param key
	 */
	public AVLTreeNode(K root){
		this.key = root;
		this.value = null;
		this.left = null;
		this.right = null;
		this.parent = null;
		this.heightFrom = 0;
	}

	/**
	 * When inserting to a specific position of the tree, the parent is known.
	 * This method is to be used for Key-only trees
	 * @param parent a {@link BinaryTreeNode}
	 * @param key
	 */
	public AVLTreeNode(AVLTreeNode<K,V> parent, K key){
		this.key = key;
		this.value = null;
		this.left = null;
		this.right = null;
		this.parent = parent;
		this.heightFrom = 0;
	}

	//Key-Value constructors

	/**
	 * By default, all pointers are null, when setting up a binary tree.
	 * Used to define a node with undefined relatives
	 * This constructor is to be used for Key-only trees
	 * @param rootK the key of this object
	 * @param rootV the value of this object
	 */
	public AVLTreeNode(K rootK, V rootV){
		this.key = rootK;
		this.value = rootV;
		this.left = null;
		this.right = null;
		this.parent = null;
		this.heightFrom = 0;
	}

	/**
	 * When inserting to a specific position of the tree, the parent is known.
	 * This method is to be used for Key-Value trees
	 * @param parent a {@link BinaryTreeNode}
	 * @param key
	 * @param value
	 */
	public AVLTreeNode(AVLTreeNode<K,V> parent, K key, V value){
		this.key = key;
		this.value = value;
		this.left = null;
		this.right = null;
		this.parent = parent;
		this.heightFrom = 0;
	}

	//Additional methods

	/**
	 * @return the height of the subtree rooted in this node
	 */
	public int getSubtreeHeight(){
		return this.heightFrom;
	}

	/**
	 * Increment the height of the subTree by the given integer
	 * @param byInt
	 * @return true of the incrementation was successfull
	 */
	public boolean incrSubtreeHeight(int byInt){
	  heightFrom += byInt;
		return true;
	}

	/**
	 * Set the height of the subtree. 
	 * Only values >= 0 are permitted 
	 * @param val
	 * @return true if the value was >= 0
	 * @return false otherwise
	 */
	public boolean setSubtreeHeight(int val){
		if (val >= 0){
			heightFrom = val;
			return true;
		}
		return false;
	}

	/**
	 * In a balanced AVL tree,
	 * -1 <= h(leftChild)-h(rightChild) <= 1
	 * if this value exceeds its boundaries to either direction, rebalaning is needed
	 */
	public int balanceFactor(){
		int bal = (this.getLeft()).getSubtreeHeight();
		bal -= (this.getRight()).getSubtreeHeight();
		return bal;
		
	}

	//superclass methods

	/**
	 * @return key of the node
	 */
	public K getKey(){
		return this.key;
	}

	/**
	 * @return value of the node
	 */
	public V getValue(){
		return this.value;
	}

	/**
	 * @return parent of the node
	 */
	public AVLTreeNode<K,V> getParent(){
		return this.parent;
	}

	/**
	 * @return left child of the node
	 */
	public AVLTreeNode<K,V> getLeft(){
		return this.left;
	}

	/**
	 * @return right child of the node
	 */
	public AVLTreeNode<K,V> getRight(){
		return this.right;
	}

	/**
	 * @return true if the node is the left child of its parent (if exists)
	 * @return false if the node is either the right child or the root
	 */
	public boolean isLeft(){
		if ((this.parent != null) && (this.equals(parent.getLeft())))
			return true;
		return false;
	}

	/**
	 * @return true if there is at least one undefined child of the node.
	 * @return false otherwise
	 */
	public boolean isLeaf(){
		return (((this.getLeft()).isEmpty()) || ((this.getRight()).isEmpty()));
	}

	/**
	 * @return whether the node has a key or not
	 */
	public boolean isEmpty(){
		return (key == null);
	}

	/**
	 * Set the parent of the node
	 * @param parent ; a {@link BinaryTreeNode} object
	 */
	public void setParent(AVLTreeNode<K,V> parent){
		this.parent = parent;
	}

	/**
	 * Set the left child of the node
	 * @param left a {@link BinaryTreeNode} object
	 */
	public void setLeft(AVLTreeNode<K,V> left){
		this.left = left;
	}

	/**
	 * Set the right child of the node
	 * @param right ; a {@link BinaryTreeNode} object
	 */
	public void setRight(AVLTreeNode<K,V> right){
		this.right = right;
	}

	/**
	 * Set the key of the node
	 * @param key
	 */
	public void setKey(K key){
		 this.key = key;
	 }

	/**
	 * set the value of the object
	 * @param value the new value
	 */
	public void setValue(V value){
		this.value = value;
	}

}
