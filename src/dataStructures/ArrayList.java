package dataStructures;

//dataStructures/Stores a dynamic amount of data using a combination of recursion and arrays
public class ArrayList<T> {
	private ArrayList<T> sublist;
	public Object[] objects;
	private int length = 0;
	public ArrayList(){
		objects = new Object[100];
	}
	public ArrayList(int size){
		objects = new Object[size];
	}
	public void add(T o){
		if(length < objects.length)
			objects[length] = o;
		else{
			if(sublist == null)
				sublist = new ArrayList<T>();
			sublist.add(o);
		}
		length++;
	}
	public void add(ArrayList<T> a){
		for(int i = 0; i < a.size(); i++){
			this.add(a.get(i));
		}
	}
	@SuppressWarnings("unchecked")
	public T get(int index){
		if(index < objects.length)
			return (T)objects[index];
		else
			return sublist.get(index-objects.length);
	}
	public int size(){
		return length;
	}
	public String toString(){
		String str = "[";
		for(int i = 0; i < size(); i++){
			str += this.get(i) + ", ";
		}
		str += "]";
		return str;
	}
	public void addAll(ArrayList<T> list){
		for(int i = 0; i < list.size(); i++){
			this.add(list.get(i));
		}
	}
	//This just sets the index to null
	public void remove(int index){
		if(index < objects.length)
			objects[index] = null;
		else
			sublist.remove(index-objects.length);
	}
}
