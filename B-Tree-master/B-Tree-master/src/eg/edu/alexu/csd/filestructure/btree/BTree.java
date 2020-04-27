package eg.edu.alexu.csd.filestructure.btree;

import eg.edu.alexu.csd.filestructure.btree.BNode.point;

import java.util.ArrayList;
import java.util.List;

public class BTree<K extends Comparable<K>, V> implements IBTree <K,V> {
	private BNode root=null;
	private int Mindegree;

	public BTree( int mindegree) {
		this.Mindegree = mindegree;
	}

	@Override
	public int getMinimumDegree() {
		return Mindegree;
	}

	@Override
	public IBTreeNode getRoot() {
		return root;
	}

	public void nonfullInsert(BNode x, Comparable key) {
		int i=x.getNumOfKeys();
		if(x.isLeaf())
		{
			List<K> keys=new ArrayList<K>();
			keys=x.getKeys();
			while(i >= 1 && key.compareTo(keys.get(i-1))<0 )//here find spot to put key.
			{
				keys.set(i,keys.get(i-1));
				i--;
			}

			System.out.println(keys);
			keys.add(0, (K) key);
			System.out.println(keys);

			if(x.getNumOfKeys()==0)
			    keys.add(i, (K) key);
			else
				keys.set(i, (K) key);

			x.setKeys(keys);
		}
		else
		{
			int j = 0;
			List<K> keys=new ArrayList<K>();
			keys=x.getKeys();
			while(j < x.getNumOfKeys()  &&key.compareTo(keys.get(j))>0  )//find spot to recurse
			{			             //on correct child
				j++;
			}

			//	i++;
			List<BNode<K, V>>childx=new ArrayList<BNode<K, V>>();
			childx=x.getChildren();
			if( childx.get(j).getNumOfKeys() == Mindegree*2 - 1)
			{
				split(x,j,childx.get(j));//call split on node x's ith child
				List<K> keysx=new ArrayList<K>();
				keysx=x.getKeys();
				if(key.compareTo(keysx.get(j)) > 0)
				{
					j++;
				}
			}

			nonfullInsert(childx.get(j),key);//recurse
		}

	}

	@Override
	public void insert(Comparable key, Object value) {

		BNode root= (BNode) getRoot();
		if(root==null){
			this.root =new BNode(Mindegree*2,null);
			List<K> keys=new ArrayList<K>();
			keys.add((K) key);
			this.root.setKeys(keys);
			return;
		}

		if(root.getNumOfKeys()==Mindegree*2-1){
			BNode s=new BNode(Mindegree*2,null);
			this.root=s;
			s.setLeaf(false);
			s.setNumOfKeys(0);

			s.children[0]=root;
			split(s,0,root);
			nonfullInsert(s, key); //call insert method
		}
		else
			nonfullInsert(root,key);//if its not full just insert it
		}




	public void split(BNode x, int i, BNode y)
	{
		BNode z = new BNode(Mindegree*2,null);
		z.setLeaf(y.isLeaf());
		z.setNumOfKeys(Mindegree-1);
		List<K> keys=new ArrayList<K>();
		keys=y.getKeys();
		for(int j = 0; j < Mindegree - 1; j++)
		{
			keys.remove(j+Mindegree);
		}
			z.setKeys(keys);

		if(!y.isLeaf())//if not leaf we have to reassign child nodes.
		{
			List<IBTreeNode<K, V>>child=new ArrayList<IBTreeNode<K, V>>();
			child=y.getChildren();
			for(int k = 0; k < Mindegree; k++)
			{
				child.remove(k+Mindegree);
//				z.child[k] = y.child[k+order]; //reassing child of y
			}
			z.setChildren(child);

		}
		y.setNumOfKeys(Mindegree-1);


		List<IBTreeNode<K, V>>childx=new ArrayList<IBTreeNode<K, V>>();
		childx=x.getChildren();
		for(int j = x.getNumOfKeys() ; j> i ; j--)
		{
			childx.set(j+1,childx.get(j));
//			x.child[j+1] = x.child[j]; //shift children of x
		}
		childx.set(i+1,z);
		x.setChildren(childx);
		List<K> keysx=new ArrayList<K>();
		keysx=x.getKeys();

		for(int j = x.getNumOfKeys(); j> i; j--)
		{
			keysx.set(j+1,keysx.get(j));
		}
		List<K> keysy=new ArrayList<K>();
		keysy=y.getKeys();
		keysx.set(i,keys.get(Mindegree-1));
		keysy.set(Mindegree-1,null);
		keysy.remove(Mindegree-1);

		for(int j = 0; j < Mindegree - 1; j++)
		{
			keysy.remove(Mindegree+j);
		}
		y.setKeys(keysy);
		x.setNumOfKeys(x.getNumOfKeys()+1);
	}

	@Override
	public Object search(Comparable key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Comparable key) {
		// TODO Auto-generated method stub
		return false;
	}

}
