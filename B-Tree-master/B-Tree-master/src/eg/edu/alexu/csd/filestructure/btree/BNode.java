package eg.edu.alexu.csd.filestructure.btree;
import java.util.ArrayList;
import java.util.List;

public class BNode <K extends Comparable<K>, V> implements IBTreeNode<K, V> {
	
	public class point <K extends Comparable<K>, V>{
		public K key;
		public V value;
	}
	
	private boolean leaf;
	private int keysN;
	private int m;
	public BNode [] children;
	public BNode parent;
	public point[] data;
//	public ArrayList <K> keys;
//	public ArrayList<V> values;
	
	
	
	//constructor
	public BNode (int numberOfChildren,BNode Parent) {
		keysN=0;
		m=numberOfChildren;
		leaf=true;
		children=new BNode [m];
		data=new point[m-1];
//		keys=new ArrayList<K>();
//		values=new ArrayList<V>(m-1);
		this.parent=Parent;
	}
	@Override
	public int getNumOfKeys() {
		return keysN;
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		this.keysN=numOfKeys;
	}

	@Override
	public boolean isLeaf() {
		return leaf;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
		this.leaf=isLeaf;
	}

	@Override
	public List<K> getKeys() {
		if(keysN==0)
		return null;
		List<K>ans=new ArrayList<K>();
		for(int i=0;i<keysN;i++) {
			ans.add((K) data[i].key);
		}
		return ans;
	}

	@Override
	public void setKeys(List<K> keys) {
		if(keys.size()<m) {
			for(int i=0;i<keys.size();i++) {
				 K key=keys.get(i);
				data[i].key=key;
			}
		}
		
	}

	@Override
	public List<V> getValues() {
		if(keysN==0)
			return null;
			List<V>ans=new ArrayList<V>();
			for(int i=0;i<keysN;i++) {
				ans.add( (V) data[i].value);
			}
			return ans;
	}

	@Override
	public void setValues(List<V> values) {
		if(values.size()<m) {
			for(int i=0;i<values.size();i++) {
				data[i].value=values.get(i);
			}
		}
		
	}

	@Override
	public List<IBTreeNode<K, V>> getChildren() {
		if(keysN==0)
			return null;
			List<IBTreeNode<K, V>>ans=new ArrayList<IBTreeNode<K, V>>();
			for(int i=0;i<keysN;i++) {
				ans.add(children[i]);
			}
			return ans;
	}

	@Override
	public void setChildren(List<IBTreeNode<K, V>> children) {
		if(children.size()<=m) {
			for(int i=0;i<children.size();i++) {
				this.children[i]=(BNode) children.get(i);
			}
		}		
	}

}
