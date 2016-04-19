package dataStructures;

public class HashMap<K,V>{
	private NumberedList<V> list = new NumberedList<V>(-100000,100000);
	
	public void put(K key, V obj){
		list.put(key.hashCode()%list.max(), obj);
	}
	public ArrayList<V> getArrayList(){
		return list.getArrayList();
	}
	public V get(K key){
		ArrayList<V> a = list.get(key.hashCode()%list.max());
		if(a.size() == 0)
			return null;
		else
			return a.get(0);
	}
	public int size(){
		return list.size();
	}
	public String toString(){
		return "" + list.getArrayList();
	}
}
