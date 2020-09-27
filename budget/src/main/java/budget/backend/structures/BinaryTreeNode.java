package budget.backend.structures;

/**
 * Implementation of a node in a binary tree. Consists of a key of any Comparable type object and three pointers to other nodes: Parent, Left, Right
 * Supports two types of trees:
 * Key-only, where there is only one value that needs to be stored, and values remain undefined
 * Key-Value trees, where the keys are pointers to other objects to other objects
 */
public class BinaryTreeNode<K,V>{

	/**Left child of the node*/
	protected BinaryTreeNode<K,V> left;
	/**Right child of the node*/
	protected BinaryTreeNode<K,V> right;
	/**Parent of the node*/
	protected BinaryTreeNode<K,V> parent;
	/**Value of the node*/
	protected K key;
	protected V value;

	//Key-only constructors

	public BinaryTreeNode() throws NullPointerException{
		this.left = null;
		this.right = null;
		this.parent = null;
		this.key = null;
		this.value = null;
	}

	/**
	 * By default, all pointers are null, when setting up a binary tree.
	 * Used to define a node with undefined relatives
	 * This constructor is to be used for Key-only trees
	 * @param key
	 */
  public BinaryTreeNode(K root) throws NullPointerException{
		if (root == null) 
			throw new NullPointerException("Null is not allowed as key!");
		else{
			this.key = root;
			this.parent = null;
			this.left = null;
			this.right = null;
			this.value = null;
		}
  }

	/**
	 * When inserting to a specific position of the tree, the parent is known.
	 * This method is to be used for Key-only trees
	 * 
	 * does not throw null pointer exception, because it is used to insert to a specific position. Based on the implementation, this null-key has to be avoided.
	 * @param parent a {@link BinaryTreeNode}
	 * @param key
	 */
  public BinaryTreeNode(BinaryTreeNode<K,V> parent, K key) {
		this.key = key;
		this.parent = parent;
		this.left = null;
		this.right = null;
		this.value = null;
  }

	//Key-Value constructors

	/**
	 * By default, all pointers are null, when setting up a binary tree.
	 * Used to define a node with undefined relatives
	 * This constructor is to be used for Key-only trees
	 * @param rootK the key of this object
	 * @param rootV the value of this object
	 */
  public BinaryTreeNode(K rootK, V rootV) throws NullPointerException{
		if (rootK == null) 
			throw new NullPointerException("Null is not allowed as key!");
		else{
			this.key = rootK;
			this.parent = null;
			this.left = null;
			this.right = null;
			this.value = rootV;
		}
  }

	/**
	 * When inserting to a specific position of the tree, the parent is known.
	 * This method is to be used for Key-Value trees
	 * 
	 * does not throw null pointer exception, because it is used to insert to a specific position. Based on the implementation, this null-key has to be avoided.
	 * @param parent a {@link BinaryTreeNode}
	 * @param key
	 * @param value
	 */
  public BinaryTreeNode(BinaryTreeNode<K,V> parent, K key, V value){
		this.key = key;
		this.parent = parent;
		this.left = null;
		this.right = null;
		this.value = value;		
  }

	//Additional methods

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
	public BinaryTreeNode<K,V> getParent(){
	  return this.parent;
	}

	/**
	 * @return left child of the node
	 */
	public BinaryTreeNode<K,V> getLeft(){
	  return this.left;
	}

	/**
	 * @return right child of the node
	 */
	public BinaryTreeNode<K,V> getRight(){
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
	  return ((this.getLeft() == null) || (this.getRight() == null));
	}

	/**
	 * Set the parent of the node
	 * @param parent ; a {@link BinaryTreeNode} object
	 */
	public void setParent(BinaryTreeNode<K,V> parent){
	  this.parent = parent;
	}

	/**
	 * Set the left child of the node
	 * @param left a {@link BinaryTreeNode} object
	 */
	public void setLeft(BinaryTreeNode<K,V> left){
	  this.left = left;
	}

	/**
	 * Set the right child of the node
	 * @param right ; a {@link BinaryTreeNode} object
	 */
	public void setRight(BinaryTreeNode<K,V> right){
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
